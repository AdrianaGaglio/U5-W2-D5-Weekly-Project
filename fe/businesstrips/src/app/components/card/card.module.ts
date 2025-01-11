import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CardComponent } from './card.component';
import { NgIconsModule } from '@ng-icons/core';
import {
  bootstrapPencil,
  bootstrapSearch,
  bootstrapTrash,
} from '@ng-icons/bootstrap-icons';

@NgModule({
  declarations: [CardComponent],
  imports: [
    CommonModule,
    NgIconsModule.withIcons({
      bootstrapSearch,
      bootstrapPencil,
      bootstrapTrash,
    }),
  ],
  exports: [CardComponent],
})
export class CardModule {}
