import { Component } from '@angular/core';
import { PagetitleService } from '../../services/pagetitle.service';
import { EmployeeService } from '../../services/employee.service';
import { iEmployee } from '../../interfaces/iemployee';

@Component({
  selector: 'app-employees',
  templateUrl: './employees.component.html',
  styleUrl: './employees.component.scss',
})
export class EmployeesComponent {
  constructor(
    private pageTitle: PagetitleService,
    private employeeSvc: EmployeeService
  ) {}

  employees!: iEmployee[];
  page: number = 0;
  iterations: number[] = [];

  ngOnInit() {
    this.pageTitle.title.next('Manage employees');
    this.employeeSvc.employees$.subscribe((res) => (this.employees = res));
    this.employeeSvc.getPagedEmployees(this.page, 5).subscribe((result) => {
      this.iterations = Array(result.totalPages);
    });
  }

  changePage(num: number) {
    this.page = num;
    this.employeeSvc.getPagedEmployees(this.page, 5).subscribe((result) => {
      this.employees = result.content;
    });
  }
}
