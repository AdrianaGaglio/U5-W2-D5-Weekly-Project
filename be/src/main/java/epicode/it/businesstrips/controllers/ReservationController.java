package epicode.it.businesstrips.controllers;

import epicode.it.businesstrips.entities.employee.Employee;
import epicode.it.businesstrips.entities.preference.PreferenceAddRequest;
import epicode.it.businesstrips.entities.reservation.IReservationResponse;
import epicode.it.businesstrips.entities.reservation.Reservation;
import epicode.it.businesstrips.entities.reservation.ReservationCreateRequest;
import epicode.it.businesstrips.entities.reservation.ReservationSvc;
import epicode.it.businesstrips.entities.trip.Trip;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationSvc reservationSvc;

    @GetMapping
    public ResponseEntity<List<IReservationResponse>> getAll() {
        return ResponseEntity.ok(reservationSvc.getAllResponse());
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<Reservation>> getAllPageable(Pageable pageable) {
        return ResponseEntity.ok(reservationSvc.getAllPageable(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getById(@PathVariable Long id) {
        return ResponseEntity.ok(reservationSvc.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return new ResponseEntity<>(reservationSvc.delete(id), HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<IReservationResponse> create(@RequestBody ReservationCreateRequest request) {
        return new ResponseEntity<>(reservationSvc.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/preference")
    public ResponseEntity<Reservation> addPreference(@PathVariable Long id, @RequestBody PreferenceAddRequest
            request) {
        return new ResponseEntity<>(reservationSvc.addPreference(id, request), HttpStatus.OK);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<List<IReservationResponse>> getByEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(reservationSvc.getByEmployee(id));
    }

    @GetMapping("trip/{tripId}")
    public ResponseEntity<List<IReservationResponse>> getByTrip(@PathVariable Long tripId) {
        return ResponseEntity.ok(reservationSvc.findByTrip(tripId));
    }
}
