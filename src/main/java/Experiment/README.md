## Небольшой эксперимент
 
 Допустим, нам поставили задачу по сбору статистики опросов из веб-формы, а также попросили дать возможность аналитикам считывать эти данные в режиме реального времени.
 
 Команда frontend'а дала нам вот такой [файл](./../../resources/index.html) и заверили, что он полностью рабочий.
 По прохождению всего опроса он отправляет результаты в формате json вида {"passedQuestions":[0,1],"totalQuestions":2}, где passedQuestions - id вопросов, на кторые ответ был правильно дан, а passedQuestions - общее количество вопросов.
 
 Команда дата-инженеров очень просила дать им возможность считывать данные по отчетам в Stream-потоке. 
 
 Наша задача: 
 - Создать веб-сервер для отображения формы с отчетом
 - Создать клиента и сервер для чтения собранной статистики
 
 В рамках текущей задачи статистика может храниться в памяти. 
 
 Давайте поможем собрать фитбек от пользователей!