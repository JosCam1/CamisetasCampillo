import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Pedido } from '../modelos/pedido';

@Injectable({
  providedIn: 'root'
})
export class PedidosService {
  private APIurl = "http://localhost:8080/pedidos/";

  constructor(private httpClient: HttpClient) { }

  obtenerPedidosPorUsuario(idUsuario: number): Observable<Pedido[]> {
    const url = `${this.APIurl}usuario/${idUsuario}`;
    return this.httpClient.get<Pedido[]>(url).pipe(
      catchError(error => {
        console.error('Error obteniendo pedidos por usuario:', error);
        return throwError('Error al obtener pedidos por usuario. Por favor, intenta de nuevo más tarde.');
      })
    );
  }

  obtenerPedidos(): Observable<Pedido[]> {
    const url = `${this.APIurl}`;
    return this.httpClient.get<Pedido[]>(url).pipe(
      catchError(error => {
        console.error('Error obteniendo todos los pedidos:', error);
        return throwError('Error al obtener todos los pedidos. Por favor, intenta de nuevo más tarde.');
      })
    );
  }

  cambiarEstadoPedido(id: number): Observable<Pedido> {
    const url = `${this.APIurl}${id}/cambiarEstado`;
    return this.httpClient.put<Pedido>(url, {}).pipe(
      catchError(error => {
        console.error('Error cambiando estado del pedido:', error);
        return throwError('Error al cambiar estado del pedido. Por favor, intenta de nuevo más tarde.');
      })
    );
  }

}