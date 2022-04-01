package hw13v2;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class Task3 {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new Gson();

    public static void sendGetOpenUserTasks(String url, int id) throws IOException, InterruptedException {
        URI uri = URI.create(url + "/users/" + id + "/todos");

        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        List<ToDo> toDoList = GSON.fromJson(response.body(), new TypeToken<List<ToDo>>(){}.getType());
        ArrayList<ToDo> toDos = new ArrayList<>();
        for (ToDo value : toDoList) {
            boolean taskActivity = value.isCompleted();
            if (taskActivity) {
                toDos.add(value);
            }
        }
        System.out.println(toDos);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        String url = "https://jsonplaceholder.typicode.com";
        sendGetOpenUserTasks(url, 4);
    }
}