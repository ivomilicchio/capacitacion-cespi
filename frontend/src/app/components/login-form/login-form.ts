import { HttpClient } from '@angular/common/http';
import { Component, inject, Inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-form',
  imports: [ReactiveFormsModule],
  templateUrl: './login-form.html',
  styleUrl: './login-form.css'
})
export class LoginForm {

  http = inject(HttpClient);
  router = inject(Router);

  loginForm: FormGroup = new FormGroup({
    phoneNumber: new FormControl(""),
    password: new FormControl(""),
  });

  onLogIn() {
    const formValue = this.loginForm.value;
    this.http.post("http://localhost:8080/api/auth/login", formValue).subscribe({
      next: (result) => {
        console.log(result)
        this.router.navigateByUrl("/parking")
        
      },
      error: (error) => {
        alert(error.error)
      }
    })
  }

}
