import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class ParkingService {

  http = inject(HttpClient);

  startParkingSession(formValue: any) {
    return this.http.post("http://localhost:8080/api/parking-sessions", formValue);
  }

  finishParkingSession() {
    return this.http.get("http://localhost:8080/api/parking-sessions");
  }
}
