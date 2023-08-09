import React from "react"
import {AccessType} from "../AccessType";

export default function Create({subject, checkNull, api, apiComplement, inputParams, handleChange, access}){

    const [loginError, setLoginError] = React.useState(false)
    const [createError, setCreateError] = React.useState(false)

    function init(){
       return subject === null ? {
            method: getMethod(access),
            headers: {"authorization" : "Bearer " + localStorage.getItem("token")}
        } : {
           method: getMethod(access),
           body: JSON.stringify(subject),
           headers: {
               "Content-type": "application/json; charset=UTF-8",
               "authorization" : "Bearer " + localStorage.getItem("token")
           }
       }
    }

    function handleSubmit(event){
        event.preventDefault()
        if(checkNull()){
            setCreateError(true)
            return
        }
        fetch(api + apiComplement, init()).then(resp => {
            return resp.status
        })
            .then(resp => resp >= 400 && resp < 500 ? setLoginError(true) : window.alert("Successful"))
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

    function getMethod(access){
        switch (access) {
            case AccessType.CREATE:
                return "POST"
            case AccessType.READ:
                return "GET"
            case AccessType.UPDATE:
                return "PUT"
            case AccessType.DELETE:
                return "DELETE"

        }
    }
    return (<div>
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