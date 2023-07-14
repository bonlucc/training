import React from "react"
import { createRoot } from "react-dom/client"
import Main from "./Main"
import MainProduct from "./MainProduct";
import client from "./client"

let container
let root

switch (window.location.pathname.split("/").pop()){
    case "product":
        container = document.getElementById("reactProduct")
        root = createRoot(container)
        root.render(<MainProduct />)
        break

    default:
        container = document.getElementById("react")
        root = createRoot(container)
        root.render(<Main />)
        break
}
