import { Equipo } from "./equipo";
import { Liga } from "./liga";
import { Marca } from "./marca";

export class Camiseta {
    id!:number;
    precio!:number;
    descripcion!:string;
    decuento!:number;
    foto!:Blob;
    liga!:Liga;
    marca!:Marca;

}
