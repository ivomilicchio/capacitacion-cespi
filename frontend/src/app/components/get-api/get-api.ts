import { HttpClient } from '@angular/common/http';
import { Component, inject, OnInit } from '@angular/core';

@Component({
  selector: 'app-get-api',
  imports: [],
  templateUrl: './get-api.html',
  styleUrl: './get-api.css'
})
export class GetApi implements OnInit {

  http = inject(HttpClient);
  resultado : any[] = [];

  ngOnInit(): void {
    this.getUsers();
  }


  getUsers() {

    this.http.get("http://localhost:8080/api/auth/prueba").subscribe((result:any) => {
      this.resultado = result;
      console.log(this.resultado);
    })
  }



}
