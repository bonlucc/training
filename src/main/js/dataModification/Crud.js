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
        }).then(resp => {
            switch (resp){
                case 401:
                    return setLoginError(true)
                case 403:
                    return setLoginError(true)
                case 404:
                    return window.alert("Requested object not found")
                case 409:
                    return window.alert("Requested object already exists")
                case 200:
                    return window.alert("Successful")
                default:
                    return window.alert("Unknown Server Error")
            }
        })

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