import { Component, inject, OnInit } from '@angular/core';
import { ParkingService } from '../../services/parking-service';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { BalanceService } from '../../services/balance-service';
import { NumberPlateService } from '../../services/number-plate-service';


declare var bootstrap: any;

@Component({
  selector: 'app-parking-form',
  imports: [ReactiveFormsModule],
  templateUrl: './parking-form.html',
  styleUrl: './parking-form.css'
})
export class ParkingForm implements OnInit {

  balanceForm: FormGroup;
  numberPlateForm: FormGroup;
  parkingService = inject(ParkingService);
  balanceService = inject(BalanceService);
  numberPlateService = inject(NumberPlateService);
  router = inject(Router);
  toastr = inject(ToastrService);
  numberPlates: Set<String> = new Set();
  balance: number | undefined;

  parkingForm: FormGroup = new FormGroup({
    numberPlate: new FormControl("Seleccione la patente", [Validators.required])
  });

    constructor(
    private modalService: NgbModal,
    private fb: FormBuilder
  ) {
    this.balanceForm = this.fb.group({
      balance: ['', [Validators.required, Validators.min(100)]]
    });
    this.numberPlateForm = this.fb.group({
      number: ['', [Validators.required]]
    });
  }


  ngOnInit(): void {
    this.numberPlateService.getNumberPlates().subscribe({
      next: (result: any) => {
        this.numberPlates = new Set(result.numberPlates);
      }
    });
    this.balanceService.getBalance().subscribe({
      next: (result: any) => {
        this.balance = result.balance;
      }
    });
  }

  onSubmitParkingForm() {
    if (this.parkingForm.value.numberPlate == "Seleccione la patente") {
      this.toastr.warning("Debe seleccionar una patente");
    }
    else {
      this.parkingService.startParkingSession(this.parkingForm.value).subscribe({
        next: (result: any) => {
          this.router.navigateByUrl('/parking-session');
          this.toastr.success("Servicio de estacionamiento iniciado con éxito");
        }
      });
    }
  }

  openModal(content: any) {
    this.modalService.open(content, { centered: true });
  }

  onSubmitNumberPlateForm() {
    if (this.numberPlateService.validNumberPlateFormat(this.numberPlateForm.value.number)) {
      this.numberPlateService.addNumberPlate(this.numberPlateForm.value).subscribe({
        next: (result: any) => {
          this.numberPlates.add(result.number);
          this.toastr.success("Patente registrada con éxito");
        }
      });
      this.modalService.dismissAll();
    }
    else {
      this.toastr.warning("Formato de patente inválido")
    }
  }

  onSubmitBalanceForm() {
    if (this.balanceForm.valid) {
      this.balanceService.addBalance(this.balanceForm.value).subscribe({
        next: (result: any) => {
          this.balance = result.balance;
          this.toastr.success("Fondos añadidos con éxito");
        }
      });
      this.modalService.dismissAll();
    }
    else {
      this.toastr.warning("El monto mínimo de carga es de $" + this.balanceService.minAmount);
    }
  }
}
