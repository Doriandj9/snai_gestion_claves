
import { Skeleton, Stack } from "@chakra-ui/react";



export const LoadingTable = () => {

    return (
        <>
            <div className="mt-4 w-full">
                <Stack gap="1" >
                    <Skeleton width={'full'} height={'8'} />
                    <Skeleton width={'full'} height={'8'} />
                    <Skeleton width={'full'} height={'8'} />
                    <Skeleton width={'full'} height={'8'} />
                    <Skeleton width={'full'} height={'8'} />
                    <Skeleton width={'full'} height={'8'} />
                </Stack>
            </div>
        </>
    );
};