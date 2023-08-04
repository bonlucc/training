import React from "react"
import {AccessType} from "./AccessType";
import Crud from "./dataModification/Crud"
import User from "./User";
import Role from "./Role";
export default function MainAdmin(){


    const [loginError, setLoginError] = React.useState(false)

    if(loginError){
        window.location.href = "http://localhost:8080/login"
    }

    return (
        <div>
            {loginError ?
                <a href="http://localhost:8080/login" style={{color: "red"}}>Invalid Login</a>
                :
                <div>
                    <User setLoginError={setLoginError}/>
                    <Role setLoginError={setLoginError}/>
                </div>
            }

        </div>
    )




}