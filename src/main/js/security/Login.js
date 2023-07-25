import React from "react";
export default function Login(props){
    const [formData, setFormData] = React.useState({
        username: "",
        password: ""
    })
    const [token, setToken] = React.useState("")

    const [loginError, setLoginError] = React.useState(false)


    React.useEffect( () => {
        localStorage.setItem('token', token)

    }, [token])


    function handleFormChange(event){
        const {name, value} = event.target

        setFormData(prevState => {
            return {...prevState,
                [name]: value}
        })

    }


    function handleFormSubmit(event){
        event.preventDefault()
        fetch("http://localhost:8080/api/services/controller/login",{
            method: "POST",
            body: JSON.stringify(formData),
            headers: {
                "Content-type" : "application/json; charset=UTF-8"
            }
        })
            .then(resp => {
                return resp.status === 200 ? resp.text() : resp.status
            })
            .then(res => {
                if(res === 401){
                    setLoginError(true)
                }else{
                    setToken(res.split(" ").pop())
                    setLoginError(false)
                    window.location.href = "http://localhost:8080/welcome"

                }
            })
    }

    return(<div>
            {loginError && <p style={{color: "red"}}>Invalid Login</p>}
        <form onSubmit={handleFormSubmit}>
            <input
            type="text"
            name="username"
            placeholder="Username"
            onChange={handleFormChange}
            value={formData.username}
            />
            <input
                type="text"
                name="password"
                placeholder="Password"
                onChange={handleFormChange}
                value={formData.password}
            />
            <button>Submit</button>
        </form>
            <button onClick={props.flipLogin}>Sign-up</button>
            <a href="http://localhost:8080/welcome">Go anyway</a>
    </div>
    )
}