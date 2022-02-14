package lectureNotes;

public class ProtocolsHTTPorHTTPS {}

/*
    Протоколы HTTP/HTTPS#
    Вы скорей всего читаете сейчас этот текст из браузера - программы для просмотра веб-страниц. А эту веб-страницу
    браузер загрузил с помощью протокола HTTP.

    Интернет и его основа, HTTP-протокол, настолько прочно вошел в нашу жизнь, что его следует рассмотреть отдельно.

    Что такое вообще протокол#
    Протоколы передачи данных - это набор соглашений, по которым происходит обмен данными между программами.

    ПОЛЕЗНО
    Протокол можно сравнить с иностранным языком, например, английским. Если двое участников знают один язык, они
    смогут на нем общаться. Так же и программы - если две разные программы понимают один протокол, они смогут общаться
    по этому протоколу.

    Протокол задает единообразный способ передачи сообщений и обработки ошибок, которому обязаны следовать
    "участники коммуникации". Каждому из вас знакомы аббревиатуры IP, HTTP, однако что они обозначают на самом деле
    и как соотносятся друг с другом?

    Различные протоколы отвечают за разные уровни и аспекты коммуникации, эти уровни описаны в модели OSI
    (The Open Systems Interconnection model): todo, смотри картинку src\main\java\lectureNotes\01.png
    Сейчас не нужно глубоко вникать во все уровни этой модели. Важно знать, что есть такой стек, OSI, и что протокол
    HTTP - один из уровней этого стека.

    HTTP#
    Hypertext Transfer Protocol - протокол передачи гипертекста, который используется службой WWW для передачи
    Web-страниц.
    Программа-клиент и программа-сервер (согласно протоколу HTTP) взаимодействуют через запрос клиента и ответ сервера.

    ВНИМАНИЕ
    Коммуникация в форме "запрос-ответ" - отличительная черта HTTP. То есть, в отличие от, например, протокола
    Web-socket, сервер не может "самостоятельно" отправить данные клиенту, пока не получит от него запрос на
    получение информации.

    Модель взаимодействия в HTTP - запрос-ответ: todo, смотри картинку src\main\java\lectureNotes\02.jpg

    Любой запрос содержит набор заголовков (header), которые содержат информацию о запросе (путь, длину и проч.).

    Ряд запросов может иметь body, в котором передается объект (наличие body зависит от HTTP метода).

    Методы HTTP#
    HTTP поддерживает ряд методов, или команд для отправки запроса:
    todo, смотри картинку src\main\java\lectureNotes\03.jpg

    ВНИМАНИЕ
    В HTTP протоколе версии 2.0 появилась возможность отправлять тело (body) для GET запроса

    Основные методы, с которыми вы будете работать, связаны с созданием, получением, изменением или удалением объектов
    в программе: todo, смотри картинку src\main\java\lectureNotes\04.png

    ПОЛЕЗНО
    Наиболее часто используемым HTTP-методом является GET - именно его используют браузеры для получения веб-страниц.
    Второй по популярности метод - POST.

    HTTP статусы#
    Первая строка в ответе сервера - число, которое обозначает статус ответа сервера.

    С некоторыми из них вы уже знакомы. Например, вы могли видеть ошибку в своем браузере 404 Not Found. В этом
    примере число 404 - это статус ответа, и он означает, что запрашиваемый ресурс не найден.

    Все ответы сервера можно разделить на 5 групп в зависимости от предназначения:
    todo, смотри картинку src\main\java\lectureNotes\05.png

    Полный список статусов и их значение
    Коды состояния HTTP
    httpstatuses.com — это удобная база данных кодов состояния HTTP с их определениями и полезными ссылками на код в
    одном месте. Посетите индивидуальный код состояния через httpstatuses.com/code или просмотрите список ниже.
        @ Share on Twitter⊕ Add to Pinboard
        1×× Informational
        100 Continue
        101 Switching Protocols
        102 Processing
        2×× Success
        200 OK
        201 Created
        202 Accepted
        203 Non-authoritative Information
        204 No Content
        205 Reset Content
        206 Partial Content
        207 Multi-Status
        208 Already Reported
        226 IM Used
        3×× Redirection
        300 Multiple Choices
        301 Moved Permanently
        302 Found
        303 See Other
        304 Not Modified
        305 Use Proxy
        307 Temporary Redirect
        308 Permanent Redirect
        4×× Client Error
        400 Bad Request
        401 Unauthorized
        402 Payment Required
        403 Forbidden
        404 Not Found
        405 Method Not Allowed
        406 Not Acceptable
        407 Proxy Authentication Required
        408 Request Timeout
        409 Conflict
        410 Gone
        411 Length Required
        412 Precondition Failed
        413 Payload Too Large
        414 Request-URI Too Long
        415 Unsupported Media Type
        416 Requested Range Not Satisfiable
        417 Expectation Failed
        418 I'm a teapot
        421 Misdirected Request
        422 Unprocessable Entity
        423 Locked
        424 Failed Dependency
        426 Upgrade Required
        428 Precondition Required
        429 Too Many Requests
        431 Request Header Fields Too Large
        444 Connection Closed Without Response
        451 Unavailable For Legal Reasons
        499 Client Closed Request
        5×× Server Error
        500 Internal Server Error
        501 Not Implemented
        502 Bad Gateway
        503 Service Unavailable
        504 Gateway Timeout
        505 HTTP Version Not Supported
        506 Variant Also Negotiates
        507 Insufficient Storage
        508 Loop Detected
        510 Not Extended
        511 Network Authentication Required
        599 Network Connect Timeout Error

    HTTPS#
    HTTPS (HyperText Transfer Protocol Secure) — расширение протокола HTTP, поддерживающее шифрование.

    Данные, передаваемые по протоколу HTTP, «упаковываются» в криптографический протокол SSL или TLS. Фактически,
    HTTPS не является отдельным протоколом.

    ВНИМАНИЕ
    Протокол был разработан компанией Netscape Communications для браузера Netscape Navigator в 1994 году. HTTPS широко
    используется в мире веб и поддерживается всеми популярными браузерами.

    HTML#
    HTML (HyperText Markup Language) - это язык разметки гипертекста. Строго говоря, он никак не связан с HTTP, но
    посредством HTTP протокола чаще всего передаются именно HTML-страницы.

    Документ, который вы сейчас читаете - это тоже HTML-страница. HTML довольно простая и очень популярная технология.

    Базовое знание HTML - полезный навык, который пригодится вам почти в любой сфере программирования.

    Детальней о HTML можете почитать на Википедии - https://ru.wikipedia.org/wiki/HTML
 */