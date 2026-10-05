import type { EntityAudit } from "@/core/@types/core";
import type { FormSubcategoryCreate } from "../../schema/subcategoriesSchema";
import type { Categoria } from "../categories/categories";
import type { ObjectColumns } from "../../components/SelectColumnsForBuild";



export interface Subcategoria extends EntityAudit {
  subcategoriaId: string;
  nombre: string;
  payload?: ObjectColumns | null;         
  categoria?: Categoria | null;
}


export type CreateSubCategoriaFn = (data: FormSubcategoryCreate) => Promise<Subcategoria>;

export type AllSubcategories = () => Promise<Subcategoria[]>;