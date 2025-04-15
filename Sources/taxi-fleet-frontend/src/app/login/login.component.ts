import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatLabel } from '@angular/material/select';
import { Observable } from 'rxjs/internal/Observable';
import { User } from '../model/user.model';
import { CommonService } from '../services/common.service';
import { HttpClientModule } from '@angular/common/http';
import { StateService } from '../services/state.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, MatSelectModule, MatButtonModule, MatFormFieldModule, MatInputModule, HttpClientModule],
  providers: [CommonService],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  role: string = 'user'; // Default role is 'user'

  selectedUser !: User;

  users!: Observable<User[]>;

  constructor(private router: Router, private commonService: CommonService, private stateService: StateService
  ) { }

  ngOnInit(): void {
    this.users = this.commonService.getUsers();
    this.selectDefaultUser();
  }

  private selectDefaultUser(): void {
    this.users.subscribe(userList => {
      // Try to find Palraj Jayaraj in the list
      const defaultUser = userList.find(u => u.name === 'Palraj Jayaraj');
      if (defaultUser) {
        this.selectedUser = defaultUser;
      }
    });

  }

  /**Reacts to the login button click. */
  onLogin() {
    if (this.role === 'user') {
      this.stateService.currentUser = this.selectedUser;
      this.router.navigate(['/user-dashboard']); // Navigate to the User Dashboard
    }
  }
}


