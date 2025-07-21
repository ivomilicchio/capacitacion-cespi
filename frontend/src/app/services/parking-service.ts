import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class ParkingService {

  http = inject(HttpClient);
  router = inject(Router);

  getNumberPlates(): any {
    return this.http.get("http://localhost:8080/api/users/number-plates");
  }
  
  startParkingSession(formValue: any) {
    this.http.post("http://localhost:8080/api/parking-sessions", formValue).subscribe({ 
        next: (result: any) => {
        console.log(result)
        
      }
    });;
    this.router.navigateByUrl('/parking-session')
  }

  addNumberPlate(formValue: any) {
     this.http.post("http://localhost:8080/api/users/number-plates", formValue).subscribe({ 
        next: (result: any) => {
        console.log(result)
        
      }
    });;
    
  }
}
