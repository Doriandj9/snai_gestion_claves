import { recursiveRoutes } from "@/core/utilities/url";
import { webRoutes, type StructureBasicRoutesWeb } from "@/routes/webRoutes";
import { NavLink } from "react-router-dom";

export const Navbar = () => {
 const routes = webRoutes.customer;

    const customerRoutes: StructureBasicRoutesWeb[] = [];

    recursiveRoutes(routes, customerRoutes);


    return (
        <>
            <div className="bg-transparent">
                <nav className="">
                    <ul className="">
                        {customerRoutes.map((route) => (
                            <li className="" key={route.path}>
                                <NavLink
                                    className={({ isActive }) => isActive ? 'nav-item item-selected' : 'nav-item item'}
                                    to={route.path}
                                    end
                                >
                                    {route.icon} {route.title}
                                </NavLink>

                            </li>
                        ))}
                    </ul>
                </nav>
            </div>

        </>
    );
};