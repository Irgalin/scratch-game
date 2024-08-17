# Scratch Game Application

This application is a scratch game that, based on a given configuration and betting amount, generates a matrix (for
example, 3x3) from symbols (based on probabilities for each individual cell) and calculates winning combinations and the
final reward.

## Prerequisites

- JDK 1.8 or higher

## Building the Application

Clone the repository and navigate to the project directory:

```sh
cd path/to/scratch-game
```

To build the application, run the following command:

```sh
./gradlew build
```

It will run all the tests and create an executable JAR file.
The executable JAR file will be stored in the `build/libs` directory with the name `scratch-game-1.0.0.jar`.

## Running the Application

To run the application, you need to provide the following arguments:

- `-c` or `--config`: Path to the configuration file (must be a valid path to a JSON file)
- `-b` or `--betting-amount`: Betting amount (must be a positive integer)

Example command:

```sh
java -jar build/libs/scratch-game-1.0.0.jar --config path/to/config.json --betting-amount 100
```

There is an example configuration file stored in the root project directory: `config.json`.

Example output:

```sh   
{
  "matrix" : [
    ["A", "A", "F"],
    ["C", "10x", "F"],
    ["E", "E", "E"]
],
  "reward" : 2400.0,
  "applied_winning_combinations" : {
    "E" : [ "same_symbol_3_times", "same_symbols_horizontally" ]
  },
  "applied_bonus_symbol" : "10x"
}
```