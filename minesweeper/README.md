# Minesweeper

Beskrivelse
- 
Minesweeper er et single-player logikkbasert spill. Man spiller på et rutefelt, og målet er å ha trykket på alle rutene uten miner. Hvis man trykker på en mine, taper man. Når man åpner trygge ruter, står det hvor mange miner som ligger rundt den ruta. Hvis det står 8, betyr det at alle rutene rundt inneholder en mine. Hvis det er 0 (vist som en blank rute), er alle rutene rundt trygge. Hvis du mistenker at en rute inneholder en mine, kan du markere den med et flagg. Du får poengsum ut ifra hvor fort du løser brettet.

Poengsummen kan settes på poengtavlen med navnet ditt.

![minesweeper bilde](/minesweeper/ui/src/main/resources/minesweeper/ui/minesweeper.png)

Under finner man et pakkediagram.

```plantuml
@startuml klassediagram

component core {
	package minesweeper.core
	package minesweeper.json {
	  package minesweeper.json.internal
	}
}

minesweeper.core ..> minesweeper.json
minesweeper.json ..> minesweeper.json.internal

component jackson {
}
minesweeper.json ..> jackson
minesweeper.json.internal ..> jackson


component server {
  package minesweeper.server
}

minesweeper.server ..> minesweeper.core
minesweeper.server ..> minesweeper.json

component spring_boot {
}
server ..> spring_boot


component ui {
	package minesweeper.ui
}

minesweeper.ui ..> minesweeper.core

component javafx {
	component fxml {
	}
}
ui ..> javafx
ui ..> fxml

@enduml
```


Her kan man se et klassediagram som illustrerer de viktigste klassene til et viktig bruksområde.
Klassene under er de som blir brukt mens man spiller Minesweeper-spillet.

```plantuml
@startuml classDiagram

Timer "1" <-- MinesweeperController
Difficulty "1" <-- MinesweeperController
MinesweeperView "1"<-- MinesweeperController
Minesweeper "1" <-- MinesweeperController
Minefield "1" <-- Minesweeper
Action "*" <-- Minesweeper
Action "*" <-- Timer
Square "*" <-- Minefield

class Timer {
    - List<Action>: onSecondAction
    - boolean: startTimeIsSet
    - long: startTime
    - double: NANO_SECONDS_IN_SECOND
    - int: seconds
    + void: handle()
    + void: addOnSecond()
    - void: onSecond()
    + int: getSeconds()
}

enum Difficulty {
    - int: width
    - int: height
    - int: mineCount
    - String: name
    + int: getWidth()
    + int: getHeight()
    + int: getMineCount()
    + String: getName()
    + Difficulty: getDifficulty()
}

class MinesweeperController{
    - SceneManager: sceneManager
    - Minesweeper: minesweeper
    - HighscoresAccess: access
    - Difficulty: difficulty
    - Timer: timer
    - MinefieldView: minefieldView
    - void: changeDifficulty()
    - void: setUpMinesweeper()
    - void: setUpMinefieldView()
    - void: setUpTimer()
    + void: restart()
    - void: handleRestart()
    - void: handleClickedSquare()
    - void: handleLeftClickedSquare()
    - void: openSquare()
    - void: handleRightClickedSquare()
    - void: handleSpacebarOnSquare()
    - void: chord()
    - void: toggleFlag()
    - void: saveScore()
    - void: handleWin()
    - void: handleLoss()
    - void: handleInput()
    - void: handleLoss()
    + void: setSceneManager()
    - void: showHighscores()
    + void: onKeyReleased()
    - void: alertCouldNotReachServer()
}

class MinefieldView {
    - int: width
    - int: height
    - List<List<ImageView>>: squareImages
    - List<List<Button>>: squareButtons
    - Image: bombImage
    - Image: flagImage
    - Image[]: openedSquareImages
    - void: createButtons()
    - void: createImages()
    - ImageView: getImageView()
    - Button: getButton()
    + void: bindGridPane()
    + void: setOnMouseRelease()
    + void: setBombImage()
    + void: setFlagImage()
    + void: setBlankImage()
    + void: setOpenedSquareImage()
    + void: showLoss()
    + Integer[]: hoveredSquare()
}

class Minesweeper {
    - Minefield: minefield
    - List<Action>: onWinActions
    - List<Action>: onLossActions
    - List<Action>: onStartActions
    - boolean: gameIsStarted
    - int: mineCount
    - int: openedSquares
    - int: flagCount
    + boolean: hasMine()
    + void: toggleFlag()
    + boolean: isFlagged()
    + int: flagsLeft()
    + void: openSquare()
    - boolean: allSafeSquaresAreOpened()
    + boolean: squareIsOpened()
    + void: addOnWin()
    + void: addOnLoss()
    + void: addOnStart()
    - void: win()
    - void: lose()
    - void: start()
    + int: getWidth()
    + int: getHeight()
    + int: getAdjacentMines()
    + Integer[][]: safeSquaresAround()
}

interface Action {
    void: run()
}

class Minefield {
    - List<List<Square>>: squares
    - int: width
    - int: height
    + Square: getSquare()
    + void: setSquare()
    + boolean: isOutOfBounds()
    + void: toggleFlag()
    + boolean: isFlagged()
    + void: placeMine()
    + boolean: hasMine()
    + void: initializeMines()
    - int: safeSquareCount()
    - boolean: isSafeSquare()
    + void: openSquare()
    + boolean: squareIsOpened()
    + int: getWidth()
    + int: getHeight()
}

class Square {
    - boolean: isFlagged
    - boolean: hasMine
    - boolean: isOpened
    + void: toggleFlag()
    + boolean: isFlagged()
    + void: setMine()
    + boolean: hasMine()
    + void: open()
    + boolean: isOpened()
}

@enduml
```


Til slutt er det laget et sekvensdiagram som skal illustrere sekvenser ved et vanlig brukstilfelle.

Det kan finnes [her](/docs/sequence-diagram.md)
