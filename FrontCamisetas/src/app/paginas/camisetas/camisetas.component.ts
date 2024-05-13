import { Component, OnInit } from '@angular/core';
import { Camiseta } from '../../modelos/camiseta';
import { CamisetasService } from '../../servicios/camisetas.service';
import { error } from 'console';

@Component({
  selector: 'app-camisetas',
  templateUrl: './camisetas.component.html',
  styleUrls: ['./camisetas.component.css']
})
export class CamisetasComponent implements OnInit {

  camisetas:Camiseta[] = [];

  constructor(private servicio:CamisetasService) { }

  ngOnInit() {
    
  }

  obtenerCamisetas(){
    this.servicio.obtenerEmpleados().subscribe(
      (data: Camiseta[]) => {
        this.camisetas = data;
      },
      (error) => {
        console.error("Error encontrando camisetas: ", error)
      }
    )
  }

}
