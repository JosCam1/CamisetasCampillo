import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CamisetasComponent } from './paginas/camisetas/camisetas.component';
import { LigasComponent } from './paginas/ligas/ligas.component';
import { MarcasComponent } from './paginas/marcas/marcas.component';

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
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
