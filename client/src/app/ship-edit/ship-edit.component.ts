import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroupDirective, FormBuilder, FormGroup, NgForm, Validators} from '@angular/forms';
import {DataService} from '../data.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Ship} from '../ship/ship';

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
  }

  ngOnInit() {
    /*this.service.getShip(this.route.snapshot.params['id']).subscribe(
      data => this.ship$ = data);
        this.uid = this.ship$.uid;
        this.shipForm.setValue({
          'sh_title': this.ship$.sh_title,
          'skipper': this.ship$.skipper,
          'year': this.ship$.year,
          'capacity': this.ship$.capacity,
          'type_id': this.ship$.type_id
        });
    this.shipForm = this.formBuilder.group({
    'sh_title' : ['', [Validators.required]],
    'skipper' : ['', [Validators.required]],
    'year' : [null, [Validators.required]],
    'capacity' : [null, [Validators.required]],
    'type_id' : ['', [Validators.required]]
  });
*/
  }

}
