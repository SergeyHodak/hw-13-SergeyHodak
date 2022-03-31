package hw13v2;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class Task3 {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    public static void sendGetOpenUserTasks(String url, int id) throws IOException, InterruptedException {
        URI uri = URI.create(url + "/users/" + id + "/todos");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri).GET().build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        String test = response.body().substring(1, response.body().length()-1);
        ArrayList<String> result = new ArrayList<>();
        while (test.length() > 1) {
            int st = test.indexOf('{');
            int a = test.indexOf('}');

            String s = test.substring(st, a+1);
            int b = s.indexOf("\"completed\": ");
            int c = s.indexOf('\n', b+13);
            if (s.substring(b+13, c).equals("true")) {
                result.add(s);
            }
            test = test.substring(a+1);
        }
        System.out.println(result);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        String url = "https://jsonplaceholder.typicode.com"; // к какому сайту будем подключаться
        sendGetOpenUserTasks(url, 4); // вывеси в консоль открытые задачи указанного юзера по id
    }
}