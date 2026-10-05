import { zodResolver } from '@hookform/resolvers/zod';
import { useForm } from 'react-hook-form';
import { z } from 'zod';
import { useCreateSubcategory } from '../hooks/subcategories/queries';
import { showError } from '@/core/utilities/errors';

const regexPayload = /^\[\s*\{\s*"columnName"\s*:\s*"[^"]+"\s*,\s*"selected"\s*:\s*(true|false)\s*,\s*"isRequired"\s*:\s*(true|false)\s*,\s*"order"\s*:\s*(\d+|"")\s*\}(?:\s*,\s*\{\s*"columnName"\s*:\s*"[^"]+"\s*,\s*"selected"\s*:\s*(true|false)\s*,\s*"isRequired"\s*:\s*(true|false)\s*,\s*"order"\s*:\s*(\d+|"")\s*\})*\s*\]$/;
const schema = z.object({
    nombre: z.string("Escriba el nombre del sub categoría").min(3, "Mínimo 3 caracteres"),
    payload: z.string().regex(regexPayload, `Ingrese todos las columnas [{"columnName":"clase","isRequired":false,"order":2]`),
    categoryId: z.uuid("Seleccione una categoría")
});

export type FormSubcategoryCreate = z.input<typeof schema>;

export const useFormCreateSubcategory = (fn?: (op: boolean) => void) => {
    const { control, handleSubmit, reset, setValue } = useForm<FormSubcategoryCreate>({
        resolver: zodResolver(schema)
    });

    const { mutate, isPending, isSuccess } = useCreateSubcategory();

    const handleCreateSubcategory = handleSubmit((data) => {
        mutate(data, {
            onError(error) {
                showError(error);
            },
            onSuccess() {
                reset({ nombre: '' });
                fn?.(false);
            }
        });
    });



    return { control, handleCreateSubcategory, loading: isPending, success: isSuccess, setValue };
};