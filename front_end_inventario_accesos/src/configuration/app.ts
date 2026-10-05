import axios from "axios";
import type { appType } from "./@types/configuration";

const { VITE_SERVER_API: server_api, VITE_APP_ENV } = import.meta.env;

export const app: appType = {
    server: server_api,
    colors: {
        primary: '#2877f5',
        secondary: '#20295c'
    },
    environment: VITE_APP_ENV ?? 'development'
};


export const api = axios.create({
    baseURL: `${app.server}/api`
});
