# Mobile Automation Test - Sauce Labs Sample App

Automation testing for Sauce Labs Mobile Sample App using Java, Appium, and Cucumber BDD.

## Tech Stack
- Java 17
- Maven
- Appium 3.1.0
- Cucumber 7.18.0
- JUnit 4.13.2
- Page Object Model (POM) Pattern

## Prerequisites
- Java JDK 17+
- Maven 3.6+
- Node.js
- Appium
- Android SDK
- Android device or emulator

## Project Structure
```
mobile-automation-test/
├── apps/                           # APK files
├── src/
│   ├── main/java/pages/           # Page Object classes
│   │   ├── LoginPage.java
│   │   ├── ProductPage.java
│   │   └── CartPage.java
│   └── test/
│       ├── java/
│       │   ├── runners/           # Test Runner
│       │   └── stepdefinitions/   # Step Definitions
│       └── resources/features/    # BDD Feature files
├── pom.xml
└── README.md
```

## Setup Instructions

### 1. Clone Repository
```bash
git clone <your-repo-url>
cd mobile-automation-test
```

### 2. Install Dependencies
```bash
mvn clean install
```

### 3. Start Appium Server
```bash
appium
```

### 4. Connect Android Device
```bash
adb devices
```

### 5. Run Tests
```bash
mvn test
```

## Test Scenario
Purchase flow for Sauce Labs products:
- Login with valid credentials
- Add "Sauce Labs Backpack" to cart
- Add "Sauce Labs Bike Light" to cart
- Complete checkout process
- Verify order confirmation

## Test Results
- Test Duration: ~35 seconds
- Status: ✅ PASSED

## Demo Video
[Watch Test Execution](Video will be added later)

## Author
[Muhammad Ihsan Rafii]