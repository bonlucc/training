import React from "react"

export default function Register(props){
    const [formData, setFormData] = React.useState({
        username: "",
        password: "",
        email: ""
    })

    function handleSubmit(event) {
        event.preventDefault()

        fetch("http://localhost:8080/api/user",{
            method: "POST",
            body: JSON.stringify(formData),
            headers: {
                "Content-type" : "application/json; charset=UTF-8"
            }
        })
            .then(() => {
                    window.alert("Registration successful!\nRedirecting to login page...")
                    const sleep = (delay) => new Promise((resolve) => setTimeout(resolve, delay))
                    const delay = async () =>  {
                        await sleep(5000)
                    }
                    delay().then()

                    window.location.href = "http://localhost:8080/login"
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