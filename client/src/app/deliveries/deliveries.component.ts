import { Component, OnInit } from '@angular/core';
import {DataService} from '../data.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-deliveries',
  templateUrl: './deliveries.component.html',
  styleUrls: ['./deliveries.component.scss']
})
export class DeliveriesComponent implements OnInit {
  deliveries$: Object;
  user_id: number;
  visible = false;

  constructor(private service: DataService, private route: ActivatedRoute, private router: Router) {
    this.user_id = this.route.snapshot.params['user_id'];
  }

  ngOnInit() {
    this.service.getDeliveries(this.user_id).subscribe(
      service => {this.deliveries$ = service; },
      (err) => {
      console.log(err);
    });
    this.visible = localStorage.getItem('token') != null;
  }

}
