import { Checkbox, Field, Input, Table } from "@chakra-ui/react";
import { useAllResourcesAttributes } from "../hooks/resources/queries";
import { useState } from "react";

export type ObjectColumns = {
    columnName: string;
    selected: boolean;
    order: string;
    isRequired: boolean;
}[];


type ChangeValue =
    | { type: "selected"; value: boolean }
    | { type: "isRequired"; value: boolean }
    | { type: "order"; value: string };



export const SelectColumnsForBuild = ({setData}: {setData: (payload: string) => void}) => {
    const { data } = useAllResourcesAttributes();
    const [columns, setColumns] = useState<ObjectColumns>([]);

    const handleChange = (columnName: string, change: ChangeValue) => {
        setColumns((prev) => {
            const existing = prev.find((c) => c.columnName === columnName);

            // Si existe, actualizamos
            if (existing) {
                const result = prev.map((col) =>
                    col.columnName === columnName
                        ? {
                            ...col,
                            selected:
                                change.type === "selected" ? change.value : col.selected,
                            isRequired:
                                change.type === "isRequired" ? change.value : col.isRequired,
                            order: change.type === "order" ? change.value : col.order,
                        }
                        : col
                );
                setData(JSON.stringify(result.map((item) => {
                    const t = {...item, order: item.order !== '' ? parseInt(item.order) : ''};
                    return t;
                })));
                return result;
            }

            // Si NO existe, lo creamos
            const result = [
                ...prev,
                {
                    columnName,
                    selected: change.type === "selected" ? change.value : false,
                    isRequired: change.type === "isRequired" ? change.value : false,
                    order: change.type === "order" ? change.value : "",
                },
            ];
            setData(JSON.stringify(result.map((item) => {
                    const t = {...item, order: item.order !== '' ? parseInt(item.order) : ''};
                    return t;
                })));
            return result;
        });
    };


    return (
        <>
            <div className="my-2">
                <Field.Root>
                    <Field.Label fontWeight={'semibold'}>
                        Seleccione las columnas que requerir al construir el registro.
                    </Field.Label>
                    <div>
                        <Table.Root size="sm">
                            <Table.Header>
                                <Table.Row>
                                    <Table.ColumnHeader>Columna</Table.ColumnHeader>
                                    <Table.ColumnHeader>Obligatorio</Table.ColumnHeader>
                                    <Table.ColumnHeader>Orden</Table.ColumnHeader>
                                </Table.Row>
                            </Table.Header>
                            <Table.Body>
                                {data?.map((item) => (
                                    <Table.Row key={item}>
                                        <Table.Cell>
                                            <Field.Root>
                                                <Checkbox.Root
                                                    checked={columns.find((col) => col.columnName === item)?.selected ?? false}
                                                    onCheckedChange={(e) => {
                                                        handleChange(item,{type: 'selected', value: !!e.checked});
                                                    }}
                                                >
                                                    <Checkbox.HiddenInput />
                                                    <Checkbox.Control />
                                                    <Checkbox.Label>{item}</Checkbox.Label>
                                                </Checkbox.Root>
                                            </Field.Root>

                                        </Table.Cell>
                                        <Table.Cell>
                                            <Field.Root>
                                                <Checkbox.Root
                                                    checked={columns.find((col) => col.columnName === item)?.isRequired ?? false}
                                                    onCheckedChange={(e) => {
                                                        handleChange(
                                                            item,
                                                            {type: 'isRequired', value: !!e.checked}
                                                        );
                                                    }}
                                                >
                                                    <Checkbox.HiddenInput />
                                                    <Checkbox.Control />
                                                    <Checkbox.Label>SI</Checkbox.Label>
                                                </Checkbox.Root>
                                            </Field.Root>
                                        </Table.Cell>
                                        <Table.Cell>
                                            <Field.Root width={'20'}

                                            >
                                                <Input
                                                    type="number"
                                                    value={columns.find((col) => col.columnName === item)?.order ?? ''}
                                                    onChange={(e) => {
                                                        handleChange(item,{type: 'order', value: e.currentTarget.value});
                                                    }}
                                                />
                                            </Field.Root>
                                        </Table.Cell>
                                    </Table.Row>
                                ))}
                            </Table.Body>
                        </Table.Root>
                    </div>
                </Field.Root>

            </div>
        </>
    );
};