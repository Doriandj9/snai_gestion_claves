import {
    Combobox,
    Field,
    Portal,
    useFilter,
    useListCollection,
    type ComboboxRootProps,
} from "@chakra-ui/react";
import { Controller } from "react-hook-form";

type AppComboboxProps = Omit<
    ComboboxRootProps, "collection" | ""
> & {
    items: { label: string, value: string | number }[];
    label: string;
    inEmergent?: boolean;
    textHelper?: string;
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    control?: any;
    name: string;
    placeholder?: string;
    isRequired?: boolean;
    topHelperText?: boolean;
};

export const AppCombobox: React.FC<AppComboboxProps> = ({
    items,
    name,
    label,
    control,
    inEmergent,
    textHelper,
    placeholder,
    isRequired,
    className,
    topHelperText,
    ...rest
}) => {
    const { contains } = useFilter({ sensitivity: "base" });

    const { collection, filter } = useListCollection({
        initialItems: items,
        filter: contains,
    });

    const handleInputChange = (details: Combobox.InputValueChangeDetails) => {
        filter(details.inputValue);
    };

    return (
        <>
            <div className={className}>
                <Field.Root>
                    <Field.Label fontWeight={'semibold'}>{label} {isRequired && <span className="text-red-400">*</span>}</Field.Label>
                    {topHelperText && <Field.HelperText>{textHelper}</Field.HelperText>}
                    <Controller
                        name={name}
                        control={control}
                        render={({ field, fieldState }) => (

                            <Combobox.Root
                                collection={collection}
                                value={field.value ? [field.value] : []}
                                onValueChange={({ value }) => field.onChange(value[0] || "")}
                                onInputValueChange={handleInputChange}
                                onInteractOutside={() => field.onBlur()}
                                openOnClick
                                {...rest}
                            >
                                <Combobox.Control>
                                    <Combobox.Input placeholder={placeholder ?? "Buscar por"} />
                                    <Combobox.IndicatorGroup>
                                        <Combobox.ClearTrigger />
                                        <Combobox.Trigger />
                                    </Combobox.IndicatorGroup>
                                </Combobox.Control>
                                {(!topHelperText) && <Field.HelperText>{textHelper}</Field.HelperText>}
                                <div>
                                    <span className="text-red-600 text-xs">{fieldState.error?.message}</span>
                                </div>

                                {inEmergent ? (
                                    <Portal>
                                        <Combobox.Positioner>
                                            <Combobox.Content>
                                                <Combobox.Empty>No se encontró coincidencias</Combobox.Empty>
                                                {collection.items.map((item) => (
                                                    <Combobox.Item item={item} key={item.value}>
                                                        {item.label}
                                                        <Combobox.ItemIndicator />
                                                    </Combobox.Item>
                                                ))}
                                            </Combobox.Content>
                                        </Combobox.Positioner>
                                    </Portal>
                                ) : (
                                    <Combobox.Positioner>
                                        <Combobox.Content>
                                            <Combobox.Empty>No se encontró coincidencias</Combobox.Empty>
                                            {collection.items.map((item) => (
                                                <Combobox.Item item={item} key={item.value}>
                                                    {item.label}
                                                    <Combobox.ItemIndicator />
                                                </Combobox.Item>
                                            ))}
                                        </Combobox.Content>
                                    </Combobox.Positioner>
                                )}
                            </Combobox.Root>
                        )}
                    />
                </Field.Root>
            </div>

        </>
    );
};