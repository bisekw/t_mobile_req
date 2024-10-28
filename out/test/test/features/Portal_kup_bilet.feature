Feature: [WEB]

  Background:

    When Użytkownik uruchamia przeglądarkę CHROME

  @RUN
  Scenario:[WEB] Logowanie konto: Imienne
    * Przechodzę na stronę transportGZMver
    * Weryfikuję czy strona wczytała się poprawnie
    * Klikam na przycisk Zaloguj się
    * Wpisuje email tatodiy453@cindalle.com
    * Wpisuje hasło Aaaa1234$
    #* Jeżeli widoczna zmiana regulaminow to akceptuje

