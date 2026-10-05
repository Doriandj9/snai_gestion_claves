import type { EntityAudit } from "@/core/@types/core";
import type { CreateCategoryType } from "../../schema/categoriesSchema";


export interface Categoria extends EntityAudit {
  categoriaId: string; // UUID → string
  nombre: string;
}


export type CrateCategoryFn = (data: CreateCategoryType) => Promise<Categoria>;

export type AllCategoriesFn = () => Promise<Categoria[]>;