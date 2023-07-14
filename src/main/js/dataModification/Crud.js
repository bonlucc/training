import React from "react"
import {AccessType} from "../AccessType"
//import Create from "./Create"
import Read from "./Read"
//import Update from "./Update"
//import Delete from  "./Delete"
export default function Crud({access}){
    return (
        <div>
            {access === AccessType.READ && <Read />}
        </div>
    )
}