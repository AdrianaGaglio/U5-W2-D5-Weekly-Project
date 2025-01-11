package epicode.it.businesstrips.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class upload {
    @Configuration
    public static class CloudinaryConfig {

        @Value("${cloudinary.cloud-name}")
        private String cloudName;

        @Value("${cloudinary.api-key}")
        private String apiKey;

        @Value("${cloudinary.api-secret}")
        private String apiSecret;

        @Bean
        public Cloudinary getCloudinary() {
            Map<String, String> config = new HashMap<>();
            config.put("cloud_name", cloudName);
            config.put("api_key", apiKey);
            config.put("api_secret", apiSecret);
            return new Cloudinary(config);
        }
    }

    @Service
    public static class UploadSvc {

        @Autowired
        private Cloudinary cloudinary;

        public String uploadFile(MultipartFile file) {
            Map uploadResult = null;
            try {
                uploadResult = cloudinary.uploader().upload(file.getBytes(),
                        ObjectUtils.asMap("folder", "businesstrips", "resource_type", "image", "public_id", file.getOriginalFilename()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return uploadResult.get("url").toString(); // Restituisce l'URL del file caricato
        }
    }

    @RestController
    @RequestMapping("/upload")
    @RequiredArgsConstructor
    public static class UploadController {
        private final UploadSvc uploadSvc;

        @PostMapping(consumes = "multipart/form-data")
        public ResponseEntity<String> uploadFile(MultipartFile file) throws Exception {
            return new ResponseEntity<>(uploadSvc.uploadFile(file), HttpStatus.CREATED);
        }
    }
}
