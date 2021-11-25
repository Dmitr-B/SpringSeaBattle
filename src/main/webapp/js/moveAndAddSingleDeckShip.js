let activeShipId = null; /* add change active ship with cursor */
let ship1 = document.querySelectorAll('.test');
let ship = document.getElementById(ship1[activeShipId].id);
// window.onmousemove = function (e) {
//     if (document.elementFromPoint(e.clientX, e.clientY).className === "test") {
//         let id = document.elementFromPoint(e.clientX, e.clientY);
//         ship = document.querySelector('#'+id.id);
//         ship = document.getElementById(id.id);
//         console.log("dasddfcds " + ship.id + " " + id.id);
//     }

//}
// switch (activeShipId) {
//     case 0:
//         ship = document.querySelector("#test_1");
//         console.log("id1 " + ship.id);
//         break;
//     case 1:
//         ship = document.querySelector("#test_2");
//         console.log("id2 " + ship.id);
//         break;
// }

let startShipCell = null;
let currentDroppable = null;
let startX = null;
let startY = null;
let buttonX = null;
let buttonY = null;

//console.log("sdasfds " + ship[0].id);

ship.onmousedown = function (e) {

    let coords = getCoords(ship);
    let shiftX = e.pageX - coords.left;
    let shiftY = e.pageY - coords.top;
    console.log("suka " + activeShipId);

    startX = coords.left;
    startY = coords.top;
    ship.style.position = "absolute";
    moveAt(e)
    document.body.appendChild(ship);
    ship.style.zIndex = "1000";

    function moveAt(e) {
        ship.style.left = e.pageX - shiftX + "px";
        ship.style.top = e.pageY - shiftY + "px";
    }

    document.onmousemove = function (e) {
        console.log("sadsfds " + activeShipId);

        moveAt(e);
        ship.hidden = true;
        const elemBelow = document.elementFromPoint(e.clientX, e.clientY);
        let cell = null;
        ship.hidden = false;
        console.log("elBelow " + elemBelow.clientLeft);
        cell = document.getElementById(elemBelow.id);

        if (cell !== null) {
            startShipCell = cell;
            console.log("cellX " + f(cell).top.valueOf());
            console.log("cellY " + f(cell).left.valueOf());
            moveToCellAndSetButtonCoordinates(cell);
        }
        console.log("mouseX " + e.clientX);
        console.log("mouseY " + e.clientY);

        let droppableBelow = elemBelow.closest(".test_table");

        console.log("dBelow " + droppableBelow);

        if (currentDroppable !== droppableBelow) {
            if (currentDroppable) {
                leaveDroppable(ship);
            }

            currentDroppable = droppableBelow;

            if (currentDroppable) {
                enterDroppable(ship);
            }
        }
    }

    ship.onmouseup = function () {
        document.onmousemove = null;
        ship.onmouseup = null;

        showApplyButton();
        alert(currentDroppable + "cDroppable ");
        //if (currentDroppable === droppableBelow) {
        //moveToStart();
        //}
    }
}
ship.ondragstart = function() {
    return false;
}

function getCoords(elem) {
    let box = elem.getBoundingClientRect();
    return {
        top: box.top + pageYOffset,
        left: box.left + pageXOffset,
        bottom: box.bottom,
        right: box.right
    }
}

function f(elem) {
    let box = elem.getBoundingClientRect();
    return {
        top: box.top,
        left: box.left
    }
}

function enterDroppable(elem) {
    elem.style.backgroundColor = 'rgba(144,238,144,0.3)';
    elem.style.borderColor = 'green';
}

function leaveDroppable(elem) {
    elem.style.background = '';
    elem.style.opacity = '1';
    elem.style.borderColor = 'mediumblue';
}

function moveToStart() {
    ship.style.left = startX + "px";
    ship.style.top = startY + "px";
    leaveDroppable(ship);
    alert("1 " + ship.style.left + " " + startX);
    alert("2 " + ship.style.top + " " + startY);
}

function moveToCellAndSetButtonCoordinates(element) {
    ship.style.left = f(element).left.valueOf() + "px";
    ship.style.top = f(element).top.valueOf() + "px";
    buttonX = (Number.parseInt(ship.style.left) - 55).toString() + "px";
    console.log("buttonX " + buttonX);
    buttonY = ship.style.top;
    console.log("buttonY " + buttonY);
}

function showApplyButton() {
    const button = document.getElementById("applySingleDeckButton");
    button.style.left = buttonX;
    button.style.top = buttonY;
    button.hidden = false;
}

function hideApplyButton() {
    const button = document.getElementById("applySingleDeckButton");
    button.hidden = true;
}

function addSingleDeckShip(cell) {
    //let xhr = new XMLHttpRequest();
    //let url = '/game/' + sessionStorage.getItem("gameId") + '/fight_field/' + sessionStorage.getItem("owner") + '/ship';
    let singleDeckShip = {
        type: "SINGLE",
        cells: [
            {
                cellState: "SHIP",
                coordinates: {
                    x: cell.dataset.x,
                    y: cell.dataset.y
                }
            }
        ]
    }

    let data = JSON.stringify(singleDeckShip);

    //xhr.open('PATCH', url, true);
    //xhr.setRequestHeader("Content-Type", "application/json");
    //xhr.setRequestHeader("Authorization", sessionStorage.getItem("token"));

    //alert("x " + singleDeckShip.cells.coordinates.x);
    //alert("y " + singleDeckShip.cells.coordinates.y);
    alert("ship " + data);

    //xhr.send(data);

    //xhr.onreadystatechange = function () {
    leaveDroppable(ship);
    hideApplyButton();
    //}
}

function changeActiveShip(value) {
    activeShipId = value;
    // switch (activeShipId) {
    //     case 0:
    //         ship = document.querySelector("#test_1");
    //         console.log("id1 " + ship.id);
    //         break;
    //     case 1:
    //         ship = document.querySelector("#test_2");
    //         console.log("id2 " + ship.id);
    //         break;
    // }
}