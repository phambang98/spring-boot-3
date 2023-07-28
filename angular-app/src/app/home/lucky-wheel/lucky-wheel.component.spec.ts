import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LuckyWheelComponent } from './lucky-wheel.component';

describe('LuckyWheelComponent', () => {
  let component: LuckyWheelComponent;
  let fixture: ComponentFixture<LuckyWheelComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LuckyWheelComponent]
    });
    fixture = TestBed.createComponent(LuckyWheelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
