import type { Usuario } from "@/core/@types/core";
import { AppDialog } from "@/core/constants/components";
import { Alert, Button, IconButton, Show } from "@chakra-ui/react";
import { useState } from "react";
import { LuBan } from "react-icons/lu";





export const UserActions: React.FC<{ usuario: Usuario }> = ({ usuario }) => {

    return (
        <>
            <span className="flex gap-2 items-center">
                <InactiveUser usuario={usuario} />
            </span>
        </>
    );
};




export const InactiveUser: React.FC<{ usuario: Usuario }> = ({ usuario }) => {

    const [open, setOpen] = useState(false);
    const handleClose = (op?: boolean) => {
        if (op) {
            setOpen(open);
            return;
        }
        setOpen(false);
    };

    return (
        <>
            <IconButton colorPalette={usuario.activo ? 'red' : 'green'} onClick={() => setOpen(true)}>
                <LuBan />
            </IconButton>
            <AppDialog.Root
                open={open}
                title={usuario.activo ? 'Desactivar Usuario' : 'Activar Usuario'}
                onClose={handleClose}
            >
                <AppDialog.Body>
                    <Show
                        when={usuario.activo}
                        fallback={
                            <>
                                <Alert.Root status="info">
                                    <Alert.Indicator />
                                    <Alert.Content>
                                        <Alert.Title>Acción</Alert.Title>
                                        <Alert.Description>
                                            ¿ Esta seguro de activar este usuario ?
                                        </Alert.Description>
                                    </Alert.Content>
                                </Alert.Root>
                            </>
                        }
                    >
                        <Alert.Root status="warning">
                            <Alert.Indicator />
                            <Alert.Content>
                                <Alert.Title>Acción</Alert.Title>
                                <Alert.Description>
                                    ¿ Esta seguro de desactivar este usuario ?
                                </Alert.Description>
                            </Alert.Content>
                        </Alert.Root>
                    </Show>
                </AppDialog.Body>
                <AppDialog.Actions>
                    <Button colorPalette={'blue'}>
                        confirmar
                    </Button>
                    <Button onClick={() => handleClose()}
                    > salir</Button>
                </AppDialog.Actions>
            </AppDialog.Root>
        </>
    );
};