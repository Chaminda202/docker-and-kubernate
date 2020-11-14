import { AuthService } from './../shared/auth.service';
import { LoginRequest } from './login-request.model';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { throwError } from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public loginForm: FormGroup;
  public isError: boolean;
  private loginRequest: LoginRequest;
  public registerSuccessMessage: string;

  constructor(private authService: AuthService, private router: Router,
              private activatedRoute: ActivatedRoute, private toastrService: ToastrService) {
    this.activatedRoute.queryParams.subscribe(params => {
      if (params.registered !== 'undefined' && params.registered === 'true') {
        this.toastrService.success('Signup Successful');
        this.registerSuccessMessage = 'Please Check your inbox for activation email activate your account before you Login!';
      }
    });
  }

  ngOnInit(): void {
    this.initializeLoginForm();
    this.initializeLoginRequest();
  }

  onLogin() {
    this.loginRequest.username = this.username.value;
    this.loginRequest.password = this.password.value;

    this.authService.login(this.loginRequest).subscribe(() => {
      this.clear();
      this.isError = false;
      this.router.navigateByUrl('/');
      this.toastrService.success('Login Successful');

    }, error => {
      console.log(JSON.stringify(error));
      this.isError = true;
      throwError(error);
    });
  }

  private initializeLoginForm() {
    this.loginForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    });
  }

  private initializeLoginRequest() {
    this.loginRequest = {
      username: '',
      password: ''
    };
  }

  private clear() {
    this.initializeLoginForm();
    this.initializeLoginRequest();
  }

  get username() {
    return this.loginForm.get('username');
  }

  get password() {
    return this.loginForm.get('password');
  }
}
