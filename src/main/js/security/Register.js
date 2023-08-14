import React from "react"

export default function Register(props){
    const [formData, setFormData] = React.useState({
        username: "",
        password: "",
        email: "",
        roles: ["ROLE_USER"]
    })

    function handleSubmit(event) {
        event.preventDefault()

        fetch("http://localhost:8080/api/user/create",{
            method: "POST",
            body: JSON.stringify(formData),
            headers: {
                "Content-type" : "application/json; charset=UTF-8"
            }
        })
            .then(resp => {
                switch (resp.status){
                    case 409:
                        return window.alert("Username or email already exist")
                    case 200:
                        window.alert("Registration successful!\nRedirecting to login page...")
                        const sleep = (delay) => new Promise((resolve) => setTimeout(resolve, delay))
                        const delay = async () =>  {
                            await sleep(5000)
                        }
                        delay().then()

                        window.location.href = "http://localhost:8080/login"
                        return
                    default:
                        return window.alert("Unknown Server Error")
                }
                }
            )
    }

    function handleFormChange(event) {
        const {name, value} = event.target

        setFormData(prevState => {
            return {...prevState,
                [name]: value}
        })
    }

    return (
        <div>
            <form onSubmit={handleSubmit}>
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
                <input
                    type="text"
                    name="email"
                    placeholder="Email"
                    onChange={handleFormChange}
                    value={formData.email}
                />
                <button>Submit</button>
            </form>
            <button onClick={props.flipLogin}>Login</button>

        </div>
    )
}