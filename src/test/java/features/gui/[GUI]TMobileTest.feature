Feature: Wybranie telefonu z listy ofert
  Background:
    When Użytkownik uruchamia przeglądarkę CHROME

  Scenario: Wybranie telefonu z listy ofert
    * Przechodzę do strony T-mobile
    * Weryfikuję czy strona wczytała się poprawnie
    * Wybieram Urządzenia z górnego menu
    * Wybieram Bez abonamentu z sekcji Smartwatche i opaski
    * Klikam w pierwszy produkt z listy
    * Dodaję produkt do koszyka
    * Wyświetlana jest strona koszyka z poprawną ceną
    * Wracam na stronę główną
    * Strona główna jest widoczna z ikoną koszyka wyświetlającą liczbę produktów