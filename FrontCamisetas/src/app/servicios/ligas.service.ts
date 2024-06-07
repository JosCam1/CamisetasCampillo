import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Liga } from '../modelos/liga';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LigasService {

  APIurl = "http://localhost:8080/ligas/";

constructor(private httpClient:HttpClient) { }

public insertarLiga(liga:Liga):Observable<Liga>{
  return this.httpClient.post<Liga>(`${this.APIurl}`,liga);
}

public obtenerLigas():Observable<Liga[]>{
  return this.httpClient.get<Liga[]>(`${this.APIurl}`);
}

eliminarLiga(id:number):Observable<Liga>{
  return this.httpClient.delete<Liga>(`${this.APIurl}${id}`);
}

public obtenerLigaById(id: number): Observable<Liga> {
  return this.httpClient.get<Liga>(`${this.APIurl}${id}`);
}
public actualizarLiga(liga: Liga): Observable<void> {
  return this.httpClient.put<void>(`${this.APIurl}${liga.id}`, liga);
}
}
