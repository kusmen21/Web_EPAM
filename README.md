This project is a realization of the hostel.

Short description: the client registers, creates request for booking beds, looks through his current requests. 
The administrator confirms (or denies) the request, 
puts the ban to users, 
scans the users data and their requests, 
changes the users data, 
looks at the current requests

Этот проект представляет собой реализацию хостела.

Краткое описание: клиент заполняет заявку на бронирование кроватей, просматривает свои текущие заявки. Администратор подтверждает (или отказывает) заявку, ставит бан пользователям, просматривает данные пользователей и их заявки, изменяет данные пользователей, просматривает актуальные заявки.

Алгоритм работы системы хостела
1)	Клиент регистрируется на сайте хостела, позже он может войти на сайт с зарегистрированными данными. 
2)	Клиент создает заявку, указывает время начала и конца заказа. При выборе дат становятся недоступными для выбора занятые на данный момент кровати. Клиент выбирает нужные ему кровати, их стоимость подсчитывается с учетом персональной скидки клиента и создается заявка со статусом НЕ_ПРОЧИТАНО и полной ценой всех выбранных кроватей с учетом скидки.
3)	Клиент может следить за актуальными и старыми заявками в своем кабинете. Актуальные заявки – заявки со статусами НЕ_ПРОЧИТАНО, ОДОБРЕНО, ОПЛАЧЕНО и конечной датой больше сегодняшнего дня.
4)	Клиент может отменить свою заявку в личном кабинете, статус заявки становится ОТМЕНЕНО.
5)	Администратор просматривает заявки со статусом НЕ_ПРОЧИТАНО, звонит пользователю для уточнения данных и одобряет заявку (ОДОБРЕНО) либо отказывает (ОТКАЗАНО)
6)	Клиент приходит в хостел, оплачивает заявку. Администратор устанавливает заявке значение ОПЛАЧЕНО и клиент заселяется.
