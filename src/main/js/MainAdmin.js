import React from "react"
import {AccessType} from "./AccessType";
import Crud from "./dataModification/Crud"

export default function MainAdmin(){
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

    const [roleCreate, setRoleCreate] = React.useState({
        name: "",
        privileges:[""]
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

    function handleChangeRole(event){
        const {name, value, selectedOptions} = event.target
        setRoleCreate(prevState => {
            if(!name.startsWith("select")){
                return {
                    ...prevState,
                    [name]:  value
                }}else{
                return {...prevState,
                    privileges: extractOptions(selectedOptions)}
            }})
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
    function checkNullRole(){
        return roleCreate.name === ""
    }
    const [loginError, setLoginError] = React.useState(false)

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

    const inputParamsRole = {input: [{
            type: "text",
            placeholder: "Name",
            value: roleCreate.name,
            name: "name"
        }
        ],select: {
            active: true,
            label:"Privileges",
            name:"selectPrivileges",
            value: roleCreate.privileges,
            multiple:true,
            options: [{value:"READ_PRIVILEGE", name: "Read"}, {value:"WRITE_PRIVILEGE", name:"Write"}, {value:"DELETE_PRIVILEGE", name:"Delete"}]}}


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

    const roleTableData = roleList.map( role =>
        <tr key={role.id}>
            <td style={tableStyle}>{setGroup(role.name)}</td>
            <td style={tableStyle}>{role.privileges.some(item => item.name === "READ_PRIVILEGE") ? "Yes" : "No"}</td>
            <td style={tableStyle}>{role.privileges.some(item => item.name === "WRITE_PRIVILEGE") ? "Yes" : "No"}</td>
            <td style={tableStyle}>{role.privileges.some(item => item.name === "DELETE_PRIVILEGE") ? "Yes" : "No"}</td>
        </tr>)


    function pageChange(event){
        const direction = event.target.name
        setTable(prevState => {
            return {
                ...prevState,
                page: direction === "right" ? prevState.page + 1 : prevState.page - 1
            }
        })
    }
    if(loginError){
        window.location.href = "http://localhost:8080/login"
    }

    return (
        <div>

            {loginError ?
                <a href="http://localhost:8080/login" style={{color: "red"}}>Invalid Login</a>
                :
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
                    <h1>Roles</h1>
                    <Crud subject={roleCreate} api={"http://localhost:8080/api/roles/create"} checkNull={checkNullRole()}
                          inputParams={inputParamsRole} handleChange={(event) => handleChangeRole(event)} />
                    <table style={{borderStyle: "solid", borderColor: "black", width: "100%"}}>
                        <tbody>
                        <tr><th style={tableStyle}>Role</th><th style={tableStyle}>Read</th><th style={tableStyle}>Write</th><th style={tableStyle}>Delete</th></tr>
                        {roleTableData}
                        </tbody>
                    </table>
                    {table.page > 0 && <button name="left"  onClick={pageChange} > &#8592; </button>}
                    {table.page + 1 < (total / table.size) && <button name="right" onClick={pageChange} > &#8594; </button>}
                </div>
            }

        </div>
    )




}