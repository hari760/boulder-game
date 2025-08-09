# Boulder Sliding Puzzle

A Java implementation of a sliding boulder puzzle game with a graphical user interface built using Java Swing.

## Overview

This project implements a 2D grid-based puzzle game where players move boulders to reach an exit. Boulders can be horizontal or vertical, with movement constraints based on their orientation. The goal is to strategically move boulders to clear a path and get one boulder to the exit.

## Game Rules

- **Grid**: 2D grid with cells that can be ground, walls, or exits
- **Boulders**: 1 cell wide by 2+ cells long, oriented horizontally or vertically
- **Movement**: 
  - Horizontal boulders can only move left and right
  - Vertical boulders can only move up and down
- **Objective**: Move one boulder to the exit cell
- **Constraints**: Only one boulder can occupy a cell at a time

## Project Structure

```
src/
├── hw3/                    # Main implementation package
│   ├── Boulder.java        # Boulder object representation
│   ├── Board.java          # Game board logic
│   ├── GridUtil.java       # Grid parsing utilities
│   └── Solver.java         # Extra credit: AI solver
├── api/                    # API package (provided)
│   ├── Cell.java
│   ├── Move.java
│   ├── Orientation.java
│   ├── Direction.java
│   └── CellType.java
├── ui/                     # GUI package (provided)
│   ├── GameMain.java       # Main application entry point
│   ├── GamePanel.java
│   └── ScorePanel.java
├── games.txt              # Sample game configurations
└── test files...          # Example test cases
```

## Core Classes

### Boulder
Represents a boulder with position and orientation properties.
- Tracks row/column position of upper-left corner
- Handles movement validation and position updates
- Supports reset to original position

### Board
Main game logic controller managing the game state.
- Maintains 2D cell array and boulder list
- Handles boulder grabbing/releasing mechanics
- Tracks move history and game completion status
- Provides move validation and execution

### GridUtil
Utility class for parsing string-based board descriptions into game objects.
- Converts text representations to Cell arrays and Boulder lists
- Supports various symbols for walls, ground, exits, and boulders

## Board Description Format

The game uses text-based board descriptions with the following symbols:

```
* * * * * * * *
* [ ] ^ ^ . . *
* . . v v [ ] *
* [ # # ] ^ . *
* ^ [ ] . v ^ e
* # ^ . [ ] # *
* # v . [ ] v *
* v [ # # # ] *
* * * * * * * *
```

**Symbol Legend:**
- `*` = Wall
- `e` = Exit
- `.` = Ground
- `[ # # ]` = Horizontal boulder (with zero or more `#` segments)
- `^`, `#`, `v` = Vertical boulder (top, middle, bottom segments)

## How to Run

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- IDE (Eclipse recommended, but any Java IDE works)

### Running the Game
1. Clone or download the project
2. Import into your Java IDE
3. Run `ui.GameMain.java`
4. Use mouse to grab and drag boulders
5. Try to get a boulder to the exit!

### Loading Custom Games
- Use "Choose from file" button in the GUI
- Select game configurations from `games.txt`
- Or edit `GameMain.java` to use different initial board descriptors

## Controls

- **Mouse Click + Drag**: Grab any segment of a boulder and drag to move
- **Release**: Let go of mouse button to release boulder
- Boulders move one cell at a time as you drag

## Features

- **Interactive GUI**: Intuitive drag-and-drop interface
- **Move History**: Track and display move sequences
- **Game State Management**: Reset functionality to return to start
- **Hint System**: Get suggestions for possible moves
- **Multiple Puzzles**: Various difficulty levels and configurations
- **Undo Functionality**: Reverse recent moves
- **Solver** (Extra Credit): Automatic puzzle solving using recursion

## Development Notes

### Testing
- Unit tests are included for core functionality
- Test incrementally - don't rely solely on GUI for debugging
- Focus on testing individual methods and edge cases

### Code Organization
- Core game logic separated from UI
- Clean separation of concerns between classes
- Extensive Javadoc documentation for public methods

### Design Patterns Used
- **Model-View separation**: Game logic (model) separated from GUI (view)
- **State management**: Centralized game state in Board class
- **Factory pattern**: GridUtil creates game objects from descriptions



## Future Enhancements

Potential improvements could include:
- Sound effects and animations
- Level editor for creating custom puzzles
- Multiplayer support
- Performance optimizations for larger grids
- Mobile app version

