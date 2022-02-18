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

обновление объекта в https://jsonplaceholder.typicode.com/users. Возможно, обновления не будут видны на сайте напрямую. Будем считать, что метод работает правильно, если в ответ на запрос придет обновленный JSON (он должен быть точно таким же, какой вы отправляли).

удаление объекта из https://jsonplaceholder.typicode.com/users. Здесь будем считать корректным результат - статус из группы 2хх в ответ на запрос.

получение информации обо всех пользователях https://jsonplaceholder.typicode.com/users

получение информации о пользователе с определенным id https://jsonplaceholder.typicode.com/users/{id}

получение информации о пользователе с опредленным username - https://jsonplaceholder.typicode.com/users?username={username}
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Task1 {
    public String post() throws IOException {
        Document doc = Jsoup.connect("https://jsonplaceholder.typicode.com/users").post();
        System.out.println(doc);
        return "";
    }

    public static void main(String[] args) throws IOException {
        Task1 test = new Task1();
        test.post();
    }
}
