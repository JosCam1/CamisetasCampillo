import { Component, OnInit, OnDestroy } from '@angular/core';
import { Equipo } from '../../modelos/equipo';
import { Subscription } from 'rxjs';
import { EquiposService } from '../../servicios/equipos.service';
import { ActivatedRoute } from '@angular/router';
import { LigasService } from '../../servicios/ligas.service';
import { Liga } from '../../modelos/liga';

@Component({
  selector: 'app-gestion-equipos',
  templateUrl: './gestionEquipos.component.html',
  styleUrls: ['./gestionEquipos.component.css']
})
export class GestionEquiposComponent implements OnInit, OnDestroy {
  cuadroNombre: string = '';
  cuadroFoto: string = '';
  equipos: Equipo[] = [];
  fotoBase64: string = '';
  editando: boolean = false;
  equipoId!: number;
  ligas: Liga[] = [];
  ligaSeleccionadaId!: number;
  private subscription: Subscription = new Subscription();

  constructor(private servicio: EquiposService, private route: ActivatedRoute, private servicioLigas: LigasService) { }

  ngOnInit(): void {
    this.loadEquipos();
    this.loadLigas();
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  loadLigas() {
    this.servicioLigas.obtenerLigas().subscribe(
      (data: Liga[]) => { // Cambiado a Liga[] en lugar de any
        console.log('Raw data:', data);
        this.ligas = data;
      },
      (error) => {
        console.error('Error encontrando ligas:', error);
      }
    );
  }

  loadEquipos() {
    this.servicio.obtenerEquipos().subscribe(
      (data: Equipo[]) => { // Cambiado a Equipo[] en lugar de any
        console.log('Raw data:', data);
        this.equipos = data;
      },
      (error) => {
        console.error('Error encontrando equipos:', error);
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
  onSubmit() {
    const foto: string = this.fotoBase64 || ''; // Asegúrate de que fotoBase64 esté definido correctamente

    // Buscar la liga seleccionada en el array de ligas
    const ligaSeleccionada = this.ligas[this.ligaSeleccionadaId - 1];

    // Verificar si se encontró la liga seleccionada
    if (ligaSeleccionada) {
      const data: Equipo = {
        id: 0, // Esto podría ser 0 si estás insertando un nuevo equipo
        nombre: this.cuadroNombre, // Asegúrate de que cuadroNombre esté definido correctamente
        foto: foto,
        liga: ligaSeleccionada
      };

      // Continuar con el proceso de inserción del equipo
      this.subscription.add(
        this.servicio.insertarEquipo(data).subscribe(
          response => {
            console.log(response); // Verifica la respuesta del servidor en la consola
            this.loadEquipos(); // Recargar la lista de equipos después de la inserción
            this.cuadroFoto = ''; // Limpiar cuadroFoto después de la inserción exitosa
            this.cuadroNombre = ''; // Limpiar cuadroNombre después de la inserción exitosa
            alert("Equipo Insertado con Éxito!!");
          },
          error => {
            console.error("Error insertando equipo:", error); // Mostrar errores en la consola para depuración
            alert("¡El equipo no se ha podido insertar!"); // Alerta al usuario sobre el error
          }
        )
      );
    } else {
      console.error("Error: No se encontró la liga seleccionada.");
      alert("Por favor, seleccione una liga válida.");
    }
  }

  eliminarEquipo(id: number) {
    if (confirm("¿Estás seguro de que quieres eliminar el equipo? Se eliminaran tambien todas las camisetas asociadas a ese equipo")) {
      this.subscription.add(
        this.servicio.eliminarEquipo(id).subscribe(
          () => {
            alert("Equipo eliminado exitosamente!");
            this.loadEquipos();
          },
          error => {
            console.error("Error eliminando equipo:", error);
            alert("¡No se pudo eliminar el equipo!");
          }
        )
      );
    }
  }

  editarEquipo(id: number): void {
    this.editando = true;
    this.subscription.add(
      this.servicio.obtenerEquipoById(id).subscribe(
        equipo => {
          this.cuadroNombre = equipo.nombre;
          this.cuadroFoto = equipo.foto;
          this.equipoId = equipo.id;
          this.fotoBase64 = equipo.foto;
  
          // Buscar la liga seleccionada en el array de ligas
          const index = this.ligas.findIndex(liga => liga.id === equipo.liga.id);
          if (index !== -1) {
            // Asignar el índice correcto ajustando por 1 (ya que parece haber un off-by-one error)
            this.ligaSeleccionadaId = index;
          } else {
            console.error("Error: No se encontró la liga seleccionada en el array de ligas.");
            alert("¡No se pudo obtener la información del equipo!");
          }
        },
        error => {
          console.error("Error obteniendo información del equipo:", error);
          alert("¡No se pudo obtener la información del equipo!");
        }
      )
    );
  }

  onSubmitEdit(): void {
    console.log('ligaSeleccionadaId:', this.ligaSeleccionadaId);
    console.log('ligas:', this.ligas);
    this.editando = false;

    // Verificar si ligaSeleccionadaId está definida y es un índice válido
    if (this.ligaSeleccionadaId !== undefined && this.ligaSeleccionadaId >= 0 && this.ligaSeleccionadaId < this.ligas.length) {
      const ligaSeleccionada = this.ligas[this.ligaSeleccionadaId];

      // Verificar si se encontró la liga seleccionada
      if (ligaSeleccionada) {
        const equipoActualizado: Equipo = {
          id: this.equipoId,
          nombre: this.cuadroNombre,
          foto: this.fotoBase64,
          liga: ligaSeleccionada
        };

        this.subscription.add(
          this.servicio.actualizarEquipo(equipoActualizado).subscribe(
            () => {
              this.cuadroNombre = '';
              this.cuadroFoto = '';
              this.fotoBase64 = '';

              this.loadEquipos();

              alert("¡Equipo actualizado exitosamente!");
            },
            error => {
              console.error("Error actualizando equipo:", error);
              alert("¡No se pudo actualizar el equipo!");
            }
          )
        );
      } else {
        console.error("Error: No se encontró la liga seleccionada.");
        alert("¡No se pudo obtener la información del equipo!");
      }
    } else {
      console.error("Error: ligaSeleccionadaId no es un índice válido.");
      alert("¡No se pudo obtener la información del equipo!");
    }
  }
}