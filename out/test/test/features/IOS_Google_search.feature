Feature: [IOS]

Background:
  When Użytkownik uruchamia aplikację IOS_APP na telefonie iOS 00008110-001A682801C2801E

  Scenario:[IOS] Google Search
    When I go to page google.pl
    And I ensure application opened
    And I closed cookie

