import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CamisetasComponent } from './paginas/camisetas/camisetas.component';
import { LigasComponent } from './paginas/ligas/ligas.component';
import { MarcasComponent } from './paginas/marcas/marcas.component';
import { GestionLigasComponent } from './paginas/gestionLigas/gestionLigas.component';
import { GestionMarcasComponent } from './paginas/gestionMarcas/gestionMarcas.component';
import { GestionCamisetasComponent } from './paginas/gestionCamisetas/gestionCamisetas.component';
import { GestionEquiposComponent } from './paginas/gestionEquipos/gestionEquipos.component';

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
    component: GestionLigasComponent
  },
  {
    path: 'gestionMarcas',
    component: GestionMarcasComponent
  },
  {
    path: 'gestionCamisetas',
    component: GestionCamisetasComponent
  },
  {
    path: 'gestionEquipos',
    component: GestionEquiposComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
