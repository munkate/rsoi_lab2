import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, NgForm, Validators} from '@angular/forms';
import {DataService} from '../data.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-shipment-edit',
  templateUrl: './shipment-edit.component.html',
  styleUrls: ['./shipment-edit.component.scss']
})
export class ShipmentEditComponent implements OnInit {
  shipment$: Object;
  shipmentForm: FormGroup;
  title = '';
  declare_value: number;
  unit_id: number;
 del_id: number;
  uid: number;
  isLoadingResults = false;


  constructor(private service: DataService, private route: ActivatedRoute,
              private router: Router, private formBuilder: FormBuilder) { }

  ngOnInit() {this.shipmentForm = this.formBuilder.group({
    'title' : ['', [Validators.required]],
    'declare_value' : [null, [Validators.required]],
    'unit_id' : [null, [Validators.required]],
    'del_id' : [null, [Validators.required]],
    'uid': [null, [Validators.required]]
  });
    this.shipmentForm.controls['del_id'].setValue(this.del_id);
  }
  onFormSubmit(form: NgForm) {
    console.log('ghjikj');
    console.log(form);
    this.isLoadingResults = true;
    this.service.updateShipment(form)
      .subscribe(res => {
        this.isLoadingResults = false;
        this.router.navigate([history.back()]);
      }, (err) => {
        console.log(err);
        this.isLoadingResults = false;
      });


  }
  onLoad() {
  this.service.getShipment(this.route.snapshot.params['id']).subscribe(
    data => this.shipment$ = data);
  this.uid = this.shipment$['uid'];
  this.del_id = this.shipment$['del_id'];
  this.shipmentForm.setValue({
    title: this.shipment$['title'],
    declare_value : this.shipment$['declare_value'],
    unit_id: this.shipment$['unit_id'],
    del_id: this.shipment$['del_id'],
    uid: this.shipment$['uid']
  });
}

}
