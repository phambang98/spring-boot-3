import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CherryCharmComponent } from './cherry-charm.component';

describe('CherryCharmComponent', () => {
  let component: CherryCharmComponent;
  let fixture: ComponentFixture<CherryCharmComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CherryCharmComponent]
    });
    fixture = TestBed.createComponent(CherryCharmComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
