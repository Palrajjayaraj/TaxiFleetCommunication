import { Routes } from '@angular/router';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { LoginComponent } from './login/login.component';
import { TaxiDashboardComponent } from './taxi-dashboard/taxi-dashboard.component';
import { UserDashboardComponent } from './user-dashboard/user-dashboard.component';

export const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'login', component: LoginComponent },
  { path: 'user-dashboard', component: UserDashboardComponent },
  { path: 'taxi-dashboard', component: TaxiDashboardComponent },
  { path: 'admin-dashboard', component: AdminDashboardComponent },
];
