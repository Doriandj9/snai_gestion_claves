import { AuthLayout } from "@/core/layouts/AuthLayout";
import { Button, IconButton } from "@chakra-ui/react";
import logo from '@/assets/imgs/banner.jpg';
import { AppFormInput } from "@/core/components/AppInput";
import { Form } from "react-router-dom";
import { useAuthForm, type AuthFormType } from "../schemas/auth";
import { useState } from "react";
import { LuEye, LuEyeOff } from "react-icons/lu";

export const AuthLogin = () => {
    const {control, handleAuth, loading} = useAuthForm();
    const [typeInput, setTypeInput] = useState<'text'| 'password'>('password');

    return (
        <>
            <AuthLayout>

                <section className="m-8 flex gap-4">
                    <div className="w-full lg:w-3/5 mt-24">
                        <div className="text-center">
                            <h2 className="font-bold mb-4 text-3xl">Iniciar Session</h2>
                            <p className="text-lg font-normal">Ingrese su nombre de usuario y contraseña de red (de su computadora)</p>
                        </div>
                        <Form onSubmit={handleAuth}
                         className="mt-8 mb-2 mx-auto w-80 max-w-screen-5xl lg:w-1/2">
                            <div className="mb-1 flex flex-col gap-6">
                                <AppFormInput<AuthFormType>
                                control={control}
                                name="username"
                                label="Usuario"
                                isRequired
                                isDisable={loading}
                                />

                               <div className="relative">
                                 <AppFormInput<AuthFormType>
                                control={control}
                                name="password"
                                label="Contraseña"
                                isRequired
                                type={typeInput}
                                isDisable={loading}

                                />

                                <IconButton position={'absolute'} bottom={0} right={0}
                                onClick={() => setTypeInput(typeInput === 'password' ? 'text' : 'password')}
                                variant={'ghost'}
                                >
                                    {
                                        typeInput === 'password' ?
                                        <LuEye />
                                        :
                                        <LuEyeOff />
                                    }
                                </IconButton>
                               </div>
                            </div>

                            <div className="my-4">
                                <Button loading={loading} type="submit">Ingresar</Button>
                            </div>

                        </Form>

                    </div>
                    <div className="w-2/5 h-full hidden lg:block">
                    <h2 className="text-center text-xl my-6 font-semibold">
                        Sistema de registro de inventario de accesos
                    </h2>
                        <img
                            src={logo}
                            className="h-full w-full object-cover rounded-3xl"
                        />
                    </div>

                </section>
            </AuthLayout>

        </>
    );
};