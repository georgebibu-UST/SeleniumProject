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
    @Story("Categories")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Demonstrates reporting metadata and attachments")
    void reportingShowsRichMetadata() {

        Allure.step("Load sample order");
        Allure.step("Verify order status");

        attachText(
                "Order Data",
                """
                SKU=SKU-RET-101
                Quantity=1
                Status=NEW
                """
        );

        assertEquals("NEW", "NEW");
    }

    @Test
    @Story("Attachments")
    @Severity(SeverityLevel.NORMAL)
    @Description("Demonstrates attachment support")
    void attachmentExample() {

        attachText(
                "Execution Notes",
                "Demonstration of Allure text attachment."
        );

        assertTrue(true);
    }

    @Test
    @Story("Business Validation")
    @Severity(SeverityLevel.MINOR)
    @Description("Demonstrates step-by-step reporting")
    void stepsExample() {

        Allure.step("Create order");
        Allure.step("Submit order");
        Allure.step("Verify confirmation");

        assertEquals(1, 1);
    }

    @Attachment(value = "{name}", type = "text/plain")
    private String attachText(String name, String content) {
        return content;
    }
}