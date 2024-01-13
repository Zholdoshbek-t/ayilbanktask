# To-Do-List Spring Boot Приложение

Приложение представляет собой систему управления списком задач с двумя контроллерами: `TaskController` and `TaskTypeController`.

Следуйте инструкциям ниже, чтобы настроить и изучить проект.

## Getting Started

### Prerequisites

Убедитесь, что на вашем компьютере установлено следующее:

- Java (JDK 17 or later)
- Maven
- Git
- Postgresql
- pgAdmin4

### Создаем новую базу данных

Откройте pgAdmin4 и введите свои данные.
Создайте новую базу данных "tasks", так как при запуске приложения для создания таблиц, приложение будет искать базу данных под названием "tasks".
Иначе приложение выведет ошибку.

### Клонируем удаленный репозиторий

Клонируйте репозиторий проекта, используя следующую команду:

```bash
git clone https://github.com/Zholdoshbek-t/ayilbanktask
```

## Откройте проект
### Перейдите в каталог проекта:
```bash
cd ayilbanktask
```

## Запуск приложения
### 1. Создайте проект с помощью Maven:
```bash
mvn clean install
```
### 2. Запустите приложение Spring Boot:
```bash
java -jar target/task-0.0.1-SNAPSHOT.jar
```

The application will be accessible at http://localhost:8080.