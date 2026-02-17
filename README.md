## 🎯 Project Overview

### A Selenium and TestNG-based automation framework that monitors **[Leaflet](https://leafletjs.com/) - an open-source JavaScript map** for availability, performance, and GPS accuracy.

**Test Target:** `https://leafletjs.com/examples/quick-start/example.html`

  
## 🎯 **Project Goal**

Automatically verify that a web-based map application:

| Check | What It Tests | How It's Tested |
|-------|---------------|-----------------|
| **Map Availability** | Map container loads and is visible | `WebDriverWait` for map element |
| **Response Time** | Time from click to popup appears | `System.currentTimeMillis()` measurement |
| **GPS Accuracy** | Returned coordinates match expected values | Parse popup text, compare with expected |

### Test Parameters
- **Expected Latitude:** 51.505
- **Tolerance:** ±0.001
- **SLA Threshold:** < 2000ms

### Sample Result
- **Actual Latency:** 95ms
- **Actual Latitude:** 51.505003
- **GPS Drift:** 0.000003

## 🛠️ Tools & Technologies

| Category | Tools Used |
| :--- | :--- |
| Language | Java 17 |
| Automation | Selenium WebDriver 4.39 |
| Testing | TestNG 7.11 |
| Reporting | Allure 2.29 |
| Build Tool | Maven 3.9 |
| CI/CD | Jenkins (Poll SCM  and WebHook) |
| Version Control | Git, GitHub |
| IDE | IntelliJ IDEA |

## ✨ Key Features

✅ Page Object Model design pattern for maintainable code

✅ Automated browser testing with ChromeDriver

✅ Real-time latency measurement (95ms achieved)

✅ GPS coordinate validation (0.000003 drift)

✅ Screenshot capture on test failures

✅ Allure reports with rich attachments

✅ Jenkins CI/CD pipeline with automatic triggers

📊 Test Results
### 📊 Performance Summary

| Metric | Result | Threshold | Status |
| :--- | :---: | :---: | :--- |
| **Response Time** | `95ms` | < 2000ms | ✅ 95% faster |
| **GPS Expected** | `51.505` | - | - |
| **GPS Actual** | `51.505003` | - | - |
| **GPS Drift** | `0.000003` | < 0.001 | ✅ 300x better |
| **Test Pass Rate** | `100%` | 100% | ✅ All passing |

## 📟 Live Test Execution
Here's an actual test run showing real-time metrics:

```bash
=== TEST SETUP COMPLETE ===

=== MONITORING INITIALIZED: DISPATCH SYSTEM ===

CHECK: Verifying map visibility...

HEALTH LOG: Map UI is visible and responsive.

CHECK: Measuring system latency...

METRIC: System Latency -> 95ms

METRIC: Raw Popup Content -> You clicked the map at LatLng(51.505003, -0.089951)

PARSING: Extracting latitude from: You clicked the map at LatLng(51.505003, -0.089951)

PARSING: Extracted latitude: 51.505003

METRIC: GPS Precision Check -> Found Lat: 51.505003

METRIC: GPS Drift -> 2.9999999995311555E-6

=== HEALTH CHECK PASSED ===

Performance and Data are within Mission-Ready specs.

=== TEARDOWN STARTING ===

Closing browser...

=== MONITORING SESSION CLOSED ===

===============================================

Total tests run: 1, Passes: 1, Failures: 0, Skips: 0

===============================================
```

## 📸 Screenshots
Allure Report Dashboard
<img width="1918" height="872" alt="image" src="https://github.com/user-attachments/assets/2b48574f-fa95-4916-9398-00c9663fc739" />

Complete test suite with 100% pass rate and Jenkins integration

## Jenkins CI/CD Pipeline

<img width="1906" height="507" alt="image" src="https://github.com/user-attachments/assets/6ed9b0c3-0522-4433-85f7-2b0b3b1abb61" />



Jenkins automation with 6 successful builds, all passing



## 🤖 CI/CD with Jenkins
Pipeline Overview
<img width="3723" height="231" alt="deepseek_mermaid_20260217_f04832" src="https://github.com/user-attachments/assets/a1b640ec-6b4a-4dec-996f-05fb91a7094f" />

|  👤 Push  |        🤖 Jenkins |       🧪 Maven    |    📊 Allure      |   🏁 Results |
| :--- | :---: | :---: | :--- | :---: |
|Feature/Main  ──▶ |  Poll SCM   ──▶ | clean test  ──▶ |  Report    ──▶ |  Available |


## ⚙️ Jenkins Job Configuration

| Setting | Value |
| :--- | :--- |
| **Job Name** | `Monitoring-Tests` |
| **Job Type** | Freestyle Project |
| **SCM** | Git |
| **Repository** | https://github.com/SemantaSetuu/monitoring.git |
| **Branch** | `*/main` |
| **Build Trigger** | Poll SCM (`H * * * *`) and WebHook | 
| **Build Step** | `mvn clean test` |
| **Post-Build Action** | Allure Report (`target/allure-results`) |

## What Jenkins Automates
✅ Automatic execution on every code change

✅ Consistent environment for all builds

✅ Complete build history with trends

✅ Immediate feedback

✅ Zero manual intervention after setup


## 🚀 Quick Start
### Prerequisites
Java JDK 17

Maven 3.9

Chrome Browser

ChromeDriver (matching version)

Installation & Execution

# Clone the repository
```bash
git clone https://github.com/SemantaSetuu/monitoring.git
cd monitoring
```

# Run tests
```
mvn clean test
```
# Generate and view Allure report
```bash
mvn allure:serve
```
