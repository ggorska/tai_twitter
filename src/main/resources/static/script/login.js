$(document).ready(function () {
    var user = $('#user');

    $.get('/api/user', function (data) {
        user.text("Logged in as " + data.username);
    });

    console.log("Login successful");
});
