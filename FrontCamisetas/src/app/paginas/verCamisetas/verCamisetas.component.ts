import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common'; // Importar Location
import { CamisetasService } from '../../servicios/camisetas.service';
import { CarritoService } from '../../servicios/carrito.service';
import { Camiseta } from '../../modelos/camiseta';
import { LoginService } from '../../servicios/login.service';
import { Usuario } from '../../modelos/usuario';

@Component({
  selector: 'app-verCamisetas',
  templateUrl: './verCamisetas.component.html',
  styleUrls: ['./verCamisetas.component.css']
})
export class VerCamisetasComponent implements OnInit {
  camiseta: Camiseta | undefined;
  usuario: Usuario | null = null;
  carrito:Camiseta[] = [];

  constructor(
    private route: ActivatedRoute,
    private camisetasService: CamisetasService,
    private usuarioService: LoginService,
    private location: Location,
    private carritoService:CarritoService
  ) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      const camisetaId = +params['id'];
      this.camisetasService.obtenerCamisetaById(camisetaId).subscribe(camiseta => {
        this.camiseta = camiseta;
      });
    });

    this.usuarioService.getUsuario().subscribe(
      usuario => {
        this.usuario = usuario;
      },
      error => {
        console.error('Error al obtener datos del usuario:', error);
      }
    );
    this.carritoService.getCarrito().subscribe(carrito => {
      this.carrito = carrito;
      console.log('Carrito actualizado:', this.carrito); 
    });
  }

  volver() {
    this.location.back(); 
  }

  anadirAlCarrito(camiseta: Camiseta) {
    if (camiseta) {
      this.carritoService.anadirAlCarrito(camiseta);
      alert('Camiseta añadida al carrito!');
    }
  }
}