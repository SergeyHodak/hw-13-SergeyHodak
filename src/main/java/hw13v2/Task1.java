package hw13v2;

import com.google.gson.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Task1 {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new Gson();

    public static User addUser(String url, User user) throws IOException, InterruptedException {
        URI uri = URI.create(url+ "/users");
        String requestBody = GSON.toJson(user);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        return GSON.fromJson(response.body(), User.class);
    }

    public static User updateUser(String url, User user) throws IOException, InterruptedException {
        URI uri = URI.create(url+ "/posts/" + user.getId());
        String requestBody = GSON.toJson(user);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-type", "application/json; charset=UTF-8")
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), User.class);
    }

    public static int deleteUser(String url, int id) throws IOException, InterruptedException {
        URI uri = URI.create(url + "/posts/" + id);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri).DELETE().build();

        HttpResponse<String> response =
                CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode();
    }

    public static ArrayList<User> getAllUsers(String url) throws IOException, InterruptedException {
        URI uri = URI.create(url+ "/users");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri).GET().build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        ArrayList<User> userList = GSON.fromJson(response.body(), (Type) User.class);


        System.out.println("-------------------------------------------");




        String[] elements = response.body()
                .replace("},\n" + "  {", "}#{")
                .replace("[", "")
                .replace("]", "")
                .split("#");

        ArrayList<User> users = new ArrayList<>();
        for (int i=0; i<elements.length; i++) {
            users.add(GSON.fromJson(elements[i], User.class));
        }
        return users;
    }

    public static User getUserById(String url, int id) throws IOException, InterruptedException {
        URI uri = URI.create(url + "/users/" + id);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri).GET().build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        return GSON.fromJson(response.body(), User.class);
    }

    public static User getUserByUsername(String url, String username) throws IOException, InterruptedException {
        URI uri = URI.create(url + "/users?username=" + username);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri).GET().build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        String result = response.body().substring(1, response.body().length()-1);
        return GSON.fromJson(result, User.class);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        String url = "https://jsonplaceholder.typicode.com";

//        User user1 = Task1.addUser(url, User.defaultUser()); // создать нового юзера на сайте
//        System.out.println(user1 + "\n"); // посмотреть на ответ сайта (код 201)

//        User user2 = Task1.updateUser(url, User.defaultUser()); // изменить юзера на сайте
//        System.out.println(user2 + "\n"); // посмотреть на ответ сайта (код 200)

//        int user3 = Task1.deleteUser(url, 3); // Удалить Юзера
//        System.out.println(user3 + "\n"); // посмотреть на код ответа от сайта (код 200)

        ArrayList<User> user4 = getAllUsers(url); // прочитать и записать в коллекцию Юзеров
        System.out.println(user4 + "\n"); // посмотреть коллекцию (код 200)

//        User user5 = Task1.getUserById(url, 2); // взять данные пользователя по ID
//        System.out.println(user5 + "\n"); // посмотреть на ответ сайта (код 200)

//        User user6 = Task1.getUserByUsername(url, "Samantha"); // взять данные пользователя по username
//        System.out.println(user6); // посмотреть на ответ сайта (код 200)
    }
}

