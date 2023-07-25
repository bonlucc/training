import React from "react"
import Crud from "./dataModification/Crud"
import {AccessType} from "./AccessType";

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
            <Crud access={access} />

        </main>
    )
}