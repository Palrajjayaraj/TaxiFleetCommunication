import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  role: string = 'user'; // Default role is 'user'

  constructor(private router: Router) {}

  // This method is called when the role is selected
  onSubmit() {
    if (this.role === 'user') {
      this.router.navigate(['/user-dashboard']); // Navigate to the User Dashboard
    }
  }
}
