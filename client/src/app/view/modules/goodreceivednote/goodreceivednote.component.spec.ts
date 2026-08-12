import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GoodreceivednoteComponent } from './goodreceivednote.component';

describe('GoodreceivednoteComponent', () => {
  let component: GoodreceivednoteComponent;
  let fixture: ComponentFixture<GoodreceivednoteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GoodreceivednoteComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GoodreceivednoteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
