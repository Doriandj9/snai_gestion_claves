import type { AuthStore, User } from '@/core/@types/core';
import { buildUserFromUserToken, deleteAllTokens, extractRefreshTokenForStorage, extractTokenForStorage, setRefreshTokenForStorage, setTokenForStorage, tokenDecode } from '@/core/utilities/tokens';
import { create } from 'zustand';

const tokenStore = extractTokenForStorage();
const refreshTokenStore = extractRefreshTokenForStorage();

export const useAuthStore = create<AuthStore>()((set) => {

  return {
    token: tokenStore,
    refreshToken: refreshTokenStore,
    user: tokenStore ? buildUserFromUserToken(tokenDecode(tokenStore)) : null,
    isAuth: tokenStore ? true : false,
    setAuth: (token, refreshToken) => set(() => {
      let authenticate = false;
      let user: null | User = null;
      try {
        setTokenForStorage(token);
        setRefreshTokenForStorage(refreshToken);
        const userToken = tokenDecode(token);
        user = buildUserFromUserToken(userToken);
        authenticate = true;
      } catch (error) {
        console.log(error);
        authenticate = false;
      }
      return {
        user,
        isAuth: authenticate,
        token,
        refreshToken
      };
    }),
    logout: () => set(() => {
      deleteAllTokens();
      return {
        isAuth: false,
        token: null,
        refreshToken: null,
        user: null
      };
    })
  };
});
