import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TransactionsService {

  http = inject(HttpClient);

  getParkingSessionHistory(): any {
    return this.http.get("http://localhost:8080/api/parking-sessions/history");
  }

}
