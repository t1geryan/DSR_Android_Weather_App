# Android Weather App
Приложение, написанное для производственной практики в DSR 02.05.23-30.05.23

## Возможности приложения:
- Отображение списка добавленных/избранных локаций с кратким прогнозом погоды
- Гибкое добавление локации:
  - Через интерактивную карту (с помощью меток)
  - Через текстовое поле поиска с автозаполнением
  - С помощью текущего местоположения
- Отображение детального прогноза погоды для локации
  - Детальный прогноз на текущий момент
  - Краткий рогноз на несколько дней
  - График изменения температуры
- Экран настроек для ручного изменения темы приложения и системы метрики
- Система триггеров (_в разработке_)
  - Добавление и удаление триггеров
  - Отправка уведомлений по заданным триггерам
- Имеется поддержка
  - Светлой и темной тем
  - Альбомной ориентации и различных конфигураций дисплея
  - Английской и русской локализации 

## Используемый стек и паттерны:
- Traditional UI (XML) + Material Design
- MVVM Architecture Pattern
- Clean Architecture Pattern
- JUnit + MockK for Unit tests
- Kotlin Coroutines + Flow
- Retrofit
- Room Database
- Jetpack Navigation
- Hilt for DI

### Дополнительно использовались:
- Fragments
- Preference Data Store with Flow Synchronization Api
- AndroidX Preference library for Preference Screen
- Glide
- Yandex MapKit
- Location Google Play Service
- MpAndroidChart

## Контакты для связи:
##### tigeryandevelop@gmail.com

## License:
See [LICENSE](https://github.com/t1geryan/DSR_Android_Weather_App/blob/master/LICENSE)
