import { Routes } from '@angular/router';
import { LoginForm } from './components/login-form/login-form';
import { ParkingForm } from './components/parking-form/parking-form';

export const routes: Routes = [

    {
        path: '',
        component: LoginForm,
        title: 'SEM'
    },
    {
        path: 'parking',
        component: ParkingForm,
        title: 'SEM'
    }
];
