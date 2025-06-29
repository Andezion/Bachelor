# Currency Analysis Tool (MIVR)

![GitHub last commit](https://img.shields.io/github/last-commit/YOUR_USERNAME/YOUR_REPOSITORY)
![GitHub issues](https://img.shields.io/github/issues/YOUR_USERNAME/YOUR_REPOSITORY)
![License](https://img.shields.io/badge/license-MIT-blue.svg)

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Why This Tool?](#why-this-tool)
- [Technical Stack & Design Choices](#technical-stack--design-choices)
- [System Architecture](#system-architecture)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Running the Application](#running-the-application)
- [Project Timeline](#project-timeline)
- [Intended Audience](#intended-audience)
- [Assumptions & Dependencies](#assumptions--dependencies)
- [Future Enhancements](#future-enhancements)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## Introduction

[cite_start]The **Currency Analysis Tool (MIVR)** is a robust and intuitive desktop application designed to provide comprehensive insights into currency exchange trends[cite: 2]. [cite_start]Built with Java and JavaFX, this tool simplifies the process of analyzing historical exchange rate data sourced directly from the official NBP (National Bank of Poland) API[cite: 5, 12]. [cite_start]Whether you're a financial analyst, an academic researcher, or a student, MIVR offers an efficient platform for statistical evaluation and visual exploration of currency dynamics[cite: 5, 11].

## Features

MIVR offers a powerful set of functionalities to help users understand currency behavior:

* [cite_start]**Statistical Analysis:** Calculate key statistical measures such as median, mode, standard deviation, and coefficient of variation for selected currencies and time periods[cite: 8, 21, 24].
* [cite_start]**Session Trend Analysis:** Determine the number of upward, downward, and unchanged sessions within specified timeframes, providing insights into daily exchange rate fluctuations[cite: 7, 28].
* [cite_start]**Exchange Rate Trend Visualization:** Generate textual summaries or graphical histograms to visualize the distribution of currency value changes across monthly and quarterly periods[cite: 9, 14, 33, 38].
* [cite_start]**Flexible Time Period Selection:** Analyze currency behavior over various time periods [cite: 6, 14][cite_start], with options for "Last N days," "Custom period," and specific dates[cite: 40, 42].
* [cite_start]**Intuitive User Interface:** A user-friendly graphical interface (GUI) [cite: 39] [cite_start]built with JavaFX for seamless currency selection, period definition, and result visualization[cite: 40, 41].
* [cite_start]**Real-time Data Retrieval:** All necessary exchange rate data will be fetched in real-time from the NBP public API, ensuring up-to-date analysis[cite: 45].

## Why This Tool?

In today's interconnected global economy, understanding currency exchange trends is paramount for informed decision-making. Existing tools can often be complex, expensive, or lack direct access to reliable official data. MIVR addresses these challenges by:

* [cite_start]**Simplifying Complex Data:** It provides an intuitive platform to analyze what might otherwise be overwhelming raw exchange rate data[cite: 11, 15].
* [cite_start]**Leveraging Official Sources:** By exclusively using the NBP API, the tool guarantees the reliability and accuracy of the underlying data[cite: 5, 12, 18, 50, 58]. This reliance on official data reduces the risk of using unverified or outdated information.
* [cite_start]**Empowering Various Users:** Designed for financial analysts, economists, academic researchers, university students, and business professionals, it serves a broad spectrum of users who need to interpret currency movements[cite: 3, 4, 53].
* [cite_start]**Standalone and Lightweight:** As a desktop application [cite: 44, 46][cite_start], it requires no additional software installations (like web servers, browsers, or database engines) [cite: 51] [cite_start]and handles all data in memory[cite: 52], making it easy to deploy and use. This design choice minimizes setup complexities and ensures a smooth user experience.
* **Addressing the Gap:** It fills a niche for users seeking a straightforward, dedicated tool for statistical and visual currency analysis without the overhead of enterprise-level software.

## Technical Stack & Design Choices

The MIVR application is developed using the following technologies and adheres to specific design principles:

* **Java:** The core logic of the application is implemented in Java, chosen for its robustness, platform independence, and extensive ecosystem of libraries.
* **JavaFX:** The graphical user interface (GUI) is built with JavaFX. [cite_start]This framework was selected for its ability to create modern, rich desktop applications with a clean separation between UI and backend logic[cite: 39]. JavaFX provides a visually appealing and responsive user experience crucial for data visualization.
* [cite_start]**NBP API:** The National Bank of Poland's official API (`http://api.nbp.pl/`) serves as the sole data source[cite: 50, 45]. [cite_start]This ensures data accuracy and consistency, aligning with the project's goal of providing analysis based on reliable official information.
* [cite_start]**Desktop Application:** Implemented as a desktop application [cite: 44][cite_start], specifically a standalone Windows executable (.exe) application [cite: 46][cite_start], and designed to be compatible with Linux[cite: 60]. This choice was made to provide a dedicated, high-performance user experience without relying on web browsers or external servers. [cite_start]All data is processed in memory for each session [cite: 52][cite_start], avoiding the need for persistent storage or database systems[cite: 45], which simplifies deployment and maintenance.
* **Modular Design:** The system is structured with a clear separation between the backend logic (statistical analysis, session analysis, trend calculations) and the frontend visualization, enhancing maintainability and scalability.

## System Architecture

[cite_start]The system architecture of the Currency Analysis Tool is straightforward, involving a user interacting with a backend system that communicates with the NBP API[cite: 16].
