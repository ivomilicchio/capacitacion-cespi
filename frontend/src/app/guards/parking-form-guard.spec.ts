import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { parkingFormGuard } from './parking-form-guard';

describe('parkingFormGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => parkingFormGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
