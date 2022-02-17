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
    private static final String TEST_URL = "https://jsonplaceholder.typicode.com/users"; // текстовое поле (в начале протокол "https:...")

    public static void main(String[] args) throws IOException { // запускалка с исключением
        sendGET(); // метод запроса о получении информации со ссылки
        sendPOST();
    }

    private static void sendGET() throws IOException { // Метод - отправитьПОЛУЧИТЬ. + исключение (запрос о Получении)
        URL url = new URL(TEST_URL); // новый экземпляр класса URL, со ссылкой из текстового поля

        // После получения сущности URL, вызываем метод openConnection() который возвратит нам сущность HttpsUrlConnection.
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET"); // для выполнения HTTP GET

        int responseCode = connection.getResponseCode(); // узнать HTTP статус
        System.out.println("GET response code: " + responseCode); // посмотреть этот статус

        if (responseCode == HttpURLConnection.HTTP_OK) { // если равно 200
            BufferedReader in =
                new BufferedReader( // Java класс BufferedReader читает текст из потока ввода символов,
                                    // буферизуя прочитанные символы, чтобы обеспечить эффективное
                                    // считывание символов, массивов и строк.
                    new InputStreamReader( // InputStreamReader преобразует InputStream к Reader
                            connection
                                    .getInputStream() // Возвращает входной поток, который считывается из
                                                      // этого открытого соединения.
                    ));

            String inputLine; // строковая переменная
            StringBuffer response = new StringBuffer(); // Поточно-безопасная изменяемая последовательность символов.
                                                        // Строковый буфер подобен String, но может быть изменен. В
                                                        // любой момент времени он содержит определенную
                                                        // последовательность символов, но длину и содержимое
                                                        // последовательности можно изменить с помощью вызовов
                                                        // определенных методов.
            while ((inputLine = in.readLine()) != null) {
                // readLine() - Читает строку текста. Строка считается завершенной одним из следующих символов:
                // перевод строки ('\n'), возврат каретки ('\r'), возврат каретки, за которым сразу следует перевод
                // строки, или достижение конца файла. (ЭОФ).
                // Возвращает: Строка, содержащая содержимое строки, не включая символы завершения строки, или null,
                // если конец потока был достигнут без чтения каких-либо символов.
                response.append(inputLine); // добавить на вывод
            }
            in.close(); // завершить поток
            System.out.println(response.toString()); // печатать результат в консоль
        } else { // иначе, не равно 200
            System.out.println("GET request not worked"); // сообщение: GET-запрос не работает
        }
    }

    private static void sendPOST() throws IOException { // отправить сообщение + исключение
        URL url = new URL(TEST_URL); // новый экземпляр класса URL, со ссылкой из текстового поля
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // После получения сущности URL,
                                                                                 // вызываем метод openConnection()
                                                                                 // который возвратит нам сущность
                                                                                 // HttpsUrlConnection.
        connection.setRequestMethod("POST"); // для выполнения HTTP POST
        connection.setDoOutput(true); // Устанавливает значение поля doOutput для этого URLConnection в указанное
                                      // значение. Соединение URL может использоваться для ввода и/или вывода.
                                      // Установите для флага doOutput значение true, если вы собираетесь использовать
                                      // URL-соединение для вывода, и значение false, если нет. Значение по
                                      // умолчанию — ложь. Параметры: dooutput – новое значение.
        OutputStream os = connection.getOutputStream(); // getOutputStream() - Возвращает выходной поток, который
                                                        // записывает в это соединение.
        os.write( // Записывает байты b.length из указанного массива байтов в этот выходной поток.
                Files   // Этот класс состоит исключительно из статических методов, которые работают с файлами,
                        // каталогами или другими типами файлов.
                        .readAllBytes( // Читает все байты из файла.
                                new File( // Создает новый экземпляр File путем преобразования заданной строки пути
                                          // в абстрактный путь.
                                        "src\\main\\java\\lectureNotes\\user.json")
                                        .toPath())); // Возвращает объект java.nio.file.Path, созданный из этого
                                                     // абстрактного пути. Полученный путь связан с файловой
                                                     // системой по умолчанию.
        os.flush(); // Сбрасывает этот выходной поток и принудительно записывает любые буферизованные выходные байты.
        os.close(); // Закрывает этот выходной поток и освобождает все системные ресурсы, связанные с этим потоком.

        int responseCode = connection.getResponseCode(); // узнать HTTP статус
        System.out.println("POST response code: " + responseCode); // посмотреть этот статус
        if (responseCode == HttpURLConnection.HTTP_CREATED) { // если равно 201
            BufferedReader in =
                new BufferedReader( // Java класс BufferedReader читает текст из потока ввода символов,
                                    // буферизуя прочитанные символы, чтобы обеспечить эффективное
                                    // считывание символов, массивов и строк.
                    new InputStreamReader( // InputStreamReader преобразует InputStream к Reader
                            connection.getInputStream())); // Возвращает входной поток, который считывается из
                                                           // этого открытого соединения.
            StringBuffer response = new StringBuffer(); // Поточно-безопасная изменяемая последовательность символов.
                                                        // Строковый буфер подобен String, но может быть изменен. В
                                                        // любой момент времени он содержит определенную
                                                        // последовательность символов, но длину и содержимое
                                                        // последовательности можно изменить с помощью вызовов
                                                        // определенных методов.
            String inputLine; // строковая переменная
            while ((inputLine = in.readLine()) != null) {
                // readLine() - Читает строку текста. Строка считается завершенной одним из следующих символов:
                // перевод строки ('\n'), возврат каретки ('\r'), возврат каретки, за которым сразу следует перевод
                // строки, или достижение конца файла. (ЭОФ).
                // Возвращает: Строка, содержащая содержимое строки, не включая символы завершения строки, или null,
                // если конец потока был достигнут без чтения каких-либо символов.
                response.append(inputLine); // добавить на вывод
            }
            in.close(); // завершить поток
            System.out.println(response.toString()); // печатать результат в консоль
        } else { // иначе, не равно 201
            System.out.println("POST request not worked"); // сообщение: POST-запрос не работает
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