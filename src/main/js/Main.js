import React from "react"

export default function Main() {
    const prodPage = "http://localhost:8080/product"
    const adminPage = "http://localhost:8080/admin"
    return (
        <main>
            <h1>Welcome</h1>
            <a href={prodPage}><h1>To product</h1></a>
            <a href={adminPage}><h1>Admin</h1></a>
        </main>
    )
}