import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class ParkingService {

  http = inject(HttpClient);
  minAmount: Number = 100;

  getNumberPlates(): any {
    return this.http.get("http://localhost:8080/api/number-plates");
  }

  startParkingSession(formValue: any) {
    return this.http.post("http://localhost:8080/api/parking-sessions", formValue);
  }

  addNumberPlate(formValue: any) {
    return this.http.post("http://localhost:8080/api/number-plates", formValue);
  }

  finishParkingSession() {
    return this.http.get("http://localhost:8080/api/parking-sessions");
  }

  getBalance() {
    return this.http.get("http://localhost:8080/api/current-accounts/balance");
  }

  addBalance(formValue: any) {
    return this.http.post("http://localhost:8080/api/current-accounts/balance", formValue);
  }

  validNumberPlateFormat(number: string): Boolean {

    const sanitizedNumber = number.toUpperCase().replace(/[\s-]/g, "");

    const pattern1 = /^[A-Z]{2}[0-9]{3}[A-Z]{2}$/;
    const pattern2 = /^[A-Z]{3}[0-9]{3}$/;

    return pattern1.test(sanitizedNumber) || pattern2.test(sanitizedNumber);
  }

  validMinAmount(amount: Number): Boolean {

    return amount >= this.minAmount;
  }


}
