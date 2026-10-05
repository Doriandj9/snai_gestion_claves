import { CloseButton, Dialog, Portal, type DialogRootProps } from "@chakra-ui/react";
import type { PropsWithChildren } from "react";


type DisableAppDialogProps = Omit<DialogRootProps, "open,title">

type AppDialogProps = React.PropsWithChildren & DisableAppDialogProps & {
    open: boolean;
    title?: string;
    loading?: boolean;
    onClose?: {
        (): void;
        (op: boolean): void;
    };
};


export const DialogRoot: React.FC<AppDialogProps> = ({ open, title, children, onClose, loading, ...props }) => {

    return (
        <Dialog.Root lazyMount open={open} {...props} onOpenChange={(detail) => {
            if(onClose && !loading){
                onClose(detail.open);
            }
        }}>
            <Portal>
                <Dialog.Backdrop />
                <Dialog.Positioner>

                    <Dialog.Content>
                        <Dialog.Header>
                            <Dialog.Title>{title}</Dialog.Title>
                        </Dialog.Header>

                        {children}
                        {
                            (onClose && !loading ) &&
                            <Dialog.CloseTrigger onClick={onClose}  asChild>
                                <CloseButton size="sm" />
                            </Dialog.CloseTrigger>
                        }
                    </Dialog.Content>
                </Dialog.Positioner>
            </Portal>
        </Dialog.Root>
    );
};

export const DialogBody: React.FC<PropsWithChildren> = ({ children }) => {
    return (
        <Dialog.Body>
            {children}
        </Dialog.Body>
    );
};


export const DialogActions: React.FC<PropsWithChildren> = ({ children }) => {

    return (
        <Dialog.Footer>
            {/* <Dialog.ActionTrigger asChild>
                <Button variant="outline">Cancel</Button>
            </Dialog.ActionTrigger>
            <Button>Save</Button> */}
            {children}
        </Dialog.Footer>
    );
};

