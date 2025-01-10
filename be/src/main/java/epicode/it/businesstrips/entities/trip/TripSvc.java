package epicode.it.businesstrips.entities.trip;

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
public class TripSvc {
    private final TripRepo tripRepo;

    public List<Trip> getAll() {
        return tripRepo.findAll();
    }

    public Page<Trip> getAllPageable(Pageable pageable) {
        return tripRepo.findAll(pageable);
    }

    public Trip getById(Long id) {
        return tripRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Trip not found"));
    }

    public int count() {
        return (int) tripRepo.count();
    }

    public String delete(Long id) {
        Trip e = getById(id);
        tripRepo.delete(e);
        return "Trip deleted successfully";
    }

    public String delete(Trip e) {
        Trip foundTrip = getById(e.getId());
        tripRepo.delete(foundTrip);
        return "Trip deleted successfully";
    }

    public Trip create(@Valid TripCreateRequest request) {
        if (tripRepo.existsByDestinationAndDate(request.getDestination(), request.getDate()))
            throw new EntityExistsException("Trip already exists");

        Trip trip = new Trip();
        BeanUtils.copyProperties(request, trip);
        return tripRepo.save(trip);
    }

    public Trip update(Long id, @Valid TripUpdateRequest request) {
        Trip trip = getById(id);

        trip.setDate(request.getDate() != null ? request.getDate() : trip.getDate());
        trip.setDestination(request.getDestination() != null ? request.getDestination() : trip.getDestination());
        trip.setMaxCapacity(request.getMaxCapacity() > 0 ? request.getMaxCapacity() : trip.getMaxCapacity());

        return tripRepo.save(trip);
    }

    public Trip updateStatus(Long id, String status) {
        Trip trip = getById(id);
        trip.setStatus(TripStatus.valueOf(status.toUpperCase()));
        return tripRepo.save(trip);
    }
}
