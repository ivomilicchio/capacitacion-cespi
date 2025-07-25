import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, EMPTY, throwError } from 'rxjs';
import { ToastrService } from 'ngx-toastr';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const toastr = inject(ToastrService); 
  return next(req). pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status == 0) {
        toastr.error("No se puede establecer la conexión con el servidor. Inténtelo más tarde");
      }
      else {
        toastr.error(error.error.mensaje);
      } 
      return throwError(() => error);
    })
  );
};
