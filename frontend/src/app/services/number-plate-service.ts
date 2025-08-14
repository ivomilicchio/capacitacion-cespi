import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class NumberPlateService {

  http = inject(HttpClient);

  getNumberPlates(): any {
    return this.http.get("http://localhost:8080/api/number-plates");
  }

  addNumberPlate(formValue: any) {
    return this.http.post("http://localhost:8080/api/number-plates", formValue);
  }

  validNumberPlateFormat(number: string): Boolean {

    const sanitizedNumber = number.toUpperCase().replace(/[\s-]/g, "");

    const pattern1 = /^[A-Z]{2}[0-9]{3}[A-Z]{2}$/;
    const pattern2 = /^[A-Z]{3}[0-9]{3}$/;

    return pattern1.test(sanitizedNumber) || pattern2.test(sanitizedNumber);
  }

}
