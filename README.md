# DOKUMENTACJA PROJEKTU

### „FOOTBALL LEAGUE – liga piłkarska”

##### AUTOR: Marcin Cyc

##### 28.05.2019r.
<br>
<br>
<br>

## SPIS TREŚCI

###### 1.Wprowadzenie, cel projektu.

###### 2.Założenia i ograniczenia.

###### 3.Krótki opis struktury bazy danych.
    a. Architektura
        i. Diagram ERD - encje
        ii. Model relacyjny - tabele
        iii. Procesy–diagramy DFD
        iv. Triggers

###### 4.Implementacja
    a. Ogólny opis aplikacji

    b. Opis zastosowania

    c. Funkcjonalności

    d. Bezpieczeństwo

    e. Administracja

###### 5.Użyte technologie


## 1.Wprowadzenie, cel projektu.

Projekt naukowy umożliwiający zdobycie nowej wiedzy w dziedzinie informatyki
realizowany na potrzeby zaliczenia przedmiotu „Projekt bazodanowy” na drugim roku studiów
informatycznych (inżynierskich 3.5 letnich) pierwszego stopnia na Uniwersytecie Mikołaja
Kopernika w Toruniu. Celem projektu jest:

- utworzenie pierwszej działającej bazy danych korzystającej z serwera wydziałowego tj.
opracowanie modelu logicznego i relacyjnego, który poprawnie generuje kod DDL, przygotowanie
DFD – diagramu przepływu danych ilustrującego ich wędrówkę, wykonanie wyzwalacza (tzw.
trigger’a) nadzorującego modyfikację danych oraz zamieszczenie danych na serwerze
uruchomionym m na jednej z maszyn wydziałowych
- opanowanie technologi używanych przy jej projektowaniu, tworzeniu i zarządzaniu, zrozumienie i
sensowne używanie strukturalnego języka zapytań OracleSQL
- oswojeniu się z programami ułatwiającymi pracę, zaznajomienie się z SQLDeveloper’em, oraz
SQL Data Modeler’em
- poznanie niebezpieczeństw zagrażającym bezpieczeństwu danych koncentrując się na atakach
SQL Injection (wstrzykiwaniu kodu SQL) oraz metod zapobiegania dostaniu się wrażliwych
informacji w niepowołane ręce
- zaimplementowanie aplikacji umożliwiającej odczytywanie informacji z tabel umieszczonych w
bazie danych, modyfikowanie ich, wprowadzenie oraz usuwanie, a także zrozumieniu biblioteki
ORM i wykorzystanie jej w aplikacji, poznanie technologii w języku Java umożliwiających
komunikację z bazą


## 2.Założenia i ograniczenia

Projekt powstał w celach naukowych, dlatego podczas tworzenia aplikacji skupiłem się na
poznaniu nowych technologii niż nad dbałością o szczegóły i bezbłędnym utworzeniem
oprogramowania, więc przyjąłem kilka ograniczeń i założeń:

- struktura drużyny i zawodników przeznaczona jest dla klubów piłkarskich, ponadto aplikacja
obsługuje tylko jedną ligę nie ma możliwości żeby drużyna A grała w lidze 1, a drużyna B grała w
lidze 2, wszystkie drużyny uczestniczą w tych samych rozgrywkach
- liczba drużyn i piłkarzy w lidze jest nieograniczona
- liczba kibiców(użytkowników) również jest nieograniczona
- aplikacja korzysta z „pseudo kont” oznacza to, że nie zostało zaimplementowane powiązanie
konta z adresem e-mail, co za tym idzie nie ma możliwości odzyskania hasła od strony
użytkownika, dopiero po kontakcie z administratorem kibic ma możliwość odzyskania hasła, które
nie jest również w żadnym stopniu zabezpieczone (brak haszowania), podział na konta powstał w
celu odseparowania jednego uprawnionego użytkownika do edycji danych o pseudonimie „admin”,
za to użytkownicy mogą wspierać dowolną liczbę zawodników i drużyn
- pozycje zawodników zostały ograniczone do 4 możliwości: BR – bramkarz, OB – obrońca, PO –
pomocnik, NA – napastnik, brak dodatkowych pozycji takich jak ŚO – środkowy obrońca , ŚP –
środkowy pomocnik itd. oraz brak deklarowania własnych pozycji przez administratora
- trener może prowadzić tylko jedną drużynę
- braki wydajnościowe spowodowane terminem oddania projektu, nakładem pracy na inne
przedmioty oraz innym głównym celem projektu
- aplikacja też nie jest w pełni dobrze przetestowana, nie zostały zaimplementowane testy
jednostkowe. Jedyny sprawdzenie poprawności jest kilku dziesiętne uruchomienie aplikacji przeze
mnie i sprawdzenie kilkunastu scenariuszy użycia
- brak dao – interfejsu do obiektów zmapowanych przy pomocy Hibernate’a, przy tak małym
projekcie uznałem za zbędną implementacje tego rozwiązania
- zostały też nałożone ograniczenia w aplikacji na pewne tabele np. wiek trenera/użytkownika
występuje w zakresie 0 – 120 (w przypadku rejestracji dowolna dodatnia liczba) pomimo tego, że w
bazie nie ma takiego ograniczenia; podobnie dla numeru na koszulce zawodnika: zakres od 0 - 99


