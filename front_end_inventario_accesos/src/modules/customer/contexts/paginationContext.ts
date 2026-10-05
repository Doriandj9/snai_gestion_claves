import { createContext } from "react";


type PaginationContextType = {
    page: number;
    change: React.Dispatch<React.SetStateAction<number>>;
};

export const PaginationContext = createContext<PaginationContextType>({page: 0,change: () => {}});