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

  constructor(private service: DataService, private route: ActivatedRoute, private router: Router) {
    this.route.params.subscribe( params => this.ship$ = params.id);
  }

  ngOnInit() {
    this.service.getShip(this.ship$).subscribe(
      service => this.ship$ = service);

  }

  back() {
    history.back();
  }

  delete(ship$) {
    this.service.deleteShip(Number(this.ship$)).subscribe(res => {
        this.router.navigate(['ships']);
      }, (err) => {
        console.log(err);
      }
    );
  }

}
