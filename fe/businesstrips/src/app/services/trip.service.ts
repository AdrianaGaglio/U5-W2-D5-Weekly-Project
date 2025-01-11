import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { iTrip } from '../interfaces/itrip';
import { BehaviorSubject, Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class TripService {
  constructor(private http: HttpClient) {}

  url: string = environment.tripUrl;
  trips$ = new BehaviorSubject<iTrip[]>([]);

  getTrips(): Observable<iTrip[]> {
    return this.http
      .get<iTrip[]>(this.url)
      .pipe(tap((trips) => this.trips$.next(trips)));
  }

  delete(trip: iTrip): Observable<string> {
    return this.http
      .delete<string>(`${this.url}/${trip.id}`, {
        responseType: 'text' as 'json',
      })
      .pipe(
        tap((res) =>
          this.trips$.next(
            this.trips$.getValue().filter((t) => t.id !== trip.id)
          )
        )
      );
  }

  create(trip: Partial<iTrip>): Observable<iTrip> {
    return this.http
      .post<iTrip>(this.url, trip)
      .pipe(tap((res) => this.trips$.next([...this.trips$.getValue(), res])));
  }

  update(trip: Partial<iTrip>): Observable<iTrip> {
    return this.http.put<iTrip>(`${this.url}/${trip.id}`, trip);
  }
}
