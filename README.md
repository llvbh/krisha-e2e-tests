<a id="readme-top"></a>
<p align="center">
<a href="https://krisha.kz/"><img src="images/logo/Krisha.svg" width="300" alt="krisha.kz"/></a>
</p>

# Проект по тестированию сайта krisha.kz для поиска квартиры

> <a href="https://krisha.kz/">Крыша</a> — сайт объявлений о продаже и аренде недвижимости в Казахстане

## :scroll: Содержание:

- <a href="#tools">Технологии и инструменты</a>
- <a href="#cases">Реализованные тесты</a>
- <a href="#console">Запуск тестов из терминала</a>
- <a href="#jenkins">Сборка в Jenkins</a>
- <a href="#allure">Allure-отчет</a>
- <a href="#testops">Интеграция с Allure TestOps</a>
- <a href="#jira">Интеграция с Jira</a>
- <a href="#telegram">Уведомление в Telegram</a>
- <a href="#video">Видео тестов</a>

<a id="tools"></a>
## :hammer_and_wrench: Использованные инструменты

<p align="center">
<a href="https://www.java.com/"><img src="images/logo/Java.svg" width="50" height="50" alt="Java"/></a>
<a href="https://www.jetbrains.com/idea/"><img src="images/logo/Idea.svg" width="50" height="50" alt="IDEA"/></a>
<a href="https://github.com/"><img src="images/logo/GitHub.png" width="50" height="50" alt="GitHub"/></a>
<a href="https://junit.org/junit5/"><img src="images/logo/Junit5.svg" width="50" height="50" alt="JUnit 5"/></a>
<a href="https://gradle.org/"><img src="images/logo/Gradle.png" width="50" height="50" alt="Gradle"/></a>
<a href="https://selenide.org/"><img src="images/logo/Selenide.svg" width="50" height="50" alt="Selenide"/></a>
<a href="https://aerokube.com/selenoid/"><img src="images/logo/Selenoid.svg" width="50" height="50" alt="Selenoid"/></a>
<a href="https://github.com/allure-framework"><img src="images/logo/Allure.svg" width="50" height="50" alt="Allure"/></a>
<a href="https://qameta.io/"><img src="images/logo/Allure_TO.svg" width="50" height="50" alt="Allure TestOps"/></a>
<a href="https://www.atlassian.com/software/jira"><img src="images/logo/Jira.svg" width="50" height="50" alt="Jira"/></a>
<a href="https://www.jenkins.io/"><img src="images/logo/Jenkins.svg" width="50" height="50" alt="Jenkins"/></a>
<a href="https://web.telegram.org/"><img src="images/logo/Telegram.svg" width="50" height="50" alt="Telegram"/></a>
</p>

