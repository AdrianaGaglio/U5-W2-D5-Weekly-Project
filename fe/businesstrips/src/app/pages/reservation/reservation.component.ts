import { Component } from '@angular/core';
import { PagetitleService } from '../../services/pagetitle.service';

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrl: './reservation.component.scss',
})
export class ReservationComponent {
  constructor(private pageTitle: PagetitleService) {}

  ngOnInit() {
    this.pageTitle.title.next('Manage reservations');
  }
}