## 3.Krótki opis struktury bazy

## danych.

##### a. Architektura

###### i. Diagram ERD – encje

W moim projekcie umieściłem takie encje jak: piłkarz – reprezentuje zawodnika biorącego
udział w rozgrywkach, kibic – użytkownik systemu, drużyna – klub biorący udział w rozgrywkach,
trener – odpowiada osobie trenującą drużynę w rozgrywkach oraz mecz przedstawiający wynik
rozegranego meczu pomiędzy dwoma drużynami. Encje połączone są relacjami : 1:1 (jeden do
jednego), 1:n (jeden do wielu), n:m (wiele do wielu).

Przykładową relacją 1:1 jest relacją pomiędzy drużyną i trenerem: 1 drużyna musi mieć 1
trenera, a 1 trener może prowadzić tylko jedną drużynę oznacza to, że drużyna bez trenera nie może
istnieć w bazie danych, za to trener bez przypisanej mu drużyny może.

Przykładem relacji 1:n jest relacja zawiązana pomiędzy piłkarzem i drużyną: 1 drużyna
może posiadać wielu piłkarzy, natomiast 1 piłkarz musi być zawodnikiem w tylko jednej drużynie.

Przykładem relacji n:m jest relacja zawiązana pomiędzy piłkarzem i kibicem: 1 drużyna
może być wspierana przez wielu kibiców, natomiast 1 kibic może kibicować wielu drużynom.

