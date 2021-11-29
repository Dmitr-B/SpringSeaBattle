let activeSingleShip = null; /* add change active ship with cursor */
let allSingleDeckShips = document.querySelectorAll('.singleDeckShip');
let singleDeckShip = null;
let startSingleDeckCell = null;
let currentDroppable = null;
let startX = null;
let startY = null;
let buttonX = null;
let buttonY = null;

let rotateShip = 0;
let activeDoubleShip = null; /* add change active ship with cursor */
let allDoubleDeckShips = document.querySelectorAll('.doubleDeckShip');
let doubleDeckShip = null;
let startDoubleDeckCell = null;
let currentDoubleDroppable = null;
let startDoubleX = null;
let startDoubleY = null;
let buttonDoubleX = null;
let buttonDoubleY = null;

window.onmousemove = function (e) {
    console.log("X " + e.clientX + " " + "Y " +  e.clientY);

    switch (document.elementFromPoint(e.clientX, e.clientY).id) {
        case "singleDeckShip_1":
            activeSingleShip = 0;
            activeDoubleShip = null;
            break;
        case "singleDeckShip_2":
            activeSingleShip = 1;
            activeDoubleShip = null;
            break;
        case "singleDeckShip_3":
            activeSingleShip = 2;
            activeDoubleShip = null;
            break;
        case "singleDeckShip_4":
            activeSingleShip = 3;
            activeDoubleShip = null;
            break;
        case "doubleDeckShip_1":
            activeDoubleShip = 0;
            activeSingleShip = null;
            break;
        case "doubleDeckShip_2":
            activeDoubleShip = 1;
            activeSingleShip = null;
            break;
        case "doubleDeckShip_3":
            activeDoubleShip = 2;
            activeSingleShip = null;
            break;
    }

    if (activeSingleShip != null) {
        console.log("loshok " + activeSingleShip);
        console.log("ebanui penis1 " + activeDoubleShip);
        moveShip(allSingleDeckShips, activeSingleShip, singleDeckShip);
    }

     if (activeDoubleShip != null) {
         console.log("ebanui penis " + activeDoubleShip);
         console.log("loshok1 " + activeSingleShip);
         moveShip(allDoubleDeckShips, activeDoubleShip, doubleDeckShip);
         //moveDoubleShip(allDoubleDeckShips, activeDoubleShip, doubleDeckShip);
     }
}

function moveShip(allShips, activeShip, ship) {

    ship = document.getElementById(allShips[activeShip].id)
    ship.onmousedown = function (e) {

        let coords = getCoords(ship);
        let shiftX = e.pageX - coords.left;
        let shiftY = e.pageY - coords.top;

        startX = coords.left;
        startY = coords.top;
        ship.style.position = "absolute";
        hideApplyButton();
        moveAt(e)
        document.body.appendChild(ship);
        ship.style.zIndex = "1000";

        function moveAt(e) {
            ship.style.left = e.pageX - shiftX + "px";
            ship.style.top = e.pageY - shiftY + "px";
        }

        document.onmousemove = function (e) {

            moveAt(e);
            ship.hidden = true;
            const elemBelow = document.elementFromPoint(e.clientX, e.clientY);
            ship.hidden = false;
            let cell = document.getElementById(elemBelow.id);
            console.log("top " + coords.top);
            console.log("left " + coords.left);
            console.log("right " + coords.right);
            console.log("bottom " + coords.bottom);


            if (cell !== null) {
                startSingleDeckCell = cell;
                moveToCellAndSetButtonCoordinates(cell, ship);
            }

            let droppableBelow = elemBelow.closest(".test_table");

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

            if (currentDroppable != null) {
                if (document.getElementById(currentDroppable.id).className === "test_table") {
                    showApplyButton();
                }
            } else {
                moveToStart(ship);
            }
            rotate(ship);
        }
    }
    ship.ondragstart = function() {
        return false;
    }

    if (activeSingleShip != null) {
        singleDeckShip = ship;
    }

    if (activeDoubleShip != null) {
        doubleDeckShip = ship;
    }

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

function getCellCoords(elem) {
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
    currentDroppable = null;
}

function moveToStart(ship) {
    ship.style.left = startX + "px";
    ship.style.top = startY + "px";
    leaveDroppable(ship);
    alert("1 " + ship.style.left + " " + startX);
    alert("2 " + ship.style.top + " " + startY);
}

function moveToCellAndSetButtonCoordinates(element, ship) {
    ship.style.left = getCellCoords(element).left.valueOf()-1 + "px";
    ship.style.top = getCellCoords(element).top.valueOf() + "px";
    buttonX = (Number.parseInt(ship.style.left) - 55).toString() + "px";
    console.log("buttonX " + buttonX);
    buttonY = ship.style.top;
    console.log("buttonY " + buttonY);
}

function rotate(ship) {
    let tempWidth = ship.offsetWidth-4;
    let tempHeight = ship.offsetHeight-4;

    ship.style.height = tempWidth.toString() + "px";
    ship.style.width = tempHeight.toString() + "px";

    if (rotateShip === 0) {
        rotateShip = 1;
    } else {
        rotateShip = 0;
    }
    alert("zalupa " + rotateShip);
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

function addSingleDeckShip(cell, ship) {
    //let xhr = new XMLHttpRequest();
    //let url = '/game/' + sessionStorage.getItem("gameId") + '/fight_field/' + sessionStorage.getItem("owner") + '/ship';
    let singleDeckShipJSON = {
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

    let data = JSON.stringify(singleDeckShipJSON);

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

