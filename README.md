cross_master
1. Pobranie i zbudowanie paczki za pomocą Maven
   Sklonuj repozytorium:


Skopiuj kod
https://github.com/bisekw/t_mobile_master
Zbuduj projekt, pomijając testy:

mvn clean install -DskipTests
2. Weryfikacja instalacji paczki
   Paczka powinna być zainstalowana w lokalnym repozytorium Maven:

C:\Users\xxx\.m2\repository\pl\cross-test-master\CrossTestMaster
Wersja paczki: 1.0

WebTester
konfiguracja wer. Chrome
WebTester_New\src\main\resources
np podajemy wer.
CHROME_VERSION=131.0.6778.140
1. Uruchomienie testów Cucumber w IntelliJ
   Otwórz konfigurację uruchamiania w IntelliJ.
   Skonfiguruj następujące opcje:
   Glue:
   framework.steps steps
   VM options:
   -DIS_REMOTE_RUN=false
   -Denvironment=UAT
   Program arguments:
   --plugin pretty
2. Uruchomienie testów za pomocą TestNG
   Przejdź do katalogu projektu.
   Wykonaj polecenie:
   bash
   Skopiuj kod
   mvn clean test -D"Surefire.suiteXmlFiles=src/test/java/TestNg.xml" -DIS_REMOTE_RUN=false -Denvironment=UAT
3. Konfiguracja Jenkinsa jako kontenera Dockerowego
   Uruchomienie Jenkinsa
   Przejdź do folderu jenkins-docker-compose.
   Uruchom kontener Docker:
   docker-compose -f docker-compose.yml -p jenkins up -d --force-recreate --build
   Jenkins będzie dostępny pod adresem: http://localhost:8082
   Konfiguracja Jenkinsa
   Otwórz Jenkins w przeglądarce: http://localhost:8082

Zainstaluj wymagane wtyczki:

Pipeline,
Git,
JUnit
Jak zainstalować wtyczki:

Przejdź do Manage Jenkins > Manage Plugins > Available.
Wyszukaj i zainstaluj wymienione wtyczki.
Skonfiguruj narzędzia Maven:

Przejdź do http://localhost:8082/manage/configureTools/.
Dodaj konfigurację Maven, wybierając odpowiednią instalację, np. Apache Maven.
Konfiguracja zadania Pipeline
Utwórz nowe zadanie typu Pipeline.
W sekcji Pipeline wybierz: Pipeline script from SCM.
W polu SCM wybierz: Git.
Podaj URL do repozytorium, np.:
https://github.com/bisekw/t_mobile_req.git
W polu Script Path pozostaw domyślną wartość: Jenkinsfile (o ile plik znajduje się w głównym katalogu repozytorium).
Zapisz konfigurację.
