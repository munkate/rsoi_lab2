import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ShipsComponent} from './ships/ships.component';
import {ShipComponent} from './ship/ship.component';
import {ShipmentsComponent} from './shipments/shipments.component';
import {DeliveriesComponent} from './deliveries/deliveries.component';
import {HomeComponent} from './home/home.component';
import {ShipAddComponent} from './ship-add/ship-add.component';
import {ShipEditComponent} from './ship-edit/ship-edit.component';

const routes: Routes = [
  {
    path: 'add',
    component: ShipAddComponent,
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
    path: 'ships',
    component: ShipsComponent
  },
  {
    path: 'deliveries',
    component: DeliveriesComponent
  },
  {
    path: 'shipments',
    component: ShipmentsComponent
  },
  {
    path: '',
    component: HomeComponent
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
