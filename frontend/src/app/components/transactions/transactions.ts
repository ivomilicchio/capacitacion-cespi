import { Component, inject, OnInit } from '@angular/core';
import { TransactionsService } from '../../services/transactions-service';


interface ParkingSessionTransaction {
  day: String;
  startHour: string;
  endHour: string;
  amount: number;
}

interface BalanceTopUpTransaction {
  day: String;
  hour: string;
  amount: number;
}

@Component({
  selector: 'app-transactions',
  imports: [],
  templateUrl: './transactions.html',
  styleUrl: './transactions.css'
})
export class Transactions implements OnInit {

  parkingSessionHistory: ParkingSessionTransaction[] = [];

  BalanceTopUpHistory: BalanceTopUpTransaction[] = [];


  service = inject(TransactionsService);

  ngOnInit(): void {
    this.service.getParkingSessionHistory().subscribe({
      next: (result: any) => {
        this.parkingSessionHistory = result.parkingSessions;

      }
    });
    this.service.getBalanceTopUpHistory().subscribe({
      next: (result: any) => {
        this.BalanceTopUpHistory = result.balanceTopUps;

      }
    });
  }



}
