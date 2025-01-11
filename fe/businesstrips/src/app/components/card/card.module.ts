import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CardComponent } from './card/card.component';
import { NgIconsModule } from '@ng-icons/core';
import {
  bootstrapPencil,
  bootstrapSearch,
  bootstrapTrash,
} from '@ng-icons/bootstrap-icons';
import { ModalModule } from '../modal/modal.module';
import { CardHorComponent } from './card-hor/card-hor.component';
import { TripCardComponent } from './trip-card/trip-card.component';

@NgModule({
  declarations: [CardComponent, CardHorComponent, TripCardComponent],
  imports: [
    CommonModule,
    NgIconsModule.withIcons({
      bootstrapSearch,
      bootstrapPencil,
      bootstrapTrash,
    }),
  ],
  exports: [CardComponent, CardHorComponent, TripCardComponent],
})
export class CardModule {}
