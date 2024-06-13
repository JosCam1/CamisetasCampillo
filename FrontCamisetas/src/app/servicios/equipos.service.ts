import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Equipo } from '../modelos/equipo';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EquiposService {

  APIurl = "http://localhost:8080/equipos/";

  constructor(private httpClient: HttpClient) { }

  public insertarEquipo(equipo: Equipo): Observable<Equipo> {
    return this.httpClient.post<Equipo>(`${this.APIurl}`, equipo);
  }

  public obtenerEquipos(): Observable<Equipo[]> {
    return this.httpClient.get<Equipo[]>(`${this.APIurl}`);
  }

  eliminarEquipo(id: number): Observable<Equipo> {
    return this.httpClient.delete<Equipo>(`${this.APIurl}${id}`);
  }

  public obtenerEquipoById(id: number): Observable<Equipo> {
    return this.httpClient.get<Equipo>(`${this.APIurl}${id}`);
  }

  public actualizarEquipo(equipo: Equipo): Observable<void> {
    return this.httpClient.put<void>(`${this.APIurl}${equipo.id}`, equipo);
  }
}