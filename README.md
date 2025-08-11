---

<p align="center">
<b>All data seen in demo is dummy data (fake) for demonstration purposes</b>
</p>

---

# Arcade Game Simulator (A.G.M) - UEA Module Coursework


[![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/en/)

## Purpose:

This project was created as part of my programming assessment for the UEA Module. The goal was to design and build an "Arcade Game Simulator" to showcase fundamental programming concepts, OOP, and software design. I am aiming to produce a simulator that parses text documents and produces various game objects that could easily be extended with new games objects of various types from cabinet games to VR. I made modular codebase that allowed for rapid prototyping with java unit testing.

---

# UEA Project Arcade Simulator

Arcade Game Simulator (A.G.M) is a Java application that simulates a real-world arcade. Users (customers) interact with various types of arcade games, manage their balances, and experience the practical effects of inheritance and exception handling in a modular codebase.

---

## Tech Stack

| Layer         | Technology         | Purpose                                          |
| ------------- | ------------------| ------------------------------------------------ |
| **Core**      | Java              | Core game logic, player management               |
| **Database**  | Flat File Storage | Persisting scores and player data                |
| **Testing**   | Java unit testing | Unit tests for core modules                      |

---

## Feature Breakdown

### Core Simulation Features

<details open>
  <summary><b>Object-Oriented Game System</b></summary>
  <ul>
    <li>Models multiple types of arcade games using class inheritance:
      <ul>
        <li><b>CabinetGame</b>: classic cabinet games with optional reward payouts</li>
        <li><b>ActiveGame</b>: games with minimum age restrictions</li>
        <li><b>VirtualRealityGame</b>: VR games with equipment requirements and age restrictions</li>
      </ul>
    </li>
    <li>Game type determines available features and restrictions, demonstrating OOP inheritance and polymorphism</li>
    <li>Games are parsed from configuration files, showing dynamic instantiation and extensibility</li>
  </ul>
</details>

---

<details open>
  <summary><b>Customer Accounts & Balance Management</b></summary>
  <ul>
    <li>Each customer has a unique ID, name, age, balance, and discount type (Staff, Student, None)</li>
    <li>Balances can be topped up, and funds are deducted when playing games</li>
    <li>Student accounts feature a special overdraft, allowing their balance to drop to -£5.00 before restricting further play</li>
    <li>Discounts are automatically applied based on customer type and whether usage is peak/off-peak</li>
    <li>Robust validation for customer creation (ID format, initial balance, etc.)</li>
  </ul>
</details>

---

<details open>
  <summary><b>Transaction and Simulation Engine</b></summary>
  <ul>
    <li>Reads customer, game, and transaction data from files to simulate real-world arcade interactions</li>
    <li>Supports creating new customers, adding funds, and simulating game play transactions</li>
    <li>Transaction handling enforces:
      <ul>
        <li>Age restrictions for game types</li>
        <li>Sufficient balance (including overdraft logic for students)</li>
        <li>Discounts based on customer type and time of play</li>
      </ul>
    </li>
    <li>All transactions and failures (e.g., insufficient funds, age restriction, invalid IDs) are logged with clear summaries</li>
    <li>Exception handling demonstrates real-world error management in OOP systems</li>
  </ul>
</details>

---

<details open>
  <summary><b>Statistics and Reporting</b></summary>
  <ul>
    <li>Finds the richest customer based on account balance</li>
    <li>Calculates the median price of all games in the arcade</li>
    <li>Counts and reports the number of games by type (Cabinet, Active, VR)</li>
    <li>Tracks total arcade revenue</li>
    <li>Prints corporate liability statements for demonstration</li>
  </ul>
</details>

---

## Project Structure

<details open>
  <summary><b>File Structure</b></summary>

```

📁 UEA-Project-Arcade-Simulator
├── 📁 src                             # Source code files
│   ├── 📁 arcade                      # Core arcade game classes
│   │   ├── ActiveGame.java
│   │   ├── Arcade.java
│   │   ├── ArcadeGame.java
│   │   ├── CabinetGame.java
│   │   ├── EquipmentType.java
│   │   └── VirtualRealityGame.java
│   │
│   ├── 📁 customer                    # Customer-related classes and enums
│   │   ├── Customer.java
│   │   └── DiscountType.java
│   │
│   ├── 📁 exceptions                  # Custom exception classes
│   │   ├── AgeLimitException.java
│   │   ├── InsufficientBalanceException.java
│   │   ├── InvalidCustomerException.java
│   │   └── InvalidGameIdException.java
│   │
│   └── 📁 simulation                  # Simulation controller
│       └── Simulation.java
│
├── .gitignore                        # Git ignore rules
├── Assessment 2.iml                  # IntelliJ project/module file
├── customers.txt                    # Customer data storage
├── games.txt                        # Arcade games data
└── transactions.txt                 # Transaction logs and data

```
</details>