![Model Logiczny](http://www-users.mat.umk.pl/~marcinbedcyc/images/footballLeague/modelLogiczny.png)

###### ii. Model relacyjny – tabele

W wyniku przekształceniu modelu logicznego w model relacyjny otrzymujemy następujące
tabele: piłkarz, trener, drużyna, kibic, mecz, kibicowanie_drużynie oraz kibicowanie_piłkarzowi.
Powstały dwie nowe tabele w porównaniu do modelu logicznego. Powodem tego są dwie relacje
n:m (wiele do wiele). Aby poprawnie przechowywać informacje o takiej relacji niezbędne są
osobne tabele odpowiedzialne za trzymanie tych danych. W moim przypadku potrzebuję
przechowywać wiadomości na temat kibicowania drużynom i kibicowania piłkarzom.

![Model Relacyjny](http://www-users.mat.umk.pl/~marcinbedcyc/images/footballLeague/modelRelacyjny.png)

###### iii. Procesy–diagramy DFD

Przykładowym procesem zachodzącym w mojej aplikacji jest wybór drużyn i piłkarzy,
którym zalogowany użytkownik chce kibicować. Do procesu przekazywane są dane na temat
obecnie zalogowanego użytkownika oraz cała tabela drużyn lub piłkarzy zależnie od procesu, w
wyniku wykonaniu się akcji zapisywane są dane w tabeli kibicowanie_drużynie lub
kibicowanie_piłkarzowi. Poniżej diagram DFD ilustrujący ten proces:


###### iv. Triggers

W projekcie powstały wyzwalacze zarządzające punktacją ligi. Punkty przyznawane są
następująco: zwycięstwo +3pkt oraz +1 mecz wygrany, remis +1pkt oraz +1 mecz zremisowany,
porażka +0pkt +1 mecz przegrany. Trigger’y są uruchamiane w trakcie dodawania, usuwania lub
modyfikacji meczu. W przypadku dodawania rezultatu meczu w zależności od wyniku są
dodawanie punkty, lub ich stan się nie zmienia oraz zmienia się liczba przegranych meczy,
wygranych meczy lub remisów. W przypadku usunięcia punkty są odejmowane lub ich stan się nie
zmienia oraz zmienia się liczba przegranych meczy, wygranych meczy lub remisów. Jeśli nastąpi
edycja wyniku może zmienić się liczba punktów, liczba wygranych, przegranych lub
zremisowanych meczy.


## 4.Implementacja

###### a. Ogólny opis aplikacji

Aplikacja utworzona do projektu „FOOTBALL LEAGUE - liga piłkarska” jest
przeznaczona na komputery stacjonarne/ laptopy z dowolnym systemem operacyjnym
wspierającym wirtualną maszynę Javy. Przedstawia zawodników, drużyny, kibiców oraz wyniki
konkretnej ligi zarządzanej przez administratora. Program pozwala użytkownikowi śledzić
rozgrywki ligi piłkarskiej oraz zdobyć informację na temat drużyn/ trenerów i zawodników w niej
uczestniczących. Ponadto umożliwia wybranie piłkarzy i drużyn, którymi jest zainteresowana i im
kibicuje. Z kolei administrator w przejrzystym interfejsie graficznym może modyfikować dane bez
jakiejkolwiek znajomości SQL oraz może być pewny bezpieczeństwa swoich danych.

###### b. Opis zastosowania

Aplikacja znajduje zastosowaniem w instytucjach zarządzających rozgrywkami piłkarskimi,
które poprzez aplikację mogą udostępniać dane rozgrywek wyniki/ piłkarzy/ drużyny/ trenerów
osobom zainteresowanym piłką nożną, które chcą na bieżąco je śledzić. Ponadto dzięki możliwości
wybrania przez użytkowników ulubionych zawodników i klubów, zarządzający aplikacją może
śledzić i analizować zainteresowanie rozgrywkami, klubami lub piłkarzami.

###### c. Funkcjonalności

- kibicowanie drużynie – użytkownik aplikacji po wybraniu interesującej go drużyny może jej
kibicować, zarówno zwykły użytkownik jak i administrator
- kibicowanie piłkarzowi - użytkownik aplikacji po wybraniu interesującego go piłkarza
może mu kibicować, zarówno zwykły użytkownik jak i administrator

![Kibicuj](http://www-users.mat.umk.pl/~marcinbedcyc/images/footballLeague/kibicuj.png)
<br>
<br>
<br>
![Przestań kibicować](http://www-users.mat.umk.pl/~marcinbedcyc/images/footballLeague/stopKibicuj.png)

- przeglądanie wyników meczu
- przeglądanie drużyn, piłkarzy, trenerów, kibiców

![Wyniki](http://www-users.mat.umk.pl/~marcinbedcyc/images/footballLeague/wyniki.png)
<br>
<br>
<br>
![Tabela](http://www-users.mat.umk.pl/~marcinbedcyc/images/footballLeague/tabela.png)

- opcje administracyjne: dodawanie, usuwanie, modyfikowanie elementów – po
zalogowaniu jak administrator ma możliwość edytowania wszystkich danych
umieszczonych w bazie. Może dodać nowego zawodnika, usunąć już istniejącego lub
edytować takie pola jak imię, nazwisko, wiek, numer na koszulce, pozycję czy drużynę, w
której gra. Administrator może także dodać drużynę, usunąć oraz edytować pola takie jak
nazwa, trener prowadzący czy datę założenia klubu. Administrator nie ma bezpośredniego
wpływu na punktację, ponieważ ilość punktów, zwycięstw, porażek i remisów jest obliczana
na podstawie meczów umieszczonych w bazie. Obliczenia odbywają się przy pomocy
trigger’a. Aplikacja także umożliwia dodawanie trenerów, usuwanie i zmianę ich imion,
nazwisk czy wieku. Użytkownik z dodatkowymi prawami (admin) może także dodawać,
usuwać i modyfikować wyniku meczów, które mają bezpośredni wpływ na tabele
rozgrywek. Administrator może też zmienić dane innych użytkowników aplikacji, dodawać i
usuwać ich.

![Zarządzaj](http://www-users.mat.umk.pl/~marcinbedcyc/images/footballLeague/zarzadzaj.png)


###### d. Bezpieczeństwo

- zapytania parametryzowane – zabezpieczają przed niepowołanym odwołaniem się do bazy przez
użytkownika

![Paramtery](http://www-users.mat.umk.pl/~marcinbedcyc/images/footballLeague/parametry.png)

- zastosowanie comboBox’ów zamiast TextField’ów w celu weryfikacji wprowadzanych danych w
miejscach, gdzie to możliwe, tak jak w podpunkcie d. już na poziomie aplikacji minimalizujemy
niechciane odwołania do bazy przez użytkownika. W przypadku TextField’ów weryfikacja
poprawności wprowadzonych danych następuje przed wywołaniem zapytania na poziomie
aplikacji.

![ComboBox](http://www-users.mat.umk.pl/~marcinbedcyc/images/footballLeague/comboBox.png)

###### e. Administracja

- super użytkownik zarządzający danymi z poziomu aplikacji – jest to zwykły użytkownik o
specjalnym loginie, umożliwiającym otworzenie dodatkowej opcji w menu do zarządzania danymi
w bazie
- administrowanie danymi z poziomu bazy danych za pomocą aplikacji SQL Developer – opcja dla
bardziej zaawansowanego klienta z podstawowymi umiejętnościami korzystania z języka SQL. Taki
użytkownik bezpośrednio edytuje dane w bazie.


## 5. Użyte technologie

**- Java**

Współbieżny , obiektowy język programowania oparty na klasach, w którym została
utworzona aplikacja. Pracuje na wirtualnej maszynie oraz zarządzanie pamięcią realizowane jest za
pomocą tzw. Garbage Collector’a, dzięki niemu skupiamy się bardziej na odpowiedniej
implementacji komunikacji z bazą danych i utworzeniem interfejsu graficznego dla użytkownika
niż na zarządzaniu pamięcią jak w C++. Wybór padł na Javę, ponieważ inne zajęcia na tym samym
kierunku umożliwiają zdobycie rzetelnej wiedzy to pisania programów.

**- Oracle Database**

System zarządzania relacyjnymi bazami danych stworzonym przez firmę Oracle
wykorzystujący standardowy język zapytań SQL oraz posiada wbudowany wewnętrzny język
tworzenia procedur składowych PL/SQL. Technologia wybrana ze względu na poznanie jej podstaw
na poprzednim semestrze na przedmiocie „Bazy danych”. Ponadto na jednej z maszyn
wydziałowych został postawiony serwer, do którego my jako studenci mamy dostęp, dzięki niemu
nie ma potrzeby tworzenia własnego serwera bazy danych, tylko korzystamy z gotowego
rozwiązania. Jedyną rzeczą do wykonania jest zamieszczenie bazy wykorzystywanej w projekcie na
tym serwerze.

**- Biblioteka ORM Hibernate**

Biblioteka ORM (z ang. Object-Realtional Mapping) mapowania obiektowo-relacyjnego
wykorzystana w celu bezpośredniego rzutowania obiektów w języku Java na istniejące tabele bazy
danych. Dodatkowo Hibernate zwiększa wydajność operacji na bazie danych dzięki buforowaniu i
minimalizacji liczby przesyłanych zapytań. Dodatkowo zapewnia bezpieczeństwo dzięki
wykorzystywaniu zapytań parametryzowanych

**- JavaFX**

Biblioteka tworzenia graficznego interfejsu użytkownika w języku Java, pozwala tworzyć
aplikacje, które wyglądają bardziej nowocześnie jednocześnie poprawiając wydajności w
porównaniu do poprzednich rozwiązań (AWT, Swing). W projekcie technologia użyta w celu
zbudowaniu graficznego interfejsu oraz wydajnego zarządzania wszystkim kontrolkami w
interfejsie (przyciskami, polami tekstowymi i innymi).

**- Apache Maven**

Jest narzędziem, które automatyzuje budowę oprogramowania na platformę Java, min.
ułatwia dodawanie zależności (pliki jar), w projekcie wykorzystane do dodania jdbc diver’a oraz
hibernate’a.



