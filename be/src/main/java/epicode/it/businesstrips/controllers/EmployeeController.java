package epicode.it.businesstrips.controllers;

import epicode.it.businesstrips.entities.employee.Employee;
import epicode.it.businesstrips.entities.employee.EmployeeCreateRequest;
import epicode.it.businesstrips.entities.employee.EmployeeSvc;
import epicode.it.businesstrips.entities.employee.EmployeeUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeSvc employeeSvc;

    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        return ResponseEntity.ok(employeeSvc.getAll());
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<Employee>> getAllPageable(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(employeeSvc.getAllPageable(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeSvc.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return new ResponseEntity<>(employeeSvc.delete(id), HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody EmployeeCreateRequest request) {
        return new ResponseEntity<>(employeeSvc.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable Long id, @RequestBody EmployeeUpdateRequest
            request) {
        return new ResponseEntity<>(employeeSvc.update(id, request), HttpStatus.OK);
    }

}