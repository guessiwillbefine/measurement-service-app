## Journal-service






---
Данный модуль предназначен для журналирования определенных событий. Все записи сохраняются в MongoDB и хранят время события и его тип.
Для того что бы воспользоваться его функционалом нужно добавить в pom.xml следующую зависимость:
```xml 
<dependency>
    <groupId>ua.ms</groupId>
    <artifactId>journal-service</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```
Журналирование доступно для следующих событий (все они находятся в классе ua.ms.journalservice.entity.EventType):
- AUTHORIZATION - авторизация
- CRITICAL_RECEIVED - получение критического значения от сенсора
- ALERT_PUSHED - сообщение было отправлено в брокер
- ALERT_CONSUMED - сообщение было обработано брокером

Для работы с сервисом нужно наследовать класс ua.ms.journalservice.service.EventJournalService. 
В нём уже реализованы базовые операции над записями и внедрено DAO.
