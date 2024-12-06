# Portfolio project IDATG1003

STUDENT NAME = "Nick Heggø"

STUDENT ID = "134132"

## Project description

[//]: # (TODO: Write a short description of your project/product here.)
This project is a Meal Planning application developed as a part of the IDATG1003 course.
The application allows users to create, track, remove both Recipes and Ingredients.
The goal of this project was to help achieve the UN's sustainability goal by reducing food waste.

## Project structure

```aiignore
.
├── IDATG1003-Mappe-meal-planning.iml
├── Meal-Planning-Nick-Heggø.iml
├── README.md
├── pom.xml
├── src
│   ├── main
│   │   └── java
│   │       └── edu
│   │           └── ntnu
│   │               └── idi
│   │                   └── bidata
│   │                       ├── Launcher.java
│   │                       ├── user
│   │                       │   ├── User.java
│   │                       │   ├── inventory
│   │                       │   │   ├── Ingredient.java
│   │                       │   │   ├── IngredientStorage.java
│   │                       │   │   ├── InventoryManager.java
│   │                       │   │   └── Measurement.java
│   │                       │   └── recipe
│   │                       │       ├── CookBook.java
│   │                       │       ├── Recipe.java
│   │                       │       ├── RecipeBuilder.java
│   │                       │       ├── RecipeManager.java
│   │                       │       └── Step.java
│   │                       └── util
│   │                           ├── AbortException.java
│   │                           ├── Application.java
│   │                           ├── InputScanner.java
│   │                           ├── OutputHandler.java
│   │                           ├── Utility.java
│   │                           ├── command
│   │                           │   ├── AddCommand.java
│   │                           │   ├── Command.java
│   │                           │   ├── CommandRegistry.java
│   │                           │   ├── FindCommand.java
│   │                           │   ├── GoCommand.java
│   │                           │   ├── HelpCommand.java
│   │                           │   ├── IllegalCommandCombinationException.java
│   │                           │   ├── ListCommand.java
│   │                           │   ├── RemoveCommand.java
│   │                           │   ├── StatsCommand.java
│   │                           │   ├── SubcommandRegistry.java
│   │                           │   └── ValidCommand.java
│   │                           ├── input
│   │                           │   ├── CommandInput.java
│   │                           │   └── UnitInput.java
│   │                           └── unit
│   │                               ├── UnitConverter.java
│   │                               ├── UnitRegistry.java
│   │                               └── ValidUnit.java
│   └── test
│       └── java
│           └── edu
│               └── ntnu
│                   └── idi
│                       └── bidata
│                           ├── user
│                           │   └── inventory
│                           │       ├── IngredientStorageTest.java
│                           │       └── IngredientTest.java
│                           └── util
│                               ├── InputScannerTest.java
│                               ├── command
│                               │   └── RemoveCommandTest.java
│                               └── unit
│                                   └── UnitConverterTest.java
```

[//]: # (TODO: Describe the structure of your project here. How have you used packages in your structure. Where are all sourcefiles stored. Where are all JUnit-test classes stored. etc.)
The project is divided into two parts, package `user` && `util`.

* In the package `user` you can find all relevant classes that are connected with class User.
* In the package `util` you can find all the related classed related to the application's helper class and command
  layer.

## Link to repository

[GitHub Repository](https://github.com/NTNU-BIDATA-IDATG1003-2024/meal-planning-nickhe-ntnu.git)

## How to run the project

![](run.gif)

The `main()` method is called from the class `Launcher`,
where it creates a `new Application` and run the `run()` method.

This project uses [Maven](https://maven.apache.org/) as the project build system.
Running this project will require Maven to be installed on the system.
(Developed with Maven version 3.9.9)

1. Go to the project directory.

```bash
cd .
```

2. Run the following command in the terminal to build and run the project.

```bash
mvn -q clean install && mvn -q exec:java
```

## How to run the tests

![unitTest](unitTest.gif)

Maven allows simply running:

```bash
mvn test
```

while in the project root directory and will automatically run all the test files.

## References

[//]: # (TODO: Include references here, if any. For example, if you have used code from the course book, include a reference to the chapter. Or if you have used code from a website or other source, include a link to the source.)

### Builder design pattern

[![Builder design pattern](https://refactoring.guru/images/patterns/diagrams/builder/example-en-2x.png)](https://refactoring.guru/design-patterns/builder)

The Builder pattern allows for a flexible solution for scenarios involving multiple steps and varied components,
such as recipe creation.
[Refactoring Guru](https://refactoring.guru/design-patterns/builder)

### Command design pattern

[![Command design pattern](https://refactoring.guru/images/patterns/diagrams/command/example-2x.png)](https://refactoring.guru/design-patterns/command)

Command design pattern helped me to fully understand the inheritance,
abstract classes, and methods which are the core of this project.
[Refactoring Guru](https://refactoring.guru/design-patterns/command)
