import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { AbstractControl } from '@angular/forms';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import { catchError, Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  router = inject(Router);
  http = inject(HttpClient);

  login(formValue: any) {
    return this.http.post("http://localhost:8080/api/auth/login", formValue);
  }

  register(formValue: any) {
    return this.http.post("http://localhost:8080/api/auth/register", formValue);
  }

  isLoggedIn() {
    const token = localStorage.getItem('token');
    return (token && !this.isTokenExpired(token));
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigateByUrl('/login');

  }

  isTokenExpired(token: string): boolean {
    try {
      const payloadBase64 = token.split('.')[1];
      const payload = JSON.parse(atob(payloadBase64));
      const currentTime = Math.floor(Date.now() / 1000);
      if (payload.exp < currentTime) {
        localStorage.removeItem("token");
        return true;
      }
      return false;
    } catch (error) {
      console.error('Token inválido:', error);
      return true;
    }
  }

  userHasSessionStarted() {
    return this.http.get("http://localhost:8080/api/parking-sessions/started", {
      observe: 'response'
    });

  }
}