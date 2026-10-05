import type { ObjectColumns } from '@/modules/admin/components/SelectColumnsForBuild';
import { zodResolver } from '@hookform/resolvers/zod';
import { useForm } from 'react-hook-form';
import { z } from 'zod';
import { useCreateRecurso, useUpdateRecurso } from '../hooks/recursos/queries';
import { showError, showSuccess } from '@/core/utilities/errors';

export const useFormCreateRecurso = (columns: ObjectColumns, defaultValues: {
    alcanceId: string;
    nombreUsuario: string,
    subcategoriaId: string,
    [key: string]: unknown
}, nmUser: string, page: number, size: number, fn?: (op: boolean) => void, update?: boolean, recursoId?: string) => {
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    let schema: z.ZodObject<any> = z.object({
        alcanceId: z.uuid(),
        subcategoriaId: z.uuid(),
        nombreUsuario: z.string().min(3, 'Falta el usuario'),
        titulo: z.string("El campo es requerido").min(3, 'Ingrese un titulo de al menos 3 caracteres'),
        ubicacionId: z.uuid("Seleccione una ubicación")
    });

    for (const col of columns) {
        if (col.selected) {
            schema = schema.extend({
                [col.columnName]: col.isRequired ? z.string("El campo es requerido").min(1, 'El campo es requerido') :
                    z.string("El campo es requerido").optional()
            });
        }
    }

    type ResourceCreateType = z.input<typeof schema>;


    const { control, handleSubmit, setValue } = useForm<ResourceCreateType>({
        resolver: zodResolver(schema),
        values: { ...defaultValues }
    });
    const { mutate, isPending } = useCreateRecurso(nmUser, page, size);
    const { mutate: put, isPending: isPendingUpdate } = useUpdateRecurso(recursoId, nmUser, page, size);


    const handleCreate = handleSubmit((data) => {
        if (!update) {
            mutate(data, {
                onSuccess() {
                    showSuccess('Se creo correctamente el registro');
                    fn?.(false);
                    const { alcanceId, nombreUsuario, subcategoriaId, ...rest } = data;
                    const cols = Object.keys(rest);
                    setValue('alcanceId', alcanceId);
                    setValue('nombreUsuario', nombreUsuario);
                    setValue('subcategoriaId', subcategoriaId);
                    for (const col of cols) {
                        setValue(col, '');
                    }

                },
                onError(error) {
                    showError(error);
                },
            });
            return;
        }

        put(data, {
            onSuccess() {
                showSuccess('Se actualizo correctamente el registro');
                fn?.(false);
            },
            onError(error) {
                showError(error);

            }
        });

    });


    return { schema, control, handleCreate, loading: isPending, loadingUpdate: isPendingUpdate };
};
