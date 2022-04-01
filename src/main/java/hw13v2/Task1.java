package hw13v2;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class Task1 {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new Gson();

    public static User addUser(String url, User user) throws IOException, InterruptedException {
        URI uri = URI.create(url+ "/users");
        String requestBody = GSON.toJson(user);
        HttpRequest request = HttpRequest.newBuilder().uri(uri)
                .header("Content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        return GSON.fromJson(response.body(), User.class);
    }

    public static User updateUser(String url, User user) throws IOException, InterruptedException {
        URI uri = URI.create(url+ "/posts/" + user.getId());
        String requestBody = GSON.toJson(user);
        HttpRequest request = HttpRequest.newBuilder().uri(uri)
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-type", "application/json; charset=UTF-8").build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), User.class);
    }

    public static int deleteUser(String url, int id) throws IOException, InterruptedException {
        URI uri = URI.create(url + "/posts/" + id);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).DELETE().build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode();
    }

    public static ArrayList<User> getAllUsers(String url) throws IOException, InterruptedException {
        URI uri = URI.create(url+ "/users");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        List<User> userList = GSON.fromJson(response.body(), new TypeToken<List<User>>(){}.getType());
        return new ArrayList<>(userList);
    }

    public static User getUserById(String url, int id) throws IOException, InterruptedException {
        URI uri = URI.create(url + "/users/" + id);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), User.class);
    }

    public static User getUserByUsername(String url, String username) throws IOException, InterruptedException {
        URI uri = URI.create(url + "/users?username=" + username);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        String result = response.body().substring(1, response.body().length()-1);
        return GSON.fromJson(result, User.class);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        String url = "https://jsonplaceholder.typicode.com";

        User user1 = Task1.addUser(url, User.defaultUser());
        System.out.println(user1);
        System.out.println("--------------------------------------------------------------------------------------");
        User user2 = Task1.updateUser(url, User.defaultUser());
        System.out.println(user2);
        System.out.println("--------------------------------------------------------------------------------------");
        int user3 = Task1.deleteUser(url, 3);
        System.out.println(user3);
        System.out.println("--------------------------------------------------------------------------------------");
        ArrayList<User> user4 = getAllUsers(url);
        System.out.println(user4);
        System.out.println("--------------------------------------------------------------------------------------");
        User user5 = Task1.getUserById(url, 2);
        System.out.println(user5);
        System.out.println("--------------------------------------------------------------------------------------");
        User user6 = Task1.getUserByUsername(url, "Samantha");
        System.out.println(user6);
    }
}