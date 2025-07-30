import { Component, inject } from '@angular/core';
import { ParkingService } from '../../services/parking-service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-parking-session',
  imports: [],
  templateUrl: './parking-session.html',
  styleUrl: './parking-session.css'
})
export class ParkingSession {

  service = inject(ParkingService)
  router = inject(Router);
  toastr = inject(ToastrService);

  onSubmit() {
    this.service.finishParkingSession().subscribe({ 
        next: (result: any) => {
        this.router.navigateByUrl('/parking');
        this.toastr.success("Servicio de estacionamiento finalizado con éxito");
      }
    });

  }
}
