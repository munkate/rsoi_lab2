import {Component, Injectable, Input, OnInit} from '@angular/core';
import {DataService} from '../data.service';
import {ActivatedRoute, Router} from '@angular/router';
import { FormControl, FormGroupDirective, FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';

@Component({
  selector: 'app-delivery-add',
  templateUrl: './delivery-add.component.html',
  styleUrls: ['./delivery-add.component.scss']
})
@Injectable()
export class DeliveryAddComponent implements OnInit {
  ships$: Object;
  deliveryForm: FormGroup;
  departure_date: null;
  arrive_date: null;
  origin: '';
  destination: '';
  user_id = 1;
  uid: number;
  ship_id: number;
  id: number = Math.floor((Math.random() * 1000000) + 10);
  isLoadingResults = false;
  child_response: Object;
  error = false;

  constructor(private service: DataService, private route: ActivatedRoute,
              private router: Router, private formBuilder: FormBuilder) {
    this.user_id = this.route.snapshot.params['id'];
  }
  ngOnInit() {
    this.error = false;
    this.service.getShips().subscribe(
      service => this.ships$ = service);
    this.deliveryForm = this.formBuilder.group({
    'departure_date' : ['', [Validators.required]],
    'arrive_date' : [null, [Validators.required]],
    'origin' : [null, [Validators.pattern('[a-zA-Z]*')]],
      'destination' : [null, [Validators.pattern('[a-zA-Z]*')]],
    'ship_id' : [null, [Validators.required]],
      'user_id' : [null, [Validators.required]],
      'uid' : [null, [Validators.required]]
  });
    this.deliveryForm.patchValue({
      user_id: this.user_id,
      uid: this.id
    });
  }
  onFormSubmit(form: NgForm) {
    let data;
    const shipments = [];
    let i = 0;
    while (sessionStorage.getItem(i.toString()) !== null) {
      shipments.push(JSON.parse(sessionStorage.getItem(i.toString())));
      i = i + 1;
    }
    i = 0;
    data = JSON.stringify( {delivery: form, shipments: shipments});
    console.log(data);
    this.isLoadingResults = true;
    this.service.createDelivery(this.user_id, JSON.parse(data))
      .subscribe(response => {
        this.isLoadingResults = false;
        this.router.navigate(['users/1/deliveries']);
      }, (err) => {
        console.log(err);
        this.error = true;
        let y = 0;
        while (sessionStorage.getItem(y.toString()) !== null) {
          sessionStorage.removeItem(y.toString());
          y = y + 1;
        }
        this.isLoadingResults = false;
      });
  }

}
