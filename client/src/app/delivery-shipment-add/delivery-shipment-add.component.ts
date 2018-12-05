import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, NgForm, Validators} from '@angular/forms';
import {DataService} from '../data.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-delivery-shipment-add',
  templateUrl: './delivery-shipment-add.component.html',
  styleUrls: ['./delivery-shipment-add.component.scss']
})
export class DeliveryShipmentAddComponent implements OnInit {

  shipmentForm: FormGroup;
  title = '';
  declare_value: number;
  unit_id: number;
  del_id: number;
  uid: number;
  isLoadingResults = false;
  id: number = Math.floor((Math.random() * 1000) + 10);

  constructor(private service: DataService, private route: ActivatedRoute,
              private router: Router, private formBuilder: FormBuilder) {
    this.del_id = this.route.params['id'];
  }

  ngOnInit() {this.shipmentForm = this.formBuilder.group( {
    'title' : ['', [Validators.required]],
    'declare_value' : [null, [Validators.required]],
    'unit_id' : [null, [Validators.required]],
    'del_id' : [null, [Validators.required]],
    'uid': [null, [Validators.required]]
  });
    this.shipmentForm.controls['del_id'].setValue(this.del_id);
    this.shipmentForm.controls['uid'].setValue(this.id);
  }
  onFormSubmit(form: NgForm) {

    this.isLoadingResults = true;
    this.service.createShipment(form)
      .subscribe(res => {
        this.isLoadingResults = false;
        this.router.navigate([history.back()]);
      }, (err) => {
        console.log(err);
        this.isLoadingResults = false;
      });
  }
  back() {
    history.back();
  }
}
