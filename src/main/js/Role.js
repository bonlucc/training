import React from "react"
import {AccessType} from "./AccessType";
import Crud from "./dataModification/Crud"

export default function Role({setLoginError}){

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
    function extractOptions(arr){
        let roles = []
        for (let i = 0; i < arr.length; i++){
            roles.push(arr[i].value)
        }
        return roles
    }

    function checkNullRole(){
        return roleCreate.name === ""
    }

    const tableStyle = {borderStyle: "solid", borderColor: "black"}

    React.useEffect( () => {

        fetch("http://localhost:8080/api/roles/all", {
            headers: {
                "authorization": "Bearer " + localStorage.getItem('token')
            }
        }).then(resp => resp.status === 200 ? resp.json() : resp.status )
            .then(resp => resp >= 400 && resp < 500 ? setLoginError(true) : setRoleList(resp))
    }, [])

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


    return (
        <div>
            <h1>Roles</h1>
            <button name={AccessType.CREATE} onClick={sAccess}>Create</button>
            <button name={AccessType.UPDATE} onClick={sAccess}>Update</button>
            <button name={AccessType.DELETE} onClick={sAccess}>Delete</button>
            {access !== AccessType.NO && <Crud subject={roleCreate} api={"http://localhost:8080/api/roles/create"} checkNull={checkNullRole()}
                                               inputParams={inputParamsRole} handleChange={(event) => handleChangeRole(event)} />}
            <table style={{borderStyle: "solid", borderColor: "black", width: "100%"}}>
                <tbody><tr><th style={tableStyle}>App</th><th style={tableStyle}>Read</th><th style={tableStyle}>Write</th><th style={tableStyle}>Delete</th></tr>
                        {roleTableData}
                </tbody>
            </table>

        </div>
    )




}