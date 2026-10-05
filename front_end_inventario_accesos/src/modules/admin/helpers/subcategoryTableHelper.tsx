import type { TableHelperHook } from "@/core/@types/core";
import { formatTimeWithZone } from "@/core/utilities/dates";
import type { Subcategoria } from "../hooks/subcategories/subcategories";



export const useSubcategoryTableHelper: TableHelperHook<Subcategoria> = (actionFn) => {

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
                header: 'Categoría Padre',
                render(item) {
                    return `${item?.categoria?.nombre}`;
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