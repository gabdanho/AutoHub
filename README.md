**AutoHub** – приложение для покупки/продажи б/у автомобилей: регистрация, авторизация, фильтрация и 
создание объявлений с обязательной загрузкой фото, чат с продавцом, уведомления о новых сообщениях. 

**Использовались следующие библиотеки:**

* Firebase Firestore для хранения объявлений;
* Firebase Storage для хранения изображений;
* Авторизация через Firebase Auth;
* Firebase Messaging и Ktor-сервер для Push-уведомлений;
* Retrofit для работы с сервером.

**Цель проекта**: научиться использовать Firebase от Google.

**Скриншоты**

Авторизация и регистрация:

Присутствует возможность изменить пароль, если он был утерян (на почту придёт ссылка для изменения пароля).

При регистрации нужно заполнить ВСЕ поля корректно (присутствует проверка на ввод каждого из полей).

<img src="https://github.com/user-attachments/assets/d7d2d301-fd36-456d-ad26-d5d62eb0abda" width="200" />  <img src="https://github.com/user-attachments/assets/2db511f6-85e4-48b9-b611-488cd76572aa" width="200" />

Стена с объявлениями и фильтрация поиска:

<img src="https://github.com/user-attachments/assets/9f9999bd-99f9-4445-9e46-f06b4982563f" width="200" />  <img src="https://github.com/user-attachments/assets/5c7d858e-51ad-4a0c-a3c0-2129c2a6345e" width="200" />

Аккаунт пользователя и его настройки:

<img src="https://github.com/user-attachments/assets/eaa88662-d9ef-445e-801f-cf0de6dd5e6d" width="200" />  <img src="https://github.com/user-attachments/assets/9926b5cc-19e5-4041-9777-e119edbdbe41" width="200" />

Объявление продавца:

Присутствует возможность позвонить (вызывается интент звонка) и написать продавцу в чат.

<img src="https://github.com/user-attachments/assets/eb96b464-de09-4d8a-adfd-c4e07ac40895" width="200" />

Создание объявления:

Все поля должны быть заполнены, также должна быть загружена хотя бы одна фотография.

<img src="https://github.com/user-attachments/assets/0ae8a4d4-e8ca-4cf4-ae6a-f5a012e6e278" width="200" />

Чаты с пользователями:

<img src="https://github.com/user-attachments/assets/4795e9c2-a3a3-4c99-9194-3dd0473bf46a" width="200" />

Чаттинг с пользователем:

Отображается сетевой статус пользователя, а также состояние, которое проверяет, прочитано ли сообщение.

<img src="https://github.com/user-attachments/assets/ad697fae-88a3-414e-9a1a-2bded7c1d6bc" width="200" />
