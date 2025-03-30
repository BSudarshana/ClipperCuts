import { TestBed } from '@angular/core/testing';
import {Empstatusservice} from "./empstatusservice";


describe('Employee Status Service', () => {
  let service: Empstatusservice

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Empstatusservice);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
