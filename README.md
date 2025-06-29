# Currency Analysis Tool (MIVR)

![GitHub last commit](https://img.shields.io/github/last-commit/YOUR_USERNAME/YOUR_REPOSITORY)
![GitHub issues](https://img.shields.io/github/issues/YOUR_USERNAME/YOUR_REPOSITORY)
![License](https://img.shields.io/badge/license-MIT-blue.svg)

## Introduction

The **Currency Analysis Tool (MIVR)** is a JavaFX desktop application for comprehensive currency exchange trend analysis. [cite_start]It utilizes historical exchange rate data directly from the official NBP API[cite: 5]. [cite_start]This tool aids financial analysts, economists, students, and business professionals in statistical evaluation and visual exploration of currency dynamics[cite: 3].

## Features

MIVR offers key functionalities for in-depth currency insights:
* [cite_start]**Statistical Analysis:** Calculate median, mode, standard deviation, and coefficient of variation for selected currencies and time periods[cite: 8, 24].
* [cite_start]**Session Trend Analysis:** Determine upward, downward, and unchanged sessions within specified timeframes[cite: 7, 28].
* [cite_start]**Exchange Rate Trend Visualization:** Generate textual or graphical (histogram) distributions of currency value changes over monthly and quarterly periods[cite: 9, 14, 33, 38].
* [cite_start]**Intuitive GUI:** A user-friendly interface for currency selection, period definition, and result visualization[cite: 39, 40, 41].

## Why This Tool?

[cite_start]MIVR simplifies complex currency data by leveraging reliable NBP API data[cite: 11, 12, 50]. [cite_start]As a standalone desktop application, it's lightweight and requires no additional software installations[cite: 44, 51]. [cite_start]It's an ideal tool for accurate currency trend analysis without the overhead of complex systems[cite: 11].

## Technical Stack & Design Choices

Developed using **Java** and **JavaFX** for a robust and intuitive desktop experience. [cite_start]Data is fetched in real-time from the **NBP API** (`http://api.nbp.pl/`)[cite: 45]. [cite_start]The system processes all data in memory, eliminating the need for persistent storage or databases[cite: 45, 52].

## System Architecture

The system involves a User interacting with a Backend System, which communicates with the NBP API. [cite_start]The Backend System performs statistical analysis, analyzes sessions, and views exchange rate trends[cite: 16].

## Getting Started

### Prerequisites
* Java Development Kit (JDK) 8 or higher.
* [cite_start]Stable Internet Connection (for NBP API data)[cite: 47, 59].

### Installation & Running
1.  **Clone the repository:** `git clone https://github.com/YOUR_USERNAME/YOUR_REPOSITORY.git`
2.  **Navigate:** `cd YOUR_REPOSITORY`
3.  **Build (Maven example):** `mvn clean install`
4.  **Run:** `java -jar target/CurrencyAnalysisTool.jar`

## Project Timeline

[cite_start]The project followed a three-sprint agile approach[cite: 68]:
* [cite_start]**Sprint 1 (Apr 1 - Apr 10, 2025):** Implementation and testing of "Perform Statistical Analysis" functionality[cite: 69, 70].
* [cite_start]**Sprint 2 (Apr 10 - Apr 16, 2025):** Implementation and testing of "Analyze Sessions" functionality[cite: 71, 72].
* [cite_start]**Sprint 3 (Apr 16 - Apr 28, 2025):** Implementation and testing of "View Exchange Rate Trends" functionality[cite: 73, 74].
* [cite_start]**System Release (Apr 28 - Apr 30, 2025):** Final deployment[cite: 75].

## Intended Audience

[cite_start]The system targets financial analysts, economists, academic researchers, university students, and business professionals[cite: 4]. [cite_start]Users should have basic computer skills and an understanding of statistical measures and economic trends[cite: 54, 55, 61].

## Assumptions & Dependencies

[cite_start]The system relies on the NBP API remaining stable and available[cite: 57, 58]. [cite_start]Users need a stable internet connection[cite: 59]. [cite_start]The application is expected to run on modern desktop OS, primarily Windows and Linux[cite: 46, 60].

## License

This project is licensed under the MIT License.

## Contact

[Your Name/Team Name]
[Your Email Address]
