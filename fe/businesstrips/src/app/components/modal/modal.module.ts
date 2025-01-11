import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModalComponent } from './modal.component';
import { FormsModule, NgModel } from '@angular/forms';

@NgModule({
  declarations: [ModalComponent],
  imports: [CommonModule, FormsModule],
  exports: [ModalComponent],
})
export class ModalModule {}
