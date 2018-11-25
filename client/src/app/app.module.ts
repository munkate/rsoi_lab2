import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {
  MatButtonModule,
  MatInputModule,
  MatCardModule,
  MatCheckboxModule,
  MatDialogModule,
  MatSidenavModule,
  MatFormFieldModule,
  MatProgressSpinnerModule,
  MatIconModule,
  MatNativeDateModule, MatOptionModule, MatSelectModule, MatToolbarModule, MatNavList, MatListModule
} from '@angular/material';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { ShipsComponent } from './ships/ships.component';
import { ShipmentsComponent } from './shipments/shipments.component';
import { DeliveriesComponent } from './deliveries/deliveries.component';

import {HttpClientModule} from '@angular/common/http';
import { ShipComponent } from './ship/ship.component';
import { HomeComponent } from './home/home.component';
import { ShipAddComponent } from './ship-add/ship-add.component';
import { ShipEditComponent } from './ship-edit/ship-edit.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {ReactiveFormsModule} from '@angular/forms';
import { DeliveryComponent } from './delivery/delivery.component';

@NgModule({
  declarations: [
    AppComponent,
    SidebarComponent,
    ShipsComponent,
    ShipmentsComponent,
    DeliveriesComponent,
    ShipComponent,
    HomeComponent,
    ShipAddComponent,
    ShipEditComponent,
    DeliveryComponent  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    MatButtonModule,
    MatInputModule,
    MatCardModule,
    MatCheckboxModule,
    MatDialogModule,
    MatSidenavModule,
    MatFormFieldModule,
    MatProgressSpinnerModule,
    MatIconModule,
    MatNativeDateModule,
    MatSelectModule,
    MatToolbarModule,
    MatSidenavModule,
    MatListModule,
    MatSidenavModule,
    BrowserAnimationsModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
