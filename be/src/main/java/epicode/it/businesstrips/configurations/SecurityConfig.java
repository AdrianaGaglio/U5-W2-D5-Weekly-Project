package epicode.it.businesstrips.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowCredentials(true);
                    config.addAllowedOrigin("http://localhost:4200"); // Specifica l'origine consentita
                    config.addAllowedHeader("*"); // Consenti tutti gli header
                    config.addAllowedMethod("*"); // Consenti tutti i metodi HTTP
                    return config;
                })
                .and()
                .csrf().disable() // Disabilita CSRF
                .authorizeHttpRequests()
                .anyRequest().permitAll(); // Permetti tutte le richieste (configurazione di base)

        return http.build();
    }
}
