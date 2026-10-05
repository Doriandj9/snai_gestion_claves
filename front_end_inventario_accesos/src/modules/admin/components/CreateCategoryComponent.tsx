import { AppDialog } from "@/core/constants/components";
import { Button } from "@chakra-ui/react";
import { useState } from "react";
import { LuPlus } from "react-icons/lu";
import { Form } from "react-router-dom";
import { useFormCreateCategory, type CreateCategoryType } from "../schema/categoriesSchema";
import { AppFormInput } from "@/core/components/AppInput";



export const CreateCategoryComponent = () => {
    const [open, setOpen] = useState(false);
    const { control, handleCreateCategory, loading } = useFormCreateCategory(setOpen);


    const handleClose = (open?: boolean) => {
        if (open) {
            setOpen(open);
            return;
        }

        setOpen(false);
    };

    return (
        <>
            <Button onClick={() => {
                setOpen(true);
            }}>
                <LuPlus /> Crea nueva categoría
            </Button>

            <AppDialog.Root open={(open)} onClose={handleClose}
                title="Crear una nueva categoría"
                size={'lg'}
                loading={loading}
            >
                <Form onSubmit={handleCreateCategory}>
                    <AppDialog.Body>
                        <AppFormInput<CreateCategoryType>
                            name="nombre"
                            control={control}
                            isRequired
                            label="Nombre de la categoría"
                            isDisable={loading}
                        />
                    </AppDialog.Body>
                    <AppDialog.Actions >
                        <Button colorPalette={'blue'} type="submit" loading={loading}>
                            Guardar
                        </Button>
                        <Button onClick={() => handleClose()} loading={loading}>
                            Salir
                        </Button>
                    </AppDialog.Actions>
                </Form>
            </AppDialog.Root>
        </>
    );
};