import { Component, OnInit } from '@angular/core';
import { LoginService } from '../../servicios/login.service';
import { Usuario } from '../../modelos/usuario';
import { PedidosService } from '../../servicios/pedidos.service';
import { Pedido } from '../../modelos/pedido';

@Component({
  selector: 'app-pedidos',
  templateUrl: './pedidos.component.html',
  styleUrls: ['./pedidos.component.css']
})
export class PedidosComponent implements OnInit {
  usuario: Usuario | null = null;
  pedidos: Pedido[] = [];

  constructor(private servicioUsuario: LoginService, private servicioPedidos: PedidosService) { }

  ngOnInit() {
    this.servicioUsuario.getUsuario().subscribe(
      usuario => {
        this.usuario = usuario;
        this.loadPedidos();
      },
      error => {
        console.error('Error al obtener datos del usuario:', error);
      }
    );
  }

  loadPedidos(): void {
    if (this.usuario) {
      this.servicioPedidos.obtenerPedidosPorUsuario(this.usuario.id).subscribe(
        pedidos => {
          this.pedidos = pedidos;
          console.log('Pedidos obtenidos:', pedidos);
        },
        error => {
          console.error('Error obteniendo pedidos:', error);
          // Aquí puedes mostrar un mensaje de error al usuario si lo deseas
        }
      );
    } else {
      console.error('Error: No se ha obtenido el usuario.');
    }
  }
}