import { Routes } from '@angular/router';
import { LoginForm } from './components/login-form/login-form';

export const routes: Routes = [

    {
        path: '',
        component: LoginForm,
        title: 'Login'
    },
];
