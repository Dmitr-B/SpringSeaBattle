let activeSingleShip = null; /* add change active ship with cursor */
let activeDoubleShip = null; /* add change active ship with cursor */
let activeTripleShip = null; /* add change active ship with cursor */
let activeFourShip = null; /* add change active ship with cursor */
let allSingleDeckShips = document.querySelectorAll('.singleDeckShip');
let allDoubleDeckShips = document.querySelectorAll('.doubleDeckShip');
let allTripleDeckShips = document.querySelectorAll('.tripleDeckShip');
let allFourDeckShips = document.querySelectorAll('.fourDeckShip');
//let singleDeckShip = null;
let shipOnMove = null;
let startShipCell = null;
let currentDroppable = null;
let startX = null;
let startY = null;
let buttonX = null;
let buttonY = null;

let rotateShip = 0;


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
            setActiveShip("single", 0);
            break;
        case "singleDeckShip_2":
            setActiveShip("single", 1);
            break;
        case "singleDeckShip_3":
            setActiveShip("single", 2);
            break;
        case "singleDeckShip_4":
            setActiveShip("single", 3);
            break;
        case "doubleDeckShip_1":
            setActiveShip("double", 0);
            break;
        case "doubleDeckShip_2":
            setActiveShip("double", 1);
            break;
        case "doubleDeckShip_3":
            setActiveShip("double", 2);
            break;
        case "tripleDeckShip_1":
            setActiveShip("triple", 0);
            break;
        case "tripleDeckShip_2":
            setActiveShip("triple", 1);
            break;
        case "fourDeckShip_1":
            setActiveShip("four", 0);
            break;
    }

    if (activeSingleShip != null) {
        moveShip(allSingleDeckShips, activeSingleShip, shipOnMove);
    }

     if (activeDoubleShip != null) {
         moveShip(allDoubleDeckShips, activeDoubleShip, shipOnMove);
     }

    if (activeTripleShip != null) {
        moveShip(allTripleDeckShips, activeTripleShip, shipOnMove);
    }

    if (activeFourShip != null) {
        moveShip(allFourDeckShips, activeFourShip, shipOnMove);
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


            if (cell !== null) {
                startShipCell = cell;
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
        }
    }
    ship.ondragstart = function() {
        return false;
    }

    // if (activeSingleShip != null) {
    //     singleDeckShip = ship;
    // }
    //
    // if (activeDoubleShip != null) {
    //     doubleDeckShip = ship;
    // }
    shipOnMove = ship;

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
}

function showApplyButton() {
    const applyButton = document.getElementById("applyButton");
    const rotateButton = document.getElementById("rotateButton");

    applyButton.style.left = buttonX;
    applyButton.style.top = buttonY;

    rotateButton.style.left = buttonX;
    rotateButton.style.top = (Number.parseInt(buttonY) + 24) + "px";

    applyButton.hidden = false;

    if (document.getElementById(shipOnMove.id).className !== "singleDeckShip") {
        rotateButton.hidden = false;
    }
}

function hideApplyButton() {
    const applyButton = document.getElementById("applyButton");
    const rotateButton = document.getElementById("rotateButton");

    applyButton.hidden = true;
    rotateButton.hidden = true;
}

function setActiveShip(type, value) {
    switch (type) {
        case "single":
            activeSingleShip = value;
            activeDoubleShip = null;
            activeTripleShip = null;
            activeFourShip = null;
            break;
        case "double":
            activeSingleShip = null;
            activeDoubleShip = value;
            activeTripleShip = null;
            activeFourShip = null;
            break;
        case "triple":
            activeSingleShip = null;
            activeDoubleShip = null;
            activeTripleShip = value;
            activeFourShip = null;
            break;
        case "four":
            activeSingleShip = null;
            activeDoubleShip = null;
            activeTripleShip = null;
            activeFourShip = value;
            break;
    }
}

