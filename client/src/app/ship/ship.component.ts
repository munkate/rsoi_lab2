import { Component, OnInit } from '@angular/core';
import {DataService} from '../data.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Observable} from 'rxjs';
import {Ship} from './ship';

@Component({
  selector: 'app-ship',
  templateUrl: './ship.component.html',
  styleUrls: ['./ship.component.scss']
})
export class ShipComponent implements OnInit {

  ship$: Object;
  id: number;

  constructor(private service: DataService, private route: ActivatedRoute, private router: Router) {
    this.id = this.route.snapshot.params['id'];
  }

  ngOnInit() {
    this.service.getShip(this.id).subscribe(
      service => this.ship$ = service);

  }

  delete(id) {
    this.service.deleteShip(this.id).subscribe(res => {
        this.router.navigate(['ships']);
      }, (err) => {
        console.log(err);
      }
    );
  }

  back() {
    history.back();
  }



}
