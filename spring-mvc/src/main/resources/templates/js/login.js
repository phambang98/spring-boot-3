const inputs = document.querySelectorAll("input");
inputs.forEach(function (input) {
    input.addEventListener("focus", function () {
        const parentElement = input.parentElement.parentElement;
        parentElement.classList.add("box-animation");
    });
    input.addEventListener("blur", function () {
        const parentElement = input.parentElement.parentElement;
        parentElement.classList.remove("box-animation");
    });
});

const buttons = document.querySelectorAll("#multiple-btn button");
const form_container = document.getElementById('form_section')
buttons.forEach((button) => {
    button.addEventListener("click", () => {
        form_container.classList.toggle("left-right");

    });
});

$(document).ready(function () {
    var makeItRain = function () {
        $('.rain').empty();

        var increment = 0;
        var drops = "";
        var backDrops = "";
        while (true) {
            var randoHundo = (Math.floor(Math.random() * (98 - 1 + 1) + 1));
            var randoFiver = (Math.floor(Math.random() * (5 - 2 + 1) + 2));
            increment += randoFiver;
            if (increment > 100) {
                break;
            }
            drops += '<div class="drop" style="left: ' + increment + '%; bottom: ' + (randoFiver + randoFiver - 1 + 100) + '%; animation-delay: 0.' + randoHundo + 's; animation-duration: 0.5' + randoHundo + 's;"><div class="stem" style="animation-delay: 0.' + randoHundo + 's; animation-duration: 0.5' + randoHundo + 's;"></div><div class="splat" style="animation-delay: 0.' + randoHundo + 's; animation-duration: 0.5' + randoHundo + 's;"></div></div>';
            backDrops += '<div class="drop" style="right: ' + increment + '%; bottom: ' + (randoFiver + randoFiver - 1 + 100) + '%; animation-delay: 0.' + randoHundo + 's; animation-duration: 0.5' + randoHundo + 's;"><div class="stem" style="animation-delay: 0.' + randoHundo + 's; animation-duration: 0.5' + randoHundo + 's;"></div><div class="splat" style="animation-delay: 0.' + randoHundo + 's; animation-duration: 0.5' + randoHundo + 's;"></div></div>';
        }

        $('.rain.front-row').append(drops);
        $('.rain.back-row').append(backDrops);
    }

    makeItRain();

    var userNameError = $(".userNameError span").text();
    if (userNameError) {
        $(".userNameError").css("padding-top", "10px");
    }
    var passwordError = $(".passwordError span").text();
    if (passwordError) {
        $(".userNameError").css("padding-top", "10px");
    }

    var authorizationRequestNotFound = $(".authorizationRequestNotFound span").text();
    if (authorizationRequestNotFound) {
        $(".userNameError").css("padding-bottom", "10px");
    }

    $(".error-login").delay(5000).fadeOut('slow');

});

