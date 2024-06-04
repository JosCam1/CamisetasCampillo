import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Rol } from '../modelos/rol';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RolesService {

  APIurl = "http://localhost:8080/roles/";

  constructor(private httpClient:HttpClient) { }

  public obtenerRoles():Observable<Rol[]>{
    return this.httpClient.get<Rol[]>(`${this.APIurl}`);
  }

}
