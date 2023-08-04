import React from "react"
import {AccessType} from "./AccessType";
import Crud from "./dataModification/Crud"

export default function User({setLoginError}){
    const [userList, setUserList] = React.useState([{
        id: 0,
        username: "",
        email: "",
        enabled: false,
        roles: [{
            id: 0,
            name: "",
            privileges:[
                {
                    name:""
                }
            ]
        }]
    }])

    const [userCreate, setUserCreate] = React.useState({
        username: "",
        email: "",
        password: "",
        roles: ["ROLE_USER"]
    })

    const [roleList, setRoleList]= React.useState([{
        name: "",
        privileges:[
            {
                name:""
            }
        ]
    }])

    const [access, setAccess] = React.useState(AccessType.NO)
    function sAccess(event) {
        const elementAccess = event.target.name
        setAccess( prevState => {
            return prevState === elementAccess ? AccessType.NO : elementAccess
        })

    }

    function handleChange(event){
        const {name, value, selectedOptions} = event.target
        setUserCreate(prevState => {
            if(!name.startsWith("select")){
                return {
                    ...prevState,
                    [name]:  value
                }}else{
                return {...prevState,
                    roles: extractOptions(selectedOptions)}
            }})
    }

    function extractOptions(arr){
        let roles = []
        for (let i = 0; i < arr.length; i++){
            roles.push(arr[i].value)
        }
        return roles
    }

    function checkNull(){
        return userCreate.email === "" || userCreate.password === "" || userCreate.username === ""
    }

    const [table, setTable] = React.useState({
        page: 0,
        size: 5
    })

    const [total, setTotal] = React.useState(0)

    const tableStyle = {borderStyle: "solid", borderColor: "black"}
    React.useEffect(
        ()=> {
            fetch("http://localhost:8080/api/user/management/Page?page=" + table.page + "&size=" + table.size, {
                headers: {
                    "authorization": "Bearer " + localStorage.getItem('token')
                }
            })
                .then(resp => {
                    return resp.status === 200 ? resp.json() : resp.status
                })
                .then(resp => resp >= 401 && resp < 500 ? setLoginError(true) : setUserList(resp))

            fetch("http://localhost:8080/api/user/management/total", {
                headers: {
                    "authorization": "Bearer " + localStorage.getItem('token')
                }
            })
                .then(resp => {
                    return resp.status === 200 ? resp.text() : resp.status
                })
                .then(resp => resp >= 400 && resp < 500 ? setLoginError(true) : setTotal(resp))
        }, [table])

    React.useEffect( () => {

        fetch("http://localhost:8080/api/roles/all", {
            headers: {
                "authorization": "Bearer " + localStorage.getItem('token')
            }
        }).then(resp => resp.status === 200 ? resp.json() : resp.status )
            .then(resp => resp >= 400 && resp < 500 ? setLoginError(true) : setRoleList(resp))
    }, [])


    const inputParams = {input: [{
            type: "text",
            placeholder: "Username",
            value: userCreate.username,
            name: "username"
        },
            {
                type: "text",
                placeholder: "Email",
                value: userCreate.email,
                name: "email"
            },
            {
                type: "text",
                placeholder: "Password",
                value: userCreate.password,
                name: "password"
            }
        ],select: {
            active: true,
            label:"Roles",
            name:"selectRoles",
            value: userCreate.roles,
            multiple:true,
            options:roleList.map(item =>
            {return {value: item.name, name:setGroup(item.name)}})}}


    const userTableData = userList.map( user =>
        <tr key={user.id}>
            <td style={tableStyle}>{user.username}</td>
            <td style={tableStyle}>{user.email}</td>
            <td style={tableStyle}>{user.roles.map(role => `|${setGroup(role.name)}`)}</td>
        </tr>)



    function setGroup(role) {
        switch (role) {
            case "ROLE_ADMIN":
                return "Administrator"
            case "ROLE_STAFF":
                return "Staff Member"
            case "ROLE_USER":
                return "User"
            default:
                return role
        }
    }


    function pageChange(event){
        const direction = event.target.name
        setTable(prevState => {
            return {
                ...prevState,
                page: direction === "right" ? prevState.page + 1 : prevState.page - 1
            }
        })
    }

    return (
        <div>

                <div>
                    <h1>Users</h1>
                    <button name={AccessType.CREATE} onClick={sAccess}>Create</button>
                    <button name={AccessType.UPDATE} onClick={sAccess}>Update</button>
                    <button name={AccessType.DELETE} onClick={sAccess}>Delete</button>
                    {access !== AccessType.NO && <Crud subject={userCreate} api={"http://localhost:8080/api/user"} checkNull={checkNull()}
                                                       inputParams={inputParams} handleChange={(event) => handleChange(event)} />}

                    <table style={{borderStyle: "solid", borderColor: "black", width: "100%"}}>
                        <tbody>
                        <tr><th style={tableStyle}>Username</th><th style={tableStyle}>Email</th><th style={tableStyle}>Roles</th></tr>
                        {userTableData}
                        </tbody>
                    </table>
                    {table.page > 0 && <button name="left"  onClick={pageChange} > &#8592; </button>}
                    {table.page + 1 < (total / table.size) && <button name="right" onClick={pageChange} > &#8594; </button>}
                </div>


        </div>
    )




}