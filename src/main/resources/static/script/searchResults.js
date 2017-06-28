$(document).ready(function () {
    var title = $('#title');
    $.get('/api/tweetnum', function (data) {
        title.text("Filtered out " + data + " tweets.");
    });

    var results = $('#results');
    $.get('/api/tweets', function (data) {
        for (var i in data) {
            embed(data[i]);
        }
    });
});

function embed(id) {
    var tweet = document.getElementById("results");
    twttr.widgets.createTweet(
        id, tweet,
        {
            conversation : 'all',
            cards        : 'visible',
            theme        : 'light'
        });
}