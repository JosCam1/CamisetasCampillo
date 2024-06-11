import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CamisetasComponent } from './paginas/camisetas/camisetas.component';
import { LigasComponent } from './paginas/ligas/ligas.component';
import { MarcasComponent } from './paginas/marcas/marcas.component';
import { GestionLigasComponent } from './paginas/gestionLigas/gestionLigas.component';
import { GestionMarcasComponent } from './paginas/gestionMarcas/gestionMarcas.component';
import { GestionCamisetasComponent } from './paginas/gestionCamisetas/gestionCamisetas.component';
import { GestionEquiposComponent } from './paginas/gestionEquipos/gestionEquipos.component';
import { LoginComponent } from './paginas/login/login.component';
import { RegistroComponent } from './paginas/registro/registro.component';
import { LoginService } from './servicios/login.service';
import { authGuard } from './guards/auth.guard';
import { ErrorComponent } from './paginas/error/error.component';
import { MicuentaComponent } from './paginas/micuenta/micuenta.component';
import { PedidosComponent } from './paginas/pedidos/pedidos.component';
import { TodosPedidosComponent } from './paginas/todosPedidos/todosPedidos.component';
import { CreacionUsuariosComponent } from './paginas/creacionUsuarios/creacionUsuarios.component';

const routes: Routes = [
  {
    path: '',
    component: CamisetasComponent
  },
  {
    path: 'ligas',
    component: LigasComponent
  },
  {
    path: 'marcas',
    component: MarcasComponent
  },
  {
    path: 'gestionLigas',
    component: GestionLigasComponent,
    canActivate: [authGuard], data: { expectedRole: 'Admin' }
  },
  {
    path: 'gestionMarcas',
    component: GestionMarcasComponent,
    canActivate: [authGuard], data: { expectedRole: 'Admin' }
  },
  {
    path: 'pedidos',
    component: PedidosComponent,
    canActivate: [authGuard], data: { expectedRole: 'Cliente' }
  },
  {
    path:'todos-pedidos',
    component:TodosPedidosComponent,
    canActivate: [authGuard], data: {expectedRole: 'Admin'}
  },
  {
    path:'creacionUsuarios',
    component:CreacionUsuariosComponent,
    canActivate: [authGuard], data: {expectedRole: 'SuperAdmin'}
  },
  {
    path: 'gestionCamisetas',
    component: GestionCamisetasComponent,
    canActivate: [authGuard], data: { expectedRole: 'Admin' }
  },
  {
    path: 'gestionEquipos',
    component: GestionEquiposComponent,
    canActivate: [authGuard], data: { expectedRole: 'Admin' }
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'registro',
    component: RegistroComponent
  },
  {
    path: 'micuenta',
    component:MicuentaComponent
  },
  {
    path: 'error', 
    component: ErrorComponent
  },
  {
    path: '**',
    redirectTo: '/error'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
