import { Component, OnInit } from '@angular/core';
import { Marca } from '../../modelos/marca';
import { Subscription } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { MarcasService } from '../../servicios/marcas.service';

@Component({
  selector: 'app-gestionMarcas',
  templateUrl: './gestionMarcas.component.html',
  styleUrls: ['./gestionMarcas.component.css']
})
export class GestionMarcasComponent implements OnInit {

  cuadroNombre: string = '';
  cuadroFoto: string = '';
  Marcas: Marca[] = [];
  fotoBase64: string = '';
  editando: boolean = false;
  marcaId!: number;
  private subscription: Subscription = new Subscription();

  constructor(private servicio: MarcasService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.loadMarcas();
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  loadMarcas() {
    this.subscription.add(
      this.servicio.obtenerMarcas().subscribe(
        (data: Marca[]) => {
          this.Marcas = data;
        },
        (error) => {
          console.error("Error encontrando marcas: ", error);
        }
      )
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
    const foto: string = this.fotoBase64 || '';
    const data = {
      nombre: this.cuadroNombre,
      foto: foto,
      id: 0
    };
    this.subscription.add(
      this.servicio.insertarMarca(data).subscribe(response => {
        console.log(response);
        this.loadMarcas();
        this.cuadroFoto = '';
        this.cuadroNombre = '';
        alert("Marca Insertada con Exito!!");
      }, error => {
        console.error("Error insertando marca:", error);
        alert("¡¡La marca no se ha podido insertar!!");
      })
    );
  }

  eliminarMarca(id: number) {
    if (confirm("¿Estás seguro de que quieres eliminar la marca, se eliminaran todas las camisetas que contengan esa marca?")) {
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

  editarMarca(id: number): void {
    this.editando = true;
    this.subscription.add(
      this.servicio.obtenerMarcaById(id).subscribe(
        marca => {
          this.cuadroNombre = marca.nombre;
          this.cuadroFoto = marca.foto;
          this.marcaId = marca.id;
          // Convertir la imagen de base64 a una imagen visualizable
          this.fotoBase64 = marca.foto;
        },
        error => {
          console.error("Error obteniendo información de la marca:", error);
          alert("¡No se pudo obtener la información de la marca!");
        }
      )
    );
  }

  onSubmitEdit(): void {
    this.editando = false;
    const marcaActualizada: Marca = {
      id: this.marcaId,
      nombre: this.cuadroNombre,
      foto: this.fotoBase64 
    };

    this.subscription.add(
      this.servicio.actualizarMarca(marcaActualizada).subscribe(
        () => {
          this.cuadroNombre = '';
          this.cuadroFoto = '';
          this.fotoBase64 = '';

          this.loadMarcas();

          alert("¡Marca actualizada exitosamente!");
        },
        error => {
          console.error("Error actualizando marca:", error);
          alert("¡No se pudo actualizar la marca!");
        }
      )
    );
  }

}
