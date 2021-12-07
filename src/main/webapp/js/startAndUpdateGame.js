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
            alert("gameeee " + respText);
            sessionStorage.setItem("game", respText);
            alert("gameInSessionStorage " + sessionStorage.getItem("game"));
            let jData = JSON.parse(respText);

            //alert("field1 " + JSON.stringify(fightField1));
            // user1Cells = JSON.stringify(jData["fightField1"]["cells"]);
            // alert("celllllls " + user1Cells);
             user1Cells = jData["fightField1"]["cells"];
             changeCellStyle();
            // alert("user1Cells " + user1Cells[0][0]["cellState"]);

            // for (let i = 0; i < user1Cells.length; i++) {
            //     alert(user1Cells[i]);
            // }

            // let id = jData["id"];
            // sessionStorage.setItem("token", token);
            // //sessionStorage.setItem("id", id);
            //
            // alert("tokenInSession " + sessionStorage.getItem("token"));
            // alert("idInSession " + sessionStorage.getItem("id"));
            // window.location.replace("/auth/success");
        }
    }
}

function changeCellStyle() {
    let cell;

    for (let x = 0; x <= 9; x++) {
        for (let y = 0; y <= 9; y++) {
            switch (user1Cells[i][j]["cellState"]) {
                case "EMPTY":
                    cell = document.getElementById("cell_" + i +"_" + j);
                    cell.style.backgroundColor = "#fce38a";
                break;
            }
        }
    }

}