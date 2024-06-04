import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LigasService } from '../../servicios/ligas.service';
import { Liga } from '../../modelos/liga';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { Subscription } from 'rxjs';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-gestionLigas',
  templateUrl: './gestionLigas.component.html',
  styleUrls: ['./gestionLigas.component.css']
})
export class GestionLigasComponent implements OnInit, OnDestroy {
  cuadroNombre: string = '';
  cuadroFoto: string = '';
  Ligas: Liga[] = [];
  fotoBase64: string = '';
  private subscription: Subscription = new Subscription();

  constructor(private servicio: LigasService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.loadLigas();
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  loadLigas() {
    this.subscription.add(
      this.servicio.obtenerLigas().subscribe(
        (data: Liga[]) => {
          this.Ligas = data;
        },
        (error) => {
          console.error("Error encontrando ligas: ", error);
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
      this.servicio.insertarLiga(data).subscribe(response => {
        console.log(response);
        this.loadLigas();
        this.cuadroFoto='';
        this.cuadroNombre='';
        alert("¡¡Liga Insertada con Exito!!");
      }, error => {
        console.error("Error insertando liga:", error);
        alert("¡¡La liga no se ha podido insertar!!");
      })
    );
  }

  eliminarLiga(id: number) {
    if (confirm("¿Estás seguro de que quieres eliminar esta liga?")) {
      this.subscription.add(
        this.servicio.eliminarLiga(id).subscribe(
          () => {
            alert("¡Liga eliminada exitosamente!");
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