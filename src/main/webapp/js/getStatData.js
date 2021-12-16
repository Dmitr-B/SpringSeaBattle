let stat = null;
let statData = [];

function statWon() {
    stat = "win";
}

function statLose() {
    stat = "lose";
}

function statGame() {
    stat = "game";
}

function redirectToStatPage() {
    window.location.replace("");
}


window.onload = function () {
    let xhr = new XMLHttpRequest();
    let url = '/game/stat/won';
    //let userId = sessionStorage.getItem("id");

    xhr.open('GET', url, true);
    //xhr.setRequestHeader("Authorization", sessionStorage.getItem("token"));
    // xhr.setRequestHeader("Content-Type", "application/json");

    //alert("token " + sessionStorage.getItem("token"));


    //alert("userId " + userId);

    //xhr.setRequestHeader("Authorization", sessionStorage.getItem("token"));



    xhr.send();
    //alert("stat " + stat);

     xhr.onreadystatechange = function () {
         if (xhr.readyState === XMLHttpRequest.DONE) {
             let responseData = xhr.responseText;
             statData = responseData.split(',');
             // statData = JSON.parse(responseData);
              console.log("text " + statData);
             console.log("resp" + responseData);
             //sessionStorage.setItem("gameId", xhr.responseText);
             //sessionStorage.setItem("owner", "PLAYER1");
             //sessionStorage.setItem("id", id);

             //alert("gameId " + sessionStorage.getItem("gameId"));
             //window.location.replace("/game");
         }
     }
};

function tableCreate() {
    //body reference
    let body = document.getElementsByTagName("body")[0];

    // create elements <table> and a <tbody>
    let tbl = document.createElement("table");
    let tblBody = document.createElement("tbody");

    // cells creation
    for (let j = 0; j <= 9; j++) {
        // table row creation
        let row = document.createElement("tr");

        for (let i = 0; i < 9; i++) {
            // create element <td> and text node
            //Make text node the contents of <td> element
            // put <td> at end of the table row
            let cell = document.createElement("td");
            let cellText = document.createTextNode("cell is row " + j + ", column " + i);

            cell.appendChild(cellText);
            row.appendChild(cell);
        }

        //row added to end of table body
        tblBody.appendChild(row);
    }

    // append the <tbody> inside the <table>
    tbl.appendChild(tblBody);
    // put <table> in the <body>
    body.appendChild(tbl);
    // tbl border attribute to
    tbl.setAttribute("border", "2");
}