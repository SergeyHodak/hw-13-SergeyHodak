package hw13v1;

/*
    Напишите программу, которая будет взаимодействовать с API https://jsonplaceholder.typicode.com.
    Можно пользоваться стандартными возможностями Java (HttpURLConnection), либо познакомиться самостоятельно со
    сторонними библиотеками (Apache Fluent API, Apache HTTPClient, Jsoup).

    Задание 1#
    Программа должна содержать методы для реализации следующего функционала:
        создание нового объекта в https://jsonplaceholder.typicode.com/users. Возможно, вы не увидите обновлений
        на сайте. Метод создания работает правильно, если в ответ на JSON с объектом вернулся такой же JSON, но с
        полем id со значением на 1 больше, чем самый большой id на сайте.

        Обновление объекта в https://jsonplaceholder.typicode.com/users. Возможно, обновления не будут видны на сайте
        напрямую. Будем считать, что метод работает правильно, если в ответ на запрос придет обновленный JSON
        (он должен быть точно таким же, какой вы отправляли).

удаление объекта из https://jsonplaceholder.typicode.com/users. Здесь будем считать корректным результат - статус из группы 2хх в ответ на запрос.

        получение информации обо всех пользователях https://jsonplaceholder.typicode.com/users

получение информации о пользователе с определенным id https://jsonplaceholder.typicode.com/users/{id}

получение информации о пользователе с опредленным username - https://jsonplaceholder.typicode.com/users?username={username}
 */

import com.google.gson.Gson;
import java.io.IOException;
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

    public static User sendPost(URI uri, User user) throws IOException, InterruptedException { // добавить нового пользователя
        String requestBody = GSON.toJson(user);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-type", "application/json")
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), User.class);
    }

    public static User sendPut(URI uri, User user) throws IOException, InterruptedException {
        String requestBody = GSON.toJson(user);
        System.out.println("TEST " + requestBody);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                //.PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .method("PUT", HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-type", "application/json; charset=UTF-8")
                //.header("Content-type", "application/json; charset=UTF-8")
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response);
        return GSON.fromJson(response.body(), User.class);
    }




    public static List<User> sendGet(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        String test = response.body().substring(1, response.body().length()-1);
        ArrayList<User> result = new ArrayList<>();

        while (test.length() > 10) {
            int st = test.indexOf('{');
            int a = test.indexOf('}');
            int b = test.indexOf('}', a+1);
            int c = test.indexOf('}', b+1);
            int d = test.indexOf('}', c+1);
            String s = test.substring(st, d+1);
            result.add(GSON.fromJson(s, User.class));
            test = test.substring(d+1);
        }
        return result;

    }




    public static void main(String[] args) throws IOException, InterruptedException {
        String url = "https://jsonplaceholder.typicode.com/users"; // к какому сайту будем подключаться
        // 1 РАБОТАЕТ
        //User user1 = Task1.sendPost(URI.create(url), User.defaultUser()); // создать нового юзера на сайте
        //System.out.println(user1 + "\n"); // посмотреть на ответ сайта

        //User user2 = Task1.sendPut(URI.create(url), User.defaultUser()); // создать нового юзера на сайте
        //System.out.println(user2 + "\n"); // посмотреть на ответ сайта

        // 4 РАБОТАЕТ (но не получает коллекции)
        List<User> users = sendGet(URI.create(url)); // прочитать и записать в коллекцию Юзеров
        System.out.println(users); // посмотреть коллекцию
    }
}