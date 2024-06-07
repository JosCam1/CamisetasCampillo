import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { LoginService } from '../../servicios/login.service';
import { Usuario } from '../../modelos/usuario';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  usuario: Usuario | null = null;

  constructor(private servicioLogin: LoginService, private router: Router, private cdr: ChangeDetectorRef) { }

  ngOnInit() {
    this.verificarInicioSesion();
    this.servicioLogin.getUsuario().subscribe(
      usuario => {
        this.usuario = usuario;
        this.cdr.detectChanges(); // Forzar la detección de cambios
      },
      error => {
        console.error('Error al obtener datos del usuario:', error);
      }
    );
  }

  verificarInicioSesion() {
    this.servicioLogin.fetchUsuario().subscribe(
      usuario => {
        this.usuario = usuario;
      },
      error => {
        console.error('Error al obtener datos del usuario:', error);
      }
    );
  }

  cerrarSesion() {
    if (confirm("¿Estás seguro de que quieres cerrar sesión?")) {
      this.servicioLogin.logout().subscribe(
        response => {
          this.usuario = null;
          this.cdr.detectChanges();
          this.router.navigate(['']);
        },
        error => {
          console.error('Error al cerrar sesión:', error);
        }
      );
    }
  }
}