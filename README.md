# Neural Networks in Java

This repository contains a Java implementation of artificial neural networks (ANNs) from scratch. It provides a hands-on approach to understanding and using neural networks in Java applications, with a focus on efficiency, adaptability, and practical functionality.

## Overview

This project demonstrates the creation of a fast and efficient neural network capable of recognizing handwritten digits and adaptable to other tasks. It includes foundational mathematics such as linear algebra, calculus, and information theory to support ANN implementation and training.

## Key Features

- **Core Neural Network Implementation**: Build and train neural networks using custom Java code.
- **Matrix Operations**: Leverage linear algebra for efficient computations.
- **Backpropagation Algorithm**: Train networks with the fundamental algorithm for minimizing error.
- **Performance Evaluation**: Use loss functions and information theory concepts to measure network accuracy.
- **Adaptability**: Easily modify the network for categorization and other tasks.
- **Handwritten Digit Recognition**: Train the network using the MNIST dataset.

## Project Structure

The project is organized as follows:

```
neuralnetwork/
├── pom.xml            # Maven build file
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── Hansra7/
│   │       │   ├── calculus/      # Mathematical operations
│   │       │   ├── matrix/        # Matrix computations
│   │       │   └── neuralnetwork/ # Core neural network implementation
│   │       └── hansra/            # Application entry points
│   └── test/                      # Test cases (if any)
├── target/            # Build output directory
└── RandomVisualiser/  # Visualization-related resources
```

### Key Files

- **Hansra7.neuralnetwork**:
  - `NeuralNetwork.java`: Implements the core neural network functionality.
  - `Engine.java`: Manages training and evaluation processes.
  - `LossFunctions.java`: Defines loss functions for performance measurement.
- **Hansra7.matrix**:
  - Provides matrix computations essential for efficient neural network operations.
- **Hansra7.calculus**:
  - Contains calculus utilities for backpropagation.
- **hansra.App.java**:
  - Entry point for running the application.
- **pom.xml**:
  - Configures Maven dependencies and build process.

## Prerequisites

- Basic understanding of Java programming
- Familiarity with linear algebra and calculus (optional but helpful)

## How to Build and Run the Project

1. Clone this repository:
   ```bash
   git clone https://github.com/your-username/neural-networks-in-java.git
   cd neural-networks-in-java
   ```
2. Build the project using Maven:
   ```bash
   mvn clean install
   ```
3. Run the main application:
   ```bash
   java -cp target/neuralnetwork-1.0-SNAPSHOT.jar hansra.App
   ```

## Tools and Technologies

- **Programming Language**: Java
- **Build Tool**: Maven
- **Development Environment**: Any IDE supporting Java and Maven (e.g., IntelliJ IDEA, Eclipse)
- **Data**: MNIST handwritten digit database (freely available)

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
