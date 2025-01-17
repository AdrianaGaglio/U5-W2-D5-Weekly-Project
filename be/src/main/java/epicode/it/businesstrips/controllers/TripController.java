package epicode.it.businesstrips.controllers;

import epicode.it.businesstrips.entities.trip.Trip;
import epicode.it.businesstrips.entities.trip.TripCreateRequest;
import epicode.it.businesstrips.entities.trip.TripSvc;
import epicode.it.businesstrips.entities.trip.TripUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripController {
    private final TripSvc tripSvc;

    @GetMapping
    public ResponseEntity<List<Trip>> getAll() {
        return ResponseEntity.ok(tripSvc.getAll());
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<Trip>> getAllPageable(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(tripSvc.getAllPageable(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getById(@PathVariable Long id) {
        return ResponseEntity.ok(tripSvc.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return new ResponseEntity<>(tripSvc.delete(id), HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<Trip> create(@RequestBody TripCreateRequest request) {
        return new ResponseEntity<>(tripSvc.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trip> update(@PathVariable Long id, @RequestBody TripUpdateRequest request) {
        return new ResponseEntity<>(tripSvc.update(id, request), HttpStatus.OK);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Trip> update(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(tripSvc.updateStatus(id, status));
    }

}
