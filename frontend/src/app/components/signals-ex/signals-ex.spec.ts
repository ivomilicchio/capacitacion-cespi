import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SignalsEx } from './signals-ex';

describe('SignalsEx', () => {
  let component: SignalsEx;
  let fixture: ComponentFixture<SignalsEx>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SignalsEx]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SignalsEx);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
