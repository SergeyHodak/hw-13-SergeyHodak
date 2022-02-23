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

        удаление объекта из https://jsonplaceholder.typicode.com/users. Здесь будем считать корректным результат
        - статус из группы 2хх в ответ на запрос.

        получение информации обо всех пользователях https://jsonplaceholder.typicode.com/users

        получение информации о пользователе с определенным id https://jsonplaceholder.typicode.com/users/{id}

        получение информации о пользователе с опредленным username -
        https://jsonplaceholder.typicode.com/users?username={username}
 */

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class Task1 {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new Gson();

    public static User sendPost(String url, User user) throws IOException, InterruptedException { // добавить нового пользователя
        URI uri = URI.create(url+ "/users"); // трансформировать ссылку
        String requestBody = User.transformToString(user); // превратить класс в строку Json
        HttpRequest request = HttpRequest // HTTP-запрос.
                .newBuilder() // Построитель дефолтных настроек
                .uri(uri) // соединение с сайтом
                .header("Content-type", "application/json") // указывает тип содержимого
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))  // метод POST (добавит нового пользователя)
                .build();  // Создает и возвращает HttpRequest
        HttpResponse<String> response = // HTTP-ответ.
                CLIENT.send(request, HttpResponse.BodyHandlers.ofString()); // Отправляет данный запрос, и получает ответ от сайта
        return User.transformToClass(response.body()); // вернуть ответ в виде класса Юзер
    }

    public static User sendPut(String url, User user) throws IOException, InterruptedException {
        URI uri = URI.create(url+ "/posts/" + user.getId()); // трансформировать ссылку
        String requestBody = GSON.toJson(user); // превратить класс в строку Json
        HttpRequest request = HttpRequest // HTTP-запрос.
                .newBuilder() // Построитель дефолтных настроек
                .uri(uri) // соединение с сайтом
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                //.method("PUT", HttpRequest.BodyPublishers.ofString(requestBody))
                //.header("Content-type", "application/json; charset=UTF-8")
                .header("Content-type", "application/json; charset=UTF-8")
                .build();
        HttpResponse<String> response = // HTTP-ответ.
                CLIENT.send(request, HttpResponse.BodyHandlers.ofString()); // Отправляет данный запрос, и получает ответ от сайта
        return GSON.fromJson(response.body(), User.class); // вернуть ответ в виде класса Юзер
    }

    public static int sendDelete(String url, int id) throws IOException, InterruptedException { // прочитать список Юзеров
        URI uri = URI.create(url + "/posts/" + id); // трансформировать ссылку
        HttpRequest request = HttpRequest // HTTP-запрос.
                .newBuilder() // Построитель дефолтных настроек
                .uri(uri) // соединение с сайтом
                .DELETE() // метод DELETE (удалит указанного Юзера)
                .build(); // Создает и возвращает HttpRequest

        HttpResponse<String> response = // HTTP-ответ.
                CLIENT.send(request, HttpResponse.BodyHandlers.ofString()); // Отправляет данный запрос, и получает ответ от сайта
        return response.statusCode(); // Вернуть статус код ответ сайта на действие
    }

    public static ArrayList<User> sendGet(String url) throws IOException, InterruptedException { // прочитать список Юзеров
        URI uri = URI.create(url+ "/users"); // трансформировать ссылку
        HttpRequest request = HttpRequest // HTTP-запрос.
                .newBuilder() // Построитель дефолтных настроек
                .uri(uri) // соединение с сайтом
                .GET() // метод ГЕТ (прочитать сайт)
                .build(); // Создает и возвращает HttpRequest

        HttpResponse<String> response = // HTTP-ответ.
                CLIENT.send(request, HttpResponse.BodyHandlers.ofString()); // Отправляет данный запрос, и получает ответ от сайта

        String test = response.body().substring(1, response.body().length()-1); // полученный ответ, -1 и -последний символы
        ArrayList<User> result = new ArrayList<>(); // коллекция юзеров

        while (test.length() > 1) { // выполнять цикл пока в строке больше чем 1 символ
            int st = test.indexOf('{'); // Индекс стартовой фигурной скобки с которой начинается описание юзера
            int a = test.indexOf('}'); // индекс первой закрывающейся фигурной скобки
            int b = test.indexOf('}', a+1); // индекс второй закрывающейся фигурной скобки
            int c = test.indexOf('}', b+1); // индекс третей закрывающейся фигурной скобки
            int d = test.indexOf('}', c+1); // индекс четвертой закрывающейся фигурной скобки
            String s = test.substring(st, d+1); // получаем строку описывающий одного юзера
            result.add(User.transformToClass(s)); // превращаем эту строчку в класс, и добавляем в коллекцию
            test = test.substring(d+1); // убираем из полученных данных трансформированного юзера
        }
        return result; // отдать коллекцию юзеров в место вызова
    }

    public static User sendGetId(String url, int id) throws IOException, InterruptedException { // прочитать список Юзеров
        URI uri = URI.create(url + "/users/" + id); // трансформировать ссылку
        HttpRequest request = HttpRequest // HTTP-запрос.
                .newBuilder() // Построитель дефолтных настроек
                .uri(uri) // соединение с сайтом
                .GET() // метод GET (получить информацию указанного Юзера)
                .build(); // Создает и возвращает HttpRequest

        HttpResponse<String> response = // HTTP-ответ.
                CLIENT.send(request, HttpResponse.BodyHandlers.ofString()); // Отправляет данный запрос, и получает ответ от сайта
        return User.transformToClass(response.body());  // вернуть ответ в виде класса Юзер
    }

    public static User sendGetUsername(String url, String username) throws IOException, InterruptedException { // прочитать список Юзеров
        URI uri = URI.create(url + "/users?username=" + username); // трансформировать ссылку
        HttpRequest request = HttpRequest // HTTP-запрос.
                .newBuilder() // Построитель дефолтных настроек
                .uri(uri) // соединение с сайтом
                .GET() // метод GET (получить информацию указанного Юзера)
                .build(); // Создает и возвращает HttpRequest

        HttpResponse<String> response = // HTTP-ответ.
                CLIENT.send(request, HttpResponse.BodyHandlers.ofString()); // Отправляет данный запрос, и получает ответ от сайта
        return User.transformToClass(response.body());  // вернуть ответ в виде класса Юзер
    }

    public static void main(String[] args) throws IOException, InterruptedException { // ТЕСТ проверки методов
        String url = "https://jsonplaceholder.typicode.com"; // к какому сайту будем подключаться

        User user1 = Task1.sendPost(url, User.defaultUser()); // создать нового юзера на сайте
        System.out.println(user1 + "\n"); // посмотреть на ответ сайта (код 201)

        User user2 = Task1.sendPut(url, User.defaultUser()); // создать нового юзера на сайте
        System.out.println(user2 + "\n"); // посмотреть на ответ сайта (код 200)

        int user3 = Task1.sendDelete(url, 3); // Удалить Юзера
        System.out.println(user3 + "\n"); // посмотреть на код ответа от сайта (код 200)

        ArrayList<User> user4 = sendGet(url); // прочитать и записать в коллекцию Юзеров
        System.out.println(user4 + "\n"); // посмотреть коллекцию (код 200)

        User user5 = Task1.sendGetId(url, 2); // взять данные пользователя по ID
        System.out.println(user5 + "\n"); // посмотреть на ответ сайта (код 200)

        User user6 = Task1.sendGetUsername(url, "Samantha"); // взять данные пользователя по username
        System.out.println(user6); // посмотреть на ответ сайта (код 200)
    }
}