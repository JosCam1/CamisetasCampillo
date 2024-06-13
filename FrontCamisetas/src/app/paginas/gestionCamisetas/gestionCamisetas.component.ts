import { Component, OnInit, OnDestroy } from '@angular/core';
import { Camiseta } from '../../modelos/camiseta';
import { Subscription } from 'rxjs';
import { CamisetasService } from '../../servicios/camisetas.service';
import { ActivatedRoute } from '@angular/router';
import { LigasService } from '../../servicios/ligas.service';
import { Liga } from '../../modelos/liga';
import { MarcasService } from '../../servicios/marcas.service';
import { Marca } from '../../modelos/marca';
import { EquiposService } from '../../servicios/equipos.service';
import { Equipo } from '../../modelos/equipo';

@Component({
  selector: 'app-gestionCamisetas',
  templateUrl: './gestionCamisetas.component.html',
  styleUrls: ['./gestionCamisetas.component.css']
})
export class GestionCamisetasComponent implements OnInit {
  cuadroNombre: string = '';
  cuadroPrecio: number = 0;
  cuadroDescripcion: string = '';
  camisetas: Camiseta[] = [];
  fotoBase64: string = '';
  editando: boolean = false;
  camisetaId!: number;
  equipos: Equipo[] = [];
  marcas: Marca[] = [];
  equipoSeleccionadoId!: number;
  marcaSeleccionadaId!: number;
  ligaSeleccionadaId!: number;
  private subscription: Subscription = new Subscription();

  constructor(private servicio: CamisetasService, private route: ActivatedRoute,
    private servicioMarcas: MarcasService, private servicioEquipos: EquiposService,
    private ligasService: LigasService) { }

  ngOnInit(): void {
    this.loadEquipos();
    this.loadMarcas();
    this.loadCamisetas();
  }

  loadCamisetas() {
    this.servicio.obtenerCamisetas().subscribe(
      (data: Camiseta[]) => {
        console.log('Raw data:', data);
        this.camisetas = data;
      },
      (error) => {
        console.error('Error encontrando camisetas:', error);
      }
    );
  }

  loadEquipos() {
    this.servicioEquipos.obtenerEquipos().subscribe(
      (data: Equipo[]) => {
        console.log('Raw data:', data);
        this.equipos = data;
      },
      (error) => {
        console.error('Error encontrando equipos:', error);
      }
    );
  }

  loadMarcas() {
    this.servicioMarcas.obtenerMarcas().subscribe(
      (data: Marca[]) => {
        console.log('Raw data:', data);
        this.marcas = data;
      },
      (error) => {
        console.error('Error encontrando marcas:', error);
      }
    );
  }

  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (file && file.type.startsWith('image')) {
      const reader = new FileReader();
      reader.onload = () => {
        const base64String = reader.result as string;
        const commaIndex = base64String.indexOf(',');
        if (commaIndex !== -1) {
          this.fotoBase64 = base64String.substring(commaIndex + 1);
          console.log("Base64 Image String:", this.fotoBase64);
        } else {
          console.error("Error: No se encontró una coma en la cadena Base64");
        }
      };
      reader.readAsDataURL(file);
    } else {
      console.error("Error: El archivo seleccionado no es una imagen");
    }
  }

  eliminarCamiseta(id: number) {
    if (confirm("¿Estás seguro de que quieres eliminar la camiseta?")) {
      this.subscription.add(
        this.servicio.eliminarCamiseta(id).subscribe(
          () => {
            alert("Camiseta eliminada exitosamente!");
            this.loadCamisetas();
          },
          error => {
            console.error("Error eliminando camiseta:", error);
            alert("¡No se pudo eliminar la camiseta!");
          }
        )
      );
    }
  }
  editarCamiseta(id: number): void {
    this.editando = true;
    this.subscription.add(
      this.servicio.obtenerCamisetaById(id).subscribe(
        camiseta => {
          this.cuadroNombre = camiseta.nombre;
          this.cuadroPrecio = camiseta.precio;
          this.cuadroDescripcion = camiseta.descripcion;
          this.camisetaId = camiseta.id;
          this.fotoBase64 = camiseta.foto;
  
          const equipoIndex = this.equipos.findIndex(equipo => equipo.id === camiseta.equipo.id);
          const marcaIndex = this.marcas.findIndex(marca => marca.id === camiseta.marca.id);
  
          if (equipoIndex !== -1 && marcaIndex !== -1) {
            // Asignar el índice correcto
            this.equipoSeleccionadoId = equipoIndex;
            this.marcaSeleccionadaId = marcaIndex;
          } else {
            console.error("Error: No se encontró la marca o el equipo seleccionado en el array.");
            alert("¡No se pudo obtener la información del equipo o marca!");
          }
        },
        error => {
          console.error("Error obteniendo información del equipo o marca:", error);
          alert("¡No se pudo obtener la información del equipo o marca!");
        }
      )
    );
  }

  onSubmitEdit(): void {
    const foto: string = this.fotoBase64 || '';
    const equipoSeleccionado = this.equipos.find(equipo => equipo.id === this.equipoSeleccionadoId);
    const marcaSeleccionada = this.marcas.find(marca => marca.id === this.marcaSeleccionadaId);

    if (equipoSeleccionado && marcaSeleccionada) {
      const data: Camiseta = {
        id: this.camisetaId,
        nombre: this.cuadroNombre,
        precio: this.cuadroPrecio,
        descripcion: this.cuadroDescripcion,
        equipo: equipoSeleccionado,
        marca: marcaSeleccionada,
        foto: foto
      };

      this.servicio.actualizarCamiseta(data).subscribe(
        (response: Camiseta) => {
          this.loadCamisetas();
          this.editando = false;
          alert("Camiseta actualizada con éxito");
        },
        (error) => {
          console.error("Error actualizando camiseta:", error);
          alert("¡La camiseta no se ha podido actualizar!");
        }
      );
    } else {
      console.error("Error: No se encontró la marca o el equipo seleccionado.");
      alert("Por favor, seleccione un equipo y una marca válidos.");
    }
  }


  onSubmit() {
    const foto: string = this.fotoBase64 || '';
    const equipoSeleccionado = this.equipos[this.equipoSeleccionadoId - 1];
    const marcaSeleccionada = this.marcas[this.marcaSeleccionadaId - 1];

    if (equipoSeleccionado && marcaSeleccionada) {
      const data: Camiseta = {
        id: 0,
        nombre: this.cuadroNombre,
        precio: this.cuadroPrecio,
        descripcion: this.cuadroDescripcion,
        equipo: equipoSeleccionado,
        foto: foto,
        marca: marcaSeleccionada,

      };

      this.servicio.insertarCamiseta(data).subscribe(response => {
        console.log(response);
        this.loadCamisetas();
        this.cuadroDescripcion = '';
        this.cuadroNombre = '';
        this.cuadroPrecio = 0;
        alert("Camiseta Insertada con Exito");
      }, error => {
        console.error("Error insertando equipo:", error);
        alert("¡El equipo no se ha podido insertar!");
      });
    } else {
      console.error("Error: No se encontró la liga seleccionada.");
      alert("Por favor, seleccione una liga válida.");
    }
  }
}