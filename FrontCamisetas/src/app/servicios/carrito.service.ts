import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Pedido } from '../modelos/pedido';
import { LoginService } from './login.service';
import { Camiseta } from '../modelos/camiseta';
import { Usuario } from '../modelos/usuario'; // Asegúrate de importar el modelo de Usuario correctamente

@Injectable({
  providedIn: 'root'
})
export class CarritoService {
  private carritoSubject: BehaviorSubject<Camiseta[]> = new BehaviorSubject<Camiseta[]>([]);
  private carrito: Camiseta[] = [];
  private usuario: Usuario | null = null; // Cambiado a tipo Usuario

  APIurl = "http://localhost:8080/pedidos/";

  constructor(private loginService: LoginService, private httpClient: HttpClient) {
    const storedCarrito = localStorage.getItem('carrito');
    if (storedCarrito) {
      this.carrito = JSON.parse(storedCarrito);
    }
    this.carritoSubject.next(this.carrito);

    // Suscripción al cambio de usuario para asociar el carrito
    this.loginService.getUsuario().subscribe(usuario => {
      this.usuario = usuario; // Asigna el objeto Usuario completo
      if (!usuario) {
        this.limpiarCarrito(); // Limpia el carrito si el usuario no está autenticado
      } else {
        this.actualizarStorage(); // Actualiza el carrito en localStorage
      }
    });
  }

  getCarrito(): Observable<Camiseta[]> {
    return this.carritoSubject.asObservable();
  }

  anadirAlCarrito(camiseta: Camiseta) {
    if (this.usuario) {
      this.carrito.push(camiseta);
      this.actualizarStorage();
    } else {
      console.error('No hay usuario autenticado para añadir al carrito.');
    }
  }

  eliminarDelCarrito(camiseta: Camiseta) {
    const index = this.carrito.findIndex(item => item.id === camiseta.id);
    if (index !== -1) {
      this.carrito.splice(index, 1);
      this.actualizarStorage();
    }
  }

  limpiarCarrito() {
    this.carrito = [];
    localStorage.removeItem('carrito'); // Elimina el carrito del localStorage
    this.carritoSubject.next(this.carrito); // Notifica a los suscriptores que el carrito está vacío
  }

  private actualizarStorage() {
    localStorage.setItem('carrito', JSON.stringify(this.carrito));
    this.carritoSubject.next(this.carrito);
  }

  realizarPedido(importe: number): Observable<Pedido> {
    if (this.usuario) {
      const pedido: Pedido = {
        fecha: new Date(),
        id: 0, // Deja que el servidor genere el ID
        estado: 'Pendiente de Pago', // Deja que el servidor defina el estado
        importe: importe,
        camisetas: this.carrito,
        usuario: this.usuario  // Asigna el objeto Usuario completo al pedido
      };
      return this.httpClient.post<Pedido>(this.APIurl + 'realizar', pedido);
    } else {
      throw new Error('No hay usuario autenticado para realizar el pedido.');
    }
  }
}