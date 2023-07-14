import React from "react"

export default function Main() {
    const prodPage = "http://localhost:8080/product"
    return (
        <main>
            <h1>Welcome</h1>
            <a href={prodPage}><h1>To product</h1></a>
        </main>
    )
}