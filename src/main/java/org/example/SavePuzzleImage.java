package org.example;

import chariot.Client;
import com.sys1yagi.mastodon4j.api.exception.Mastodon4jRequestException;

import javax.imageio.ImageIO;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class SavePuzzleImage {

    public SavePuzzleImage(){

    }

    public static String generateURL(){
        return "https://lichess1.org/training/export/gif/thumbnail/" +  generateName() + ".gif?theme=brown&piece=cburnett";
    }

    public static String generateName(){
        Client client = Client.basic();
        return client.puzzles().dailyPuzzle().get().puzzle().id();
    }

    public static String createStatus(){
        return "Today's Daily Puzzle! \n Puzzle ID: " + generateName() + "\n Puzzle URL: https://lichess.org/training/" + generateName();
    }

    public static void createImage(String[] args) throws IOException {
        // Create a URL object pointing to the image endpoint
        URL imageUrl = new URL(generateURL());

        // Open a connection to the URL and get an InputStream
        InputStream inputStream = imageUrl.openStream();

        // Create a new file output stream to save the image as a .png file
        FileOutputStream outputStream = new FileOutputStream( generateName() + ".png");

        // Use the ImageIO class to write the image data to the output stream as a PNG file
        ImageIO.write(ImageIO.read(inputStream), "png", outputStream);

        // Close the streams
        inputStream.close();
        outputStream.close();

    }




}
