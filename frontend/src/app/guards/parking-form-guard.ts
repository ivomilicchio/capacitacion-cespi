import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth-service';
import { catchError, map, of } from 'rxjs';

export const parkingFormGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  return authService.userHasSessionStarted().pipe(
    map((response: any) => {
      if (response.status === 204) {
        return true;
      } else {
        router.navigateByUrl('/parking-session');
        return false;
      }
    }),
    catchError((error) => {
      return of(false);
    })
  );
};

