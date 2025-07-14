import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Directivas } from './directivas';

describe('Directivas', () => {
  let component: Directivas;
  let fixture: ComponentFixture<Directivas>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Directivas]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Directivas);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
