import { Component, inject } from '@angular/core';
import { AuthService } from '../../services/auth-service';
import { Router } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-register-form',
  imports: [ReactiveFormsModule],
  templateUrl: './register-form.html',
  styleUrl: './register-form.css'
})
export class RegisterForm {

  service = inject(AuthService);
  router = inject(Router);

  registerForm: FormGroup = new FormGroup({
    phoneNumber: new FormControl("", [Validators.required]),
    mail: new FormControl("", [Validators.required, Validators.email]),
    password: new FormControl("", Validators.required),
  });

    register() {
    this.service.register(this.registerForm.value).subscribe({
      next: (result: any) => {
        localStorage.setItem('token', result.token);
        this.router.navigateByUrl('/parking');
      }
    })

  }

}
