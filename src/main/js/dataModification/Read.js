import React from "react"

export default function Read(){
    const [products, setProducts] = React.useState([{
        productName: "",
        productId: null,
        otherData: ""
    }])
    //console.log("Bearer " + localStorage.getItem('token'))
    const [loginError, setLoginError] = React.useState(false)

    const [table, setTable] = React.useState({
        page: 0,
        size: 5
    })

    const tableStyle = {borderStyle: "solid", borderColor: "black"}
    React.useEffect(
        ()=> {
            fetch("http://localhost:8080/product/api/readPage?page=" + table.page + "&size=" + table.size, {
                headers: {
                    "authorization": "Bearer " + localStorage.getItem('token')
                }
            })
                .then(resp => {
                    return resp.status === 200 ? resp.json() : resp.status
                })
                .then(resp => resp === 401 ? setLoginError(true) : setProducts(resp))
        }, [table])

    const tableData = products.map( product =>
        <tr key={product.productId}><td style={tableStyle}>{product.productName}</td>
            <td style={tableStyle}>{product.otherData}</td></tr>)

    function pageChange(event){
        const direction = event.target.name
        setTable(prevState => {
            return {
                ...table,
                page: direction === "right" ? prevState.page + 1 : prevState.page - 1
            }
        })
    }

    return (
        <div>
            <h1>Read Operation</h1>
            {loginError ?
                <a href="http://localhost:8080/login" style={{color: "red"}}>Invalid Login</a>
                :
            <table style={{borderStyle: "solid", borderColor: "black", width: "100%"}}>
                <tbody>
                <tr><th style={tableStyle}>Name</th><th style={tableStyle}>Data</th></tr>
                {tableData}
                </tbody>
            </table>
            }
            {table.page > 0 && <button name="left"  onClick={pageChange} > &#8592; </button>}
            {table.page <= (products.length / table.size)  && <button name="right" onClick={pageChange} > &#8594; </button>}
        </div>
    )
}