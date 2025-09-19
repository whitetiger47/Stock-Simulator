# Stock Simulator

Stock Simulator is a comprehensive web-based application built with Java and Spring Boot that simulates a real-world stock trading environment. It allows users to register, manage funds, and trade stocks based on dynamically updated prices. The platform supports various order types, stock baskets, systematic investment plans (SIPs), and provides portfolio visualization tools.

## Key Features

*   **User Management**: Secure user registration, login/logout, and profile management. Includes an admin role and a password reset feature via email.
*   **Stock Price Simulation**: Stock prices are dynamically and periodically updated in the background to simulate a live market. Admins can upload initial stock data via a CSV file.
*   **Trading**: Execute buy and sell orders for stocks. Supports both **Market** and **Limit** orders, as well as **Regular** and **Intraday** trading modes.
*   **Watchlists**: Users can create multiple watchlists to monitor specific stocks of interest.
*   **Baskets**: Group multiple stocks into a single "basket" for easier management and investment.
*   **Systematic Investment Plans (SIP)**: Set up automated, recurring investments into selected stock baskets on a scheduled monthly basis.
*   **Portfolio Management**:
    *   **Holdings**: View all currently owned stocks and their quantities.
    *   **Orders**: Track the status of all pending and completed orders.
    *   **Funds**: Deposit and withdraw virtual funds to and from your trading account.
*   **Trending Stocks**: Discover stocks that are currently popular based on trading volume (buy/sell counts).
*   **Data Visualization**: View historical price data for any stock on an interactive line chart.
*   **Transaction History**: A detailed log of all trading activities, including buys, sells, and fund transfers.

## Architecture

The application is built using the Model-View-Controller (MVC) architectural pattern, ensuring a clean separation of concerns.

*   **`controller`**: Handles incoming HTTP requests, processes user input, and routes to the appropriate services.
*   **`service`**: Contains the core business logic of the application, such as processing trades, updating stock prices, and managing user data.
*   **`dao` (Data Access Object)**: Responsible for all database interactions, abstracting the data persistence layer from the business logic. It extensively uses the Abstract Factory design pattern to create database and model objects.
*   **`models`**: Plain Old Java Objects (POJOs) that represent the application's data entities (e.g., User, Stock, Order).
*   **`database`**: Provides utility classes for establishing and managing database connections.
*   **`templates`**: Thymeleaf templates for rendering the dynamic HTML pages.
  <img width="1726" height="553" alt="image" src="https://github.com/user-attachments/assets/2145f0e4-58a9-4fb9-9a47-c1481c478111" />


## Technologies Used

*   **Backend**: Java 8, Spring Boot, Spring MVC, Spring JDBC, Spring Session
*   **Frontend**: Thymeleaf, HTML5, JavaScript, Chart.js
*   **Database**: MySQL
*   **Build & Dependency Management**: Apache Maven
*   **Authentication**: Custom encryption for password management and a token-based password reset system.
*   **CI/CD**: GitLab CI for automated build and test pipelines, with deployment configurations for Heroku.

## Setup and Installation

### Prerequisites

*   Java Development Kit (JDK) 1.8 or later
*   Apache Maven
*   A running MySQL database instance

### Database Configuration

1.  Create a MySQL database for the application.
2.  Update the database connection properties in `src/main/resources/application.properties`. The project supports multiple profiles (`dev`, `prod`, `test`), so you can configure the appropriate file (e.g., `application-dev.properties`) and activate the profile.

    ```properties
    spring.datasource.url=jdbc:mysql://YOUR_DATABASE_HOST:3306/YOUR_DATABASE_NAME
    spring.datasource.username=YOUR_DATABASE_USERNAME
    spring.datasource.password=YOUR_DATABASE_PASSWORD
    ```

### Running the Application

1.  Clone the repository:
    ```bash
    git clone https://github.com/whitetiger47/Stock-Simulator.git
    ```
2.  Navigate to the project's root directory:
    ```bash
    cd Stock-Simulator
    ```
3.  Run the application using the Maven wrapper:
    *   On macOS/Linux:
        ```bash
        ./mvnw spring-boot:run
        ```
    *   On Windows:
        ```bash
        mvnw.cmd spring-boot:run
        ```
4.  The application will start on `http://localhost:8080`.

### Initial Stock Data

After starting the application, an administrator can log in and navigate to the `/csv_upload` endpoint to upload the initial list of stocks and their prices using the provided `src/main/resources/StocksCSV/stocks.csv` file or a similarly formatted CSV file.
