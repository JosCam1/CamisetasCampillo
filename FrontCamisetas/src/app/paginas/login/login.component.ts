import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { LoginService } from '../../servicios/login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  email!: string;
  password!: string;
  mensaje!: string;

  constructor(private servicio: LoginService, private router: Router, private cdr: ChangeDetectorRef) { }

  ngOnInit() {
  }

  async onSubmit() {
    try {
      this.servicio.iniciarSesion(this.email, this.password).subscribe(
        (response: any) => {
          this.mensaje = "Has iniciado sesión correctamente";
          alert(this.mensaje);
          this.router.navigate(['']); 
          this.cdr.detectChanges(); 
        },
        (err: any) => {
          console.error(err);
          this.password = '';
          this.mensaje = 'Usuario o Contraseña Incorrectos';
        }
      );
    } catch (error) {
      console.error(error);
      this.password = '';
      this.mensaje = 'Error al iniciar sesión';
    }
  }

  
}