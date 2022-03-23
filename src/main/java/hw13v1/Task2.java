package hw13v1;

/*
    Задание 2#
    Дополните программу методом, который будет выводить все комментарии к последнему посту определенного пользователя
    и записывать их в файл.
    https://jsonplaceholder.typicode.com/users/1/posts Последним будем считать пост с наибольшим id.
    https://jsonplaceholder.typicode.com/posts/10/comments

    Файл должен называться "user-X-post-Y-comments.json", где Х - номер пользователя, Y - номер поста.
 */

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

    public static void sendGetCommentsOnTheUserLastPost(String url, int id) throws IOException, InterruptedException { // прочитать список Юзеров
        URI uri = URI.create(url + "/users/" + id + "/posts"); // формировка ссылки с id пользователя
        HttpRequest request = HttpRequest // HTTP-запрос.
                .newBuilder() // Построитель дефолтных настроек
                .uri(uri) // соединение с сайтом
                .GET() // метод GET (получить информацию указанного Юзера)
                .build(); // Создает и возвращает HttpRequest

        HttpResponse<String> response = // HTTP-ответ.
                CLIENT.send(request, HttpResponse.BodyHandlers.ofString()); // Отправляет данный запрос, и получает ответ от сайта

        String test = response.body().substring(1, response.body().length()-1); // полученный ответ, -1 и -последний символы
        ArrayList<Integer> result = new ArrayList<>(); // коллекция id постов
        while (test.length() > 1) { // выполнять цикл пока в строке больше чем 1 символ
            int st = test.indexOf('{'); // Индекс стартовой фигурной скобки с которой начинается описание юзера
            int a = test.indexOf('}'); // индекс первой закрывающейся фигурной скобки
            String s = test.substring(st, a+1); // получаем строку описывающую один из постов юзера
            int b = s.indexOf("\"id\": "); // индекс первой закрывающейся фигурной скобки
            int c = s.indexOf(',', b+6); // индекс запитой
            result.add(Integer.parseInt(s.substring(b+6, c))); // превращаем эту строчку в int, и добавляем в коллекцию
            test = test.substring(a+1); // убираем из проанализированный пост, с полученных данных
        }
        int maxIdPost = 0; // максимальный id поста к этому юзеру
        for (Integer d:result) { // пробежка по массиву id послов этого юзера
            if (d > maxIdPost) { // если пробегаемый id больше чем зафиксированный в переменной
                maxIdPost = d; // зафиксировать в переменную
            }
        }
        URI uri2 = URI.create(url + "/posts/" + maxIdPost + "/comments"); // формировка ссылки для комментов с id поста
        HttpRequest request2 = HttpRequest // HTTP-запрос.
                .newBuilder() // Построитель дефолтных настроек
                .uri(uri2) // соединение с сайтом
                .GET() // метод GET (получить информацию указанного поста)
                .build(); // Создает и возвращает HttpRequest

        HttpResponse<String> response2 = // HTTP-ответ.
                CLIENT.send(request2, HttpResponse.BodyHandlers.ofString()); // Отправляет данный запрос, и получает ответ от сайта
        String nameFile = "src\\main\\java\\hw13v1\\user-" + id + "-post-" + maxIdPost + "-comments.json"; // путь и имя файла
        File file = new File(nameFile); // новый файл
        try (FileWriter writer = new FileWriter(file)) // в голове тела исключения создать новый экземпляр записи файла
        {
            writer.write(response2.body()); // записать
            writer.flush(); // закрыть
        } catch (IOException e) { // общее исключение
            System.out.println(e.getMessage()); // если что вывести сообщение ошибки в консоль
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        String url = "https://jsonplaceholder.typicode.com"; // к какому сайту будем подключаться
        sendGetCommentsOnTheUserLastPost(url, 1); // обратимся к юзеру по id
    }
}