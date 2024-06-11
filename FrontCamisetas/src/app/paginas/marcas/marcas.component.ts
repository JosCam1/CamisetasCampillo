import { Component, OnInit } from '@angular/core';
import { Marca } from '../../modelos/marca';
import { Usuario } from '../../modelos/usuario';
import { Subscription } from 'rxjs';
import { MarcasService } from '../../servicios/marcas.service';
import { LoginService } from '../../servicios/login.service';

@Component({
  selector: 'app-marcas',
  templateUrl: './marcas.component.html',
  styleUrls: ['./marcas.component.css']
})
export class MarcasComponent implements OnInit {
  marcas:Marca[] = [];
  usuario: Usuario | null = null;
  private subscription: Subscription = new Subscription();

  constructor(private servicio:MarcasService, private servicioUsuarios:LoginService) { }

  ngOnInit() {
    this.loadMarcas();
    this.servicioUsuarios.getUsuario().subscribe(
       Usuario => {
        this.usuario = Usuario;
      },
      (error) => {
        console.error("Error encontrando usuario: ", error);
      }
    )
  }
  loadMarcas() {
    this.subscription.add(
      this.servicio.obtenerMarcas().subscribe(
        (data: Marca[]) => {
          this.marcas = data;
        },
        (error) => {
          console.error("Error encontrando marcas: ", error);
        }
      )
    );
  }

  verMarca(id:number){

  }

  borrarMarca(id:number){
    if (confirm("¿Estás seguro de que quieres eliminar la marca?")) {
      this.subscription.add(
        this.servicio.eliminarMarca(id).subscribe(
          () => {
            alert("Marca eliminada exitosamente!");
            this.loadMarcas();
          },
          error => {
            console.error("Error eliminando marca:", error);
            alert("¡No se pudo eliminar la marca!");
          }
        )
      );
    }
  }

}
