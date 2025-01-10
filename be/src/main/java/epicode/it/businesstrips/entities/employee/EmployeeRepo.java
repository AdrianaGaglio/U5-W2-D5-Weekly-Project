package epicode.it.businesstrips.entities.employee;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {

    public boolean existsByEmail(String email);

    public boolean existsByUsername(String username);
}
