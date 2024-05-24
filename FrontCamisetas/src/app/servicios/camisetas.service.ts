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



}
