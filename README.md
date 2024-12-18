# cross_master
1. Należy pobrać i zbudować paczkę via maven:
   https://github.com/bisekw/t_mobile_master
		mvn clean install -DskipTests
2. Weryfikacja odłożenia paczki:
	paczka powinna odłożyć się w katalogu .m2
   C:\Users\xxx\.m2\repository\pl\cross-test-master\CrossTestMaster
	z wersją 1.0

# WebTester
1. REPORTPORTALL:
	zainstaluj docker & docker-compose na windows
		przejdź do katalogu projektu następnie do reportPortall-docker-compose
		uruchom: docker-compose -f docker-compose.yml -p reportportal up -d --force-recreate --build
		
		końcowy status uruchomienia dockerów kilka minut w zależności od procesora i ramu:
   		[+] Running 14/14
		✔ Container reportportal-minio-1             Started                                                                                                                                                      11.3s
		✔ Container reportportal-gateway-1           Started                                                                                                                                                      11.3s
		✔ Container reportportal-postgres-1          Healthy                                                                                                                                                      20.3s
		✔ Container reportportal-ui-1                Started                                                                                                                                                      11.0s
		✔ Container reportportal-elasticsearch-1     Started                                                                                                                                                      11.1s
		✔ Container reportportal-rabbitmq-1          Healthy                                                                                                                                                      41.8s
		✔ Container reportportal-uat-1               Started                                                                                                                                                      19.5s
		✔ Container reportportal-db-scripts-1        Started                                                                                                                                                      19.4s
		✔ Container reportportal-metrics-gatherer-1  Started                                                                                                                                                       9.9s
		✔ Container reportportal-index-1             Started                                                                                                                                                       9.7s
		✔ Container reportportal-analyzer_train-1    Started                                                                                                                                                      39.9s
		✔ Container reportportal-jobs-1              Started                                                                                                                                                      39.4s
		✔ Container reportportal-api-1               Started                                                                                                                                                      39.2s
		✔ Container reportportal-analyzer-1          Started

           http://localhost:8080/ui/
           For user access:
           default
           1q2w3e
           For admin access:
           superadmin
           erebus
   2. Uruchomienie testów jako Cucumber w inteliJ edycja konfiguracji
      		Glue: 
   				framework.steps steps
            VM opotions:
                     -DIS_REMOTE_RUN=true
                     -Denvironment=UAT
            Program argument:
                     --plugin
                     pretty

3. Uruchomienie testów z runer TestNG (raportowanie do ReportPortall)
   przechodzimy do folderu projektowego uruchamiamy komendę:
    mvn clean test -D"Surefire.suiteXmlFiles=src/test/java/TestNg.xml" -DIS_REMOTE_RUN=true -Denvironment=UAT

4. Jenkins dostępny pod adresem http://localhost:8082

   Otwórz w przeglądarce adres Jenkinsa, np. http://localhost:8082 (lub inny, jeśli zmieniłeś port).
   Zainstaluj wtyczkę "Pipeline" (jeśli nie jest domyślnie zainstalowana)
   Przejdź do Manage Jenkins > Manage Plugins > Available.
  Wyszukaj "Pipeline".
  Wyszukaj "Git" i zainstaluj
    
    Przejdż do http://localhost:8082/manage/configureTools/
    dodaj konfigurację maven /wybierz instalację np. Apachee
Skonfiguruj Pipeline job
W konfiguracji joba:

    W sekcji Pipeline wybierz: Pipeline script from SCM (jeżeli chcesz, aby Jenkins pobierał Jenkinsfile z repozytorium).
    W polu SCM wybierz Git.
    Podaj URL do Twojego repozytorium: np. https://github.com/bisekw/t_mobile_req.git
    W polu Script Path pozostaw Jenkinsfile (o ile taki jest w root repo).
    Save