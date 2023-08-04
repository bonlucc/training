import React from "react"
import {AccessType} from "./AccessType";
import Create from "./dataModification/Create"
import Read from "./dataModification/Read"
import Update from "./dataModification/Update"
import Delete from  "./dataModification/Delete"
export default function MainProduct() {



    const [access, setAccess] = React.useState(AccessType.NO)

    function sAccess(event) {
        const elementAccess = event.target.name
        setAccess( prevState => {
            return prevState === elementAccess ? AccessType.NO : elementAccess
        })

    }

    //const productsElements = products.map(product =>
    //<div><h2>{product.productName}</h2><h2>{product.otherData}</h2></div>)

    return (
        <main>
            <button name={AccessType.CREATE} onClick={sAccess}>Create</button>
            <button name={AccessType.READ} onClick={sAccess}>Read</button>
            <button name={AccessType.UPDATE} onClick={sAccess}>Update</button>
            <button name={AccessType.DELETE} onClick={sAccess}>Delete</button>
            {access === AccessType.READ && <Read />}
            {access === AccessType.CREATE && <Create />}
            {access === AccessType.UPDATE && <Update />}
            {access === AccessType.DELETE && <Delete />}

        </main>
    )
}