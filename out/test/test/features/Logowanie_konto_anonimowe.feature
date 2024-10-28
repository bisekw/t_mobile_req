Feature: [WEB]

  Background:

    When Użytkownik uruchamia przeglądarkę CHROME

  @RUN
  Scenario:[WEB] Logowanie konto: Anonimowe
    * Przechodzę na stronę Transport GZM
    * Weryfikuję czy strona wczytała się poprawnie
    * Klikam na przycisk Zaloguj się
    * Wpisuje email jbcogysh@guerrillamailblock.com
    * Wpisuje hasło 1234!@#$QWer
    * Klikam zaloguj


