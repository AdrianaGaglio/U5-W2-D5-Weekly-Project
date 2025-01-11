import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    loadChildren: () =>
      import('./pages/dashboard/dashboard.module').then(
        (m) => m.DashboardModule
      ),
  },
  {
    path: 'employees',
    loadChildren: () =>
      import('./pages/employees/employees.module').then(
        (m) => m.EmployeesModule
      ),
  },
  {
    path: 'trips',
    loadChildren: () =>
      import('./pages/trips/trips.module').then((m) => m.TripsModule),
  },
  {
    path: 'reservation',
    loadChildren: () =>
      import('./pages/reservation/reservation.module').then(
        (m) => m.ReservationModule
      ),
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
