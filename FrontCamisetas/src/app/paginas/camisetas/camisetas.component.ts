import { Component, OnInit } from '@angular/core';
import { Camiseta } from '../../modelos/camiseta';
import { CamisetasService } from '../../servicios/camisetas.service';
import { LoginService } from '../../servicios/login.service';
import { Usuario } from '../../modelos/usuario';
import { Marca } from '../../modelos/marca';
import { MarcasService } from '../../servicios/marcas.service';

@Component({
  selector: 'app-camisetas',
  templateUrl: './camisetas.component.html',
  styleUrls: ['./camisetas.component.css']
})
export class CamisetasComponent implements OnInit {

  camisetas: Camiseta[] = [];
  filteredCamisetas: Camiseta[] = [];
  marcas: Marca[] = [];
  searchText: string = '';
  selectedMarca: string = '';
  currentPage = 1;
  itemsPerPage = 6;
  usuario: Usuario | null = null;

  constructor(
    private servicio: CamisetasService,
    private servicioLogin: LoginService,
    private marcasService: MarcasService
  ) { }

  ngOnInit() {
    this.obtenerCamisetas();
    this.obtenerMarcas();
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
        console.log('Camisetas obtenidas:', this.camisetas);
        this.applyFilters();
      },
      (error) => {
        console.error("Error encontrando camisetas: ", error);
      }
    );
  }

  obtenerMarcas() {
    this.marcasService.obtenerMarcas().subscribe(
      (data: Marca[]) => {
        this.marcas = data;
        console.log('Marcas obtenidas:', this.marcas);
      },
      (error) => {
        console.error("Error obteniendo marcas: ", error);
      }
    );
  }

  applyFilters() {
    this.filteredCamisetas = this.camisetas.filter(camiseta => {
      return camiseta.nombre.toLowerCase().includes(this.searchText.toLowerCase()) &&
        (this.selectedMarca === '' || camiseta.marca?.nombre === this.selectedMarca);
    });
  }

  setPage(page: number) {
    this.currentPage = page;
  }

  get pages(): number[] {
    const pageCount = Math.ceil(this.filteredCamisetas.length / this.itemsPerPage);
    return new Array(pageCount).fill(0).map((_, index) => index + 1);
  }

  borrarCamiseta(id: number) {
    if (confirm("¿Estás seguro de que quieres eliminar la camiseta? Se borrarán también de los pedidos existentes")) {
      this.servicio.eliminarCamiseta(id).subscribe(
        () => {
          alert("Camiseta eliminada exitosamente!");
          this.camisetas = this.camisetas.filter(camiseta => camiseta.id !== id);
          this.applyFilters(); 
        },
        error => {
          console.error("Error eliminando camiseta:", error);
          alert("¡No se pudo eliminar la camiseta!");
        }
      )
    }
  }

}