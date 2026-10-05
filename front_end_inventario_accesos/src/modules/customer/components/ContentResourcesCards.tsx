import { EmptyIcon } from "@/components/ui/svg-components";
import { useAllRecursosForUserPaged } from "../hooks/recursos/queries";
import { CardResource } from "./CardResource";
import { Flex, Skeleton } from "@chakra-ui/react";
import { ButtonGroup, IconButton, Pagination } from "@chakra-ui/react";
import { LuChevronLeft, LuChevronRight } from "react-icons/lu";
import { useContext } from "react";
import { PaginationContext } from "../contexts/paginationContext";



export const ContentResourcesCards = ({ username }: { username: string }) => {
    const {page,change: setPage} = useContext(PaginationContext);
    const size = 5;

    const { data, error, status } = useAllRecursosForUserPaged(username, (page - 1), size);


    return (
        <>
            {
                (status === 'pending') && (
                    <Flex gap={"2"} wrap={"wrap"} justifyContent={'space-around'}>
                        <Skeleton width={'280px'} height={'200px'} />
                        <Skeleton width={'280px'} height={'200px'} />
                        <Skeleton width={'280px'} height={'200px'} />
                        <Skeleton width={'280px'} height={'200px'} />
                        <Skeleton width={'280px'} height={'200px'} />
                    </Flex>
                )
            }
            {
                error &&
                <div>
                    error
                </div>
            }
            {
                data?.empty &&
                <div className="flex justify-center flex-col items-center">
                    <EmptyIcon className="w-24 h-24" />
                    <h6 className="text-xs text-slate-600">
                        Sin registros nuevos
                    </h6>
                </div>
            }
            <Flex gap={"2"} wrap={"wrap"} justifyContent={'space-around'}>
                {data?.content.map((recurso) => (
                    <CardResource key={recurso.recursoId} resource={recurso} />
                ))}
            </Flex>
            <div className="flex justify-center mt-2">
                <Pagination.Root count={data?.totalElements} pageSize={size} defaultPage={1}
                    page={page}
                    onPageChange={(e) => setPage(e.page)}
                >
                    <ButtonGroup variant="ghost" size="sm" attached>
                        <Pagination.PrevTrigger asChild>
                            <IconButton>
                                <LuChevronLeft />
                            </IconButton>
                        </Pagination.PrevTrigger>

                        <Pagination.Items
                            render={(page) => (
                                <IconButton variant={{ base: "ghost", _selected: "solid" }}>
                                    {page.value}
                                </IconButton>
                            )}
                        />

                        <Pagination.NextTrigger asChild>
                            <IconButton>
                                <LuChevronRight />
                            </IconButton>
                        </Pagination.NextTrigger>
                    </ButtonGroup>
                </Pagination.Root>
            </div>
        </>
    );
};