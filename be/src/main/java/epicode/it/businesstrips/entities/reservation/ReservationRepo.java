package epicode.it.businesstrips.entities.reservation;

import epicode.it.businesstrips.entities.employee.Employee;
import epicode.it.businesstrips.entities.trip.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepo extends JpaRepository<Reservation, Long> {

    public Reservation findFirstByTripAndEmployee(Trip trip, Employee employee);

    public List<Reservation> findByEmployeeOrderByRequestDateDesc(Employee employee);


}
