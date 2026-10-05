import { Clipboard, IconButton } from "@chakra-ui/react";
import { copyFn } from "../utilities/url";

type AppCopyComponentProps = {
    text: string;
    isPassword?: boolean;
};

export const AppCopyComponent: React.FC<AppCopyComponentProps> = ({ text, isPassword }) => {

    

    return (
        <>
            <div className="bg-slate-200 text-black shadow p-4 rounded-2xl relative">
                {
                    (!isPassword) ?
                    text :
                    '********'
                }

                <div className="absolute top-2 right-2">
                    <Clipboard.Root value={text} onClick={() => copyFn(text)}>
                        <Clipboard.Trigger asChild>
                            <IconButton variant="surface" size="xs">
                                <Clipboard.Indicator />
                            </IconButton>
                        </Clipboard.Trigger>
                    </Clipboard.Root>
                </div>
            </div>
        </>
    );
};