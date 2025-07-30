import { Routes } from '@angular/router';
import { LoginForm } from './components/login-form/login-form';
import { ParkingForm } from './components/parking-form/parking-form';
import { authGuard } from './guards/auth-guard';
import { ParkingSession } from './components/parking-session/parking-session';
import { parkingSessionGuard } from './guards/parking-session-guard';
import { parkingFormGuard } from './guards/parking-form-guard';
import { RegisterForm } from './components/register-form/register-form';
import { loginGuard } from './guards/login-guard';

export const routes: Routes = [

    {
        path: '',
        redirectTo: 'login',
        pathMatch: 'full'
    },
    {
        path: 'login',
        component: LoginForm,
        title: 'SEM',
        canActivate:[loginGuard]
    },
    {
        path: 'parking',
        component: ParkingForm,
        title: 'SEM',
        canActivate:[authGuard, parkingFormGuard]
    },
    {
        path: 'parking-session',
        component: ParkingSession,
        title: 'SEM',
        canActivate:[authGuard, parkingSessionGuard]
    },
    {
        path: 'register',
        component: RegisterForm,
        title: 'SEM',
        canActivate:[loginGuard]  
    },
];
