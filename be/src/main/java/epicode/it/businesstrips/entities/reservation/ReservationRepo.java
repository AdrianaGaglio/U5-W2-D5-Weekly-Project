package epicode.it.businesstrips.entities.reservation;

import epicode.it.businesstrips.entities.employee.Employee;
import epicode.it.businesstrips.entities.trip.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepo extends JpaRepository<Reservation, Long> {

    @Query("""
    SELECT r.id AS id, 
           r.employee AS employee, 
           r.requestDate AS requestDate, 
           r.preferences AS preferences, 
           r.trip AS trip
    FROM Reservation r
""")
    List<IReservationResponse> findAllReservations();


    @Query("""
       SELECT r
       FROM Reservation r
       WHERE r.trip = :trip
       """)
    List<IReservationResponse> findFirstByTripIReservationResponse(@Param("trip") Trip trip);

    public Reservation findFirstByTripAndEmployee(Trip trip, Employee employee);

    @Query("""
    SELECT r.id AS id, 
           r.employee AS employee, 
           r.requestDate AS requestDate, 
           r.preferences AS preferences, 
           r.trip AS trip
    FROM Reservation r
    WHERE r.employee = :employee
    ORDER BY r.requestDate DESC
""")
    List<IReservationResponse> findByEmployeeOrderByRequestDateDesc(@Param("employee") Employee employee);


    public Reservation findFirstById(Long id);

    @Query("""
        SELECT DISTINCT r.id AS id, 
               r.employee AS employee, 
               r.requestDate AS requestDate, 
               r.preferences AS preferences, 
               r.trip AS trip
        FROM Reservation r
        WHERE r.id = :reservationId
    """)
    IReservationResponse findFirstReservationResponseById(@Param("reservationId") Long reservationId);
}
