package hw13v2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class Task2 {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    public static void sendGetCommentsOnTheUserLastPost(String url, int id) throws IOException, InterruptedException {
        URI uri = URI.create(url + "/users/" + id + "/posts");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri).GET().build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        String test = response.body().substring(1, response.body().length()-1);
        ArrayList<Integer> result = new ArrayList<>();
        while (test.length() > 1) {
            int st = test.indexOf('{');
            int a = test.indexOf('}');
            String s = test.substring(st, a+1);
            int b = s.indexOf("\"id\": ");
            int c = s.indexOf(',', b+6);
            result.add(Integer.parseInt(s.substring(b+6, c)));
            test = test.substring(a+1);
        }
        int maxIdPost = 0;
        for (Integer d:result) {
            if (d > maxIdPost) {
                maxIdPost = d;
            }
        }
        URI uri2 = URI.create(url + "/posts/" + maxIdPost + "/comments");
        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(uri2).GET().build();

        HttpResponse<String> response2 = CLIENT.send(request2, HttpResponse.BodyHandlers.ofString());
        String nameFile = "src\\main\\java\\hw13v2\\user-" + id + "-post-" + maxIdPost + "-comments.json";
        File file = new File(nameFile);
        try (FileWriter writer = new FileWriter(file))
        {
            writer.write(response2.body());
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        String url = "https://jsonplaceholder.typicode.com";
        sendGetCommentsOnTheUserLastPost(url, 2); // обратимся к юзеру по id
    }
}