import { AppTable } from "@/core/components/AppTable";
import { useAllCategories } from "../hooks/categories/queries";
import { useCategoryTableHelper } from "../helpers/categoryTableHelper";
import { CreateCategoryComponent } from "../components/CreateCategoryComponent";
import { Subcategorias } from "../components/Subcategorias";


export const Categories = () => {
    const { data, isLoading, error } = useAllCategories();

    const helper = useCategoryTableHelper();

    return (
        <>
            <div className="m-2 text-end">
                <CreateCategoryComponent />
            </div>
            <AppTable data={data ?? []} error={error} isLoading={isLoading} tableHelper={helper} displayRows={5} />

            <div>
               <Subcategorias />
            </div>

        </>
    );
};