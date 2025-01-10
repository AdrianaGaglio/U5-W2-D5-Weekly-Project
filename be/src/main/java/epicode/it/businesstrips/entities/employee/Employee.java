package epicode.it.businesstrips.entities.employee;

import epicode.it.businesstrips.entities.trip.Trip;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(name="first_name" , nullable = false)
    private String firstName;

    @Column(name="last_name" , nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    private String image;
}