import { ButtonGroup, IconButton, Pagination, Table, type TableRootProps } from '@chakra-ui/react';

import { useState } from 'react';
import { LoadingTable } from '@/core/components/LoadingTable';
import type { ResultTableHelperHook } from '@/core/@types/core';
import { LuChevronLeft, LuChevronRight } from 'react-icons/lu';

type AppTableProps<T> = {
    tableHelper: ResultTableHelperHook<T>;
    isLoading: boolean;
    error: unknown;
    data: T[];
    originalPropsTable?: TableRootProps;
    displayRows?: number;
};

export function AppTable<TData>({ tableHelper, isLoading, error, data, originalPropsTable, displayRows = 5 }: AppTableProps<TData>) {
    const { actions, columns, rowStyleCustom } = tableHelper;
    const [page, setPage] = useState(1);

    if (isLoading) {
        return <LoadingTable />;
    }

    if (error) {
        return 'error';
    }

    const values = data.slice((page - 1) * displayRows, page * displayRows);

    return (
        <>
            <Table.ScrollArea>
                <Table.Root {...originalPropsTable}>
                    <Table.Header>
                        <Table.Row>
                            {columns.map((item, index) => {
                                return <Table.ColumnHeader borderColor={'gray.300'} key={index}>{item.header.trim()}</Table.ColumnHeader>;
                            })}
                            {actions && (
                                <Table.ColumnHeader borderColor={'gray.300'} style={{ position: 'sticky', zIndex: 1, right: 0, backgroundColor: 'inherit' }}>
                                    {actions.header.trim()}
                                </Table.ColumnHeader>
                            )}
                        </Table.Row>
                    </Table.Header>
                    <Table.Body>
                        {values.map((row, index) => (
                            <Table.Row style={rowStyleCustom ? rowStyleCustom(row) : {}} key={++index + (page - 1) * displayRows}>
                                {columns.map((item, columnIndex) => (
                                    <Table.Cell key={columnIndex} align="center" borderColor={'gray.300'}>
                                        {item.render(row, ++index + (page - 1) * displayRows)}
                                    </Table.Cell>
                                ))}

                                {actions && (
                                    <Table.Cell
                                        width={actions.width ? actions.width : '1.8rem'}
                                        align="center"
                                        style={{ position: 'sticky', zIndex: 1, right: 0, backgroundColor: 'inherit' }}
                                        borderColor={'gray.300'}
                                    >
                                        {actions.list?.render ? actions.list.render(row, ++index + (page - 1) * displayRows) : ''}
                                    </Table.Cell>
                                )}
                            </Table.Row>
                        ))}
                        {
                            (values.length <= 0) &&
                            <Table.Row>
                                <Table.Cell colSpan={columns.length + 1}>
                                   <div className='text-center w-full'>
                                        No hay datos
                                   </div>
                                </Table.Cell>
                            </Table.Row>
                        }
                    </Table.Body>
                    {/* <Table.Footer>
                    <Table.Row>
                        <Table.Cell />
                    </Table.Row>
                </Table.Footer> */}
                </Table.Root>
            </Table.ScrollArea>
            <div className="mt-2">

                <Pagination.Root count={data?.length}
                    pageSize={displayRows}
                    defaultPage={1}
                    onPageChange={(e) => setPage(e.page)}
                >
                    <ButtonGroup variant="ghost" size="sm">
                        <Pagination.PrevTrigger asChild>
                            <IconButton>
                                <LuChevronLeft />
                            </IconButton>
                        </Pagination.PrevTrigger>

                        <Pagination.Items
                            render={(page) => (
                                <IconButton variant={{ base: "ghost", _selected: "outline" }}>
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
}