import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TripService } from '../../services/trip.service';
import { iTrip } from '../../interfaces/itrip';

@Component({
  selector: 'app-trip',
  templateUrl: './trip.component.html',
  styleUrl: './trip.component.scss',
})
export class TripComponent {
  constructor(private route: ActivatedRoute, private tripSvc: TripService) {}

  trip!: iTrip;

  ngOnInit() {
    this.route.params.subscribe((params: any) => {
      let id = +params['id'];
      this.tripSvc.getById(id).subscribe((trip: iTrip) => {
        this.trip = trip;
        console.log(this.trip);
      });
    });
  }
}
