import { Component, OnInit } from '@angular/core';
import {DataService} from '../data.service';

@Component({
  selector: 'app-shipments',
  templateUrl: './shipments.component.html',
  styleUrls: ['./shipments.component.scss']
})
export class ShipmentsComponent implements OnInit {

  shipments$: Object;

  constructor(private service: DataService) { }

  ngOnInit() {
    this.service.getShipments().subscribe(
      service => this.shipments$ = service);
  }

}
