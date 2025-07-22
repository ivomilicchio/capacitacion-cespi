import { Component, inject } from '@angular/core';
import { ParkingService } from '../../services/parking-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-parking-session',
  imports: [],
  templateUrl: './parking-session.html',
  styleUrl: './parking-session.css'
})
export class ParkingSession {

  service = inject(ParkingService)
  router = inject(Router);

  onSubmit() {
    this.service.finishParkingSession().subscribe({ 
        next: (result: any) => {
        console.log(result)
        
      }
    });
    this.router.navigateByUrl('/parking');
  }
}
