package org.example;
import com.google.gson.Gson;
import com.sys1yagi.mastodon4j.MastodonClient;
import com.sys1yagi.mastodon4j.api.entity.Attachment;
import com.sys1yagi.mastodon4j.api.exception.Mastodon4jRequestException;
import com.sys1yagi.mastodon4j.api.method.Media;
import com.sys1yagi.mastodon4j.api.method.Statuses;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MastodonRunner {


    public MastodonRunner(){

    }


    public static void postMessage(String[] args) throws Mastodon4jRequestException, IOException {


        SavePuzzleImage.createImage(args);

        String accessToken = System.getenv("Mastodon_Token");
        String instanceName = System.getenv("instance_name");
        MastodonClient client =
                new MastodonClient.Builder(instanceName, new OkHttpClient.Builder(), new Gson())
                        .accessToken(accessToken).build();

        String filename =  SavePuzzleImage.generateName() + ".png";
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", filename,
                RequestBody.create(MediaType.parse("image/png"), new File(filename)));

        // Connect to the Mastodon API's media endpoint
        Media media = new Media(client);

        // Upload the image
        Attachment uploadedImage = media.postMedia(part).execute();

        String status = SavePuzzleImage.createStatus();
        Long inReplyToId = null;
        List<Long> mediaIds = Arrays.asList(uploadedImage.getId());
        boolean sensitive = false;
        String spoilerText = null;

        // Connect to the Mastodon API's statuses endpoint
        Statuses statusesEndpoint = new Statuses(client);

        // Post the status containing the image.
        statusesEndpoint
                .postStatus(status, inReplyToId, mediaIds, sensitive, spoilerText)
                .execute();

        System.exit(0);


    }



}
