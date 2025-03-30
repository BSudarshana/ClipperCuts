import { TestBed } from '@angular/core/testing';

import { SupplierstatusService } from './supplierstatus.service';

describe('SupplierstatusService', () => {
  let service: SupplierstatusService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SupplierstatusService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
