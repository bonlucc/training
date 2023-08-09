import React from "react"
import {AccessType} from "./AccessType";
import Crud from "./dataModification/Crud"
import Table from "./Table";

export default function Role({setLoginError}){

    const [roleCreate, setRoleCreate] = React.useState({
        name: "",
        privileges:[""]
    })


    const [role, setRole]= React.useState({
        name: "",
        id:0,
        privileges:[
            {
                name:""
            }
        ]
    })

    const [privilegeList, setPrivilegeList] = React.useState([{
        name:""
    }])

    const [roleId, setRoleId] = React.useState(0)


    const tableStyle = {borderStyle: "solid", borderColor: "black"}

    function handleChange(event){
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

    function fetchPrivileges(){
        fetch("http://localhost:8080/api/privileges/read/all", {
            headers: {
                "authorization": "Bearer " + localStorage.getItem('token')
            }
        }).then(resp => resp.status === 200 ? resp.json() : resp.status)
            .then(resp => resp >= 400 && resp < 500 ? setLoginError(true) : setPrivilegeList(resp))

    }
    React.useEffect( () => fetchPrivileges(), [])
    function handleChangeId(event){
        const {value} = event.target
        setRoleId(value)
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

    function checkboxInput(checked, name){
        return <input
          type="checkbox"
          defaultChecked={checked}
          name={name}
        />
    }


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
            multiple: true,
            options: [{value:"appProd_READ", name: "Read"}, {value:"appProd_WRITE", name:"Write"}, {value:"appProd_DELETE", name:"Delete"}]}}


    function setAuth(auth) {
        return "ROLE_" + auth.toUpperCase()
    }

    function getAppNames(privilegeList){
        let appNames = []
        let privileges = privilegeList.map(privilege => privilege.name)
        let appName, privilege
        for(let i = 0; i < privileges.length; i++){
            privilege = privileges[i]
            appName = privilege.split("_")[0]
            if(!appNames.includes(appName)) appNames.push(appName)
        }
        return appNames
    }

    const appNames = getAppNames(privilegeList)

    const roleTableData = appNames.map(appName =>
            <tr key={appName}>
                <td style={tableStyle}>{appName}</td>
                <td style={tableStyle}>
                    {checkboxInput(role.privileges.some(item => {
                        //console.log(item.name)
                        //if(item.name === `${appName}_READ`) console.log(`${appName}_READ`)
                        return item.name === `${appName}_READ`
                    }), `${appName}_READ`)}
                </td>
                <td style={tableStyle}>
                    {checkboxInput(role.privileges.some(item => item.name === `${appName}_WRITE`), `${appName}_WRITE`)}
                </td>
                <td style={tableStyle}>
                    {checkboxInput(role.privileges.some(item => item.name === `${appName}_DELETE`), `${appName}_DELETE`)}
                </td>
            </tr>
        )



    function handleTableSubmit(event) {
        event.preventDefault()
        const form = event.target
        const formData = new FormData(form)
        const formJson = Object.fromEntries(formData.entries());
        const roleInfo = {
            name: role.name,
            privileges: getPrivileges(formJson)
        }

        fetch("http://localhost:8080/api/roles/update/" + role.id, {
            method:"PUT",
            body:JSON.stringify(roleInfo),
            headers:{
            "Content-type": "application/json; charset=UTF-8",
                "authorization" : "Bearer " + localStorage.getItem("token")
            }
        }).then(resp => {
            return resp.status
        })
            .then(resp => resp >= 400 && resp < 500 ? setLoginError(true) : window.alert("Successful"))

    }

    function getPrivileges(formJson){
        console.log(JSON.stringify(formJson))
        let arr = JSON.stringify(formJson).replace(/[{}]/, "").split(",")
        console.log(JSON.stringify(arr))
        const newArr = arr.map(privilege => {
                let sepPrivilege = privilege.split(":")
                return sepPrivilege[0].replace(`"`, "")
            }
        )
        console.log(JSON.stringify(newArr))
        return newArr.map(item => item.replace(`"`, ""))


    }

    function handleSubmit(event){
        event.preventDefault()
        fetchPrivileges()
        fetch("http://localhost:8080/api/roles/read/" + roleId, {
            headers: {
                "authorization": "Bearer " + localStorage.getItem('token')
            }
        }).then(resp => resp.status === 200 ? resp.json() : resp.status)
            .then(resp => resp >= 400 && resp < 500 ? setLoginError(true) : setRole(resp))

    }
    return (
        <div>
            <h1>Roles</h1>
            <h2>Role Creation</h2>
            <Crud subject={roleCreate} api={"http://localhost:8080/api/roles/create"} apiComplement={""} checkNull={checkNullRole}
                                               inputParams={inputParamsRole} handleChange={(event) => handleChange(event)}
                                               access={AccessType.CREATE} />
            <h2>Role Management</h2>
            {role.name === "" && <form onSubmit={handleSubmit}>
                <input
                    type={"number"}
                    placeholder={"roleId"}
                    value= {roleId}
                    onChange={(event) => handleChangeId(event)}
                    name={"roleId"}
                />
                <button>Get privileges</button>
            </form>}
            <form onSubmit={handleTableSubmit} method="post">
            {role.name !== "" && <div>
                <Table tableHeader={"App_Read_Write_Delete"} tableData={roleTableData} tableStyle={tableStyle} />
                <button>Update</button>
            </div>}
            </form>
        </div>
    )




}