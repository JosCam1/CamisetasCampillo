import { Rol } from "./rol";

export class Usuario {
    id!:number;
    nombre!:string;
    apellido!:string;
    codigoPostal!:string;
    telefono!:number;
    ciudad!:string;
    direccion!:string;
    email!:string;
    password!:string;
    foto!:string;
    rol!:Rol;
}
