import {Component, Inject, OnInit} from '@angular/core';
import {Ship} from '../ship/ship';
import {DataService} from '../data.service';
import {ActivatedRoute, Router} from '@angular/router';
import { FormControl, FormGroupDirective, FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';


@Component({
  selector: 'app-ship-add',
  templateUrl: './ship-add.component.html',
  styleUrls: ['./ship-add.component.scss']
})
export class ShipAddComponent implements OnInit {
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

  ngOnInit() { this.shipForm = this.formBuilder.group({
    'sh_title' : ['', [Validators.required]],
    'skipper' : ['', [Validators.required]],
    'year' : [null, [Validators.required]],
    'capacity' : [null, [Validators.required]],
    'type_id' : ['', [Validators.required]],
  });

  }
  onFormSubmit(form: NgForm) {
    this.isLoadingResults = true;
    this.service.createShip(form)
      .subscribe(res => {
        this.isLoadingResults = false;
        this.router.navigate(['ships']);
      }, (err) => {
        console.log(err);
        this.isLoadingResults = false;
      });
  }
}
