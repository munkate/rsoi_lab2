import { Component, OnInit } from '@angular/core';
import {DataService} from '../data.service';
import {ActivatedRoute, Router} from '@angular/router';

import {MatDialog} from '@angular/material';
import {DialogComponent} from '../dialog/dialog.component';
import {Observable} from 'rxjs';
import {Ship} from './ship';
import {DialogComponent} from '../dialog/dialog.component';

@Component({
  selector: 'app-ship',
  templateUrl: './ship.component.html',
  styleUrls: ['./ship.component.scss']
})
export class ShipComponent implements OnInit {

  ship$: Object;
  id: number;

  response: string = String('Confirm');

  constructor(private service: DataService, private route: ActivatedRoute, private router: Router, public dialog: MatDialog) {
    this.id = this.route.snapshot.params['id'];
  }

  ngOnInit() {
    this.service.getShip(this.id).subscribe(
      service => this.ship$ = service);

  }

  delete(id) {
    const dialogRef = this.dialog.open(DialogComponent, {
      data: 'Вы действительно хотите удалить информацию о корабле?'
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog closed: ${result}`);
      if (this.response === result) {
        this.service.deleteShip(this.id).subscribe(res => {
            this.router.navigate(['ships']);
          }, (err) => {
            console.log(err);
          }
        );
      }
    });
  }

  back() {
    history.back();
  }



}
