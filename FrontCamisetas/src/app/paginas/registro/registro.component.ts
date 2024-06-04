import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Usuario } from '../../modelos/usuario';
import { RolesService } from '../../servicios/roles.service';
import { Rol } from '../../modelos/rol';
import { UsuariosService } from '../../servicios/usuarios.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registro',
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.css']
})
export class RegistroComponent implements OnInit {

  registroForm: FormGroup;
  usuario: Usuario = new Usuario(); 
  roles: Rol[] = [];
  fotoBase64: string = '';
  emailError: string = ''; // Añadido para almacenar el mensaje de error del email
  formSubmitted: boolean = false; // Añadido para controlar si el formulario ha sido enviado

  constructor(private fb: FormBuilder, private servicioRol: RolesService, private servicioUsuario: UsuariosService, private router: Router) {
    this.registroForm = this.fb.group({
      nombre: ['', Validators.required],
      apellido: ['', Validators.required],
      codigoPostal: ['', Validators.required],
      telefono: ['', Validators.required],
      ciudad: ['', Validators.required],
      direccion: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]], // Actualizado con la validación de longitud mínima
      fotoPerfil: ['', Validators.required]
    });
  }

  ngOnInit() {
    this.obtenerRoles();
  }

  obtenerRoles() {
    this.servicioRol.obtenerRoles().subscribe(
      (roles: Rol[]) => {
        this.roles = roles;
      },
      error => {
        console.error('Error al obtener roles:', error);
      }
    );
  }

  async onSubmit() {
    this.formSubmitted = true; // Marcar el formulario como enviado
    if (this.registroForm.valid) {
      this.usuario.nombre = this.registroForm.value.nombre;
      this.usuario.apellido = this.registroForm.value.apellido;
      this.usuario.codigoPostal = this.registroForm.value.codigoPostal;
      this.usuario.telefono = this.registroForm.value.telefono;
      this.usuario.ciudad = this.registroForm.value.ciudad;
      this.usuario.direccion = this.registroForm.value.direccion;
      this.usuario.email = this.registroForm.value.email;
      this.usuario.password = await this.hashSHA256(this.registroForm.value.password);
      this.usuario.foto = this.fotoBase64;
      this.usuario.rol = this.roles[0];

      this.servicioUsuario.buscarUsuarioPorEmail(this.usuario.email).subscribe(
        existe => {
          if (existe) {
            this.emailError = "El correo electrónico ya está registrado."; // Mostrar mensaje de error
          } else {
            this.servicioUsuario.insertarUsuario(this.usuario).subscribe(
              response => {
                console.log('Usuario insertado correctamente:', response);
                alert("¡¡Usuario Registrado Correctamente!!");
                this.router.navigate(["login"]);
              },
              error => {
                console.error('Error al insertar usuario:', error);
                alert(error.message);
              }
            );
          }
        },
        error => {
          console.error('Error al buscar usuario por email:', error);
          this.emailError = "Error al verificar el correo electrónico. Por favor, intente nuevamente.";
        }
      );
    }
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

  async hashSHA256(password: string): Promise<string> {
    const crypto = window.crypto || (window as any).msCrypto;
    if (crypto) {
      const encoder = new TextEncoder();
      const data = encoder.encode(password);
      const buffer = await crypto.subtle.digest('SHA-256', data);
      const hashArray = Array.from(new Uint8Array(buffer)); 
      const hashHex = hashArray.map(byte => byte.toString(16).padStart(2, '0')).join(''); 
      return hashHex;
    } else {
      console.error("El navegador no es compatible con la API de Web Crypto");
      return ''; 
    }
  }
}