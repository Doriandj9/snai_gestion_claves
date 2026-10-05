import { useNavigation } from "react-router-dom";
import  TopBarProgressUI  from 'react-topbar-progress-indicator';



export const TopBarProgress = () => {
    const navigation = useNavigation();

    const isLoading = navigation.state === "loading";

    
    if(!isLoading){
        return null;
    }
    
    return (<>
        <TopBarProgressUI />
    </>);
};