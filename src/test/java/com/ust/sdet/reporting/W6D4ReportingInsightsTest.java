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

    @Attachment(value = "{name}", type = "text/plain")
    private String attachText(String name, String content) {
        return content;
    }
}