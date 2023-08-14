import React from "react"

export default function Update() {
    const [products, setProducts] = React.useState({
        productName: "",
        otherData: ""
    })

    const [productId, setProductId] = React.useState(0)
    const [loginError, setLoginError] = React.useState(false)
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
        if(!(products.productName !== "" &&
             products.otherData !== "" &&
              productId !== 0)){
            window.alert("Invalid Data")
            return
        }
        fetch("http://localhost:8080/product/api/update/" + productId, {
            method: "PUT",
            body: JSON.stringify(products),
            headers: {
                "Content-type": "application/json; charset=UTF-8",
                "authorization" : "Bearer " + localStorage.getItem("token")
            }
        }).then(resp => {
            switch (resp.status){
                case 401:
                    return setLoginError(true)
                case 403:
                    return setLoginError(true)
                case 404:
                    return window.alert("Invalid Product Id")
                case 200:
                    return window.alert("Successful")
                default:
                    return window.alert("Unknown Server Error")
            }
        })
    }


    function handleChangeId(event){
        setProductId(event.target.value)
    }

    return (
        <div>
            <h1>Update Operation</h1>
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
                <input
                    type="number"
                    value={productId}
                    onChange={handleChangeId}
                />
                <button>Submit</button>
            </form>}
        </div>
    )
}
