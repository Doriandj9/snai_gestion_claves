import { Input, InputGroup, Kbd } from "@chakra-ui/react";
import { LuSearch } from "react-icons/lu";

export const SearchDashboard = () => {

    return (
        <InputGroup flex="1" startElement={<LuSearch />} endElement={<Kbd>Crt + K</Kbd>}>
            <Input placeholder="Search contacts" className="text-black" />
        </InputGroup>
    );
};