Тесты написаны на языке <code>Java</code> с использованием фреймворка [Selenide](https://selenide.org/), сборщик — <code>Gradle</code>. <code>JUnit 5</code> используется для запуска тестов.
Браузеры для удаленного запуска поднимаются в [Selenoid](https://aerokube.com/selenoid/).
Для удаленного запуска сделана джоба в <code>Jenkins</code> с формированием Allure-отчета и отправкой результатов в <code>Telegram</code> при помощи бота.

Allure-отчет включает в себя:
- шаги выполнения тестов
- скриншот страницы на последнем шаге
- исходный код страницы
- логи консоли браузера
- видео выполнения теста

<a id="cases"></a>
## :ballot_box_with_check: Реализованные тесты

### Главная страница
- Главная страница открывается: заголовок, логотип и форма поиска
- В главном меню есть вкладки «Продажа», «Аренда», «Новостройки», «Оценка», «Новости»
- В верхнем меню расположены табы «Избранное», «Регистрация», «Личный кабинет»
- Пункты меню «Продажа» и «Аренда» открывают нужные разделы

### Поиск объявлений
- Поиск двухкомнатных квартир на продажу через форму на главной странице
- Поиск однокомнатных квартир в аренду через форму на главной странице
- Страницы с объявлениями по Алматы, Астане и Шымкенту открываются и содержат объявления
- Переход на вторую страницу с объявлениями

### Фильтры и сортировка
- Фильтр по максимальной цене: все объявления не дороже указанной суммы
- Фильтр по минимальной площади
- Сортировка «Дешевые» и «Дорогие»
- Хлебные крошки: есть текущий раздел и работает переход на главную страницу

<p align="right"><a href="#readme-top">наверх</a></p>

<a id="console"></a>
## :computer: Запуск тестов из терминала

Локальный запуск:
```
./gradlew clean test
```

Запуск по тегам:
```
./gradlew clean smoke_test
./gradlew clean search_test
```

Удаленный запуск в Selenoid:
```
./gradlew clean test -DselenoidLogin=<логин> -DselenoidPassword=<пароль>
```

Дополнительные параметры:
- `-Dbrowser` — браузер, по умолчанию `chrome`
- `-DbrowserVersion` — версия браузера
- `-DbrowserSize` — размер окна, по умолчанию `1920x1080`
- `-DselenoidUrl` — адрес Selenoid, по умолчанию `selenoid.qa.guru`

Сформировать и открыть Allure-отчет:
```
./gradlew allureServe
```

<p align="right"><a href="#readme-top">наверх</a></p>

<a id="jenkins"></a>
## <img alt="Jenkins" height="25" src="images/logo/Jenkins.svg" width="25"/> Сборка в [Jenkins](https://jenkins.qa.guru/job/llvbh-krisha-e2e-tests/)

<p align="center">
<img src="images/screen/jenkins.png" alt="Jenkins" width="950"/>
</p>

### Параметры сборки в Jenkins:
- `TASK` — какие тесты запускать: `test`, `smoke_test`, `search_test`
- `BROWSER` — браузер, по умолчанию `chrome`
- `BROWSER_VERSION` — версия браузера: `156.0`, `155.0`, `148.0`
- `BROWSER_SIZE` — размер окна браузера, по умолчанию `1920x1080`
- `SELENOID_HOST` — адрес Selenoid, по умолчанию `selenoid.qa.guru`
- `SELENOID_LOGIN`, `SELENOID_PASSWORD` — логин и пароль для Selenoid
- `TELEGRAM_TOKEN`, `TELEGRAM_CHAT` — токен бота и чат для уведомлений

<p align="right"><a href="#readme-top">наверх</a></p>

<a id="allure"></a>
## <img alt="Allure" height="25" src="images/logo/Allure.svg" width="25"/> [Allure-отчет](https://jenkins.qa.guru/job/llvbh-krisha-e2e-tests/allure/)

### Основная страница отчета

<p align="center">
<img title="Allure Overview" src="images/screen/allure.png" width="850"/>
</p>

### Тест-кейсы

<p align="center">
<img title="Allure Tests" src="images/screen/allure-tests.png" width="850"/>
</p>

<p align="right"><a href="#readme-top">наверх</a></p>

<a id="testops"></a>
## <img alt="Allure TestOps" height="25" src="images/logo/Allure_TO.svg" width="25"/> Интеграция с [Allure TestOps](https://allure.qa.guru/project/5425/dashboards)

Результаты каждой сборки в <code>Jenkins</code> загружаются в <code>Allure TestOps</code>. Проверки, которые нельзя автоматизировать на этом сайте, описаны там же как ручные тест-кейсы.

### Дашборд

<p align="center">
<img title="Allure TestOps Dashboard" src="images/screen/testops-dashboard.png" width="850"/>
</p>

### Запуск тестов

<p align="center">
<img title="Allure TestOps Launch" src="images/screen/testops-launch.png" width="850"/>
</p>

### Автоматизированные и ручные тест-кейсы

<p align="center">
<img title="Allure TestOps Test Cases" src="images/screen/testops-cases.png" width="850"/>
</p>

<p align="right"><a href="#readme-top">наверх</a></p>

<a id="jira"></a>
## [Интеграция с Jira](https://jira.qa.guru/browse/MUL-69)

В задаче на разработку UI-автотестов привязаны ручные тест-кейсы из <code>Allure TestOps</code>.

<p align="center">
<img title="Jira" src="images/screen/jira.png" width="850"/>
</p>

<p align="right"><a href="#readme-top">наверх</a></p>

<a id="telegram"></a>
## <img alt="Telegram" height="25" src="images/logo/Telegram.svg" width="25"/> Уведомление в Telegram при помощи бота

После выполнения тестов бот отправляет сообщение с результатами прогона.

<p align="center">
<img title="Telegram" src="images/screen/telegram.png" width="550"/>
</p>

<p align="right"><a href="#readme-top">наверх</a></p>

<a id="video"></a>
## <img alt="Selenoid" height="25" src="images/logo/Selenoid.svg" width="25"/> Видео прохождения теста

К каждому тесту в отчете прикладывается видео прогона.

<p align="center">
<img title="Selenoid Video" src="images/screen/video.gif" width="850"/>
</p>

<p align="right"><a href="#readme-top">наверх</a></p>
