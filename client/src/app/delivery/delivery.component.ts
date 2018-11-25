import { Component, OnInit } from '@angular/core';
import {DataService} from '../data.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-delivery',
  templateUrl: './delivery.component.html',
  styleUrls: ['./delivery.component.scss']
})
export class DeliveryComponent implements OnInit {

  delivery$: Object;
  id: number;

  constructor(private service: DataService, private route: ActivatedRoute, private router: Router) {
    this.id = this.route.snapshot.params['id'];
  }
  ngOnInit() {
    this.service.getDelivery(this.id).subscribe(
      service => this.delivery$ = service);
  }

}
