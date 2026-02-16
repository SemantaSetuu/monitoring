package tests;

import io.qameta.allure.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;
import pages.MapPage;

@Epic("Dispatch System Monitoring")
@Feature("Map UI and Data Integrity")
@Listeners({io.qameta.allure.testng.AllureTestNg.class})
public class MapHealthTest {

    WebDriver driver;
    MapPage mapPage;

    // Test constants
    private final double EXPECTED_LAT = 51.505;
    private final double TOLERANCE = 0.001;
    private final long SLA_THRESHOLD_MS = 2000L;

    /**
     * Setup method - runs before each test
     * Initializes Chrome browser and navigates to the map
     */
    @BeforeMethod
    @Step("Initialize Browser and Navigate to Dispatch Map")
    public void setup() {
        // Set ChromeDriver path if not in system PATH
        // System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver.exe");

        // Initialize Chrome driver
        driver = new ChromeDriver();

        // Maximize browser window
        driver.manage().window().maximize();

        // Navigate to Leaflet map example
        driver.get("https://leafletjs.com/examples/quick-start/example.html");

        // Initialize Page Object
        mapPage = new MapPage(driver);

        System.out.println("=== TEST SETUP COMPLETE ===");
    }

    /**
     * Main test method - verifies map health, latency, and GPS accuracy
     */
    @Test(description = "Verify map availability, response latency, and GPS coordinate accuracy")
    @Severity(SeverityLevel.CRITICAL)
    @Description("This test simulates a health check on the dispatch map by clicking coordinates and validating the returned UI data against expected dispatch values.")
    public void missionCriticalHealthCheck() {
        System.out.println("=== MONITORING INITIALIZED: DISPATCH SYSTEM ===");

        // Step 1: Check if map is visible and responsive
        checkMapAvailability();

        // Step 2: Validate system performance and data accuracy
        validateSystemLatency();

        System.out.println("=== HEALTH CHECK PASSED ===");
        System.out.println("Performance and Data are within Mission-Ready specs.");
    }

    /**
     * Step 1: Verify map UI is visible
     */
    @Step("Verifying Map UI is visible and responsive")
    private void checkMapAvailability() {
        System.out.println("CHECK: Verifying map visibility...");
        mapPage.verifyMapIsLive();
        Allure.addAttachment("Map Status", "Map UI is visible and responsive");
    }

    /**
     * Step 2: Validate system latency and GPS precision
     */
    @Step("Validating System Latency and GPS Precision")
    private void validateSystemLatency() {
        System.out.println("CHECK: Measuring system latency...");

        // Start timing
        long startTime = System.currentTimeMillis();

        // Click on map to trigger popup
        mapPage.clickOnMap();

        // Get coordinates from popup
        String actualPopupContent = mapPage.getCapturedCoordinates();

        // Calculate response time
        long responseTime = System.currentTimeMillis() - startTime;

        // Add attachments for Allure report
        Allure.addAttachment("System Latency", responseTime + "ms");
        Allure.addAttachment("Raw UI Output", actualPopupContent);
        Allure.addAttachment("Expected Latitude", String.valueOf(EXPECTED_LAT));
        Allure.addAttachment("Tolerance", String.valueOf(TOLERANCE));

        // Log metrics
        System.out.println("METRIC: System Latency -> " + responseTime + "ms");
        System.out.println("METRIC: Raw Popup Content -> " + actualPopupContent);

        // Validate latency meets SLA threshold
        Assert.assertTrue(responseTime < SLA_THRESHOLD_MS,
                "PERFORMANCE ALERT: Latency exceeds safety threshold! Expected < " +
                        SLA_THRESHOLD_MS + "ms, but got " + responseTime + "ms");

        // Parse and validate GPS coordinates
        double actualLat = parseLatitude(actualPopupContent);
        double drift = Math.abs(actualLat - EXPECTED_LAT);

        System.out.println("METRIC: GPS Precision Check -> Found Lat: " + actualLat);
        System.out.println("METRIC: GPS Drift -> " + drift);

        // Validate GPS accuracy
        Assert.assertTrue(drift <= TOLERANCE,
                "DATA INTEGRITY ALERT: GPS drift (" + drift + ") exceeds tolerance of " + TOLERANCE + "!");

        // Add pass metrics to Allure
        Allure.addAttachment("Actual Latitude", String.valueOf(actualLat));
        Allure.addAttachment("GPS Drift", String.valueOf(drift));
        Allure.addAttachment("Status", "PASSED - Within tolerance");
    }

    /**
     * Helper method to parse latitude from popup text
     * Expected format: "A popup with coordinates (51.505, -0.09)"
     */
    private double parseLatitude(String text) {
        try {
            System.out.println("PARSING: Extracting latitude from: " + text);

            // Split to get the coordinates part
            String latPart = text.split("\\(")[1];
            String latValue = latPart.split(",")[0];

            // Parse and return latitude
            double latitude = Double.parseDouble(latValue.trim());
            System.out.println("PARSING: Extracted latitude: " + latitude);

            return latitude;
        } catch (Exception e) {
            System.err.println("ERROR: Failed to parse latitude from: " + text);
            e.printStackTrace();
            throw new RuntimeException("CRITICAL FAILURE: String format changed! Text was: " + text, e);
        }
    }

    /**
     * Teardown method - runs after each test
     * Takes screenshot on failure and closes browser
     */
    @AfterMethod
    @Step("Closing Monitoring Session")
    public void tearDown(ITestResult result) {
        System.out.println("=== TEARDOWN STARTING ===");

        // Take screenshot if test failed
        if (result.getStatus() == ITestResult.FAILURE) {
            System.out.println("TEST FAILED: Capturing screenshot...");
            captureScreenshot(result.getName());
            Allure.addAttachment("Test Status", "FAILED - " + result.getThrowable().getMessage());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            Allure.addAttachment("Test Status", "PASSED");
        }

        // Close browser
        if (driver != null) {
            System.out.println("Closing browser...");
            driver.quit();
            System.out.println("=== MONITORING SESSION CLOSED ===");
        }
    }

    /**
     * Capture screenshot on test failure
     */
    @Attachment(value = "Failure Screenshot - {screenshotName}", type = "image/png")
    private byte[] captureScreenshot(String screenshotName) {
        System.out.println("Capturing screenshot for: " + screenshotName);
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    /**
     * Additional test method - optional, can be used to verify multiple scenarios
     */
    @Test(description = "Verify map loads within acceptable time", enabled = false)
    @Severity(SeverityLevel.NORMAL)
    public void verifyMapLoadTime() {
        // This is an additional test that's disabled by default
        // You can enable it by changing enabled = true
        System.out.println("Checking map load time...");
        long loadTime = System.currentTimeMillis();
        Assert.assertTrue(loadTime < 5000, "Map load time exceeds threshold!");
    }
}