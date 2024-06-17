import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { LoginService } from '../../servicios/login.service';
import { Usuario } from '../../modelos/usuario';
import { UsuariosService } from '../../servicios/usuarios.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-micuenta',
  templateUrl: './micuenta.component.html',
  styleUrls: ['./micuenta.component.css']
})
export class MicuentaComponent implements OnInit {
  usuario: Usuario | null = null;
  usuarioNuevo: Partial<Usuario> = {}; 
  estado: boolean = true;
  private subscription: Subscription = new Subscription();

  constructor(private servicioLogin: LoginService, private servicioUsuario: UsuariosService, private cd: ChangeDetectorRef) { }

  ngOnInit() {
    this.obtenerUsuario();
  }

  cambiar() {
    this.estado = false;
  }

  aplicarCambios() {
    this.estado = true;
    if (this.usuario && this.usuario.id) {
      this.subscription.add(
        this.servicioUsuario.actualizarUsuario(this.usuario.id, this.usuarioNuevo).subscribe(
          () => {
            alert("Usuario actualizado correctamente");
            console.log('Usuario actualizado correctamente');
          },
          error => {
            console.error('Error al actualizar el usuario:', error);
          }
        )
      );
    } else {
      console.error('No se puede actualizar el usuario porque falta la información de identificación');
    }
    this.cd.detectChanges();
  }

  obtenerUsuario(){
    this.servicioLogin.getUsuario().subscribe(
      usuario => {
        if (usuario) {
          this.usuario = usuario;
          this.usuarioNuevo = { ...usuario }; 
        }
      },
      error => {
        console.error('Error al obtener datos del usuario:', error);
      }
    );
  }
}