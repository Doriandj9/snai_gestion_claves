
import { AppDialog } from "@/core/constants/components";
import { Button } from "@chakra-ui/react";
import { useState } from "react";
import { LuPlus } from "react-icons/lu";
import { Form } from "react-router-dom";
import { AppFormInput } from "@/core/components/AppInput";
import { useFormCreateSubcategory, type FormSubcategoryCreate } from "../schema/subcategoriesSchema";
import { AppCombobox } from "@/core/components/AppCombobox";
import { useQueryClient } from "@tanstack/react-query";
import type { Categoria } from "../hooks/categories/categories";
import { SelectColumnsForBuild } from "./SelectColumnsForBuild";

export const CreateSubcategoryComponent = () => {
    const client = useQueryClient();
    const [open, setOpen] = useState(false);
    const { control, handleCreateSubcategory, loading , setValue} = useFormCreateSubcategory(setOpen);
    const data = client.getQueryData(['categories']) as Categoria[];



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
                <LuPlus /> Crea una nueva sub categoría
            </Button>

            <AppDialog.Root open={(open)}
                title="Crear una nueva sub categoría"
                size={'lg'}
                loading={loading}
            >
                <Form onSubmit={handleCreateSubcategory}>
                    <AppDialog.Body>
                        <AppFormInput<FormSubcategoryCreate>
                            name="nombre"
                            control={control}
                            isRequired
                            label="Nombre de la sub categoría"
                            isDisable={loading}
                        />
                        <AppCombobox
                            control={control}
                            name="categoryId"
                            items={data?.map((item) => ({ label: item.nombre, value: item.categoriaId })) ?? []}
                            label="Selecciona una categoría"
                            inEmergent={false}
                            placeholder="Busca una categoría"
                            width={'full'}
                            isRequired
                            className="mt-2"
                        />

                       <SelectColumnsForBuild setData={(payload) => setValue('payload', payload)} />

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