import { TestBed } from '@angular/core/testing';

import { LoggedUserCartService } from './logged-user-cart.service';

describe('LoggedUserCartService', () => {
  let service: LoggedUserCartService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoggedUserCartService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
