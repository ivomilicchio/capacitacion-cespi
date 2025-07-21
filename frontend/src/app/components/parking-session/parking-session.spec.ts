import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ParkingSession } from './parking-session';

describe('ParkingSession', () => {
  let component: ParkingSession;
  let fixture: ComponentFixture<ParkingSession>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ParkingSession]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ParkingSession);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
