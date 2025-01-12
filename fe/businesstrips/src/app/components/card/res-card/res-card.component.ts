import { Component, Input } from '@angular/core';
import { iReservation } from '../../../interfaces/ireservation';
import { ReservationService } from '../../../services/reservation.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IReservationresponse } from '../../../interfaces/ireservationresponse';
import { ResModalComponent } from '../../modal/res-modal/res-modal.component';

@Component({
  selector: 'app-res-card',
  templateUrl: './res-card.component.html',
  styleUrl: './res-card.component.scss',
})
export class ResCardComponent {
  constructor(
    private reservationSvc: ReservationService,
    private modalService: NgbModal
  ) {}

  @Input() reservation!: iReservation;

  ngOnInit() {}

  openModal(reservation: IReservationresponse) {
    const modalRef = this.modalService.open(ResModalComponent, {
      centered: true,
    });
    modalRef.componentInstance.reservation = reservation;
  }

  delete(reservation: iReservation) {
    this.reservationSvc.delete(reservation.id).subscribe();
  }
}
