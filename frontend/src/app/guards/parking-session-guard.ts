import { CanActivateFn, Router } from '@angular/router';
import { ParkingService } from '../services/parking-service';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth-service';
import { catchError, map, of } from 'rxjs';

export const parkingSessionGuard: CanActivateFn = (route, state) => {
 
  const authService = inject(AuthService);
  const router = inject(Router);
  return authService.userHasSessionStarted().pipe(
    map((response: any) => {
      if (response.status === 200) {
        return true;
      } else {
        router.navigateByUrl('/parking');
        return false;
      }
    }),
    catchError((error) => {
      router.navigateByUrl('/parking');
      return of(false);
    })
  );
};
