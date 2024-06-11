import { Component, OnInit } from '@angular/core';
import { Usuario } from '../../modelos/usuario';
import { Subscription } from 'rxjs';
import { UsuariosService } from '../../servicios/usuarios.service';
import { RolesService } from '../../servicios/roles.service';
import { Rol } from '../../modelos/rol';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-creacionUsuarios',
  templateUrl: './creacionUsuarios.component.html',
  styleUrls: ['./creacionUsuarios.component.css']
})
export class CreacionUsuariosComponent implements OnInit {

  fotoBase64: string = '';
  usuario: Usuario = new Usuario();
  roles:Rol[] = [];
  private subscription: Subscription = new Subscription();

  constructor(private servicio: UsuariosService, private servicioRoles:RolesService, private httpClient:HttpClient) { }

  ngOnInit() {
    this.subscription.add(
      this.servicioRoles.obtenerRoles().subscribe(
        (data: Rol[]) => { 
          this.roles = data;
        },
        (error) => {
          console.error("Error encontrando roles: ", error);
        }
      )
    );
  }

  onSubmit() {
    const foto: string = this.fotoBase64 || '';
    const nuevoUsuario: Usuario = {
      id: 0,
      nombre: this.usuario.nombre,
      apellido: this.usuario.apellido,
      codigoPostal: this.usuario.codigoPostal,
      telefono: this.usuario.telefono,
      ciudad: this.usuario.ciudad,
      direccion: this.usuario.direccion,
      email: this.usuario.email,
      password: this.usuario.password,
      foto: foto,
      rol: this.usuario.rol
    };
    console.log(nuevoUsuario);
    this.subscription.add(
      this.servicio.insertarUsuario(nuevoUsuario).subscribe(response => {
        console.log(response);
        alert("¡¡Usuario Insertado con Éxito!!");
        this.limpiarCampos();
      }, error => {
        console.error("Error insertando usuario:", error);
        alert("¡¡El usuario no se ha podido insertar!!");
      })
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

  limpiarCampos() {
    this.usuario = new Usuario(); // Limpiar el objeto usuario
    this.fotoBase64 = ''; // Limpiar la foto
  }

}