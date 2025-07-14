import { computeMsgId } from '@angular/compiler';
import { Component } from '@angular/core';

@Component({
  selector: 'app-control-flow',
  imports: [],
  templateUrl: './control-flow.html',
  styleUrl: './control-flow.css'
})
export class ControlFlow {

  estudiantes: any[] = [
    {name: 'Ivo', ciudad: 'La Plata', isActive: true},
    {name: 'Momo', ciudad: 'Los Hornos', isActive: false},
    {name: 'Toto', ciudad: 'Ensenada', isActive: true},
  ];

}