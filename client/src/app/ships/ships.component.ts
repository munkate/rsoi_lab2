import { Component, OnInit } from '@angular/core';
import {DataService} from '../data.service';
import {ActivatedRoute} from '@angular/router';
import {Observable} from 'rxjs';

@Component({
  selector: 'app-ships',
  templateUrl: './ships.component.html',
  styleUrls: ['./ships.component.scss']
})
export class ShipsComponent implements OnInit {

  ships$: Object;
  enable: boolean;

  constructor(private service: DataService) {
  }

  ngOnInit() {
    this.service.getShips().subscribe(
      service => this.ships$ = service);
    this.enable = localStorage.getItem('token') != null;
  }

}
