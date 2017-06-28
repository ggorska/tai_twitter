$(document).ready(function () {
    var title = $('#title');
    title.text("Search results for ...");
    $.get('/api/tweetnum', function (data) {
        title.text("Search found " + data + " tweets.");
    });

    var results = $('#results');
    $.get('/api/tweets', function (data) {
        for (var i in data) {
            //console.log(data[i]);
            embed(data[i]);
        }
    });
});

function embed(id) {
    var tweet = document.getElementById("results");
    twttr.widgets.createTweet(
        id, tweet,
        {
            conversation : 'none',
            cards        : 'visible',
            theme        : 'light'
        });
}