# MusicId Project

# Quick start

## 1) .env конфиг

В репозитории лежит пример конфига, который ты должен заполнить [тут](.env). Скопируй, переименуй в `.env` и
заполни.

### JWT_SECRET and JWT_SECRET_REFRESH keys

Должны быть размером не менее 256 байт или 32 ascii символа.

## 2) Run
```bash
docker-compose --env-file .env up -d
```

# Перед началом работы

## .editorconfig

### About

https://editorconfig.org/

Заставите вашу IDE понимать этот [файл](.editorconfig), тогда будет проще соблюдать codestyle.

## Check style

### About

В проект добавлен плагин `checkstyle`. Перед отправкой кода **обязательно** прогоняйте эту работу (описана ниже).

Конфиг - [checkstyle.xml](config/checkstyle/checkstyle.xml) и является модифицированной
версией [этого](https://github.com/checkstyle/checkstyle/blob/master/src/main/resources/sun_checks.xml) конфига.

### Запуск

```bash
./mvnw checkstyle:check
```

## О наименовании коммитов

https://www.conventionalcommits.org/en/v1.0.0/

Обрати внимание на структуру!

## База данных

Для заполнения базы мы используем liquibase, конфиг которого
находится [тут](src/main/resources/db/changelog/db.changelog-master.yaml).

## Users

### Подробнее об менеджменте пользователей

https://www.baeldung.com/role-and-privilege-for-spring-security-registration

## Тесты

### Запуск

```bash
./mvnw test 
```
