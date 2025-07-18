import { Component, inject } from '@angular/core';
import { ParkingService } from '../../services/parking-service';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-parking-form',
  imports: [ReactiveFormsModule],
  templateUrl: './parking-form.html',
  styleUrl: './parking-form.css'
})
export class ParkingForm {

  service = inject(ParkingService);
  numberPlates: String [];

  parkingForm: FormGroup = new FormGroup({
  numberPlate: new FormControl("Seleccione la patente", [Validators.required])
  });

  constructor() {
    this.numberPlates = this.service.getNumberPlates().subscribe({ 
        next: (result: any) => {
        this.numberPlates = result;
        
      }
    });
    
  }

  onSubmit() {
    this.service.startParkingSession(this.parkingForm.value).subscribe({ 
        next: (result: any) => {
        console.log(result)
        
      }
    });;
  }

}
