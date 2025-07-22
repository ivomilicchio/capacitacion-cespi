import { Component, inject, OnInit, signal } from '@angular/core';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { Footer } from './components/footer/footer';
import { Header } from './components/header/header';
import { AuthService } from './services/auth-service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  imports: [RouterLink, RouterOutlet, Footer, Header],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {

  protected readonly title = signal('SEM');

  service = inject(AuthService);
  http = inject(HttpClient);
  router = inject(Router);

  ngOnInit(): void {
    if (!this.service.isLoggedIn()) {
      this.router.navigateByUrl('/login');
    }
    else {
      this.service.userHasSessionStarted().subscribe({
        next: (result: any) => {
          if (result == null) {
            this.router.navigateByUrl('parking')
          }
          else {
            this.router.navigateByUrl('/parking-session')
          }

        }
      })
    }
  }
}
