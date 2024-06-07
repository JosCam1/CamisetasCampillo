import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';
import { Usuario } from '../modelos/usuario';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private APIurl = "http://localhost:8080/session";
  private usuarioSubject: BehaviorSubject<Usuario | null> = new BehaviorSubject<Usuario | null>(null);

  constructor(private http: HttpClient) {}

  iniciarSesion(email: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.APIurl}/iniciar`, { email, password }).pipe(
      tap(response => {
        if (response.message === 'Inicio de sesión exitoso') {
          this.usuarioSubject.next(response.usuario);
        }
      })
    );
  }

  logout(): Observable<any> {
    return this.http.post<any>(`${this.APIurl}/logout`, {}).pipe(
      tap(() => {
        this.usuarioSubject.next(null);
      })
    );
  }

  getUsuario(): Observable<Usuario | null> {
    return this.usuarioSubject.asObservable();
  }

  fetchUsuario(): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.APIurl}/usuario`).pipe(
      tap(usuario => {
        this.usuarioSubject.next(usuario);
      })
    );
  }
}