Feature:[ANDROID]

  Background:
    When Użytkownik uruchamia aplikację TRANSPORT_GZM_ANDROID na telefonie Android R58M94EZL1T
@RUN
  Scenario:[ANDROID] Logowanie - konto ANONIMOWE
    * [ANDROID] Przechodzę na ekran użytkownika
    * [ANDROID] Klikam na przycisk Zaloguj się
    * [ANDROID] Weryfikuję czy strona logowania wczytała się poprawnie
    * [ANDROID] Użytkownik wypełnia dane logowania i klika na przycisk Zaloguj się
    * [ANDROID] Użytkownik zostaje zalogowany
    * [ANDROID] Przechodzę na ekran użytkownika
    * [ANDROID] Użytkownik klika wyloguj
