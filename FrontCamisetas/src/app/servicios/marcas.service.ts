import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Marca } from '../modelos/marca';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MarcasService {

  APIurl = "http://localhost:8080/marcas/";

  constructor(private httpClient:HttpClient) { }
  
  public insertarMarca(marca:Marca):Observable<Marca>{
    return this.httpClient.post<Marca>(`${this.APIurl}`,marca);
  }
  
  public obtenerMarcas():Observable<Marca[]>{
    return this.httpClient.get<Marca[]>(`${this.APIurl}`);
  }
  
  eliminarMarca(id:number):Observable<Marca>{
    return this.httpClient.delete<Marca>(`${this.APIurl}${id}`);
  }
  
  public obtenerMarcaById(id: number): Observable<Marca> {
    return this.httpClient.get<Marca>(`${this.APIurl}${id}`);
  }
  public actualizarMarca(marca: Marca): Observable<void> {
    return this.httpClient.put<void>(`${this.APIurl}${marca.id}`, marca);
  }

}
