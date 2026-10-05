import { Card, Flex, IconButton, Tag } from "@chakra-ui/react";
import type { Recurso, RecursoTable } from "../hooks/recursos/recurso";
import { formatTimeWithZone } from "@/core/utilities/dates";
import { LuLayers2, LuEye, LuShare2, LuCylinder } from "react-icons/lu";
import { ViewCopyResource } from "./ViewCopyResource";
import { CopyAllColumnsList } from "./CopyAllColumnsList";
import { EditResource } from "./EditRespurce";
import { AppMoreContent } from "@/core/components/AppMoreContent";




export const CardResource = ({ resource }: { resource: Recurso | RecursoTable }) => {

    return (
        <>
            <Card.Root width="280px" title="">
                <Card.Body gap="2">
                    <Card.Title mt="2"><AppMoreContent content={resource.titulo ?? ''} max={25} /></Card.Title>

                    <Flex direction={'column'} gap={2}>
                        <div>
                            <Flex direction={'column'} gap={1}>
                                <Flex gap={1} color={'gray.500'} fontSize={'sm'}>
                                    Categoría:
                                    <Tag.Root>
                                        <Tag.StartElement>
                                            <LuCylinder />
                                        </Tag.StartElement>
                                        <Tag.Label>{resource.subcategoria?.categoria?.nombre}</Tag.Label>
                                    </Tag.Root>

                                </Flex>
                                <Flex gap={1} color={'gray.500'} fontSize={'sm'}>
                                    Registro:
                                    <Tag.Root>
                                        <Tag.StartElement>
                                            <LuLayers2 />
                                        </Tag.StartElement>
                                        <Tag.Label>{resource.subcategoria?.nombre}</Tag.Label>
                                    </Tag.Root>

                                </Flex>
                                <Flex gap={1} color={'gray.500'} fontSize={'sm'}>
                                    Acceso:
                                    <Tag.Root>
                                        <Tag.StartElement>
                                            <LuEye />
                                        </Tag.StartElement>
                                        <Tag.Label>{resource.alcance?.defaultAlcance}</Tag.Label>
                                    </Tag.Root>

                                </Flex>
                            </Flex>
                        </div>
                        <span className="block text-xs">
                            {formatTimeWithZone(resource?.updatedAt ?? '')}
                        </span>
                    </Flex>
                </Card.Body>
                <Card.Footer justifyContent="flex-end">
                    <ViewCopyResource resource={resource as RecursoTable} trigger={(open, change) => (
                        <IconButton
                            onClick={() => change(true)}
                            variant={'outline'}><LuEye />
                        </IconButton>
                    )} >
                        <CopyAllColumnsList resource={resource as RecursoTable} />
                    </ViewCopyResource>
                    <EditResource recurso={resource as RecursoTable} />
                    <IconButton disabled={resource.alcance?.defaultAlcance !== 'PUBLICO'}><LuShare2 /></IconButton>
                </Card.Footer>
            </Card.Root>
        </>
    );
};


