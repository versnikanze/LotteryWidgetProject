const server_rest_url = "http://localhost:8080/api/rest/";
const post_contestant_pick = "registercontestant";

function sendContestantPick() {
    let input_name = document.getElementById("input_name").value;
    let input_picked_number = parseInt(document.getElementById("input_picked_number").value);

    let xhr = new XMLHttpRequest();
    xhr.open("POST", server_rest_url + post_contestant_pick, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log(xhr.responseText);
        }
    };
    let data = JSON.stringify({"name": input_name, "picked_number": input_picked_number});
    xhr.send(data);
}

