import { Equipo } from "./equipo";
import { Liga } from "./liga";
import { Marca } from "./marca";

export class Camiseta {
    id!:number;
    precio!:number;
    descripcion!:string;
    decuento!:number;
    talla!:string;
    foto!:Blob;
    liga!:Liga;
    marca!:Marca;

}
