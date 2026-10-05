

export const groupBy = (items: unknown[], fn: (item: unknown) => string) => {

    const result: {[key: string]: unknown[]} = {};
    const set = new Set();
    
    for(const value of items){
        const key = fn(value);
        
    }

    
};