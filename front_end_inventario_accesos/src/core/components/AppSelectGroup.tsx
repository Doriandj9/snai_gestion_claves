import { createListCollection, Portal, Select, type SelectRootProps } from '@chakra-ui/react';
import { Controller, type Control, type FieldValues, type Path } from 'react-hook-form';
import { groupBy } from 'es-toolkit/compat';

type AppSelectProps<T extends FieldValues> = Omit<SelectRootProps, 'name' | 'collection' | 'onValueChange' | 'value' | 'disabled'>
    & {
        control: Control<T>;
        name: Path<T>;                // ← 🔥 Esto sí es compatible con RHF
        label?: string;
        className?: string;
        isRequired?: boolean;
        isDisable?: boolean;
        inEmergent?: boolean;
        items: { label: string; value: string, parent: string }[],
        placeholder?: string;
        parentText?: string;
    };

export const AppSelectGroup = <T extends Record<string, unknown>>({
    name,
    control,
    label,
    isDisable,
    isRequired,
    items,
    className,
    inEmergent,
    placeholder,
    parentText,
    ...rest
}: AppSelectProps<T>) => {

    const values = createListCollection({
        items
    });


    const data = Object.entries(
        groupBy(values.items, (item) => item.parent),
    );

    return (
        <Controller
            name={name}
            control={control}
            render={({ field, fieldState }) => {

                return (
                    <span className={className}>

                        <Select.Root
                            name={field.name}
                            collection={values} size="sm" width="320px"
                            onValueChange={({ value }) => field.onChange(value)}
                            onInteractOutside={() => field.onBlur()}
                            value={field.value ? [String(field.value)] : [""]}
                            disabled={isDisable}
                            {...rest}
                        >
                           

                            <Select.HiddenSelect />
                            <Select.Label fontWeight={'semibold'}>{label}</Select.Label>
                            {isRequired && <span className="text-red-400">*</span>}
                             <span className="text-xs text-slate-700"> {parentText}</span>
                            <Select.Control>
                                <Select.Trigger>
                                    <Select.ValueText placeholder={placeholder ?? 'Seleccione'} />
                                </Select.Trigger>
                                <Select.IndicatorGroup>
                                    <Select.Indicator />
                                </Select.IndicatorGroup>
                            </Select.Control>
                            {
                                inEmergent ?
                                    <Portal>
                                        <SelectItems values={data} />
                                    </Portal>
                                    :
                                    <SelectItems values={data} />

                            }

                        </Select.Root>
                        <div>
                            <span className="text-red-600 text-xs">{fieldState.error?.message}</span>
                        </div>

                    </span>
                );
            }}
        />
    );
};


const SelectItems = ({ values }: {
    values: [string, {
        label: string;
        value: string;
        parent: string;
    }[]][]
}) => {

    return (
        <>
            <Select.Positioner>
                <Select.Content>
                    {values.map(([parent, items]) => (
                        <Select.ItemGroup key={parent}>
                            <Select.ItemGroupLabel>{parent}</Select.ItemGroupLabel>
                            {items.map((item) => (
                                <Select.Item item={item} key={item.value}>
                                    {item.label}
                                    <Select.ItemIndicator />
                                </Select.Item>
                            ))}
                        </Select.ItemGroup>
                    ))}
                </Select.Content>
            </Select.Positioner>
        </>
    );
};

