package org.example;
import com.sys1yagi.mastodon4j.api.exception.Mastodon4jRequestException;
import java.io.IOException;



public class Main {

    public static void main(String[] args) throws Mastodon4jRequestException, IOException {

          MastodonRunner.postMessage(args);

    }
}