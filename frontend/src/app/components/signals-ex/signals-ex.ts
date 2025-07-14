import { Component, computed, signal } from '@angular/core';

@Component({
  selector: 'app-signals-ex',
  imports: [],
  templateUrl: './signals-ex.html',
  styleUrl: './signals-ex.css'
})
export class SignalsEx {

  nombre = signal<string>("Ivo");
  apellido = signal<string>("Milicchio");


   nombreCompleto = computed(() => {
    return this.nombre() + ' ' + this.apellido();
  });

}
