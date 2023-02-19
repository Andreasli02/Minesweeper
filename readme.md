Minesweeper game I made in IT1901 Informatics, Project I with 3 others. The Minesweeper game is on the client, with highscores being saved to a server.

<img width="887" alt="minesweeper" src="https://user-images.githubusercontent.com/89947900/219943558-ce07b9eb-71e5-494b-8b91-4c23a112f3a4.PNG">

----
[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2243/gr2243) 

# Group gr2243 repository 

Kodeprosjektet er i minesweeper-mappen. Her finner man pom.xml-filen, men man finner også de tre hovedmodulene core, server og ui. 

### Core
Core inneholder kjerneklassene som styrer logikken og lagringen i prosjektet. I src finner man en main mappe som inneholder kjerneklassene og fillagringsklassen, og i test mappen finner man tester for lagring og logikk.

### Server
Server inneholder rest-APIet for appen, og er mellomleddet mellom lagringen og applikasjonen. i src finner man en main- og en test-mappe. I main finner man minesweeper.server pakken som inneholder APIet. I test-mappa finner man tester for minesweeper.server pakken.

### UI
Ui inneholder frontendklassene som styrer fxml filen, og bruker kjerneklassene til å kjøre prosjektet. I src finner man main mappe som inneholder selve app klassen, kontrollerklassen, og view klasse som håndterer bildene som skal settes inn i appen. Man finner også en testmappe med tester som tester om logikken og frontenden spiller sammen på måten de var laget for.

I docs-mappen finner man dokumenter med informasjon om hver enkelt release. 

## Mappe-hierarki:
minesweeper/ (kodeprosjekt)
-
- config/
    - checkstyle/
        - sun_checks.xml
    - sputbugs/
        - exclude.xml
- [core/](/minesweeper/core/) (Kjernemodul)
    - src/
        - main/
            - java/
                - minesweeper/core/
                    - Action.java
                    - Difficulty.java
                    - HighscoreEntry.java
                        - Lager et objekt som inneholder navn og poeng.
                    - HighscoreList.java
                        - Tar inn liste med HighscoreEntry og en maks størelse.
                    - Minefield.java
                        - klasse som inneholder logikken til selve griddet
                    - Minesweeper.java
                    - Square.java
                        - Her lagres verdien til en rute.
                - minesweeper/json/
                    - internal/
                        - HighscoreListDeserializer.java
                        - HighscoreListSerializer.java
                        - HighscoreEntryDeserializer.java
                        - HighscoreEntrySerializer.java
                    - HighscoresFileHandler.java
                        - Utfører alle lagringsrelaterte oppgaver, og lagrer dataen i highscoreList.json.
                - module-info.java
        - test/java/minesweeper/
            - core/ (Teste kjerneklassene)
                - HighscoreListTest.java
                    - Tester HighscoreList klassen.
                - MinefieldTest.java
                    - Tester Minefield klassen.
                - MinesweeperTest.java
                - SquareTest.java
                    - Tester Square klassen.
            - json/ (Teste lagringsklassene)
                - HighscoresFileHandlerTest.java
                    - Tester Lagring av fil.
    - pom.xml
- [server/](/minesweeper/server/) (Rest-api-modul)
    - src/
        - main/java/
            - minesweeper/server/
                - HighscoreApplication.java
                - HighscoreController.java
                - HighscoreService.java
            - module-info.java
        - test/java/minesweeper/server/
            - MinesweeperApplicationTest.java
    - pom.xml
- [ui/](/minesweeper/ui/) (Frontendmodul)
    - src/
        - main/
            - java/
                - minesweeper/ui/
                    - highscoreAccess.java
                    - HigscoreController.java
                    - HighscoreView.java
                    - MinefieldView.java
                        - Brukes til å vise frem bilder i appen.
                    - MinesweeperApp.java
                        - Initialiser app.
                    - MinesweeperController.java
                        - Kontrolerer appens funksjonalitet.
                    - SceneManager.java
                    - Timer.java
                - module-info.java
            - resources/minesweeper/ui (fxml, stil og bilder)
                - happy-face.png
                - sad-face.png
                - flag.png
                - bomb.png
                - opened0.png
                - opened1.png
                - opened2.png
                - opened3.png
                - opened4.png
                - opened5.png
                - opened6.png
                - opened7.png
                - opened8.png
                - Minesweeper.fxml
                - Highscores.fxml
                - style.css
        - test/java/minesweeper/ui (Teste funksjonalitet til app)
            - MinefieldViewTest.java
                - Tester ut funksjonaliteten til appen.
            - HighscoreAccessTest.java
            - HighscoresViewTest.java
            - TimerTest.java
    - pom.xml
- pom.xml
- .gitignore
- README.md

## Oppsett
1. Clone prosjektet fra [her](https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2243/gr2243.git), eller åpne i gitpod (lenke ligger øverst i [README](README.md)).
2. For å bygge kjør `mvn install` fra rot-prosjektet. Dette vil kjøre tester og installere pakker som trengs for prosjektet.
3. Start serveren ved å kjøre `mvn spring-boot:run -f server/pom.xml`, eller `cd server` -> `mvn spring-boot:run`.
4. Kjør `mvn javafx:run -f ui/pom.xml`, eller `cd ui` -> `mvn javafx:run`.
5. For tester kjør `mvn test` fra rot-prosjektet.

## Verktøy for kodekvalitet
Vi bruker tre verktøy knyttet til kodekvalitet. Alle tre kan bli kjørt ved å kjøre `mvn verify` fra rot-prosjektet.
Ellers kan de bli kjørt for seg selv slik:
- [Checkstyle](https://checkstyle.sourceforge.io) -> `mvn checkstyle:check`
- [Spotbugs](https://spotbugs.github.io) -> `mvn spotbugs:check`
- [Jacoco](https://www.jacoco.org) -> `mvn jacoco:report`

## JLink og JPackage
Begge kommandoene kjøres fra minesweeper/ui/. Altså må kommandoen `cd minesweeper/ui` skrives. 
### JLink
For å lage en zip-fil med prosjektet må kommandoen `mvn javafx:jlink` kjøres.
Zip filen kommer her: minesweeper/ui/target/minesweeeper.zip.
### JPackage
For å laste ned applikasjonen som en app, må kommandoen `mvn jpackage:jpackage` kjøres.
Da kommer en installer i minesweeper/ui/target/dist/
