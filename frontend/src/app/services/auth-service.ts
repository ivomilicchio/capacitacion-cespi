import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { AbstractControl } from '@angular/forms';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  router = inject(Router);
  http = inject(HttpClient);
  numberPlates: String[];

  constructor() {
    this.numberPlates = [];
  }

  login(formValue: any) {
      this.http.post("http://localhost:8080/api/auth/login", formValue).subscribe({
      next: (result: any) => {
        localStorage.setItem('token', result.token);
        this.router.navigateByUrl("/parking");
        
      },
      error: (error) => {
        alert(error.error);
      }
    })
  }

  isLoggedIn() {
    const token = localStorage.getItem('token');
    return !!token;
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigateByUrl('/login');

  }

  getNumberPlates() {
    return this.numberPlates;
  }
  
}
