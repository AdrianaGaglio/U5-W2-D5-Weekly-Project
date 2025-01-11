import { Component, Input } from '@angular/core';
import { iEmployee } from '../../interfaces/iemployee';
import { EmployeeService } from '../../services/employee.service';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrl: './card.component.scss',
})
export class CardComponent {
  constructor(private employeeSvc: EmployeeService) {}

  message!: string;

  @Input() employee!: iEmployee;

  delete(employee: iEmployee) {
    this.employeeSvc
      .delete(employee)
      .subscribe((res) => (this.message = 'Employee deleted successfully!'));
  }
}
