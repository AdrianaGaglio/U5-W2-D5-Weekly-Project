import { Component, Input, ViewChild } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { iEmployee } from '../../interfaces/iemployee';
import { UploadService } from '../../services/upload.service';
import { EmployeeService } from '../../services/employee.service';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrl: './modal.component.scss',
})
export class ModalComponent {
  constructor(
    public activeModal: NgbActiveModal,
    private uploadSvc: UploadService,
    private employeeSvc: EmployeeService
  ) {}

  @Input() employee!: iEmployee;
  selectedFile!: File;

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
    }
  }

  save() {
    console.log(this.selectedFile);
    if (!this.selectedFile) {
      this.employeeSvc.update(this.employee).subscribe((res) => {
        console.log(res);
        setTimeout(() => this.activeModal.close(), 1000);
      });
    } else {
      const formData = new FormData();
      formData.append('file', this.selectedFile);
      this.uploadSvc.upload(formData).subscribe((res) => {
        if (res) {
          this.employee.image = res;
          console.log('link immagine', res);
          this.employeeSvc.update(this.employee).subscribe();
        }
      });
    }
  }
}
