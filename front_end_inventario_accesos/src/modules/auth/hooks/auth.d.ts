import type { AuthFormType } from "../schemas/auth";



export type AuthLoginFn =  (data: AuthFormType) => Promise<{jwt: string, refreshToken: string}>;