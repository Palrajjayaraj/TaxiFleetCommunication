import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { UserDashboardComponent } from './user-dashboard/user-dashboard.component';

export const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'user-dashboard', component: UserDashboardComponent }
];
