import { zodResolver } from '@hookform/resolvers/zod';
import { useForm } from 'react-hook-form';
import { z } from 'zod';
import { useCrateCategory } from '../hooks/categories/queries';
import { showError } from '@/core/utilities/errors';

const schema = z.object({
    nombre: z.string("Escribe la categoría").min(3, "Escribe un mínimo de caracteres.")
});


export type CreateCategoryType = z.input<typeof schema>;


export const useFormCreateCategory = (fn?: (o: boolean) => void ) => {
    const { control, handleSubmit, reset } = useForm<CreateCategoryType>({
        resolver: zodResolver(schema)
    });
    const { mutate, isPending, isSuccess } = useCrateCategory();

    const handleCreateCategory = handleSubmit((data) => {
        mutate(data,{
            onError(error) {
                showError(error);
            },
            onSuccess(){
                reset({nombre: ''});
                fn?.(false);
            }
        });
    });


    return { control, handleCreateCategory, loading: isPending , success: isSuccess};


};