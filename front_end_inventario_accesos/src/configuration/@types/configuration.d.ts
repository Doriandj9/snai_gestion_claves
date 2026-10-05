export type EnvironmentType = 'production' | 'development';

export type appType = {
    server: string,
    colors: {[key:string]: string}
    environment: EnvironmentType
};