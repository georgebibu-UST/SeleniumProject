package com.ust.sdet.reporting;

import io.qameta.allure.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Framework Hardening")
@Feature("Reporting Insights")
@Owner("SDET Trainee")
class W6D4ReportingInsightsTest {

    @Test
    @Story("Successful Execution")
    @Severity(SeverityLevel.MINOR)
    @Description("Demonstrates a passing test with steps and attachments")
    void successfulScenario() {

        Allure.step("Load sample data");
        Allure.step("Verify expected values");

        attachText(
                "Sample Data",
                """
                Product: Laptop
                Price: 59999
                """
        );

        assertTrue(true);
    }

    @Test
    @Story("Product Defect")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Failed assertion should appear as a product defect")
    void productDefectExample() {

        Allure.step("Verify business rule");

        assertEquals(
                100,
                120,
                "Checkout total is incorrect"
        );
    }

    @Test
    @Story("Test Defect")
    @Severity(SeverityLevel.NORMAL)
    @Description("Unexpected exceptions appear as broken tests")
    void testDefectExample() {

        Allure.step("Execute framework action");

        throw new NullPointerException(
                "Page object was not initialized"
        );
    }

    @Test
    @Story("Flaky Failure")
    @Severity(SeverityLevel.MINOR)
    @Description("Message matches categories.json flaky regex")
    void flakyFailureExample() {

        Allure.step("Call unstable dependency");

        throw new RuntimeException(
                "connection reset while waiting for response"
        );
    }

    @Test
    @Story("Attachment Example")
    @Severity(SeverityLevel.NORMAL)
    @Description("Demonstrates multiple attachments in a passing test")
    void attachmentExample() {

        Allure.step("Attach sample execution notes");

        attachText(
                "Execution Notes",
                """
                Browser: Chrome
                Environment: QA
                Result: Success
                """
        );

        assertTrue(true);
    }

    @Test
    @Story("Business Validation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Demonstrates successful business rule validation")
    void businessValidationExample() {

        Allure.step("Validate order total");

        int quantity = 2;
        int unitPrice = 100;

        assertEquals(200, quantity * unitPrice);
    }

    @Test
    @Story("Step Reporting")
    @Severity(SeverityLevel.MINOR)
    @Description("Demonstrates detailed step reporting")
    void stepReportingExample() {

        Allure.step("Create order");
        Allure.step("Submit order");
        Allure.step("Receive confirmation");

        assertTrue(true);
    }

    @Test
    @Story("Metadata Demonstration")
    @Severity(SeverityLevel.NORMAL)
    @Description("Shows how metadata appears in the Allure report")
    void metadataExample() {

        Allure.step("Capture metadata");

        attachText(
                "Metadata",
                """
                Epic: Framework Hardening
                Feature: Reporting Insights
                Owner: SDET Trainee
                """
        );

        assertTrue(true);
    }

    @Test
    @Story("Reporting Dashboard")
    @Severity(SeverityLevel.MINOR)
    @Description("Demonstrates a successful dashboard metric")
    void dashboardExample() {

        Allure.step("Verify reporting dashboard metric");

        int passedTests = 10;
        int failedTests = 0;

        assertTrue(passedTests > failedTests);
    }

    @Attachment(value = "{name}", type = "text/plain")
    private String attachText(String name, String content) {
        return content;
    }
    
}