function addShipToField(cell, ship) {
    let shipJSON = null;
    //let xhr = new XMLHttpRequest();
    //let url = '/game/' + sessionStorage.getItem("gameId") + '/fight_field/' + sessionStorage.getItem("owner") + '/ship';
    switch (document.getElementById(ship.id).className) {
        case "singleDeckShip":
            shipJSON = {
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
        break;

        case "doubleDeckShip":
            if (rotateShip === 0) {
                shipJSON = {
                    type: "DOUBLE",
                    cells: [
                        {
                            cellState: "SHIP",
                            coordinates: {
                                x: cell.dataset.x,
                                y: cell.dataset.y
                            }
                        },
                        {
                            cellState: "SHIP",
                            coordinates: {
                                x: Number.parseInt(cell.dataset.x)+1,
                                y: cell.dataset.y
                            }
                        }
                    ]
                }
            } else {
                shipJSON = {
                    type: "DOUBLE",
                    cells: [
                        {
                            cellState: "SHIP",
                            coordinates: {
                                x: cell.dataset.x,
                                y: cell.dataset.y
                            }
                        },
                        {
                            cellState: "SHIP",
                            coordinates: {
                                x: cell.dataset.x,
                                y: Number.parseInt(cell.dataset.y)+1
                            }
                        }
                    ]
                }
            }
            break;

        case "tripleDeckShip":
            if (rotateShip === 0) {
                shipJSON = {
                    type: "THREE",
                    cells: [
                        {
                            cellState: "SHIP",
                            coordinates: {
                                x: cell.dataset.x,
                                y: cell.dataset.y
                            }
                        },
                        {
                            cellState: "SHIP",
                            coordinates: {
                                x: Number.parseInt(cell.dataset.x)+1,
                                y: cell.dataset.y
                            }
                        },
                        {
                            cellState: "SHIP",
                            coordinates: {
                                x: Number.parseInt(cell.dataset.x)+2,
                                y: cell.dataset.y
                            }
                        }
                    ]
                }
            } else {
                shipJSON = {
                    type: "THREE",
                    cells: [
                        {
                            cellState: "SHIP",
                            coordinates: {
                                x: cell.dataset.x,
                                y: cell.dataset.y
                            }
                        },
                        {
                            cellState: "SHIP",
                            coordinates: {
                                x: cell.dataset.x,
                                y: Number.parseInt(cell.dataset.y)+1
                            }
                        },
                        {
                            cellState: "SHIP",
                            coordinates: {
                                x: cell.dataset.x,
                                y: Number.parseInt(cell.dataset.y)+2
                            }
                        }
                    ]
                }
            }
            break;

        case "fourDeckShip":
            if (rotateShip === 0) {
                shipJSON = {
                    type: "FOUR",
                    cells: [
                        {
                            cellState: "SHIP",
                            coordinates: {
                                x: cell.dataset.x,
                                y: cell.dataset.y
                            }
                        },
                        {
                            cellState: "SHIP",
                            coordinates: {
                                x: Number.parseInt(cell.dataset.x)+1,
                                y: cell.dataset.y
                            }
                        },
                        {
                            cellState: "SHIP",
                            coordinates: {
                                x: Number.parseInt(cell.dataset.x)+2,
                                y: cell.dataset.y
                            }
                        },
                        {
                            cellState: "SHIP",
                            coordinates: {
                                x: Number.parseInt(cell.dataset.x)+3,
                                y: cell.dataset.y
                            }
                        }
                    ]
                }
            } else {
                shipJSON = {
                    type: "FOUR",
                    cells: [
                        {
                            cellState: "SHIP",
                            coordinates: {
                                x: cell.dataset.x,
                                y: cell.dataset.y
                            }
                        },
                        {
                            cellState: "SHIP",
                            coordinates: {
                                x: cell.dataset.x,
                                y: Number.parseInt(cell.dataset.y)+1
                            }
                        },
                        {
                            cellState: "SHIP",
                            coordinates: {
                                x: cell.dataset.x,
                                y: Number.parseInt(cell.dataset.y)+2
                            }
                        },
                        {
                            cellState: "SHIP",
                            coordinates: {
                                x: cell.dataset.x,
                                y: Number.parseInt(cell.dataset.y)+3
                            }
                        }
                    ]
                }
            }
            break;
    }
    // let singleDeckShipJSON = {
    //     type: "SINGLE",
    //     cells: [
    //         {
    //             cellState: "SHIP",
    //             coordinates: {
    //                 x: cell.dataset.x,
    //                 y: cell.dataset.y
    //             }
    //         }
    //     ]
    // }

    let data = JSON.stringify(shipJSON);

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