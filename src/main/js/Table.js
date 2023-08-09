import React from "react"

export default function Table({tableHeader, tableData, tableStyle}){
    const tableHeaderElements = tableHeader.split("_").map(header =>
    <th key={header}>{header}</th>
    )

    return(
        <table style={{borderStyle: "solid", borderColor: "black", width: "100%"}}>
            <tbody><tr style={tableStyle}>{tableHeaderElements}</tr>
            {tableData}
            </tbody>
        </table>
    )
}