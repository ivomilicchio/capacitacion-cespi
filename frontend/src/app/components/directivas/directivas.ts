import { NgClass, NgStyle, UpperCasePipe } from '@angular/common';
import { AfterContentChecked, AfterContentInit, AfterViewChecked, AfterViewInit, Component, OnDestroy, OnInit, signal } from '@angular/core';

@Component({
  selector: 'app-directivas',
  imports: [NgClass, NgStyle, UpperCasePipe],
  templateUrl: './directivas.html',
  styleUrl: './directivas.css'
})
export class Directivas implements OnInit, AfterContentInit, AfterContentChecked, AfterViewInit, AfterViewChecked, OnDestroy {

  username: string = "Bob";

  constructor() {
    console.log("Constructor");
  }

  ngOnInit(): void {
      console.log("OnInit")
  }

  ngAfterContentChecked(): void {      
    console.log("AfterContentChecked")
  }

  ngAfterContentInit(): void {
    console.log("AfterContentInit")
  }

  ngAfterViewChecked(): void {
    console.log("AfterViewChecked")
  }

  ngAfterViewInit(): void {
    console.log("AfterViewInit")
  }

  ngOnDestroy(): void {
    console.log("OnDestroy")
  }
}
