import { Routes } from '@angular/router';
import { SignalsEx } from './components/signals-ex/signals-ex';
import { ControlFlow } from './components/control-flow/control-flow';
import { Directivas } from './components/directivas/directivas';
import { GetApi } from './components/get-api/get-api';

export const routes: Routes = [

    {
        path: 'control-flow',
        component: ControlFlow,
        title: "Control Flow"
    },
    {

        path: 'directivas',
        component: Directivas,
        title: "Directivas"
    },
    {

        path: 'get-api',
        component: GetApi,
        title: 'Get API'
    },
];
