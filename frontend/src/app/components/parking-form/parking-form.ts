import { Component, inject, OnInit } from '@angular/core';
import { ParkingService } from '../../services/parking-service';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';


declare var bootstrap: any;

@Component({
  selector: 'app-parking-form',
  imports: [ReactiveFormsModule],
  templateUrl: './parking-form.html',
  styleUrl: './parking-form.css'
})
export class ParkingForm implements OnInit {

  service = inject(ParkingService);
  router = inject(Router);
  numberPlates: Set<String> = new Set();
  balance: number | undefined;

  parkingForm: FormGroup = new FormGroup({
    numberPlate: new FormControl("Seleccione la patente", [Validators.required])
  });

  numberPlateForm: FormGroup = new FormGroup({
    number: new FormControl("", [Validators.required]),
  });

  ngOnInit(): void {
    this.service.getNumberPlates().subscribe({
      next: (result: any) => {
        this.numberPlates = new Set(result.numberPlates);
      }
    });
    this.service.getBalance().subscribe({
      next: (result: any) => {
        this.balance = result.balance;
      }
    });
  }

  onSubmitParkingForm() {
    this.service.startParkingSession(this.parkingForm.value).subscribe({
      next: (result: any) => {
        this.router.navigateByUrl('/parking-session')
      }
    });

  }

  onSubmitNumberPlateForm() {
    this.service.addNumberPlate(this.numberPlateForm.value).subscribe({
      next: (result: any) => {
        this.numberPlates.add(result.number);

      }
    });
    const modalEl = document.getElementById('numberPlateModal');
    if (modalEl) {
      const modalInstance = bootstrap.Modal.getInstance(modalEl) || new bootstrap.Modal(modalEl);
      modalInstance.hide();
    }
  }

}
