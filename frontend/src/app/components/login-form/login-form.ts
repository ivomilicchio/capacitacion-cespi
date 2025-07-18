import { HttpClient } from '@angular/common/http';
import { Component, inject, Inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth-service';

@Component({
  selector: 'app-login-form',
  imports: [ReactiveFormsModule],
  templateUrl: './login-form.html',
  styleUrl: './login-form.css'
})
export class LoginForm {

  http = inject(HttpClient);
  router = inject(Router);
  service = inject(AuthService);

  loginForm: FormGroup = new FormGroup({
    phoneNumber: new FormControl("", [Validators.required]),
    password: new FormControl("", Validators.required),
  });

  onSubmit() {
    this.service.login(this.loginForm.value);
  }

}
