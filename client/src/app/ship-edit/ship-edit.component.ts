import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, NgForm, Validators} from '@angular/forms';
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
  isLoadingResults = false;

  constructor(private service: DataService, private route: ActivatedRoute,
              private router: Router, private formBuilder: FormBuilder) {
    this.route.params.subscribe( params => this.ship$ = params.id);
  }

  ngOnInit() {
    this.service.getShip(this.ship$).subscribe(
      service => this.ship$ = service);
    this.shipForm = this.formBuilder.group({
    'sh_title' : [this.ship$., [Validators.required]],
    'skipper' : ['', [Validators.required]],
    'year' : [null, [Validators.required]],
    'capacity' : [null, [Validators.required]],
    'type_id' : ['', [Validators.required]],
    'uid' : [this.route.params.id,[Validators.required]]
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
