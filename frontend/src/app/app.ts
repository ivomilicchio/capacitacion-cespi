import { Component, inject, OnInit, signal } from '@angular/core';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { Footer } from './components/footer/footer';
import { Header } from './components/header/header';
import { AuthService } from './services/auth-service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Footer, Header],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {

  protected readonly title = signal('SEM');

}
