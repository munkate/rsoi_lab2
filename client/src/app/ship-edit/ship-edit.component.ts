import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroupDirective, FormBuilder, FormGroup, NgForm, Validators} from '@angular/forms';
import {DataService} from '../data.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-ship-edit',
  templateUrl: './ship-edit.component.html',
  styleUrls: ['./ship-edit.component.scss']
})
export class ShipEditComponent implements OnInit {
  ship$: Object;
  shipForm: FormGroup;
  sh_title = '';
  skipper = '';
  year: number = null;
  capacity: number = null;
  type_id: string;
  uid: number;
  type: number;
  isLoadingResults = false;

  constructor(private service: DataService, private route: ActivatedRoute,
              private router: Router, private formBuilder: FormBuilder) {

    this.shipForm = this.formBuilder.group({
      'sh_title' : [null, [Validators.required]],
      'skipper' : [null, [Validators.required]],
      'year' : [null, [Validators.required]],
      'capacity' : [null, [Validators.required]],
      'type_id' : [null, [Validators.required]],
      'uid' : [null, [Validators.required]]
    });

  }

  ngOnInit() {

    this.getShipData();
  }

  onFormLoad() {
    this.getShipData();
  }

  getShipData() {
    this.service.getShip(this.route.snapshot.params['id']).subscribe(
      data => this.ship$ = data);
    if (this.ship$['type_id'] === 'TANKER') {
      this.type = 0;
    } else {
      this.type = 1;
    }
    this.uid = this.ship$['uid'];
    this.shipForm.setValue({
      sh_title: this.ship$['sh_title'],
      skipper : this.ship$['skipper'],
      year: this.ship$['year'],
      capacity: this.ship$['capacity'],
      type_id: this.type,
      uid: this.ship$['uid']
    });

  }
  onFormSubmit(form: NgForm) {
    this.isLoadingResults = true;
    this.service.updateShip(form)
      .subscribe(res => {
        this.isLoadingResults = false;
        this.router.navigate(['ships']);
      }, (err) => {
        console.log(err);
        this.isLoadingResults = false;
      });
  }

  back() {
    history.back();
  }


}
