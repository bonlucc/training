import React from "react"

export default function Create(){
    const [products, setProducts] = React.useState({
        productName: "",
        otherData: ""
    })

    const [loginError, setLoginError] = React.useState(false)
    const [createError, setCreateError] = React.useState(false)


    function handleChange(event){
        const {name, value} = event.target
        setProducts(prevState => {
            return {
                ...prevState,
                [name]: value
            }
        })
    }

    function handleSubmit(event){
        event.preventDefault()
        if(products.productName === "" || products.otherData === ""){
            setCreateError(true)
            return
        }
        fetch("http://localhost:8080/product/api/create", {
            method: "POST",
            body: JSON.stringify(products),
            headers: {
                "Content-type": "application/json; charset=UTF-8",
                "authorization" : "Bearer " + localStorage.getItem("token")
            }
        }).then(resp => {
            return resp.status
        })
            .then(resp => resp === 401 ? setLoginError(true) : window.alert("Successful"))
        setCreateError(false)

    }

    return (<div>
        <h1>Create Operation</h1>
            {createError && <p style={{color: "red"}}>Null fields are not allowed</p>}
        {loginError ?
            <a href="http://localhost:8080/login" style={{color: "red"}}>Invalid Login</a>
            :
        <form onSubmit={handleSubmit}>
            <input
            type="text"
            placeholder="Product Name"
            value={products.productName}
            onChange={handleChange}
            name="productName"
            />
            <input
                type="text"
                placeholder="Other Data"
                value={products.otherData}
                onChange={handleChange}
                name="otherData"
            />
            <button>Submit</button>
        </form>}
        </div>
    )
}