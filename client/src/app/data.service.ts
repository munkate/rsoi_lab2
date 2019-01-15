import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Ship} from './ship/ship';
import {map, publish, refCount} from 'rxjs/operators';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  apiURL = 'http://localhost:8085/api';
  token = localStorage.getItem('token');
  headerName = 'usertoken';

  constructor(private http: HttpClient) { }
  login(login, password) {
    return this.http.get(`http://localhost:8085/api/authentification?login=${login}&password=${password}`);
  }

  getShip(id) {
    return this.http.get(`${this.apiURL}/ships/${id}`);
  }
  getShips() {
    return this.http.get(`${this.apiURL}/ships?page=0&size=20`);
  }
  createShip(ship) {
    if (localStorage.getItem('token') != null) {
    return this.http.post(`${this.apiURL}/ships/ship`, ship, {headers: {'usertoken': localStorage.getItem('token')}}); } else { return this.http.post(`${this.apiURL}/ships/ship`, ship); }
  }

  updateShip(ship) {
    if (localStorage.getItem('token') != null) {
    return this.http.post(`${this.apiURL}/edit/ship`, ship, {headers: {'usertoken': localStorage.getItem('token')}}); } else { return this.http.post(`${this.apiURL}/edit/ship`, ship); }

  }

  deleteShip(id) {
    if (localStorage.getItem('token') != null) {
    return this.http.delete(`${this.apiURL}/ships/delete/${id}`, {headers: {'usertoken': localStorage.getItem('token')}}); } else { return this.http.delete(`${this.apiURL}/ships/delete/${id}`); }
  }

  getShipments() {
    if (localStorage.getItem('token') != null) {
    return this.http.get(this.apiURL + 'shipments', {headers: {'usertoken': localStorage.getItem('token')}}); } else { return this.http.get(this.apiURL + 'shipments'); }
  }
  getShipment(id) {
    if (localStorage.getItem('token') != null) {
    return this.http.get(`${this.apiURL}/shipments/${id}`, {headers: {'usertoken': localStorage.getItem('token')}}); } else { return this.http.get(`${this.apiURL}/shipments/${id}`); }
  }
  getDelivery(id) {
    if (localStorage.getItem('token') != null) {
    return this.http.get(`${this.apiURL}/users/1/deliveries/${id}`, {headers: {'usertoken': localStorage.getItem('token')}}); } else { return this.http.get(`${this.apiURL}/users/1/deliveries/${id}`); }
  }
  getDeliveries(user_id) {
    if (localStorage.getItem('token') != null) {
    return this.http.get(`${this.apiURL}/users/${user_id}/deliveries?page=0&size=20`, {headers: {'usertoken': localStorage.getItem('token')}}); } else { return this.http.get(`${this.apiURL}/users/${user_id}/deliveries?page=0&size=20`); }
  }
  deleteDelivery(user_id, id) {
    if (localStorage.getItem('token') != null) {
    return this.http.delete(`${this.apiURL}/delete/users/${user_id}/deliveries/${id}`, {headers: {'usertoken': this.token}}); } else { return this.http.delete(`${this.apiURL}/delete/users/${user_id}/deliveries/${id}`); }
  }
  createDelivery(user_id, delivery) {
    if (localStorage.getItem('token') != null) {
    return this.http.post(`${this.apiURL}/users/1/delivery`, delivery, {headers: {'usertoken': this.token}}); } else { return this.http.post(`${this.apiURL}/users/1/delivery`, delivery); }
  }
  updateDelivery(delivery) {
    if (localStorage.getItem('token') != null) {
    return this.http.patch(`${this.apiURL}/deliveries/editdelivery`, delivery, {headers: {'usertoken': this.token}}); } else { return this.http.patch(`${this.apiURL}/deliveries/editdelivery`, delivery); }
  }
  createShipment(shipment) {
    if (localStorage.getItem('token') != null) {
    return this.http.post(`${this.apiURL}/shipments/create`, shipment, {headers: {'usertoken': this.token}}); } else { return this.http.post(`${this.apiURL}/shipments/create`, shipment); }
  }
  updateShipment(shipment) {
    if (localStorage.getItem('token') != null) {
    return this.http.post(`${this.apiURL}/shipments/edit`, shipment, {headers: {'usertoken': this.token}}); } else { return this.http.post(`${this.apiURL}/shipments/edit`, shipment); }
  }
  deleteShipment(id) {
    if (localStorage.getItem('token') != null) {
    return this.http.delete(`${this.apiURL}/shipments/delete/${id}`, {headers: {'usertoken': this.token}}); } else { return this.http.delete(`${this.apiURL}/shipments/delete/${id}`); }
  }

}
