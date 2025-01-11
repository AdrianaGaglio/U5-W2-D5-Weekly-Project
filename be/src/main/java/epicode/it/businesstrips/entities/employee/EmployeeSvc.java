package epicode.it.businesstrips.entities.employee;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class EmployeeSvc {
    private final EmployeeRepo employeeRepo;

    public List<Employee> getAll() {
        return employeeRepo.findAll();
    }

    public Page<Employee> getAllPageable(Pageable pageable) {
        return employeeRepo.findAll(pageable);
    }

    public Employee getById(Long id) {
        return employeeRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Employee not found"));
    }

    public int count() {
        return (int) employeeRepo.count();
    }

    public String delete(Long id) {
        Employee e = getById(id);
        employeeRepo.delete(e);
        return "Employee deleted successfully";
    }

    public String delete(Employee e) {
        Employee foundEmployee = getById(e.getId());
        employeeRepo.delete(foundEmployee);
        return "Employee deleted successfully";
    }

    public Employee create(@Valid EmployeeCreateRequest request) {
        if (employeeRepo.existsByEmail(request.getEmail().toLowerCase())) {
            throw new EntityExistsException("Email already exists");
        }

        if (employeeRepo.existsByUsername(request.getUsername().toLowerCase())) {
            throw new EntityExistsException("Username already exists");
        }

        Employee e = new Employee();
        BeanUtils.copyProperties(request, e);
        return employeeRepo.save(e);
    }

    public Employee update(Long id, @Valid EmployeeUpdateRequest request) {
        Employee e = getById(id);

        Employee foundE = employeeRepo.findFirstByEmail(request.getEmail().toLowerCase());
        boolean emailOk = foundE == null || !foundE.getId().equals(request.getId());
        if (request.getEmail() != null && employeeRepo.existsByEmail(request.getEmail().toLowerCase()) && emailOk)
            throw new EntityExistsException("Email already exists");
        e.setEmail(request.getEmail() != null ? request.getEmail().toLowerCase() : e.getEmail());

        Employee foundEn = employeeRepo.findFirstByUsername(request.getUsername().toLowerCase());
        boolean usernameOk = foundEn == null || !foundEn.getId().equals(request.getId());
        if (request.getUsername() != null && employeeRepo.existsByUsername(request.getUsername().toLowerCase()) && usernameOk)
            throw new EntityExistsException("Username already exists");
        e.setUsername(request.getUsername() != null ? request.getUsername().toLowerCase() : e.getUsername());

        e.setFirstName(request.getFirstName() != null ? request.getFirstName() : e.getFirstName());
        e.setLastName(request.getLastName() != null ? request.getLastName() : e.getLastName());

        e.setImage(request.getImage() != null ? request.getImage() : e.getImage());

        return employeeRepo.save(e);
    }
}
