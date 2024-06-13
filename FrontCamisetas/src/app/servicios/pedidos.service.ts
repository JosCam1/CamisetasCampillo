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
}