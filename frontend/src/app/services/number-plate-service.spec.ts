import { TestBed } from '@angular/core/testing';

import { NumberPlateService } from './number-plate-service';

describe('NumberPlateService', () => {
  let service: NumberPlateService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NumberPlateService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
