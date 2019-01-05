import {
  AfterContentChecked,
  AfterContentInit,
  AfterViewChecked,
  AfterViewInit,
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output
} from '@angular/core';
import {FormBuilder, FormGroup, NgForm, Validators} from '@angular/forms';
import {DataService} from '../data.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ViewState} from '@angular/core/src/view';

@Component({
  selector: 'app-shipment-edit',
  templateUrl: './shipment-edit.component.html',
  styleUrls: ['./shipment-edit.component.scss']
})
export class ShipmentEditComponent implements AfterViewInit, AfterViewChecked {
  shipment$: Object;
  shipmentForm: FormGroup;
  title = '';
  declare_value: number;
  unit_id: string;
 del_id: number;
  uid: number;
  isLoadingResults = false;
  firstCheck = true;


  constructor(private service: DataService, private route: ActivatedRoute,
              private router: Router, private formBuilder: FormBuilder) {
    this.shipmentForm = this.formBuilder.group({
    'title' : ['', [Validators.pattern('[a-zA-Z]*')]],
    'declare_value' : [null, [Validators.maxLength(10000)]],
    'unit_id' : [null, [Validators.required]],
    'del_id' : [null, [Validators.required]],
    'uid': [null, [Validators.required]]
  });
    this.shipmentForm.controls['del_id'].setValue(this.del_id);
    this.service.getShipment(this.route.snapshot.params['id']).subscribe(
      data => this.shipment$ = data);
  }
  ngAfterViewChecked() {
    if (this.firstCheck) {
      this.onLoad();
      this.firstCheck = false;
    }
  }
  ngAfterViewInit() {
    this.onLoad();
  }
  onLoad() {
    this.uid = this.shipment$['uid'];
    this.del_id = this.shipment$['del_id'];
    if (this.shipment$['unit_id'] === 'KG') {
      this.unit_id = '1';
    } else if (this.shipment$['unit_id'] === 'TONNA') {
      this.unit_id = '0';
    } else {this.unit_id = '2'; }
    this.shipmentForm.setValue({
      title: this.shipment$['title'],
      declare_value : this.shipment$['declare_value'],
      unit_id: this.shipment$['unit_id'].toString(),
      del_id: this.shipment$['del_id'],
      uid: this.shipment$['uid']
    });

  }
  onFormSubmit(form: NgForm) {
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
  back() {
    history.back();
  }


}
