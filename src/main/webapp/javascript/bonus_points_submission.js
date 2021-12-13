let rectangleSpeed = 3;

document.addEventListener("DOMContentLoaded", function () {
    let canvas = document.getElementById('canvas');
    let context = canvas.getContext('2d');

    let startingPosition = { x: 25, y: 275 };
    let rectangleSize = 50;
    let rectangle = { x: startingPosition.x, y: startingPosition.y };

    function drawNextPosition() {
        context.clearRect(0, 0, canvas.width, canvas.height);

        if (rectangle.x > canvas.width - rectangleSize || rectangle.x < 0) rectangleSpeed = -rectangleSpeed;

        rectangle.x += rectangleSpeed;

        context.beginPath();
        context.fillStyle = 'red';
        context.rect(rectangle.x, rectangle.y, rectangleSize, rectangleSize);
        context.fill();
        context.closePath();
    }

    setInterval(drawNextPosition, 10);
});

window.onload = function () {
    document.getElementById("slow-down-button").addEventListener("mousedown", function () {
        rectangleSpeed /= 3;
    });
    document.getElementById("slow-down-button").addEventListener("mouseup", function () {
        rectangleSpeed *= 3;
    });
};

