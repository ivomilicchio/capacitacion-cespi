import { Routes } from '@angular/router';
import { LoginForm } from './components/login-form/login-form';
import { ParkingForm } from './components/parking-form/parking-form';
import { authGuard } from './guards/auth-guard';

export const routes: Routes = [

    
    {
        path: '',
        redirectTo: 'login',
        pathMatch: 'full'
    },
    {
        path: 'login',
        component: LoginForm,
        title: 'SEM'
    },
    {
        path: 'parking',
        component: ParkingForm,
        title: 'SEM',
        canActivate:[authGuard]
    }
];
