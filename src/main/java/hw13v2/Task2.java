package hw13v2;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class Task2 {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new Gson();

    public static void getCommentsOnTheUserLastPost(String url, int id) throws IOException, InterruptedException {
        URI uriForPosts = URI.create(url + "/users/" + id + "/posts");
        HttpRequest requestForPosts = HttpRequest.newBuilder().uri(uriForPosts).GET().build();
        HttpResponse<String> responseToAPostRequest = CLIENT.send(requestForPosts, HttpResponse.BodyHandlers.ofString());
        List<Post> postList = GSON.fromJson(responseToAPostRequest.body(), new TypeToken<List<Post>>(){}.getType());

        int maxIdPost = 0;
        for (Post value : postList) {
            int separateID = value.getId();
            if (separateID > maxIdPost) {
                maxIdPost = separateID;
            }
        }

        URI uriForComments = URI.create(url + "/posts/" + maxIdPost + "/comments");
        HttpRequest requestForComments = HttpRequest.newBuilder().uri(uriForComments).GET().build();
        HttpResponse<String> responseToACommentsRequest = CLIENT.send(requestForComments, HttpResponse.BodyHandlers.ofString());
        String nameFile = "src\\main\\java\\hw13v2\\user-" + id + "-post-" + maxIdPost + "-comments.json";
        File file = new File(nameFile);

        try (FileWriter writer = new FileWriter(file))
        {
            writer.write(responseToACommentsRequest.body());
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        String url = "https://jsonplaceholder.typicode.com";
        getCommentsOnTheUserLastPost(url, 2);
    }
}