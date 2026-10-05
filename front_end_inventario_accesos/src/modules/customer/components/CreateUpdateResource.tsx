import { AppDialog } from "@/core/constants/components";
import { useAllSubcategories } from "@/modules/admin/hooks/subcategories/queries";
import { Button } from "@chakra-ui/react";
import { useState } from "react";
import { useFormCreateRecurso } from "../schemas/recursoSchema";
import { useForm } from "react-hook-form";
import { Form } from "react-router-dom";
import { z } from 'zod';
import { AppFormInput } from "@/core/components/AppInput";
import { useAllAlcances } from "../hooks/alcances/queries";
import { useAuthStore } from "@/store/auth/userAuth";
import { AppSelectGroup } from "@/core/components/AppSelectGroup";
import type { RecursoTable } from "../hooks/recursos/recurso";
import { AppCombobox } from "@/core/components/AppCombobox";
import { useAllUbicaciones } from "../hooks/ubicaciones/queries";

type CreateUpdateResourceProps = {
    update?: boolean;
    recurso?: RecursoTable;
    page: number;
    size: number;
    trigger: (change: React.Dispatch<React.SetStateAction<boolean>>) => React.ReactNode;
};

export const CreateUpdateResource: React.FC<CreateUpdateResourceProps> = ({ page, size, recurso, update, trigger }) => {
    if (update && !recurso) {
        throw Error("Se necesita un recurso para su actualización");
    }

    const user = useAuthStore((state) => state.user);
    const [open, setOpen] = useState(false);
    const { data } = useAllSubcategories();
    const { data: ubicaciones } = useAllUbicaciones();

    let defaultInitalValues = {};

    if (recurso) {
        defaultInitalValues = { subcategoria: recurso.subcategoria.subcategoriaId };
    }
    const { control: controlInitial, watch } = useForm<{ subcategoria: string }>({ defaultValues: defaultInitalValues });

    const subcategory = data?.find((r) => {
        if (recurso) {
            return r.subcategoriaId == recurso.subcategoria.subcategoriaId;
        }

        return r.subcategoriaId == watch('subcategoria');
    });

    const columns = subcategory?.payload;

    let defaultValuesUpdate = {};

    if (recurso) {
        defaultValuesUpdate = { titulo: recurso.titulo };
        
        columns?.map((col) => {
            return {
                [col.columnName]: Reflect.get(recurso, col.columnName)
            };
        })?.forEach((col) => {
            Object.entries(col).forEach(([a, b]) => {
                defaultValuesUpdate = { ...defaultValuesUpdate, [a]: b };
            });
        });
    }

    const { data: alcances } = useAllAlcances();



    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    const { control, handleCreate, schema, loading, loadingUpdate } = useFormCreateRecurso(columns ?? [], {
        alcanceId: alcances?.find((al) => al.defaultAlcance == 'PRIVADO')?.alcanceId ?? '',
        nombreUsuario: user?.username ?? '',
        subcategoriaId: subcategory?.subcategoriaId ?? '',
        ...defaultValuesUpdate
    }, user?.username ?? '', page, size, (op) => setOpen(op), update, recurso?.recursoId);

    type DinamicType = z.input<typeof schema>;


    const handleClose = (op?: boolean) => {
        if (op) {
            setOpen(op);
            return;
        }

        setOpen(false);

    };

    const loadingAction = loading || loadingUpdate;

    return (
        <>
            {trigger(setOpen)}
            <AppDialog.Root title={update ? 'Actualizar el registro' : "Nuevo registro"} open={open} onClose={handleClose} size={'xl'}>
                <Form onSubmit={handleCreate}>

                    <AppDialog.Body>
                        <div className="flex gap-2">
                            <AppSelectGroup
                                parentText={subcategory?.categoria?.nombre}
                                label="Seleccione el tipo de registro"
                                isDisable={recurso ? true : false}
                                control={controlInitial} name="subcategoria"
                                items={data?.map((item) => ({ label: item.nombre, value: item.subcategoriaId, parent: item.categoria?.nombre ?? '' })) ?? []} />
                            <AppCombobox
                                textHelper="Sirve para identificar a que centro o lugar pertenece el registro"
                                control={control}
                                items={ubicaciones?.map((item) => ({ label: item.nombre, value: item.ubicacionId })) ?? []}
                                label="Seleccione donde pertenece el registro"
                                name="ubicacionId"
                                topHelperText
                                isRequired
                                
                            />
                        </div>


                        {
                            <>
                                {
                                    columns?.length &&
                                    <div className="my-2">
                                        <AppFormInput
                                            control={control}
                                            name="titulo"
                                            label="titulo"
                                            className=""
                                            isRequired
                                            placeholder=""
                                            helperText="Es el titulo que se muestra en la presentación"
                                            disabled={loadingAction}
                                        />
                                    </div>
                                }
                                {
                                    columns?.map((col) => {
                                        if (!col.selected) {
                                            return null;
                                        }

                                        return (
                                            <div key={col.columnName} className="mb-2">
                                                <AppFormInput<DinamicType>
                                                    control={control}
                                                    name={col.columnName}
                                                    label={col.columnName}
                                                    isRequired={col.isRequired}
                                                    disabled={loadingAction}
                                                />
                                            </div>
                                        );
                                    })
                                }
                            </>
                        }

                    </AppDialog.Body>
                    <AppDialog.Actions>
                        <Button colorPalette={'blue'} type="submit" loading={loadingAction}>
                            {update ? 'Actualizar' : 'Guardar'}
                        </Button>
                        <Button onClick={() => handleClose()} loading={loadingAction}>
                            Salir
                        </Button>
                    </AppDialog.Actions>
                </Form>
            </AppDialog.Root>
        </>
    );
};