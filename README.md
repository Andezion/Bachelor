# Currency Analysis Tool (MIVR)

![GitHub last commit](https://img.shields.io/github/last-commit/IIS-ZPI/ZPI2024_IO_MIVR)
![GitHub issues](https://img.shields.io/github/issues/IIS-ZPI/ZPI2024_IO_MIVR)
![License](https://img.shields.io/badge/license-MIT-blue.svg)

## Introduction

The **Currency Analysis Tool (MIVR)** is a JavaFX desktop application for comprehensive currency exchange trend analysis. It utilizes historical exchange rate data directly from the official NBP API. This tool aids financial analysts, economists, students, and business professionals in statistical evaluation and visual exploration of currency dynamics.

## Features

MIVR offers key functionalities for in-depth currency insights:
* **Statistical Analysis:** Calculate median, mode, standard deviation, and coefficient of variation for selected currencies and time periods.
* **Session Trend Analysis:** Determine upward, downward, and unchanged sessions within specified timeframes.
* **Exchange Rate Trend Visualization:** Generate textual or graphical (histogram) distributions of currency value changes over monthly and quarterly periods.
* **Intuitive GUI:** A user-friendly interface for currency selection, period definition, and result visualization.

## Why This Tool?

MIVR simplifies complex currency data by leveraging reliable NBP API data. As a standalone desktop application, it's lightweight and requires no additional software installations. It's an ideal tool for accurate currency trend analysis without the overhead of complex systems.

## Technical Stack & Design Choices

Developed using **Java** and **JavaFX** for a robust and intuitive desktop experience. Data is fetched in real-time from the **NBP API** (`http://api.nbp.pl/`). The system processes all data in memory, eliminating the need for persistent storage or databases.

## System Architecture

The system involves a User interacting with a Backend System, which communicates with the NBP API. The Backend System performs statistical analysis, analyzes sessions, and views exchange rate trends.

## Getting Started

### Prerequisites
* Java Development Kit (JDK) 8 or higher.
* Stable Internet Connection (for NBP API data).

### Installation & Running
1.  **Clone the repository:** `git clone https://github.com/IIS-ZPI/ZPI2024_IO_MIVR`
2.  **Navigate:** `cd YOUR_REPOSITORY`
3.  **Build (Maven example):** `mvn clean install`
4.  **Run:** `java -jar target/CurrencyAnalysisTool.jar`

## Project Timeline

The project followed a three-sprint agile approach:
* **Sprint 1 (Apr 1 - Apr 16, 2025):** Implementation and testing of "Perform Statistical Analysis" functionality.
* **Sprint 2 (Apr 16 - May 16, 2025):** Implementation and testing of "Analyze Sessions" functionality.
* **Sprint 3 (May 16 - June 28, 2025):** Implementation and testing of "View Exchange Rate Trends" functionality.
* **System Release (June 28 - June 30, 2025):** Final deployment.

## Intended Audience

The system targets financial analysts, economists, academic researchers, university students, and business professionals. Users should have basic computer skills and an understanding of statistical measures and economic trends.

## Assumptions & Dependencies

The system relies on the NBP API remaining stable and available. Users need a stable internet connection. The application is expected to run on modern desktop OS, primarily Windows and Linux.

## License

This project is licensed under the MIT License.

## Contact

[Team Name]
[Email Address]
