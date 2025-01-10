package epicode.it.businesstrips.entities.employee;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeRunner implements ApplicationRunner {
    private final EmployeeSvc employeeSvc;
    private final Faker faker;
    private final Logger logger;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (employeeSvc.count() == 0) {

            for (int i = 0; i < 100; i++) {

                EmployeeCreateRequest newEmployee = new EmployeeCreateRequest();
                newEmployee.setFirstName(faker.name().firstName());
                newEmployee.setLastName(faker.name().lastName());
                newEmployee.setUsername(newEmployee.getFirstName().toLowerCase() + newEmployee.getLastName().toLowerCase().charAt(0));

                String lastName = newEmployee.getLastName();
                if (lastName.contains(" ") || lastName.contains("'")) {
                    lastName = lastName.replace(" ", "");
                    lastName = lastName.replace("'", "");
                }

                newEmployee.setEmail(newEmployee.getUsername().toLowerCase() + "." + lastName.toLowerCase() + "@mail.com");
                newEmployee.setImage("https://ui-avatars.com/api/?name=" + newEmployee.getFirstName() + "+" + newEmployee.getLastName());

                try {
                    employeeSvc.create(newEmployee);
                } catch (RuntimeException e) {
                    System.out.println("===> " + newEmployee);
                    logger.error(e.getMessage());
                }
            }

        }
    }
}
