export const DefaultRoles = {
  ADMIN: "ADMINISTRADOR",
  CUSTOMER: "CLIENTE",
  MANAGER: "DIRECTOR",
  INFRASTRUCTURE: "INFRAESTRUCTURA",
  TECHNICAL: "TECNICO",
  RECEPTION: "SECRETARIA"
} as const;


export type DefaultRole = keyof typeof DefaultRoles & {[key:string]: string};

export const DefaultUbicaciones = {
  PLANTA_CENTRAL: "PLANTA_CENTRAL",
  TERRITORIO: "TERRITORIO"
} as const;

export type DefaultUbicacion = keyof typeof DefaultUbicaciones & {[key:string]: string};
//   "PLANTA_CENTRAL" | "TERRITORIO"


export type UserToken = {
  jti: string;
  sub: string;
  name: string;
  email: string;

  roles: DefaultRole[];
  ubicaciones: DefaultUbicacion[];

  exp: number;
  iat: number;
};

export type User = Omit<UserToken,"exp" | "iat" | "jti"| "sub"> & {
  id: string;
  username: string;
};


export type Usuario = {
  userId: string;
  usuario: string;
  nombre: string;
  correo: string;
  activo: boolean;
  roles: DefaultRole[];
  ubicaciones: DefaultUbicacion[] | null;
};

export type AuthStore = {
    user: User | null,
    token: string | null,
    isAuth: boolean,
    refreshToken: string | null,
    setAuth: (token: string, refreshToken) => void
    logout: () => void;
};



type ColumnsTableHelper<TData> = {
    header: string;
    render: {
        (item?: TData, index?: number): React.ReactNode
    };
};

export type ActionListTableHelper<TItem> = {
    render?: (item: TItem, index?: number) => React.ReactNode;
};

export type ResultTableHelperHook<TData> = {
    columns: ColumnsTableHelper<TData>[];
    actions?: {
        header: string;
        list: ActionListTableHelper<TData> | undefined;
        width?: string;
    };
    rowStyleCustom?: (item: TData) => React.CSSProperties; 
};

export type TableHelperHook<T> = {
    (action?: ActionListTableHelper<T>['render']): ResultTableHelperHook<T>;
};


export interface EntityAudit {
  creador?: Usuario | null;
  actualizador?: Usuario | null;
  createdAt?: string | null;  
  updatedAt?: string | null;
}


export type PageResponse<T> = {
  content: T[];
  pageable: {
    pageNumber: number;
    pageSize: number;
    sort: {
      empty: boolean;
      sorted: boolean;
      unsorted: boolean;
    };
    offset: number;
    paged: boolean;
    unpaged: boolean;
  };
  last: boolean;
  totalPages: number;
  totalElements: number;
  first: boolean;
  size: number;
  number: number;
  sort: {
    empty: boolean;
    sorted: boolean;
    unsorted: boolean;
  };
  numberOfElements: number;
  empty: boolean;
};
