import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatLabel, MatSelectModule } from '@angular/material/select';
import { Router } from '@angular/router';
import { Observable } from 'rxjs/internal/Observable';
import { of } from 'rxjs/internal/observable/of';
import { INamedEntity } from '../model/readable.model';
import { Role } from '../model/role.model';
import { CommonService } from '../services/common.service';
import { RoleHandlerFactory } from './role.handler';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, MatLabel, MatSelectModule, MatButtonModule, MatFormFieldModule, MatInputModule, HttpClientModule],
  providers: [RoleHandlerFactory],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {

  allRoles: Role[] = Object.values(Role);

  selectedRole: Role = Role.User; // Default role is 'user'

  entities: Observable<INamedEntity[]> = of([]);

  selectedEntity!: INamedEntity;

  constructor(
    private handlerFactory: RoleHandlerFactory,
    private commonService: CommonService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.entities = this.handlerFactory.getHandler(this.selectedRole).getAllRoleData();
    this.selectDefaultUser();
  }

  private selectDefaultUser(): void {
    this.entities.subscribe(userList => {
      this.selectedEntity = this.handlerFactory.getHandler(this.selectedRole).getDefaultUser(userList);
    });

  }

  onRoleChange(newRole: Role): void {
    this.selectedRole = newRole;
    const handler = this.handlerFactory.getHandler(newRole);
    this.entities = handler.getAllRoleData();
    this.selectDefaultUser();
  }

  /**Reacts to the login button click. */
  onLogin(): void {
    const handler = this.handlerFactory.getHandler(this.selectedRole);
    handler.onSelect(this.selectedEntity);
    this.router.navigate([handler.getNextPageName()]);
  }
  getDisplayText(entity: INamedEntity): string {
    return entity.readAbleName;
  }

}


