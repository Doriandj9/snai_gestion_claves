import type { Usuario } from "@/core/@types/core";


export type Ubicacion = {
  ubicacionId: string;        // UUID
  nombre: string;
  padreId?: string | null;    // UUID, puede ser nulo
  createdAt?: string | null;
  updatedAt?: string | null;
  createdBy?: Usuario | null;  // UUID
  updatedBy?: Usuario | null;  // UUID
};


export type AllUbicaciones = () => Promise<Ubicacion[]>;