import type { EntityAudit, PageResponse, Usuario } from "@/core/@types/core";
import type { ObjectColumns } from "@/modules/admin/components/SelectColumnsForBuild";
import type { Subcategoria } from "@/modules/admin/hooks/subcategories/subcategories";




export interface Recurso extends EntityAudit {
  recursoId: string; // UUID

  ip: string | null;
  titulo: string | null;
  clave: string | null;
  nombre: string | null;
  usuario: string | null;
  correo: string | null;
  serieCodigo: string | null;
  descripcion: string | null;
  rangosIp: string | null;
  puerto: string | null;
  nanoId: string | null;

  // Relaciones
  user: Usuario | null;
  subcategoria: Subcategoria | null;
  alcance: Alcance | null;
};

export type PayloadSubcategoria = ObjectColumns;
export type Alcance = {
  [key: string]: string;
  defaultAlcance: "PUBLICO" | "PRIVADO",
  alcanceId: string; 
};

export type RecursoTable = {
  recursoId: string;
  ip: string;
  titulo: string;
  clave: string;
  nombre: string;
  usuario: string;
  correo: string;
  serieCodigo: string;
  descripcion: string;
  rangosIp: string;
  puerto: string;
  nanoId: string;
  user: Usuario;
  subcategoria: Subcategoria;
  alcance: Alcance;
  columns: PayloadSubcategoria;
  createdAt: string;
  updatedAt: string;
}

export type AllResourcesForUserFn = {
    (username: string): Promise<RecursoTable[]>;
    (username: string, page: number, size: number): Promise<PageResponse<RecursoTable>>;
};




export type CreateRecursoFn = (data: {[x: string]: unknown}) =>  Promise<Recurso>;

export type UpdateRecursoFn = (data: {[x: string]: unknown}, recursoId: string) =>  Promise<Recurso>;
