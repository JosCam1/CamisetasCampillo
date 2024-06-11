import { Component, OnInit } from '@angular/core';
import { Liga } from '../../modelos/liga';
import { LigasService } from '../../servicios/ligas.service';
import { Subscription } from 'rxjs';
import { LoginService } from '../../servicios/login.service';
import { Usuario } from '../../modelos/usuario';

@Component({
  selector: 'app-ligas',
  templateUrl: './ligas.component.html',
  styleUrls: ['./ligas.component.css']
})
export class LigasComponent implements OnInit {
  ligas:Liga[] = [];
  usuario: Usuario | null = null;
  private subscription: Subscription = new Subscription();

  constructor(private servicio:LigasService, private servicioUsuarios:LoginService) { }

  ngOnInit() {
    this.loadLigas();
    this.servicioUsuarios.getUsuario().subscribe(
       Usuario => {
        this.usuario = Usuario;
      },
      (error) => {
        console.error("Error encontrando usuario: ", error);
      }
    )
  }

  loadLigas() {
    this.subscription.add(
      this.servicio.obtenerLigas().subscribe(
        (data: Liga[]) => {
          this.ligas = data;
        },
        (error) => {
          console.error("Error encontrando ligas: ", error);
        }
      )
    );
  }

  verLiga(id:number){

  }

  borrarLiga(id:number){
    if (confirm("¿Estás seguro de que quieres eliminar la liga, todos los equipos que pertenezcan a la misma tambien se eliminaran?")) {
      this.subscription.add(
        this.servicio.eliminarLiga(id).subscribe(
          () => {
            alert("Liga eliminada exitosamente!");
            this.loadLigas();
          },
          error => {
            console.error("Error eliminando liga:", error);
            alert("¡No se pudo eliminar la liga!");
          }
        )
      );
    }
  }

}
