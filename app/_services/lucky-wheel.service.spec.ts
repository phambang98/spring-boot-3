import { TestBed } from '@angular/core/testing';

import { LuckyWheelService } from './lucky-wheel.service';

describe('LuckyWheelService', () => {
  let service: LuckyWheelService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LuckyWheelService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
