import type React from "react";
import { useState } from "react";

type AppCopyComponentProps = React.HTMLAttributes<HTMLDivElement> & {
    content: string;
    max: number;
    min?: number;
}

export const AppMoreContent: React.FC<AppCopyComponentProps> = (props) => {
    const {min, content, max,...rest} = props;
    const [isMore, setIsMore] = useState(content.length > max);
    const text = isMore ? content.slice(min?? 0, max) : content;


    return (
        <>
            <div {...rest}>
                {text}
                {isMore ? <>
                <button 
                className="cursor-pointer text-xs text-slate-600 underline"
                    onClick={() => setIsMore((s) => !s)}
                    >
                    ...Ver mas
                </button>
                </> : '' }
            </div>
        </>
    );
};