import { Component, inject } from '@angular/core';
import { AuthService } from '../../services/auth-service';
import { Router } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-register-form',
  imports: [ReactiveFormsModule],
  templateUrl: './register-form.html',
  styleUrl: './register-form.css'
})
export class RegisterForm {

  service = inject(AuthService);
  router = inject(Router);
  toastr = inject(ToastrService);

  registerForm: FormGroup = new FormGroup({
    phoneNumber: new FormControl("", [Validators.required]),
    mail: new FormControl("", [Validators.required, Validators.email]),
    password: new FormControl("", Validators.required),
  });

  register() {
    if (!this.service.validPhoneNumber(this.registerForm.value.phoneNumber)) {
      this.toastr.warning("El número de teléfono ingresado tiene un formato inválido");
    }
    else {
      this.service.register(this.registerForm.value).subscribe({
        next: (result: any) => {
          localStorage.setItem('token', result.token);
          this.router.navigateByUrl('/parking');
          this.toastr.success("Usuario creado con éxito")
        }
      })

    }
  }
}
