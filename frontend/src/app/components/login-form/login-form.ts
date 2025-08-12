import { HttpClient } from '@angular/common/http';
import { Component, inject, Inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth-service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login-form',
  imports: [ReactiveFormsModule],
  templateUrl: './login-form.html',
  styleUrl: './login-form.css'
})
export class LoginForm {

  service = inject(AuthService);
  router = inject(Router);
  toastr = inject(ToastrService);

  loginForm: FormGroup = new FormGroup({
    phoneNumber: new FormControl("", [Validators.required]),
    password: new FormControl("", Validators.required),
  });

  login() {
    if (!this.service.validPhoneNumber(this.loginForm.value.phoneNumber)) {
      this.toastr.warning("El número de teléfono ingresado tiene un formato inválido");
    }
    else {
      this.service.login(this.loginForm.value).subscribe({
        next: (result: any) => {
          localStorage.setItem('token', result.token);
          this.userHasSessionStarted();
        }
      })
    }


  }

  userHasSessionStarted() {
    this.service.userHasSessionStarted().subscribe({
      next: (response: any) => {
        if (response.status === 200) {
          this.router.navigateByUrl('/parking-session');

        }
        else {
          this.router.navigateByUrl('/parking');
        }
      }
    })
  }
}