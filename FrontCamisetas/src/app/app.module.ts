import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './includes/navbar/navbar.component';
import { FooterComponent } from './includes/footer/footer.component';
import { CamisetasComponent } from './paginas/camisetas/camisetas.component';
import { LigasComponent } from './paginas/ligas/ligas.component';
import { MarcasComponent } from './paginas/marcas/marcas.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CamisetasService } from './servicios/camisetas.service';
import { HttpClientModule } from '@angular/common/http';
import { GestionCamisetasComponent } from './paginas/gestionCamisetas/gestionCamisetas.component';
import { GestionEquiposComponent } from './paginas/gestionEquipos/gestionEquipos.component';
import { GestionMarcasComponent } from './paginas/gestionMarcas/gestionMarcas.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { GestionLigasComponent } from './paginas/gestionLigas/gestionLigas.component';
import { LoginComponent } from './paginas/login/login.component';
import { RegistroComponent } from './paginas/registro/registro.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { ErrorComponent } from './paginas/error/error.component';
import { MicuentaComponent } from './paginas/micuenta/micuenta.component';
import { PedidosComponent } from './paginas/pedidos/pedidos.component';
import { TodosPedidosComponent } from './paginas/todosPedidos/todosPedidos.component';
import { CreacionUsuariosComponent } from './paginas/creacionUsuarios/creacionUsuarios.component';

@NgModule({
  declarations: [
    AppComponent, NavbarComponent, FooterComponent, CamisetasComponent,
    LigasComponent, MarcasComponent, GestionCamisetasComponent, GestionEquiposComponent,
    GestionMarcasComponent, GestionLigasComponent, LoginComponent, RegistroComponent, 
    ErrorComponent, MicuentaComponent, PedidosComponent, TodosPedidosComponent, CreacionUsuariosComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    NgxPaginationModule
  ],
  providers: [
    provideClientHydration()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
