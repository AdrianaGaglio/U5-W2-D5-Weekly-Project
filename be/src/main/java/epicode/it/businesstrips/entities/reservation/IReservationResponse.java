package epicode.it.businesstrips.entities.reservation;

import epicode.it.businesstrips.entities.employee.Employee;
import epicode.it.businesstrips.entities.preference.Preference;
import epicode.it.businesstrips.entities.trip.Trip;

import java.time.LocalDate;
import java.util.List;

public interface IReservationResponse {
    Long getId();
    Employee getEmployee();
    LocalDate getRequestDate();
    List<Preference> getPreferences();
    Trip getTrip();
}
