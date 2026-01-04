Finance Application

A command-line interface (CLI) application for personal finance management, developed using Java and PostgreSQL. This project enables users to track income and expenses, calculate balances, and generate transaction reports.
Project Overview

    Transaction Tracking: Log income and expenses with categories, descriptions, and dates.

    Balance Calculation: Real-time calculation of total income, expenses, and current balance.

    Data Persistence: Uses PostgreSQL for reliable data storage.

    Reporting: Filter transactions by specific time periods or view complete history.


Installation & Setup
1. Database Configuration

Ensure PostgreSQL is running and create the required database:
SQL

CREATE DATABASE finansprojektet;

Note: The application handles table creation automatically upon the first run.
2. Application Configuration

Configure the database connection settings:

    Rename src/main/resources/config.properties.example to config.properties.

    Update the file with your local PostgreSQL credentials:

Default Fallback If no config.properties file is found, the application defaults to the following settings:

    Host: localhost:5432

    Database: finansprojektet

    User: postgres

    Password: lösenord

3. Build and Run

Use the Gradle wrapper to build and execute the application:
Bash

# Build the project
./gradlew build

# Run the application
java -cp build/classes/java/main:build/resources/main:build/libs/* Main

Usage

Run the application and use the following commands to manage your finances:

    add – Register a new transaction (Income/Expense).

    list – Display all registered transactions.

    period – Filter transactions by a start and end date.

    balance – Show the current financial summary.

    edit – Modify details of an existing transaction.

    remove transaction – Delete a transaction from the database.

    exit – Save state (if applicable) and terminate the program.

Developed as a course assignment in Java programming.
