import { Rol } from "./rol";

export class Usuario {
    id!:number;
    nombre!:string;
    apellido!:string;
    codigoPosta!:number;
    telefono!:number;
    ciudad!:string;
    direccion!:string;
    email!:string;
    password!:string;
    rol!:Rol;
}
