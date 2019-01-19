import {Component, NgModule, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, NgForm, Validators} from '@angular/forms';
import {DataService} from '../data.service';
import {ActivatedRoute, Router} from '@angular/router';
import {COOKIES_OPTIONS, CookiesOptionsService, CookiesService} from '@ngx-utils/cookies';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})

export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  login = '';
  password = '';
  token: string;

  constructor(private service: DataService, private route: ActivatedRoute,
              private router: Router, private formBuilder: FormBuilder, ) {
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      'login' : ['', [Validators.required]],
      'password' : ['', [Validators.required]],
    });
  }
  onFormSubmit(form: NgForm) {
    this.service.login(form['login'], form['password'])
      .subscribe(res => {
        this.token = res['token'];
        localStorage.setItem('token', this.token);
        console.log(localStorage.getItem('token'));
      }, (err) => {
        console.log(err);
        alert(err.body);
      });

  }

}
