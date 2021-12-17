let stat = null;
let users = [];
let score = [];

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
             let responseData = JSON.parse(xhr.responseText);
             //statData = responseData.split(',');
             users = Object.keys(responseData);
             score = Object.values(responseData);
             // statData = JSON.parse(responseData);
             console.log("users " + users);
             console.log("score " + score);
             console.log("resp" + responseData);
             tableCreate();
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
    tbl.setAttribute('class', 'statTable');
    let tblBody = document.createElement("tbody");
    let tblHead = document.createElement("thead");
    let tblHeadRow = document.createElement("tr");

    // cells creation
    for (let tRow = 0; tRow < users.length; tRow++) {
        // table row creation
        let row = document.createElement("tr");

        if (tRow === 0) {
            for (let tHead = 0; tHead <= 2; tHead++) {
                let tblHeadCell = document.createElement("th");
                let cellHeadText = "";
                switch (tHead) {
                    case 0:
                        cellHeadText = "№";
                        break;
                    case 1:
                        cellHeadText = "Player";
                        break;
                    case 2:
                        cellHeadText = "Score";
                        break
                }
                let tblHeaderCellText = document.createTextNode(cellHeadText);
                console.log("cell " + tblHeaderCellText);
                console.log("text " + cellHeadText);
                tblHeadCell.appendChild(tblHeaderCellText);
                tblHeadRow.appendChild(tblHeadCell);
                tblHead.appendChild(tblHeadRow);
            }
            tblBody.appendChild(tblHeadRow);
        }


        for (let tData = 0; tData <= 2; tData++) {
            // create element <td> and text node
            //Make text node the contents of <td> element
            // put <td> at end of the table row
            let cell = document.createElement("td");
            let cellTableText = "";

            switch (tData) {
                case 0:
                    cellTableText = tRow+1;
                break;
                case 1:
                    cellTableText = users[tRow];
                break;
                case 2:
                    cellTableText = score[tRow];
                break;
            }
            //let cellText = document.createTextNode("cell is row " + tRow + ", column " + tData);
            let cellText = document.createTextNode(cellTableText);

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
    //tbl.setAttribute("border", "2");
}