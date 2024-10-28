Feature: [WEB]

  Background:

    When Użytkownik uruchamia przeglądarkę CHROME
    When Użytkownik generuje 15 min adres email

  Scenario:[WEB] Rejestracja użytkownika - konto IMIENNE
    * Przechodzę na stronę transportGZMver
  #  And Przełączam wersję językową na PL
    * Weryfikuję czy strona wczytała się poprawnie
    * Klikam na przycisk załóż konto
    * Rejestruje użytkownika
    * Użytkownik pobiera link Aktywacja konta Transport GZM z email
    * Użytkownik otwiera w nowej zakładce link do aktywacji
    * Użytkownik wybiera opcje: Zakładam konto imienne
    * Użytkownik uzupełnia dane dla oraz akceptuje regulamin i zapisuje
#    * Użytkownik potwierdza numer telefonu
    * Użytkownik widzi ekran potwierdzający udaną rejestrację
    * Użytkownik klika Przejdź do konta
    * Użytkownik Widzi ekran główny konta