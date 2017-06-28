$(document).ready(function () {
    var user = $('#user');

    $.get('/api/user', function (data) {
        user.text("Logged in as " + data);
    });

    console.log("Login successful");
});
