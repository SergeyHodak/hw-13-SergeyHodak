package hw13v1;

/*
    Задание 3#
    Дополните программу методом, который будет выводить все открытые задачи для пользователя Х.

    https://jsonplaceholder.typicode.com/users/1/todos.

    Открытыми считаются все задачи, у которых completed = false.
 */

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class Task3 {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    public static void sendGetOpenUserTasks(String url, int id) throws IOException, InterruptedException { // показать открытые задачи юзера
        URI uri = URI.create(url + "/users/" + id + "/todos"); // формировка ссылки с id пользователя
        HttpRequest request = HttpRequest // HTTP-запрос.
                .newBuilder() // Построитель дефолтных настроек
                .uri(uri) // соединение с сайтом
                .GET() // метод GET (получить информацию указанного Юзера)
                .build(); // Создает и возвращает HttpRequest

        HttpResponse<String> response = // HTTP-ответ.
                CLIENT.send(request, HttpResponse.BodyHandlers.ofString()); // Отправляет данный запрос, и получает ответ от сайта
        String test = response.body().substring(1, response.body().length()-1); // полученный ответ, -1 и -последний символы
        ArrayList<String> result = new ArrayList<>(); // коллекция открытых задач юзера
        while (test.length() > 1) { // выполнять цикл пока в строке больше чем 1 символ
            int st = test.indexOf('{'); // Индекс стартовой фигурной скобки с которой начинается описание юзера
            int a = test.indexOf('}'); // индекс первой закрывающейся фигурной скобки

            String s = test.substring(st, a+1); // получаем строку описывающую одну из задач юзера
            int b = s.indexOf("\"completed\": "); // индекс входа к информации bool
            int c = s.indexOf('\n', b+13); // индекс переноса строки
            if (s.substring(b+13, c).equals("true")) { // если информация гласит что задача открыта
                result.add(s); // добавляем в коллекцию эту задачу
            }
            test = test.substring(a+1); // убираем с полученных данных эту задачу
        }
        System.out.println(result);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        String url = "https://jsonplaceholder.typicode.com"; // к какому сайту будем подключаться
        sendGetOpenUserTasks(url, 4); // вывеси в консоль открытые задачи указанного юзера по id
    }
}