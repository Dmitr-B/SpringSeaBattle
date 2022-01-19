let user1Cells = null;

function onStartGame() {
    let xhr = new XMLHttpRequest();
    let url = '/game/' + sessionStorage.getItem("gameId");

    xhr.open('GET', url, true);
    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.setRequestHeader("Authorization", sessionStorage.getItem("token"));


    //alert("json " + data);
    xhr.send();


    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            const respText = xhr.responseText;

            sessionStorage.setItem("game", respText);

            let jData = JSON.parse(respText);


             user1Cells = jData["fightField1"]["cells"];
             changeCellStyle();
        }
    }
}

function changeCellStyle() {
    let cell;

    for (let x = 0; x <= 9; x++) {
        for (let y = 0; y <= 9; y++) {
            switch (user1Cells[x][y]["cellState"]) {
                case "PAST":
                    cell = document.getElementById("cell_" + x +"_" + y);
                    cell.className = "battlefield_cell_past";
                break;
                case "KNOCKED":
                    cell = document.getElementById("cell_" + x +"_" + y);
                    cell.className = "battlefield_cell_knocked";
                break;
                case "SUNK":
                    cell = document.getElementById("cell_" + x +"_" + y);
                    cell.className = "battlefield_cell_sunk";
                break;


            }
        }
    }
}
