

export const AuthLayout: React.FC<React.PropsWithChildren> = ({children}) => {

    return (
        <>
            <div className="relative min-h-screen w-full">
                {children}
            </div>
        </>
    );
}; 