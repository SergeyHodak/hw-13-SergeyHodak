package lectureNotes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class JSoupLibrary {}

/*
    Библиотека JSoup#
    Работа с Интернет-ресурсами по протоколу HTTP - очень популярная задача, поскольку полезная программа чаще всего
    общается с внешним миром именно по этому протоколу.

    Есть множество библиотек, упрощающих эту работу. Одна из наиболее популярных таких библиотек - JSoup.

    Сайт библиотеки - https://jsoup.org/

    Эта библиотека позволяет удобно отсылать любые HTTP-запросы, удобно настраивая их параметры (заголовки, методы,
    тело запроса и т.д.).

    Вот пример, как можно получить текст заголовка веб-страницы английской википедии:
*/
class Test1 {
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://en.wikipedia.org/").get();
        String pageTitle = doc.title();
        System.out.println(pageTitle);
    }
}
/*
----------------------------------------------------------------------
    Чтобы Jsoup заработал. В pom.xml  добавить:

    <dependencies>
        <dependency>
            <!-- jsoup HTML parser library @ https://jsoup.org/ -->
            <groupId>org.jsoup</groupId>
            <artifactId>jsoup</artifactId>
            <version>1.14.3</version>
        </dependency>
    </dependencies>

    нажать на нем ПКМ/Maven/Reload project
----------------------------------------------------------------------

    В примере выше мы отправили GET-запрос по адресу https://en.wikipedia.org/. Получили ответ (это HTML-страница),
    из текста HTML получили значение заголовка - за это отвечает тег <title>. И вывели результат в консоль.

    Библиотекой JSoup очень удобно работать именно с HTML-страницами, но она позволяет отправлять любые HTTP-запросы.

    Если вам интересна эта тема, начать изучение библиотеки можете с этой страницы - https://jsoup.org/cookbook/
 */