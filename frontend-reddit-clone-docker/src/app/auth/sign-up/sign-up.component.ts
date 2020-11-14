import { AuthService } from './../shared/auth.service';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { SingUpRequest } from './sing-up-request.model';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { throwError } from 'rxjs';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {
  public singupForm: FormGroup;
  private singUpRequest: SingUpRequest;

  constructor(private authService: AuthService, private router: Router,
              private toastrService: ToastrService) {
  }

  ngOnInit(): void {
    this.initializeSingupForm();
    this.initializeSingUpRequest();
  }

  onSignUp() {
    this.singUpRequest.email = this.email.value;
    this.singUpRequest.username = this.username.value;
    this.singUpRequest.password = this.password.value;

    this.authService.singUp(this.singUpRequest).subscribe(() => {
      this.clear(); // no need to clear details
      this.router.navigate(['/login'], {queryParams: {registered: 'true'}});
    }, (error) => {
      this.toastrService.error('Registration Failed. Please try again...!');
      throwError(error);
    });
  }

  private initializeSingupForm() {
    this.singupForm = new FormGroup({
      username: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', Validators.required)
    });
  }

  private initializeSingUpRequest() {
    this.singUpRequest = {
      email: '',
      username: '',
      password: ''
    };
  }

  private clear() {
    this.initializeSingupForm();
    this.initializeSingUpRequest();
  }

  get username() {
    return this.singupForm.get('username');
  }

  get email() {
    return this.singupForm.get('email');
  }

  get password() {
    return this.singupForm.get('password');
  }
}
