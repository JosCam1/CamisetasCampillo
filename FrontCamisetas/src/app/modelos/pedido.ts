import { Camiseta } from "./camiseta";
import { Usuario } from "./usuario";

export class Pedido {
    id!:number;
    fecha!:Date;
    estado!:string;
    importe!:number;
    usuario!:Usuario;
    camisetas!:Camiseta[];
}
