Feature: Kursy walut
  Background:
    When Użytkownik przygotowuje konfigurację pod testy API
  @API
  Scenario: Kursy walut
    Given Pobierz kursy walut z tabeli A
    When Wyświetl kurs dla waluty o kodzie: USD
    And Wyświetl kurs dla waluty o nazwie: dolar amerykański
    And Wyświetl waluty o kursie powyżej: 5
    And Wyświetl waluty o kursie poniżej: 3