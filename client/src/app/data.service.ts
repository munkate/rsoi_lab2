import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Ship} from './ship/ship';
import {map, publish, refCount} from 'rxjs/operators';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  apiURL = 'http://localhost:8085/api';

  constructor(private http: HttpClient) { }

  getShip(id) {
    return this.http.get(`${this.apiURL}/ships/${id}`);
  }
  getShips() {
    return this.http.get(`${this.apiURL}/ships?page=0&size=20`);
  }
  createShip(ship) {
    return this.http.post(`${this.apiURL}/ships/createship`, ship);
  }

  updateShip(ship) {
    return this.http.post(`${this.apiURL}/ships/edit`, ship);

  }

  deleteShip(id) {
    return this.http.delete(`${this.apiURL}/ships/delete/${id}`);
  }

  getShipments() {
    return this.http.get(this.apiURL + 'shipments');
  }


}
