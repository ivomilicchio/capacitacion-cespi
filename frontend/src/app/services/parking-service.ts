import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ParkingService {

  http = inject(HttpClient);

  getNumberPlates(): any {
    return this.http.get("http://localhost:8080/api/users/number-plates");
  }
  
  startParkingSession(formValue: any) {
    return this.http.post("http://localhost:8080/api/parking-sessions", formValue);
  }
}
