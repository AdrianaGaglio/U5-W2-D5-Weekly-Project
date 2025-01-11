import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { iEmployee } from '../interfaces/iemployee';
import { iPagedEmployees } from '../interfaces/ipagedemployees';

@Injectable({
  providedIn: 'root',
})
export class EmployeeService {
  constructor(private http: HttpClient) {}

  url: string = environment.employeeUrl;
  employees$ = new BehaviorSubject<iEmployee[]>([]);

  getEmployees(): Observable<iEmployee[]> {
    return this.http
      .get<iEmployee[]>(this.url)
      .pipe(tap((employees) => this.employees$.next(employees)));
  }

  getPagedEmployees(page: number, size: number): Observable<iPagedEmployees> {
    return this.http
      .get<iPagedEmployees>(
        `${this.url}/paged?page=${page}&size=${size}&sort=lastName`
      )
      .pipe(tap((result) => this.employees$.next(result.content)));
  }

  delete(employee: iEmployee): Observable<string> {
    return this.http
      .delete<string>(`${this.url}/${employee.id}`)
      .pipe(
        tap((res) =>
          this.employees$.next(
            this.employees$.getValue().filter((e) => e.id !== employee.id)
          )
        )
      );
  }
}
