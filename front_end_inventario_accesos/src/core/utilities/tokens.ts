import type { User, UserToken } from "../@types/core";
import { jwtDecode } from "jwt-decode";




export const tokenDecode = (token: string): UserToken => {
    return jwtDecode<UserToken>(token);
};



export const setTokenForStorage = (token: string) => {
    localStorage.setItem(btoa("jwt"), btoa(token));
};  

export const setRefreshTokenForStorage = (token: string) => {
    localStorage.setItem(btoa("refresh_jwt"), btoa(token));
};  


export const extractTokenForStorage = () => {
    const tokenB64 = localStorage.getItem(btoa("jwt"));
    if(!tokenB64){
        return null;
    }

    return atob(tokenB64);
};

export const extractRefreshTokenForStorage = () => {
    const tokenB64 = localStorage.getItem(btoa("refresh_jwt"));
    if(!tokenB64){
        return null;
    }

    return atob(tokenB64);
};


export const deleteAllTokens = () => {
    const token = btoa("refresh_jwt");
    const refreshToken = btoa("jwt");

    localStorage.removeItem(token);
    localStorage.removeItem(refreshToken);


};

export const buildUserFromUserToken = ({email, name, jti, sub, roles, ubicaciones, }: UserToken): User => {

    return {
        id: jti,
        username: sub,
        name,
        email,
        roles,
        ubicaciones,
    };
};