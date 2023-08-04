import React from "react"

export default function Create({subject, checkNull, api, inputParams, handleChange}){

    const [loginError, setLoginError] = React.useState(false)
    const [createError, setCreateError] = React.useState(false)

    function handleSubmit(event){
        event.preventDefault()
        if(checkNull){
            setCreateError(true)
            return
        }
        fetch(api, {
            method: "POST",
            body: JSON.stringify(subject),
            headers: {
                "Content-type": "application/json; charset=UTF-8",
                "authorization" : "Bearer " + localStorage.getItem("token")
            }
        }).then(resp => {
            return resp.status
        })
            .then(resp => resp >= 401 && resp < 500 ? setLoginError(true) : window.alert("Successful"))
        setCreateError(false)

    }


    let inputs = inputParams.input.map(item =>
        <div><input
            type={item.type}
            placeholder={item.placeholder}
            value={item.value}
            onChange={handleChange}
            name={item.name}
        /></div>)
    let inputsSelect = <div></div>
    if(inputParams.select.active){
        const inputsOptions = inputParams.select.options.map(item => <option value={item.value}>{item.name}</option>)
        inputsSelect = <label>
            {inputParams.select.label}:
            <select name={inputParams.select.name}
                    onChange={handleChange}
                    value={inputParams.select.value}
                    multiple={inputParams.select.multiple}>{inputsOptions}</select>
        </label>
    }

    return (<div>
            <h1>Create Operation</h1>
            {createError && <p style={{color: "red"}}>Null fields are not allowed</p>}
            {loginError ?
                <a href="http://localhost:8080/login" style={{color: "red"}}>Invalid Login</a>
                :
                <form onSubmit={handleSubmit}>
                    {inputs}
                    {inputsSelect}
                    <button>Submit</button>
                </form>}
        </div>
    )
}