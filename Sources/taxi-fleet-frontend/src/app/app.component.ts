import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {FlexLayoutModule  } from '@angular/flex-layout';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Taxi Fleet Management';
}
