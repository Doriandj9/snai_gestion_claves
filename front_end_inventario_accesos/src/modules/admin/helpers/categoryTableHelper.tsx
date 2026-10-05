import type { TableHelperHook } from "@/core/@types/core";
import type { Categoria } from "../hooks/categories/categories";
import { formatTimeWithZone } from "@/core/utilities/dates";



export const useCategoryTableHelper: TableHelperHook<Categoria> = (actionFn) => {

    return {
        columns: [
            {
                header: 'N°',
                render(item, index) {
                    return index;
                },
            },
            {
                header: 'Nombre',
                render(item) {
                    return `${item?.nombre}`;
                },
            },
            {
                header: 'Fecha de creación',
                render(item) {

                    return item?.createdAt ? formatTimeWithZone(item.createdAt) : '';
                },
            }
            
        ],
        actions: {
            header: 'Acciones',
            list: {
                render: actionFn
            },
        }
    };
};