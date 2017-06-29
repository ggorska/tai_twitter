package hello.service;

import hello.model.TweetList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceTest {

    @Mock
    TweetList tweetList;

    @Mock
    Twitter twitter;

    @InjectMocks TwitterService twitterService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @org.junit.Test
    public void filter() throws Exception {
        Status status = mock(Status.class);
        when(status.getText()).thenReturn("ala ma kota", "ala");
        ResponseList<Status> timeline = mock(ResponseList.class);
        when(timeline.get(0)).thenReturn(status);
        when(timeline.get(1)).thenReturn(status);
        when(timeline.size()).thenReturn(2);
        when(twitter.getHomeTimeline()).thenReturn(timeline);

        twitterService.filter("no", 3);

        ArgumentCaptor<List> argumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(tweetList, times(1)).addTweets(argumentCaptor.capture());
        List <Status> captured = argumentCaptor.<List<Status>> getValue();
        assertEquals(captured.size(), 2);
    }

    @Test
    public void filterOut() throws Exception {
        Status status = mock(Status.class);
        when(status.getText()).thenReturn("ala ma kota", "ala");
        ResponseList<Status> timeline = mock(ResponseList.class);
        when(timeline.get(0)).thenReturn(status);
        when(timeline.get(1)).thenReturn(status);
        when(timeline.size()).thenReturn(2);
        when(twitter.getHomeTimeline()).thenReturn(timeline);

        twitterService.filter("kota", 3);

        ArgumentCaptor<List> argumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(tweetList, times(1)).addTweets(argumentCaptor.capture());
        List <Status> captured = argumentCaptor.<List<Status>> getValue();
        assertEquals(captured.size(), 1);
    }

    @Test
    public void filterLimit() throws Exception {
        Status status = mock(Status.class);
        when(status.getText()).thenReturn("ala ma kota", "ala");
        ResponseList<Status> timeline = mock(ResponseList.class);
        when(timeline.get(0)).thenReturn(status);
        when(timeline.get(1)).thenReturn(status);
        when(timeline.size()).thenReturn(2);
        when(twitter.getHomeTimeline()).thenReturn(timeline);

        twitterService.filter("no", 1);

        ArgumentCaptor<List> argumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(tweetList, times(1)).addTweets(argumentCaptor.capture());
        List <Status> captured = argumentCaptor.<List<Status>> getValue();
        assertEquals(captured.size(), 1);
        assertEquals(captured.get(0), status);
    }

    @org.junit.Test
    public void getUserName() throws Exception {
        String username = "username";
        when(twitter.getScreenName()).thenReturn(username);
        assertEquals(username, twitterService.getUserName());
    }

    @org.junit.Test
    public void noUserName() throws Exception {
        when(twitter.getScreenName()).thenThrow(new TwitterException("message"));
        assertEquals("unknown", twitterService.getUserName());
    }
}