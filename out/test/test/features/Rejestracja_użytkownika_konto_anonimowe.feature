Feature: [WEB]

  Background:

    When Użytkownik uruchamia przeglądarkę CHROME
    When Użytkownik generuje 15 min adres email


  Scenario:[WEB] Rejestracja użytkownika - konto ANONIMOWE
    * Przechodzę na stronę Transport GZM
  #  And Przełączam wersję językową na PL
    * Weryfikuję czy strona wczytała się poprawnie
    * Klikam na przycisk załóż konto
    * Rejestruje użytkownika
    * Użytkownik pobiera link Aktywacja konta Transport GZM z email
    * Użytkownik otwiera w nowej zakładce link do aktywacji
    * Użytkownik wybiera opcje: Zakładam konto anonimowe
    * Użytkownik wypełnia dane dla konta anonimowego oraz przechodzi do profilu
    * Użytkownik widzi ekran potwierdzający udaną rejestrację konta anonimowego
    * Użytkownik klika Przejdź do konta - anonimowe
    * Użytkownik widzi ekran główny konta anonimowego




