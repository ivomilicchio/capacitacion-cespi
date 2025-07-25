import { ApplicationConfig, provideBrowserGlobalErrorListeners, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { tokenInterceptor } from './interceptor/token-interceptor';
import { errorInterceptor } from './interceptor/error-interceptor';
import { provideToastr } from 'ngx-toastr';
import { provideAnimations } from '@angular/platform-browser/animations';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideAnimations(),   
    provideToastr({
      positionClass: 'toast-top-center',
      preventDuplicates: true,
      progressBar: true,
      timeOut: 5000
    }), 
    provideHttpClient(withInterceptors([tokenInterceptor, errorInterceptor]))
  ]
};
