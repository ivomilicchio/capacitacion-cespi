import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class ParkingService {

  http = inject(HttpClient);

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

}
