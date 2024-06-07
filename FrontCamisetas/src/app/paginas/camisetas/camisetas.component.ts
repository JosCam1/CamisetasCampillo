import { Component, OnInit } from '@angular/core';
import { Camiseta } from '../../modelos/camiseta';
import { CamisetasService } from '../../servicios/camisetas.service';
import { LoginService } from '../../servicios/login.service';
import { Usuario } from '../../modelos/usuario';

@Component({
  selector: 'app-camisetas',
  templateUrl: './camisetas.component.html',
  styleUrls: ['./camisetas.component.css']
})
export class CamisetasComponent implements OnInit {

  camisetas: Camiseta[] = [];
  currentPage = 1; 
  itemsPerPage = 6; 
  usuario: Usuario | null = null;
  constructor(private servicio: CamisetasService, private servicioLogin:LoginService) { }

  ngOnInit() {
    this.obtenerCamisetas();
    this.servicioLogin.getUsuario().subscribe(
      usuario => {
        this.usuario = usuario;
      },
      error => {
        console.error("Error obteniendo usuario:", error);
      }
    );
  }
  obtenerCamisetas() {
    this.servicio.obtenerCamisetas().subscribe(
      (data: Camiseta[]) => {
        this.camisetas = data;
      },
      (error) => {
        console.error("Error encontrando camisetas: ", error)
      }
    )
  }

  // Método para cambiar de página
  setPage(page: number) {
    this.currentPage = page;
  }
  get pages(): number[] {
    const pageCount = Math.ceil(this.camisetas.length / this.itemsPerPage);
    return new Array(pageCount).fill(0).map((_, index) => index + 1);
  }
}