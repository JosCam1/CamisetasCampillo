import { Component, OnInit } from '@angular/core';
import { CarritoService } from '../../servicios/carrito.service';
import { Camiseta } from '../../modelos/camiseta';

@Component({
  selector: 'app-carrito',
  templateUrl: './carrito.component.html',
  styleUrls: ['./carrito.component.css']
})
export class CarritoComponent implements OnInit {
  carrito: Camiseta[] = [];

  constructor(private carritoService: CarritoService) { }

  ngOnInit() {
    this.carritoService.getCarrito().subscribe(camisetas => {
      this.carrito = camisetas;
    });
  }

  eliminarDelCarrito(camiseta: Camiseta) {
    this.carritoService.eliminarDelCarrito(camiseta);
  }

  totalPrecio(): number {
    return this.carrito.reduce((total, camiseta) => total + camiseta.precio, 0);
  }

  comprarCarrito() {
    const importeTotal = this.totalPrecio(); // Calcular el importe total
    if (confirm("¿Estás seguro de que quieres realizar el pedido de las camisetas?")) {
    this.carritoService.realizarPedido(importeTotal).subscribe(
      () => {
        this.carritoService.limpiarCarrito();
        alert('¡Compra realizada con éxito!');
      },
      error => {
        console.error('Error al realizar el pedido:', error);
        alert('Hubo un error al realizar la compra. Por favor, intenta nuevamente.');
      }
    );
  }
  }
}