$(document).ready(function () {
    var title = $('#title');
    title.text("Search results for ...");
    $.get('/api/tweetnum', function (data) {
        title.text("Search results count " + data);
    });

    var results = $('#results');
    $.get('/api/tweets', function (data) {

        $.get('https://publish.twitter.com/oembed', {url: data}, function (data) {
            console.log(JSON.stringify(data));
        });
        results.text("results " + JSON.stringify(data));
        console.log(JSON.stringify(data));
    });
});