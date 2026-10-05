import type { TableHelperHook, Usuario } from "@/core/@types/core";
import { Tag } from "@chakra-ui/react";



export const useUserTableHelper: TableHelperHook<Usuario> = (actionFn) => {

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
                header: 'Usuario',
                render(item) {
                    return item?.usuario;
                },
            },
            {
                header: 'Estado',
                render(item) {
                    return (
                        <Tag.Root colorPalette={item?.activo ? 'green' : 'red'}>
                            <Tag.Label>{item?.activo ? 'Activo' : 'Inactivo'}</Tag.Label>
                        </Tag.Root>
                    );
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