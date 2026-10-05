import { Field, Input, type InputProps } from '@chakra-ui/react';
import { Controller, type Control, type FieldValues, type Path } from 'react-hook-form';

type AppFormInputProps<T extends FieldValues> = Omit<InputProps, 'name' | 'type' | 'value'> & {
  control: Control<T>;
  name: Path<T>;                // ← 🔥 Esto sí es compatible con RHF
  label?: string;
  className?: string;
  isRequired?: boolean;
  isDisable?: boolean;
  type?: React.HTMLInputTypeAttribute;
  helperText?: string;
};

export const AppFormInput = <T extends Record<string, unknown>>({
  name,
  control,
  type = "text",
  label,
  isDisable,
  isRequired,
  className,
  helperText,
  ...rest
}: AppFormInputProps<T>) => {
    return (
        <Controller
            name={name}
            control={control}
            render={({ field, fieldState }) => {
                return (
                    <div className={className}>
                        <Field.Root disabled={isDisable}>
                            <Field.Label fontWeight={'semibold'}>
                                {label}
                                {isRequired && <span className="text-red-400">*</span>}
                            </Field.Label>
                        <Input {...field} type={type} value={field.value ? String(field.value) : ""}  {...rest}/>
                        {
                            helperText &&
                            <Field.HelperText>
                                {helperText}
                            </Field.HelperText>
                        }
                        </Field.Root>
                        <div>
                            <span className="text-red-600 text-xs">{fieldState.error?.message}</span>
                        </div>

                    </div>
                );
            }}
        />
    );
};


