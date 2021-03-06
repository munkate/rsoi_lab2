import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ShipsComponent} from './ships/ships.component';
import {ShipComponent} from './ship/ship.component';
import {ShipmentsComponent} from './shipments/shipments.component';
import {DeliveriesComponent} from './deliveries/deliveries.component';
import {HomeComponent} from './home/home.component';
import {ShipAddComponent} from './ship-add/ship-add.component';
import {ShipEditComponent} from './ship-edit/ship-edit.component';
import {DeliveryComponent} from './delivery/delivery.component';
import {DeliveryAddComponent} from './delivery-add/delivery-add.component';
import {ShipmentEditComponent} from './shipment-edit/shipment-edit.component';
import {DeliveryEditComponent} from './delivery-edit/delivery-edit.component';
import {ShipmentAddComponent} from './shipment-add/shipment-add.component';
import {DeliveryShipmentAddComponent} from './delivery-shipment-add/delivery-shipment-add.component';
import {LoginComponent} from './login/login.component';

const routes: Routes = [
  {
    path: 'add',
    component: ShipAddComponent,
  },
  {
    path: 'users/:user_id/delivery',
    component: DeliveryAddComponent,
  },
  {
    path: 'users/:user_id/deliveries/:id/shipment',
    component: DeliveryShipmentAddComponent,
  },
  {
    path: 'users/:user_id/deliveries/:id/edit',
    component: DeliveryEditComponent,
  },
  {
    path: 'users/:user_id/deliveries/:id',
    component: DeliveryComponent,
  },
  {
    path: 'users/:user_id/deliveries',
    component: DeliveriesComponent,
  },
  {
    path: 'edit/:id',
    component: ShipEditComponent,
  },
  {
    path: 'ships/:id',
    component: ShipComponent,
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'ships',
    component: ShipsComponent,
  },
  {
    path: 'deliveries',
    component: DeliveriesComponent,
  },

  {
    path: 'shipments/:id/edit',
    component: ShipmentEditComponent,
  },
  {
    path: 'shipments',
    component: ShipmentsComponent,
  },
  {
    path: '',
    component: HomeComponent,
  },
  {
    path: '**',
    redirectTo: ''
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
