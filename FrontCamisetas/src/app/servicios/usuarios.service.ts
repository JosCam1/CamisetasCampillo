import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Usuario } from '../modelos/usuario';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UsuariosService {

  APIurl = "http://localhost:8080/usuarios/";

  constructor(private httpClient: HttpClient) { }

  public insertarUsuario(usuario: Usuario): Observable<Usuario> {
    return this.httpClient.post<Usuario>(`${this.APIurl}`, usuario);
  }

  public buscarUsuarioPorEmail(email: string): Observable<boolean> {
    return this.httpClient.get<boolean>(`${this.APIurl}buscarPorEmail/${email}`).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'Error desconocido';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Error: ${error.error.message}`;
    } else {
      errorMessage = `Error del servidor: ${error.status}\nMensaje: ${error.message}`;
    }
    return throwError(() => new Error(errorMessage));
  }

  public obtenerUsuarioById(id: number): Observable<Usuario> {
    return this.httpClient.get<Usuario>(`${this.APIurl}${id}`);
  }
  public actualizarUsuario(id: number, usuario: Partial<Usuario>): Observable<void> {
    return this.httpClient.put<void>(`${this.APIurl}${id}`, usuario);
  }
}