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
            let zalupaCells = jData["fightField1"]["cells"];
            alert("zalupaCells " + JSON.stringify(zalupaCells[0][0]["cellState"]));

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