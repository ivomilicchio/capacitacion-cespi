import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class BalanceService {

  http = inject(HttpClient);
  minAmount: Number = 100;

  getBalance() {
    return this.http.get("http://localhost:8080/api/current-accounts/balance");
  }

  addBalance(formValue: any) {
    return this.http.post("http://localhost:8080/api/current-accounts/balance", formValue);
  }
}

