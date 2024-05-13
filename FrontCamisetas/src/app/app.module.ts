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

@NgModule({
  declarations: [
    AppComponent,NavbarComponent,FooterComponent,CamisetasComponent,LigasComponent,MarcasComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule
  ],
  providers: [
    provideClientHydration()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
