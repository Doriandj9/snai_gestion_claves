

import { createListCollection, Portal, Select, type ListCollection, type SelectRootProps } from '@chakra-ui/react';
import { Controller, type Control, type FieldValues, type Path } from 'react-hook-form';

type AppSelectProps<T extends FieldValues> = Omit<SelectRootProps, 'name' | 'collection' | 'onValueChange' | 'value' | 'disabled'>
    & {
        control: Control<T>;
        name: Path<T>;                // ← 🔥 Esto sí es compatible con RHF
        label?: string;
        className?: string;
        isRequired?: boolean;
        isDisable?: boolean;
        inEmergent?: boolean;
        items: { label: string; value: string }[],
        placeholder?: string;
    };

export const AppSelect = <T extends Record<string, unknown>>({
    name,
    control,
    label,
    isDisable,
    isRequired,
    items,
    className,
    inEmergent,
    placeholder,
    ...rest
}: AppSelectProps<T>) => {

    const values = createListCollection({
        items
    });

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
                                        <SelectItems values={values} />
                                    </Portal>
                                    :
                                    <SelectItems values={values} />

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
    values: ListCollection<{
        label: string;
        value: string;
    }>
}) => {

    return (
        <>
            <Select.Positioner>
                <Select.Content>
                    {values.items.map((item) => (
                        <Select.Item item={item} key={item.value}>
                            {item.label}
                            <Select.ItemIndicator />
                        </Select.Item>
                    ))}
                </Select.Content>
            </Select.Positioner>
        </>
    );
};