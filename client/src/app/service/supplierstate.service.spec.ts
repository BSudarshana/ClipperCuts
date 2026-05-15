import { TestBed } from '@angular/core/testing';

import { Supplierstateservice } from './supplierstateservice';

describe('SupplierstatusService', () => {
  let service: Supplierstateservice;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Supplierstateservice);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
