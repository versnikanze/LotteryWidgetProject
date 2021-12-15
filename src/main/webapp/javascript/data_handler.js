const serverRestUrl = "http://localhost:8080/api/rest/";
const postContestantPick = "registercontestant";
const getLatestWinners = "getlastfivewinners";
const updateLatestWinners = "updatewinners";

/**
 * Executes a function on window load
 */
window.onload = function () {
    getWinners(getLatestWinners);
}

/**
 * Sends the contestant data to the REST API.
 */
function sendContestantPick() {
    let inputName = document.getElementById("input_name").value;
    let inputPickedNumber = parseInt(document.getElementById("input_picked_number").value);
    let request = new XMLHttpRequest();
    request.open("POST", serverRestUrl + postContestantPick, true);
    request.setRequestHeader("Content-Type", "application/json");
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            console.log(request.responseText);
        }
    };
    let data = JSON.stringify({"contestantName": inputName, "pickedNumber": inputPickedNumber});
    request.send(data);
}

/**
 * Retrieves the current winners from the REST API. Implements AJAX long polling.
 *
 * @param  {String} restLink the REST API links to be accessed
 */
function getWinners(restLink) {
    let request = new XMLHttpRequest();
    request.overrideMimeType("application/json");
    request.open('GET', serverRestUrl + restLink, true);
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            let jsonResponse = JSON.parse(request.responseText);
            jsonToTable(jsonResponse);
            getWinners(updateLatestWinners);
        } else if (request.status === 502) {
            //Request timed out retry it
            getWinners(updateLatestWinners);
        }
    };
    request.send(null);
}

/**
 * Converts a JSONArray to an HTML table element. And updates
 * the content of the data_conainer on the webpage with it.
 *
 * @param  {JSON} jsonArray the array to be converted
 */
function jsonToTable(jsonArray) {
    let table = document.createElement("table");
    jsonArray.forEach(function (object) {
        let tr = table.insertRow(-1);
        Object.entries(object).forEach(entry => {
            let tabCell = tr.insertCell(-1);
            tabCell.innerHTML = entry[1];
        });
    })
    let divShowData = document.getElementById('data_container');
    divShowData.innerHTML = "";
    divShowData.appendChild(table);
}
