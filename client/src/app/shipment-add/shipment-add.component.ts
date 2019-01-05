import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {DataService} from '../data.service';
import {ActivatedRoute, Router} from '@angular/router';
import {FormControl, FormGroupDirective, FormBuilder, FormGroup, NgForm, Validators, FormArray, NgModelGroup} from '@angular/forms';
import {forEach} from '@angular/router/src/utils/collection';
@Component({
  selector: 'app-shipment-add',
  templateUrl: './shipment-add.component.html',
  styleUrls: ['./shipment-add.component.scss']
})
export class ShipmentAddComponent implements OnInit {
  @Output()shipmentForm: FormGroup;
  title = '';
  declare_value: number;
  unit_id: number;
  @Input() del_id: number;
  uid: number;
  isLoadingResults = false;
  id: number = Math.floor((Math.random() * 1000) + 10);
  data: Object[] = [];
  i = 0;
  @Output() public responseToParent = new EventEmitter<JSON>();

  constructor(private service: DataService, private route: ActivatedRoute,
              private router: Router, private formBuilder: FormBuilder) {
    this.del_id = this.route.params['id'];
  }

  ngOnInit() {this.shipmentForm = this.formBuilder.group( {
    'title' : ['', [Validators.pattern('[a-zA-Z]*')]],
    'declare_value' : [null, [Validators.pattern('[0-9]-*')]],
    'unit_id' : [null, [Validators.required]],
    'del_id' : [null, [Validators.required]],
    'uid': [null, [Validators.required]]
  });
  this.shipmentForm.controls['del_id'].setValue(this.del_id);
  this.shipmentForm.controls['uid'].setValue(this.id);
  }
  onFormSubmit(form: NgForm) {
    sessionStorage.setItem(this.i.toString(), JSON.stringify(form));
      this.shipmentForm.reset();
     const nextUid = Math.floor((Math.random() * 1000) + 10);
    this.shipmentForm.controls['del_id'].setValue(this.del_id);
    this.shipmentForm.controls['uid'].setValue(nextUid);
    this.i = this.i + 1;
    alert('Груз добавлен в список.');
  }
}
