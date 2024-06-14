import { Component, OnInit } from '@angular/core';
import { PedidosService } from '../../servicios/pedidos.service';
import { Pedido } from '../../modelos/pedido';
import { LoginService } from '../../servicios/login.service';
import { Usuario } from '../../modelos/usuario';

@Component({
  selector: 'app-pedidos',
  templateUrl: './todosPedidos.component.html',
  styleUrls: ['./todosPedidos.component.css']
})
export class TodosPedidosComponent implements OnInit {
  pedidos: Pedido[] = [];
  cargandoPedidos = false;
  usuario: Usuario | null = null;

  constructor(private servicioPedidos: PedidosService, private servicioLogin:LoginService) { }

  ngOnInit() {
    this.cargarTodosPedidos();
    this.servicioLogin.getUsuario().subscribe(
      usuario => {
        this.usuario = usuario;
      },
      error => {
        console.error('Error al obtener datos del usuario:', error);
      }
    );
  }

  cargarTodosPedidos() {
    this.cargandoPedidos = true;
    this.servicioPedidos.obtenerPedidos().subscribe(
      pedidos => {
        this.pedidos = pedidos;
        console.log('Pedidos obtenidos:', pedidos);
        this.cargandoPedidos = false;
      },
      error => {
        console.error('Error obteniendo todos los pedidos:', error);
        this.cargandoPedidos = false;
      }
    );
  }

  marcarComoPagado(pedido: Pedido): void {
    this.servicioPedidos.cambiarEstadoPedido(pedido.id).subscribe(
      pedidoActualizado => {
        pedido.estado = pedidoActualizado.estado;
        console.log('Estado cambiado:', pedidoActualizado);
      },
      error => {
        console.error('Error cambiando estado del pedido:', error);
      }
    );
  }

}