import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CamisetasService } from '../../servicios/camisetas.service';
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

  constructor(
    private route: ActivatedRoute,
    private camisetasService: CamisetasService,
    private usuarioService:LoginService
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
  }
}