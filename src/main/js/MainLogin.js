import React from "react"
import Login from "./security/Login"
import Register from "./security/Register"
export default function MainLogin(){
    const [login, setLogin] = React.useState(true)

    function flipLogin(){
        setLogin(prevState => !prevState)
    }


    return (<main>
                {login ? <Login flipLogin={flipLogin}/> : <Register flipLogin={flipLogin} />}
            </main>
    )
}