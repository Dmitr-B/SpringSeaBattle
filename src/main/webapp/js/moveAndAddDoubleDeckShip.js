// let activeDoubleShip = null; /* add change active ship with cursor */
// let allDoubleDeckShips = document.querySelectorAll('.doubleDeckShip');
// let doubleDeckShip = null;
// let startDoubleDeckCell = null;
// let currentDoubleDroppable = null;
// let startDoubleX = null;
// let startDoubleY = null;
// let buttonDoubleX = null;
// let buttonDoubleY = null;
//
// window.onmousemove = function (e) {
//
//     switch (document.elementFromPoint(e.clientX, e.clientY).id) {
//         case "doubleDeckShip_1":
//             activeDoubleShip = 0;
//             break;
//         case "doubleDeckShip_2":
//             activeDoubleShip = 1;
//             break;
//         case "doubleDeckShip_3":
//             activeDoubleShip = 2;
//             break;
//     }
//
//     if (activeDoubleShip != null) {
//         moveDoubleShip(allDoubleDeckShips, activeDoubleShip, doubleDeckShip);
//     }
// }
//
// function moveDoubleShip(allShips, activeShip, ship) {
//
//     ship = document.getElementById(allShips[activeShip].id)
//     ship.onmousedown = function (e) {
//
//         let coords = getCoords(ship);
//         let shiftX = e.pageX - coords.left;
//         let shiftY = e.pageY - coords.top;
//
//         startDoubleX = coords.left;
//         startDoubleY = coords.top;
//         ship.style.position = "absolute";
//         hideApplyButton();
//         moveAt(e)
//         document.body.appendChild(ship);
//         ship.style.zIndex = "1000";
//
//         function moveAt(e) {
//             ship.style.left = e.pageX - shiftX + "px";
//             ship.style.top = e.pageY - shiftY + "px";
//         }
//
//         document.onmousemove = function (e) {
//
//             moveAt(e);
//             ship.hidden = true;
//             const elemBelow = document.elementFromPoint(e.clientX, e.clientY);
//             ship.hidden = false;
//             let cell = document.getElementById(elemBelow.id);
//
//             if (cell !== null) {
//                 startDoubleDeckCell = cell;
//                 moveToCellAndSetButtonCoordinates(cell, ship);
//             }
//
//             let droppableBelow = elemBelow.closest(".test_table");
//
//             if (currentDoubleDroppable !== droppableBelow) {
//                 if (currentDoubleDroppable) {
//                     leaveDroppable(ship);
//                 }
//
//                 currentDoubleDroppable = droppableBelow;
//
//                 if (currentDoubleDroppable) {
//                     enterDroppable(ship);
//                 }
//             }
//         }
//
//         ship.onmouseup = function () {
//             document.onmousemove = null;
//             ship.onmouseup = null;
//
//             if (currentDoubleDroppable != null) {
//                 if (document.getElementById(currentDoubleDroppable.id).className === "test_table") {
//                     showApplyButton();
//                 }
//             } else {
//                 moveToStart(ship);
//             }
//         }
//     }
//     ship.ondragstart = function() {
//         return false;
//     }
//     doubleDeckShip = ship;
// }
//
// function getCoords(elem) {
//     let box = elem.getBoundingClientRect();
//     return {
//         top: box.top + pageYOffset,
//         left: box.left + pageXOffset,
//         bottom: box.bottom,
//         right: box.right
//     }
// }
//
// function getCellCoords(elem) {
//     let box = elem.getBoundingClientRect();
//     return {
//         top: box.top,
//         left: box.left
//     }
// }
//
// function enterDroppable(elem) {
//     elem.style.backgroundColor = 'rgba(144,238,144,0.3)';
//     elem.style.borderColor = 'green';
// }
//
// function leaveDroppable(elem) {
//     elem.style.background = '';
//     elem.style.opacity = '1';
//     elem.style.borderColor = 'mediumblue';
//     currentDoubleDroppable = null;
// }
//
// function moveToStart(ship) {
//     ship.style.left = startDoubleX + "px";
//     ship.style.top = startDoubleY + "px";
//     leaveDroppable(ship);
//     alert("1 " + ship.style.left + " " + startDoubleX);
//     alert("2 " + ship.style.top + " " + startDoubleY);
// }
//
// function moveToCellAndSetButtonCoordinates(element, ship) {
//     ship.style.left = getCellCoords(element).left.valueOf()-1 + "px";
//     ship.style.top = getCellCoords(element).top.valueOf() + "px";
//     buttonDoubleX = (Number.parseInt(ship.style.left) - 55).toString() + "px";
//     console.log("buttonX " + startDoubleX);
//     buttonDoubleY = ship.style.top;
//     console.log("buttonY " + startDoubleY);
// }
//
// function showApplyButton() {
//     const button = document.getElementById("applySingleDeckButton");
//     button.style.left = startDoubleX;
//     button.style.top = startDoubleY;
//     button.hidden = false;
// }
//
// function hideApplyButton() {
//     const button = document.getElementById("applySingleDeckButton");
//     button.hidden = true;
// }
//
// function addSingleDeckShip(cell, ship) {
//     //let xhr = new XMLHttpRequest();
//     //let url = '/game/' + sessionStorage.getItem("gameId") + '/fight_field/' + sessionStorage.getItem("owner") + '/ship';
//     let singleDeckShipJSON = {
//         type: "SINGLE",
//         cells: [
//             {
//                 cellState: "SHIP",
//                 coordinates: {
//                     x: cell.dataset.x,
//                     y: cell.dataset.y
//                 }
//             }
//         ]
//     }
//
//     let data = JSON.stringify(singleDeckShipJSON);
//
//     //xhr.open('PATCH', url, true);
//     //xhr.setRequestHeader("Content-Type", "application/json");
//     //xhr.setRequestHeader("Authorization", sessionStorage.getItem("token"));
//
//     //alert("x " + singleDeckShip.cells.coordinates.x);
//     //alert("y " + singleDeckShip.cells.coordinates.y);
//     alert("ship " + data);
//
//     //xhr.send(data);
//
//     //xhr.onreadystatechange = function () {
//     leaveDroppable(ship);
//     hideApplyButton();
//     //}
// }