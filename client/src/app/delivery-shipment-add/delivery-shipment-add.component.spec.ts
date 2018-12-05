import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeliveryShipmentAddComponent } from './delivery-shipment-add.component';

describe('DeliveryShipmentAddComponent', () => {
  let component: DeliveryShipmentAddComponent;
  let fixture: ComponentFixture<DeliveryShipmentAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DeliveryShipmentAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeliveryShipmentAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
