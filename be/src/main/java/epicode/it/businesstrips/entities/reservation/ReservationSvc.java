package epicode.it.businesstrips.entities.reservation;

import epicode.it.businesstrips.entities.employee.Employee;
import epicode.it.businesstrips.entities.employee.EmployeeSvc;
import epicode.it.businesstrips.entities.preference.Preference;
import epicode.it.businesstrips.entities.preference.PreferenceAddRequest;
import epicode.it.businesstrips.entities.preference.PreferenceCreateRequest;
import epicode.it.businesstrips.entities.preference.PreferenceSvc;
import epicode.it.businesstrips.entities.trip.Trip;
import epicode.it.businesstrips.entities.trip.TripSvc;
import epicode.it.businesstrips.exceptions.MaxCapacityException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationSvc {
    private final ReservationRepo reservationRepo;
    private final EmployeeSvc employeeSvc;
    private final TripSvc tripSvc;
    private final PreferenceSvc preferenceSvc;

    public Reservation save(Reservation e) {
        return reservationRepo.save(e);
    }

    public List<Reservation> getAll() {
        return reservationRepo.findAll();
    }

    public List<IReservationResponse> getAllResponse() {
        return reservationRepo.findAllReservations();
    }

    public Page<Reservation> getAllPageable(Pageable pageable) {
        return reservationRepo.findAll(pageable);
    }

    public Reservation getById(Long id) {
        return reservationRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Reservation not found"));
    }

    public int count() {
        return (int) reservationRepo.count();
    }

    public String delete(Long id) {
        Reservation e = getById(id);
        reservationRepo.delete(e);
        return "Reservation deleted successfully";
    }

    public String delete(Reservation e) {
        Reservation foundReservation = getById(e.getId());
        reservationRepo.delete(foundReservation);
        return "Reservation deleted successfully";
    }

    public Reservation getByTripAndEmployee(Long tripId, Long employeeId) {
        return reservationRepo.findFirstByTripAndEmployee(tripSvc.getById(tripId), employeeSvc.getById(employeeId));
    }

    @Transactional
    public IReservationResponse create(ReservationCreateRequest request) {
        System.out.println(request);
        Reservation foundR = getByTripAndEmployee(request.getTripId(), request.getEmployeeId());
        if (foundR != null) throw new EntityExistsException("User already has a reservation for this trip");

        Reservation r = new Reservation();

        r.setRequestDate(request.getRequestDate() != null ? request.getRequestDate() : LocalDate.now());

        Trip t = tripSvc.getById(request.getTripId());
        if (t.getReservations().size() == t.getMaxCapacity()) throw new MaxCapacityException("Trip is full");
        r.setTrip(t);

        Employee e = employeeSvc.getById(request.getEmployeeId());
        r.setEmployee(e);

        if (request.getPreferences() != null && request.getPreferences().size() > 0) {
            for (PreferenceCreateRequest p : request.getPreferences()) {
                if (p.getLocation() == null || p.getLocation().isEmpty()) p.setLocation(t.getDestination());
                Preference newP = preferenceSvc.create(p);
                r.getPreferences().add(newP);
            }
        }

        r=reservationRepo.save(r);
        return reservationRepo.findFirstReservationResponseById(r.getId());
    }

    public List<IReservationResponse> getByEmployee(Long id) {
        Employee e = employeeSvc.getById(id);
        return reservationRepo.findByEmployeeOrderByRequestDateDesc(e);
    }

    @Transactional
    public Reservation addPreference(Long id, @Valid PreferenceAddRequest request) {
        PreferenceCreateRequest pCreate = new PreferenceCreateRequest();
        BeanUtils.copyProperties(request, pCreate);

        Reservation r = getById(id);
        Preference p = preferenceSvc.create(pCreate);
        r.getPreferences().add(p);
        return save(r);
    }

    public List<IReservationResponse> findByTrip(Long id) {
        Trip t = tripSvc.getById(id);
        return reservationRepo.findFirstByTripIReservationResponse(t);
    }
}
