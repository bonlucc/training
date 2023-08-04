import React from "react"

export default function Delete(){
    const [productId, setProductId] = React.useState(0)
    const [loginError, setLoginError] = React.useState(false)
    function handleChangeId(event){
        setProductId(event.target.value)
    }

    function handleSubmit(event){
        event.preventDefault()
        if(productId === 0){
            window.alert("Invalid Data")
            return
        }
        fetch("http://localhost:8080/product/api/delete/" + productId, {
            method: "DELETE",
            headers: {
                "authorization" : "Bearer " + localStorage.getItem("token")
            }
        }).then(resp => {
            return resp.status
        })
            .then(resp => resp >= 401 && resp < 500 ? setLoginError(true) : window.alert("Successful"))
    }

    return(
        <div>
            <h1>Delete Operation</h1>
            {loginError ?
                <a href="http://localhost:8080/login" style={{color: "red"}}>Invalid Login</a>
                :
                <form onSubmit={handleSubmit}>
                    <input
                        type="number"
                        value={productId}
                        onChange={handleChangeId}
                    />
                    <button>Submit</button>
                </form>
            }
        </div>
    )
}