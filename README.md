# Currency Analysis Tool (MIVR)

![GitHub last commit](https://img.shields.io/github/last-commit/YOUR_USERNAME/YOUR_REPOSITORY)
![GitHub issues](https://img.shields.io/github/issues/YOUR_USERNAME/YOUR_REPOSITORY)
![License](https://img.shields.io/badge/license-MIT-blue.svg)

## Introduction

The **Currency Analysis Tool (MIVR)** is a JavaFX desktop application designed for comprehensive currency exchange trend analysis. [cite_start]It retrieves historical exchange rate data directly from the official NBP API[cite: 5]. [cite_start]This tool empowers financial analysts, economists, students, and business professionals to perform statistical analysis and visual exploration of currency dynamics[cite: 3, 4, 5].

## Features

MIVR offers key functionalities for in-depth currency insights:

* [cite_start]**Statistical Analysis:** Calculate median, mode, standard deviation, and coefficient of variation for selected currencies and time periods[cite: 8].
* [cite_start]**Session Trend Analysis:** Determine upward, downward, and unchanged sessions within specified timeframes[cite: 7].
* [cite_start]**Exchange Rate Trend Visualization:** Generate textual or graphical (histogram) distributions of currency value changes over monthly and quarterly periods[cite: 9, 14].
* [cite_start]**Intuitive GUI:** A user-friendly interface for currency selection, period definition, and result visualization[cite: 39, 40, 41].

## Why This Tool?

[cite_start]MIVR simplifies complex currency data, leveraging official NBP API data for reliability[cite: 12, 18]. [cite_start]As a standalone desktop application, it's lightweight and requires no additional software installations[cite: 44, 46, 51]. It's ideal for anyone needing direct, accurate currency trend analysis without the overhead of complex systems.

## Technical Stack & Design Choices

[cite_start]Developed using **Java** and **JavaFX** for a robust and intuitive desktop experience[cite: 44]. [cite_start]Data is fetched in real-time from the **NBP API** (`http://api.nbp.pl/`)[cite: 45]. [cite_start]The system processes all data in memory, eliminating the need for persistent storage or databases[cite: 45, 52].

## Getting Started

### Prerequisites

* Java Development Kit (JDK) 8 or higher.
* Stable Internet Connection (for NBP API data).

### Installation & Running

1.  **Clone the repository:** `git clone https://github.com/YOUR_USERNAME/YOUR_REPOSITORY.git`
2.  **Navigate:** `cd YOUR_REPOSITORY`
3.  **Build (Maven example):** `mvn clean install`
4.  **Run:** `java -jar target/CurrencyAnalysisTool.jar`

## Project Timeline

The project followed a three-sprint agile approach:

* [cite_start]**Sprint 1 (Apr 1 - Apr 10, 2025):** Implementation and testing of "Perform Statistical Analysis"[cite: 69, 70].
* [cite_start]**Sprint 2 (Apr 10 - Apr 16, 2025):** Implementation and testing of "Analyze Sessions"[cite: 71, 72].
* [cite_start]**Sprint 3 (Apr 16 - Apr 28, 2025):** Implementation and testing of "View Exchange Rate Trends"[cite: 73, 74].
* [cite_start]**System Release (Apr 28 - Apr 30, 2025):** Final deployment[cite: 75].

## Intended Audience

[cite_start]Financial analysts, economists, academic researchers, university students, business professionals, and finance-curious individuals[cite: 3, 4, 53]. [cite_start]Users should have basic computer skills and an understanding of statistical measures and economic trends[cite: 54, 55].

## Assumptions & Dependencies

[cite_start]Relies on the NBP API remaining stable and available[cite: 57, 58, 64]. [cite_start]Users need a stable internet connection[cite: 47, 59]. [cite_start]The application is expected to run on modern desktop OS (Windows, Linux)[cite: 60, 67].

## License

This project is licensed under the MIT License.

## Contact

[Your Name/Team Name]
[Your Email Address]
