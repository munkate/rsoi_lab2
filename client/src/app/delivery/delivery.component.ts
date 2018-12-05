import { Component, OnInit } from '@angular/core';
import {DataService} from '../data.service';
import {ActivatedRoute, Router} from '@angular/router';
import {MatDialog} from '@angular/material';
import {DialogComponent} from '../dialog/dialog.component';

@Component({
  selector: 'app-delivery',
  templateUrl: './delivery.component.html',
  styleUrls: ['./delivery.component.scss']
})
export class DeliveryComponent implements OnInit {

  delivery$: Object;
  id: number;
  user_id: number;
  dialogResult: string;
  response: string = String('Confirm');
  options: Intl.DateTimeFormatOptions = {
    day: 'numeric', month: 'numeric', year: 'numeric'};

  constructor(private service: DataService, private route: ActivatedRoute, private router: Router, public dialog: MatDialog) {
    this.id = this.route.snapshot.params['id'];
    this.user_id = this.route.snapshot.params['user_id'];
  }

  ngOnInit() {
    this.service.getDelivery(this.id).subscribe(
      service => this.delivery$ = service);
  }

  delete() {
    const dialogRef = this.dialog.open(DialogComponent, {
      data: 'Вы действительно хотите удалить доставку?'
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog closed: ${result}`);
      if (this.response === result) {
        this.service.deleteDelivery(this.user_id, this.id).subscribe(res => {
          this.router.navigate([`users/${this.user_id}/deliveries`]);
        }, (err) => {
          console.log(err);
        });
      }
    });
  }

  deleteShipment(uid) {
    const dialogRef = this.dialog.open(DialogComponent, {
      data: 'Вы действительно хотите удалить информацию о грузе?'
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog closed: ${result}`);
      if (this.response === result) {
        this.service.deleteShipment(uid).subscribe(res => {
          window.location.reload();
        }, (err) => {
          console.log(err);
        });
      }
    });
  }
  back() {
    history.back();
  }

}
