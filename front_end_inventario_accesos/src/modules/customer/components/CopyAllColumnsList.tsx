import { Button } from "@chakra-ui/react";
import type { RecursoTable } from "../hooks/recursos/recurso";
import { copyFn } from "@/core/utilities/url";

type CopyAllColumnsListProps = {
    resource: RecursoTable
};

export const CopyAllColumnsList: React.FC<CopyAllColumnsListProps> = ({resource}) => {

    const handleCopy = () => {
        const columnForValue = resource.columns.reduce((prev, current) => {
            return prev.set(current.columnName,Reflect.get(resource,current.columnName));
        },new Map());

        let text = '';
        for(const value of columnForValue){
            text += `${value[0]}: ${value[1]} \n`;
        }

        copyFn(text);
    };

    return (
        <>
            <Button colorPalette={'blue'} onClick={handleCopy}>
                Copiar todo en lista
            </Button>
        </>
    );
};