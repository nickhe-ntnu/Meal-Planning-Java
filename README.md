## Project description

This project is a Meal Planning application developed as a part of the IDATG1003 course.
The application allows users to create, track, remove both Recipes and Ingredients.
The goal of this project was to help achieve the UN's sustainability goal by reducing food waste.

## Project structure

```aiignore
.
├── README.md
├── pom.xml
├── run.gif
├── src
│   ├── main
│   │   └── java
│   │       └── edu
│   │           └── ntnu
│   │               └── idi
│   │                   └── bidata
│   │                       ├── Launcher.java
│   │                       ├── user
│   │                       │   ├── Printable.java
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
│   │                           │   ├── ClearCommand.java
│   │                           │   ├── Command.java
│   │                           │   ├── CommandRegistry.java
│   │                           │   ├── ExitCommand.java
│   │                           │   ├── FindCommand.java
│   │                           │   ├── GoCommand.java
│   │                           │   ├── HelpCommand.java
│   │                           │   ├── IllegalCommandCombinationException.java
│   │                           │   ├── ListCommand.java
│   │                           │   ├── RemoveCommand.java
│   │                           │   ├── StatsCommand.java
│   │                           │   ├── UnknownCommand.java
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
├── unitTest.gif
└── version.png

26 directories, 46 files
```

In a Maven project, the source code and unit tests are kept in different directories.
The source code is located in `src/main`, while test code is located in `src/test`.

Project source code is divided into two parts, package `user` && `util`.

* In the package `user` you can find all the relevant classes that have dependency on the `User` class.
* In the package `util` you can find all the application's helper class and command
  layer.

## Link to repository

[GitHub Repository](https://github.com/NTNU-BIDATA-IDATG1003-2024/meal-planning-nickhe-ntnu.git)
[BackUP Repository](https://github.com/nickhe-ntnu/Meal-Planning-Java.git)

## How to run the project

This project requires [Java SE 21](https://whichjdk.com/),
and [Apache Maven 3.9.x](https://maven.apache.org/) to be installed on the system.

![](version.png)
Before running any commands, check if the correct version is installed on the system by typing:

```bash
java --version
```

and

```bash
mvn --version
```

![](run.gif)

If everything looks good, we can move on to build the project from source.

The `main()` method is called from the class `Launcher`,
where it creates a `new Application` and invoke the `run()` method.

1. Go to the project directory.

```bash
cd {{PATH_TO_ROOT_DIRECTORY}}
```

2. Run the following command in the terminal to build and run the project.

```bash
mvn -q clean install && mvn -q exec:java
```

## How to run the tests

![](unitTest.gif)

When using `mvn install` Maven already automatically ran all the test files before building from source.
However, if one wishes to only run the test files,
Maven allows simply running

```bash
mvn test
```

in the terminal while in the project root directory,
and it will run all the test files within the project.

## References

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
