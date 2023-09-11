import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogLuckyWheelComponent } from './dialog-lucky-wheel.component';

describe('DialogLuckyWheelComponent', () => {
  let component: DialogLuckyWheelComponent;
  let fixture: ComponentFixture<DialogLuckyWheelComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DialogLuckyWheelComponent]
    });
    fixture = TestBed.createComponent(DialogLuckyWheelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
