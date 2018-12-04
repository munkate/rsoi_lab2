import { Component, OnInit } from '@angular/core';
import {DataService} from '../data.service';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormGroup, NgForm, Validators} from '@angular/forms';

@Component({
  selector: 'app-delivery-edit',
  templateUrl: './delivery-edit.component.html',
  styleUrls: ['./delivery-edit.component.scss']
})
export class DeliveryEditComponent implements OnInit {
  ships$: Object;
  delivery$: Object;
  deliveryForm: FormGroup;
  departure_date: null;
  arrive_date: null;
  origin: '';
  destination: '';
  user_id: number;
  uid: number;
  ship_id: number;
  isLoadingResults = false;

  constructor(private service: DataService, private route: ActivatedRoute,
              private router: Router, private formBuilder: FormBuilder) {
    this.user_id = this.route.snapshot.params['id'];
  }
  ngOnInit() {
    this.service.getShips().subscribe(
      data => this.ships$ = data);
    this.deliveryForm = this.formBuilder.group({
      'departure_date' : ['', [Validators.required]],
      'arrive_date' : [null, [Validators.required]],
      'origin' : [null, [Validators.required]],
      'destination' : [null, [Validators.required]],
      'ship_id' : [null, [Validators.required]],
      'user_id' : [null, [Validators.required]],
      'uid' : [null, [Validators.required]]
    });
    this.getDeliveryData();
    }

  private getDeliveryData() {
    this.service.getDelivery(this.route.snapshot.params['id']).subscribe(
      data => this.delivery$ = data['delivery']);
    this.uid = this.delivery$['uid'];
    this.deliveryForm.setValue({
      departure_date: this.delivery$['departure_date'],
      arrive_date : this.delivery$['arrive_date'],
      origin: this.delivery$['origin'],
      destination: this.delivery$['destination'],
      ship_id: this.delivery$['ship_id'],
      user_id: this.delivery$['user_id'],
      uid: this.delivery$['uid']
    });
  }
  onFormSubmit(form: NgForm) {
    this.isLoadingResults = true;
    this.service.updateDelivery(form)
      .subscribe(res => {
        this.isLoadingResults = false;
        this.router.navigate(['users/1/deliveries']);
      }, (err) => {
        console.log(err);
        this.isLoadingResults = false;
      });
  }
}
