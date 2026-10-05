
type Alcance = {
    alcanceId: string;
    defaultAlcance: "PUBLICO" | "PRIVADO";
};

export type AllAlcances = () => Promise<Alcance[]>;