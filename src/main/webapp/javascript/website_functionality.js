const serverRestUrl = "http://localhost:8080/api/rest/";
const postContestantPick = "registercontestant";
const getLatestWinners = "getlastfivewinners";
const updateLatestWinners = "updatewinners";

/**
 * Executes a function on window load
 */
window.onload = function () {
    getWinners(getLatestWinners);
    let thirtySeconds = 30,
        display = document.querySelector('#time');
    startTimer(thirtySeconds, display);
}

/**
 * Adds an alert to the input field
 */
function addInputAlert(inputField) {
    inputField.classList.add("input-alert");
    inputField.placeholder = "Please enter value";
}

/**
 * Removes alerts from the input fields
 *
 * @param  {Element} inputName name input field
 * @param  {Element} inputPickedNumber number input field
 */
function inputValuesAccepted(inputName, inputPickedNumber) {
    inputName.classList.remove("input-alert");
    inputName.placeholder = "Contestant name";
    inputPickedNumber.classList.remove("input-alert");
    inputPickedNumber.placeholder = "Picked number";
}

/**
 * Checks if the number is between 0 and 30
 *
 * @param  {Element} inputPickedNumber number input field
 */
function checkNumberValue(inputPickedNumber) {
    let number = parseInt(inputPickedNumber.value);
    if (number >= 1 && number <= 30) {
        return false;
    } else {
        return true;
    }
}

/**
 * Checks if the values in the input fields are correct
 *
 * @param  {Element} inputName name input field
 * @param  {Element} inputPickedNumber number input field
 */
function checkInputValues(inputName, inputPickedNumber) {
    if (isNaN(inputPickedNumber.value)) {
        inputPickedNumber.classList.add("input-alert");
        inputPickedNumber.value = "";
        inputPickedNumber.placeholder = "Not a number";
        return true;
    }
    if (checkNumberValue(inputPickedNumber)) {
        inputPickedNumber.classList.add("input-alert");
        inputPickedNumber.value = "";
        inputPickedNumber.placeholder = "Not between 0 and 30";
        return true;
    }
    if (inputName.value === "" && inputPickedNumber.value === "") {
        addInputAlert(inputName);
        addInputAlert(inputPickedNumber);
        return true;
    }
    if (inputName.value === "") {
        addInputAlert(inputName);
        inputPickedNumber.classList.remove("input-alert");
        return true;
    }
    if (inputPickedNumber.value === "") {
        addInputAlert(inputPickedNumber);
        inputName.classList.remove("input-alert");
        return true;
    }
    inputValuesAccepted(inputName, inputPickedNumber);
    return false;
}


/**
 * Sends the contestant data to the REST API.
 */
function sendContestantPick() {
    let inputName = document.getElementById("input_name");
    let inputPickedNumber = document.getElementById("input_picked_number");
    if (checkInputValues(inputName, inputPickedNumber)) {
        return;
    }
    let contestantRegistered = document.getElementById("contestant-registered")
    let request = new XMLHttpRequest();
    request.open("POST", serverRestUrl + postContestantPick, true);
    request.setRequestHeader("Content-Type", "application/json");
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            contestantRegistered.innerHTML = request.responseText;
            contestantRegistered.classList.remove("no-display-message");
            contestantRegistered.classList.add("registered-message");
            contestantRegistered.classList.remove("failed-register-message");
        } else if (request.readyState === 4) {
            contestantRegistered.innerHTML = "Contestant failed to register";
            contestantRegistered.classList.remove("no-display-message");
            contestantRegistered.classList.remove("registered-message");
            contestantRegistered.classList.add("failed-register-message");
        }
    };
    let data = JSON.stringify({"contestantName": inputName.value, "pickedNumber": inputPickedNumber.value});
    request.send(data);
}

/**
 * Retrieves the current winners from the REST API. Implements AJAX long polling.
 *
 * @param  {String} restLink the REST API links to be accessed
 */
function getWinners(restLink) {
    let request = new XMLHttpRequest();
    let contestantRegistered = document.getElementById("contestant-registered")
    request.overrideMimeType("application/json");
    request.open('GET', serverRestUrl + restLink, true);
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            let jsonResponse = JSON.parse(request.responseText);
            contestantRegistered.classList.add("no-display-message");
            contestantRegistered.classList.remove("registered-message");
            contestantRegistered.classList.remove("failed-register-message");
            jsonToTable(jsonResponse);
            getWinners(updateLatestWinners);
            let thirtySeconds = 30,
                display = document.querySelector('#time');
            startTimer(thirtySeconds, display);
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
        tr.classList.add("row100");
        tr.classList.add("body");
        let tabCell = tr.insertCell(-1);
        tabCell.classList.add("cell100");
        tabCell.classList.add("column1");
        tabCell.innerHTML = object.winners;
        let tabCell2 = tr.insertCell(-1);
        tabCell2.classList.add("cell100");
        tabCell2.classList.add("column2");
        tabCell2.innerHTML = object.lottery_number;
    })
    let divShowData = document.getElementById('data_container');
    divShowData.innerHTML = "";
    divShowData.appendChild(table);
}


/**
 * Starts or restarts the timer of the clock
 *
 * @param  {number} duration duration of the countdown
 * @param  {Element} display element to display on
 */
function startTimer(duration, display) {
    let timer = duration, seconds;
    setInterval(function () {
        seconds = parseInt(timer % 60, 10);
        seconds = seconds < 10 ? "0" + seconds : seconds;

        display.textContent = seconds;

        if (--timer < 0) {
            timer = duration;
        }
    }, 1000);
}
