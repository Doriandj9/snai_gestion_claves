import type { RecursoTable } from "../hooks/recursos/recurso";
import { useState } from "react";
import { AppDialog } from "@/core/constants/components";
import { AppCopyComponent } from "@/core/components/AppCopyComponent";
import { Button } from "@chakra-ui/react";

type ViewCopyResourceProps = {
    resource: RecursoTable;
    trigger: (open: boolean, change: React.Dispatch<React.SetStateAction<boolean>>) => React.ReactNode;
} & React.PropsWithChildren;

export const ViewCopyResource: React.FC<ViewCopyResourceProps> = ({ resource, trigger, children }) => {
    const [open, setOpen] = useState(false);

    const handleClose = (opened?: boolean) => {
        if (opened) {
            setOpen(opened);
            return;
        }

        setOpen(false);
    };


    return (
        <>
            {trigger(open, setOpen)}
            <AppDialog.Root
                open={open}
                onClose={handleClose}
                title="Datos"
                size={'lg'}
            >
                <AppDialog.Body>
                    <div className="flex gap-2 flex-col">
                        {resource.columns.map((col) => {

                            if(!col.selected) {
                                return null;
                            }

                            return (
                                <div className="" key={col.columnName}>
                                    <h5 className="mb-1 font-semibold">{col.columnName}</h5>
                                    <AppCopyComponent text={Reflect.get(resource, col.columnName)} 
                                    isPassword={col.columnName === 'clave'}
                                    />
                                </div>
                            );
                        })}
                    </div>
                </AppDialog.Body>

                <AppDialog.Actions>
                    {children}
                    <Button onClick={() => setOpen(false)}>
                        Cerrar
                    </Button>
                </AppDialog.Actions>

            </AppDialog.Root>
        </>
    );
};



