import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TaxiReportComponent } from './taxi-report.component';

describe('TaxiReportComponent', () => {
  let component: TaxiReportComponent;
  let fixture: ComponentFixture<TaxiReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TaxiReportComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TaxiReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
