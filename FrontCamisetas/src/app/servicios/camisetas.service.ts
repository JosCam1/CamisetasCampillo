import { HttpClient } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Camiseta } from '../modelos/camiseta';

@Injectable({
  providedIn: 'root'
})
export class CamisetasService implements OnInit{
  APIurl = "http://localhost:8080/camisetas/";

  constructor(private httpClient:HttpClient) { }

  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }

  public obtenerCamisetas():Observable<Camiseta[]>{
    return this.httpClient.get<Camiseta[]>(`${this.APIurl}`);
  }

  eliminarCamiseta(id:number):Observable<Camiseta>{
    return this.httpClient.delete<Camiseta>(`${this.APIurl}${id}`);
  }

  public obtenerCamisetaById(id: number): Observable<Camiseta> {
    return this.httpClient.get<Camiseta>(`${this.APIurl}${id}`);
  }

  public insertarCamiseta(camiseta: Camiseta): Observable<Camiseta> {
    return this.httpClient.post<Camiseta>(`${this.APIurl}`, camiseta);
  }

  public actualizarCamiseta(camiseta: Camiseta): Observable<Camiseta> {
    return this.httpClient.put<Camiseta>(`${this.APIurl}${camiseta.id}`, camiseta);
  }
}
