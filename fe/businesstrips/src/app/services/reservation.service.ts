import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { iReservation } from '../interfaces/ireservation';
import { environment } from '../../environments/environment.development';
import { iReservationrequest } from '../interfaces/ireservationrequest';
import { IReservationresponse } from '../interfaces/ireservationresponse';
import { iPreference } from '../interfaces/ipreference';
import { iAddpreference } from '../interfaces/iaddpreference';

@Injectable({
  providedIn: 'root',
})
export class ReservationService {
  constructor(private http: HttpClient) {}

  url: string = environment.reservationUrl;
  reservations$ = new BehaviorSubject<iReservation[] | IReservationresponse[]>(
    []
  );

  getReservations(): Observable<IReservationresponse[]> {
    return this.http
      .get<IReservationresponse[]>(this.url)
      .pipe(tap((reservations) => this.reservations$.next(reservations)));
  }

  getByEmployee(employeeId: number): Observable<IReservationresponse[]> {
    return this.http.get<IReservationresponse[]>(
      `${this.url}/employee/${employeeId}`
    );
  }

  addReservation(
    resRequest: iReservationrequest
  ): Observable<IReservationresponse> {
    return this.http
      .post<IReservationresponse>(this.url, resRequest)
      .pipe(
        tap((res) =>
          this.reservations$.next([...this.reservations$.getValue(), res])
        )
      );
  }

  delete(id: number): Observable<string> {
    return this.http
      .delete<string>(`${this.url}/${id}`)
      .pipe(
        tap((result) =>
          this.reservations$.next(
            this.reservations$.getValue().filter((res) => res.id !== id)
          )
        )
      );
  }

  addPreference(
    id: number,
    preference: iAddpreference
  ): Observable<IReservationresponse> {
    return this.http
      .put<IReservationresponse>(`${this.url}/${id}/preference`, preference)
      .pipe(
        tap((res) =>
          this.getReservations().subscribe((res) =>
            this.reservations$.next(res)
          )
        )
      );
  }

  getByTrip(tripId: number): Observable<IReservationresponse[]> {
    return this.http.get<IReservationresponse[]>(`${this.url}/trip/${tripId}`);
  }
}
