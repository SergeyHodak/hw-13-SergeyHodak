package lectureNotes;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;

public class WorkingWithHTTPinJava {}

/*
    Работа с HTTP в Java#
    Варианты работы с HTTP серверами#
    Мы уже знаем, что HTTP-протокол работает по принципу клиент-серверной архитектуры. Есть некий веб-сервер, который
    ждет запросов. Есть клиент, который оптправляет запросы. Сервер принимает запрос, обрабатывает его, и
    отправляет ответ.

    Таким образом, мы можем реализовать серверную или клиентскую часть.

    Мы сосредоточимся над работой именно над клиентской частью.

    Реализация серверной части потребует от нас знаний технологии Servlet API (или же умения работать с сокетами).
    Это мы будем изучать чуть позже.

    HttpUrlConnection#
    Класс HttpURLConnection из пакета java.net может использоваться для того, чтобы отправлять из Java приложения
    HTTP запросы. Рассмотрим использование HttpURLConnection для отправки GET и POST запросов и вывода в консоль
    результатов их исполнения.
*/


class HelloWorld {

    private static final String TEST_URL =
        "https://jsonplaceholder.typicode.com/users";

    public static void main(String[] args) throws IOException {
        sendGET();
        sendPOST();
    }

    private static void sendGET() throws IOException {
        URL url = new URL(TEST_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        System.out.println("GET response code: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in =
                new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());
        } else {
            System.out.println("GET request not worked");
        }
    }

    private static void sendPOST() throws IOException {
        URL url = new URL(TEST_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        OutputStream os = connection.getOutputStream();
        os.write(Files.readAllBytes(new File("src\\main\\java\\lectureNotes\\user.json").toPath()));
        os.flush();
        os.close();

        int responseCode = connection.getResponseCode();
        System.out.println("POST response code: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_CREATED) {
            BufferedReader in =
                new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());
        } else {
            System.out.println("POST request not worked");
        }
    }
}
/*
    Для POST запроса используется файл user.json:
        {
          "name": "Test",
          "username": "TEST",
          "email": "test@test.com",
          "address": {
            "street": "Kulas Light",
            "suite": "Apt. 556",
            "city": "Gwenborough",
            "zipcode": "92998-3874",
            "geo": {
              "lat": "-37.3159",
              "lng": "81.1496"
            }
          },
          "phone": "1-770-736-8031 x56442",
          "website": "hildegard.org",
          "company": {
            "name": "Romaguera-Crona",
            "catchPhrase": "Multi-layered client-server neural-net",
            "bs": "harness real-time e-markets"
          }
        }

    Основные моменты при работе с HttpUrlConnection:

Адрес сайта, к которому вы хотите обратиться, указывается в конструкторе класса java.net.URL.
Из экземпляра класса URL получаем экземпляр класса java.net.HttpUrlConnection.
У экземпляра класса HttpUrlConnection указывается HTTP метод, также у него можно получить статус (код и сообщение), получить http header по индексу (getHeaderField(int n)) и т. д.
Для того, чтобы отправить POST запрос, у которого есть body, необходимо установить setDoOutput(true), после чего можно передать тело запроса в OutputStream.
Для того, чтобы установить значение header, необходимо воспользоваться методом setRequestProperty(String, String). Например, connection.setRequestProperty("Content-Type", "application/json");
HttpClient#
Начиная с Java 11, появилась возможность отправлять запросы при помощи HttpClient из того же пакета java.net.

Данный класс использует паттерн Builder для формирования запроса. Пример запроса, который возвращает ответ в качестве строки:

public void get(String uri) throws Exception {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(uri))
          .build();

    HttpResponse<String> response =
          client.send(request, BodyHandlers.ofString());

    System.out.println(response.body());
}
Builder может быть использован для того, чтобы установить:

URI запроса
HTTP метод ( GET, PUT, POST )
Тело запроса (если есть)
Time-out
Request headers
HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create("http://openjdk.java.net/"))
      .timeout(Duration.ofMinutes(1))
      .header("Content-Type", "application/json")
      .POST(BodyPublishers.ofFile(Paths.get("file.json")))
      .build()
ПОЛЕЗНО
HttpClient позволяет также отправлять асинхронные запросы. Подробнее об этом вы можете узнать, ознакомившись с источниками.

Дополнительные материалы#
Introduction to the Java HTTP Client. - https://openjdk.java.net/groups/net/httpclient/intro.html
Java HttpClient. Examples and Recipes. - https://openjdk.java.net/groups/net/httpclient/recipes.html
Java.net.HttpURLConnection Class in Java - https://www.geeksforgeeks.org/java-net-httpurlconnection-class-java/?ref=lbp
 */