import { toast } from "sonner";


export const showError = (error: Error) => {
    const er = Reflect.has(Reflect.get(error, 'response') ?? {}, 'data') ? Reflect.get(Reflect.get(error, 'response'), 'data') : undefined;
    toast.error(er?.message || error.message, { position: 'top-center' });
};

export const showSuccess = (message: string, time: number = 3000) => {
    toast.success(message, { position: 'top-center', duration: time });
};