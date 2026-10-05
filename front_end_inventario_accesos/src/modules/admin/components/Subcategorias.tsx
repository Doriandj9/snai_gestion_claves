import { AppTable } from "@/core/components/AppTable";
import { useAllSubcategories } from "../hooks/subcategories/queries";
import { useSubcategoryTableHelper } from "../helpers/subcategoryTableHelper";
import { CreateSubcategoryComponent } from "./CreateSubcategoryComponent";


export const Subcategorias = () => {
    const { data, error, isLoading } = useAllSubcategories();

    const helper = useSubcategoryTableHelper();
    return (
        <>
            <div className="my-4">
                <h3 className="text-center text-xl font-semibold">Sub categorías</h3>
            </div>
            <div className="m-2 text-end">
                <CreateSubcategoryComponent />
            </div>
            <AppTable data={data ?? []} error={error} isLoading={isLoading} tableHelper={helper} />

        </>
    );
};