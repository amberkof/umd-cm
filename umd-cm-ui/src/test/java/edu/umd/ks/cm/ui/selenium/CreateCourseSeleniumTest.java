package edu.umd.ks.cm.ui.selenium;

import static org.junit.Assert.fail;

import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class CreateCourseSeleniumTest {
    private WebDriver driver;
    //private StringBuffer verificationErrors = new StringBuffer();
    private String browser;

    @Before
    public void setUp() throws Exception {
        String remoteTest = System.getProperty("selenium.remote");
        String baseURL = System.getProperty("selenium.baseurl", "http://umd-cm.qa.umd.edu/umd-cm");
        //String baseURL = System.getProperty("selenium.baseurl", "http://localhost:8282/umd-cm-embedded-dev");
        browser = System.getProperty("selenium.browser", "firefox");
        if ("true".equals(remoteTest)) {
            String version = System.getProperty("selenium.browser.version", "");
            Platform platform = Platform.valueOf(System.getProperty("selenium.platform", "ANY"));
            DesiredCapabilities capabilities = new DesiredCapabilities(browser, version, platform);
            capabilities.setCapability("max-duration", "120"); // tests can't run more than 2 mins
            //capabilities.setCapability("name", getClass().getName() + "." + name.getMethodName());
            driver = new RemoteWebDriver(new URL(baseURL),
                                         capabilities);
            // longer timeouts for remote invocation
            driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
        } else {
            if ("firefox".equals(browser)) {
                driver = new FirefoxDriver();
            } else if ("ie".equals(browser)) {
                driver = new InternetExplorerDriver();
            }
            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        }
    }

    @Test
    public void testCreateCourse() throws Exception {
        driver.get("http://umd-cm.qa.umd.edu/umd-cm/login.jsp");
        //driver.get("http://localhost:8282/umd-cm-embedded-dev/login.jsp");
        System.out.print("performLogon()");
        performLogon();
        
        System.out.println("navigateToCurriculumManagement");
        navigateToCurriculumManagement();
        
        System.out.println("clickCreateCourse");
        clickCreateCourse();
        
        System.out.println("startProposalCurReviewProcess");
        startProposalCurReviewProcess();
        //Start Proposal
        System.out.println("completeCourseInfoSection");
        completeCourseInfoSection();
        
        System.out.println("completeGovernanceSection");
        completeGovernanceSection();
        
        System.out.println("completeCourseLogisticsSection");
        completeCourseLogisticsSection();
        
        System.out.println("completeLearningOutcomesSection");
        completeLearningOutcomesSection();
        
        System.out.println("completeCourseRequisiteSection");
        completeCourseRequisiteSection();
        
        System.out.println("completeActiveDatesSection");
        completeActiveDatesSection();
        
        System.out.println("completeAuthorsAndCollaboratorsSection");
        completeAuthorsAndCollaboratorsSection();
        
        System.out.println("completeSupportingDocumentsSection");
        completeSupportingDocumentsSection();
        
        System.out.println("completeReviewProposalSection");
        completeReviewProposalSection();
        //Finally the proposal is submitted to the workflow
    }

    private void performLogon() {
        WebElement logonUserName = driver.findElement(By.id("j_username"));
        typeValue(logonUserName, "admin");
        
        WebElement logonPassword = driver.findElement(By.id("j_password"));
        typeValue(logonPassword, "admin");
        
        logonPassword.submit();
    }

    private void navigateToCurriculumManagement() throws InterruptedException {
        By bySelectAnAreaPanel = By.id("gwt-debug-Application-Header-Select-an-area--panel-0");
        for (int second = 0;; second++) {
            if (second >= 60)
                fail("timeout");
            try {
                if (isElementPresent(bySelectAnAreaPanel))
                    break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }
        driver.findElement(bySelectAnAreaPanel).click();
        
        driver.findElement(By.id("gwt-debug-Curriculum-Management-label")).click();
    }

    private void clickCreateCourse() throws InterruptedException {
        By bycreateACourseLink = By.linkText("Create a Course");
        for (int second = 0;; second++) {
            if (second >= 60)
                fail("timeout");
            try {
                if (isElementPresent(bycreateACourseLink))
                    break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }
        driver.findElement(bycreateACourseLink).click();
    }

    private void startProposalCurReviewProcess() throws InterruptedException {
        By byUseCurriculumReviewCheckbox = By.id("gwt-debug-Use-curriculum-review-process-input");
        for (int second = 0;; second++) {
            if (second >= 60)
                fail("timeout");
            try {
                if (isElementPresent(byUseCurriculumReviewCheckbox))
                    break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }
        driver.findElement(byUseCurriculumReviewCheckbox).click();
        
        driver.findElement(By.id("gwt-debug-Start-Proposal-anchor")).click();
    }

    private void completeCourseInfoSection() throws InterruptedException {
        By bySubjArea = By.xpath("//input[@id='gwt-debug-subjectArea']");
        for (int second = 0;; second++) {
            if (second >= 60)
                fail("timeout");
            try {
                if (isElementPresent(bySubjArea))
                    break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }
        WebElement subjectArea = driver.findElement(bySubjArea);
        subjectArea.click();
        
        typeValue(subjectArea, "CHEM", Keys.RIGHT);
        By byMenuItem = By.xpath("//td[@role='menuitem']");
        for (int second = 0;; second++) {
            if (second >= 60)
                fail("timeout");
            try {
                if (isElementPresent(byMenuItem))
                    break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }
        driver.findElement(byMenuItem).click();
        
        WebElement courseNumberSuffix = driver.findElement(By.xpath("//input[@id='gwt-debug-courseNumberSuffix']"));
        typeValue(courseNumberSuffix, "111");
        
        WebElement proposalName = driver.findElement(By.xpath("//input[@id='gwt-debug-proposal-name']"));
        typeValue(proposalName, "Selenium Tester");
        
        WebElement courseTitle = driver.findElement(By.xpath("//input[@id='gwt-debug-courseTitle']"));
        typeValue(courseTitle, "Selenium Tester");
        
        WebElement transcriptTitle = driver.findElement(By.xpath("//input[@id='gwt-debug-transcriptTitle']"));
        typeValue(transcriptTitle, "Selenium Transcript");
        
        WebElement description = driver.findElement(By.xpath("//textarea[@id='gwt-debug-descr-plain']"));
        typeValue(description, "Selenium Description");
        
        WebElement rationale = driver.findElement(By.xpath("//textarea[@id='gwt-debug-proposal-rationale']"));
        typeValue(rationale, "Selenium Rationale");
        
        driver.findElement(By.id("gwt-debug-Save-and-Continue-anchor")).click();
    }

    private void completeGovernanceSection() throws InterruptedException {
        By byCurrOversightUnit = By.xpath("//select[@id='gwt-debug-unitsContentOwner']");
        for (int second = 0;; second++) {
            if (second >= 60)
                fail("timeout");
            try {
                if (isElementPresent(byCurrOversightUnit))
                    break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }
        WebElement unitContentOwner = driver.findElement(byCurrOversightUnit);
        clickOptionInSelectList(unitContentOwner, "012030301360701");
        
        //Always sleep before 'Add to list' buttons
        Thread.sleep(1000);
        driver.findElement(By.id("gwt-debug-unitsContentOwner-Add-to-list-anchor")).click();
        
        driver.findElement(By.id("gwt-debug-Save-and-Continue-anchor")).click();
    }

    private void completeCourseLogisticsSection() throws InterruptedException {
        By byCompleteAssessment = By.id("gwt-debug-Completed-notation-input");
        for (int second = 0;; second++) {
            if (second >= 60)
                fail("timeout");
            try {
                if (isElementPresent(byCompleteAssessment))
                    break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }
        driver.findElement(byCompleteAssessment).click();
        
        WebElement outcome1Type = driver.findElement(By.xpath("//select[@id='gwt-debug-creditOptions-0-type']"));
        clickOptionInSelectList(outcome1Type, "kuali.result.values.group.type.fixed");
        
        WebElement outcome1CreditValue = driver.findElement(By
                .xpath("//input[@id='gwt-debug-creditOptions-0-fixedCreditValue']"));
        typeValue(outcome1CreditValue, "5");
        
        WebElement activityType = driver.findElement(By
                .xpath("//select[@id='gwt-debug-formats-0-activities-0-activityType']"));
        clickOptionInSelectList(activityType, "kuali.lu.type.activity.Lecture");
        
        WebElement contactHours = driver.findElement(By
                .xpath("//input[@id='gwt-debug-formats-0-activities-0-contactHours-unitQuantity']"));
        typeValue(contactHours, "1");
        
        WebElement frequency = driver.findElement(By
                .xpath("//select[@id='gwt-debug-formats-0-activities-0-contactHours-unitType']"));
        clickOptionInSelectList(frequency, "kuali.atp.duration.month");
        
        WebElement durationType = driver.findElement(By
                .xpath("//select[@id='gwt-debug-formats-0-activities-0-duration-atpDurationTypeKey']"));
        clickOptionInSelectList(durationType, "kuali.atp.duration.Semester");
        
        WebElement duration = driver.findElement(By
                .xpath("//input[@id='gwt-debug-formats-0-activities-0-duration-timeQuantity']"));
        typeValue(duration, "1");
        
        WebElement anticipatedClassSize = driver.findElement(By
                .xpath("//input[@id='gwt-debug-formats-0-activities-0-defaultEnrollmentEstimate']"));
        typeValue(anticipatedClassSize, "15");
        
        driver.findElement(By.id("gwt-debug-Save-and-Continue-anchor")).click();
    }

    private void completeLearningOutcomesSection() throws InterruptedException {
        //Uncomment this when UMD CM has categories
        //        for (int second = 0;; second++) {
        //            if (second >= 60)
        //                fail("timeout");
        //            try {
        //                if (isElementPresent(By.xpath("//textarea[@id='gwt-debug-courseSpecificLOs-0-loInfo-desc-plain']")))
        //                    break;
        //            } catch (Exception e) {}
        //            Thread.sleep(1000);
        //        }
        //        WebElement learningObjective1 = driver.findElement(By
        //                        .xpath("//textarea[@id='gwt-debug-courseSpecificLOs-0-loInfo-desc-plain']"));
        //        typeValue(learningObjective1, "Selenium Learning Objectives");
        //
        //        driver.findElement(By.id("gwt-debug-courseSpecificLOs-0-loInfo-desc-plain-Browse-for-categories")).click();
        //        for (int second = 0;; second++) {
        //            if (second >= 60)
        //                fail("timeout");
        //            try {
        //                if (isElementPresent(By.id("gwt-debug-Communication-Skill-input")))
        //                    break;
        //            } catch (Exception e) {}
        //            Thread.sleep(1000);
        //        }
        //
        //        driver.findElement(By.id("gwt-debug-Communication-Skill-input")).click();
        //        driver.findElement(By.id("gwt-debug-Add-anchor")).click();
        //        driver.findElement(By.id("gwt-debug-Save-and-Continue-anchor")).click();
        Thread.sleep(1000);
        By byCourseOutcomesContinue = By.id("gwt-debug-Save-and-Continue-anchor");
        for (int second = 0;; second++) {
            if (second >= 60)
                fail("timeout");
            try {
                if (isElementPresent(byCourseOutcomesContinue))
                    break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }
        WebElement courseOutcomesContinue = driver.findElement(byCourseOutcomesContinue);
        courseOutcomesContinue.click();
    }

    private void completeCourseRequisiteSection() throws InterruptedException {
        By byStudEligibilityPrerequisite = By.id("gwt-debug-Add-Student-Eligibility---Prerequisite-anchor");
        for (int second = 0;; second++) {
            if (second >= 60)
                fail("timeout");
            try {
                if (isElementPresent(byStudEligibilityPrerequisite))
                    break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }
        System.out.println("driver.findElement(byStudEligibilityPrerequisite).click()");
        driver.findElement(byStudEligibilityPrerequisite).click();
        
        By byRuleType = By.id("gwt-debug-Rule-Type");
        for (int second = 0;; second++) {
            if (second >= 60)
                fail("timeout");
            try {
                if (isElementPresent(byRuleType))
                    break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }
        WebElement ruleType = driver.findElement(byRuleType);
        System.out.println("clickOptionInSelectList(ruleType, 21)");
        clickOptionInSelectList(ruleType, "21");
        
        System.out.println("ruleTypeSelected(ruleType, 19)");
        
        By byReqPositiveInt = By
                .xpath("//input[@id='gwt-debug-kuali-reqComponent-field-type-value-positive-integer']");
        for (int second = 0;; second++) {
            if (second >= 60)
                fail("timeout");
            try {
                if (isElementPresent(byReqPositiveInt))
                    break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }
        System.out.println("driver.findElement(byReqPositiveInt)");
        WebElement minCreditValue = driver.findElement(byReqPositiveInt);
        System.out.println("typeValue(minCreditValue, 10)");
        typeValue(minCreditValue, "10");
        
        System.out.println("driver.findElement(By.id(gwt-debug-Add-anchor)).click()");
        driver.findElement(By.id("gwt-debug-Add-anchor")).click();
        //Give the 'Save' button a chance to become active
        Thread.sleep(2000);//IE needs more time here than Firefox
        System.out.println("driver.findElement(By.id(gwt-debug-Save-anchor)).click()");
        driver.findElement(By.id("gwt-debug-Save-anchor")).click();
        
        Thread.sleep(1000);
        System.out.println("driver.findElement(By.id(gwt-debug-Save-and-Continue-anchor)).click()");
        driver.findElement(By.id("gwt-debug-Save-and-Continue-anchor")).click();
    }

    private void completeActiveDatesSection() throws InterruptedException {
        By byStartTerm =  By.xpath("//select[@id='gwt-debug-startTerm']");
        for (int second = 0;; second++) {
            if (second >= 60)
                fail("timeout");
            try {
                if (isElementPresent(byStartTerm))
                    break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }
        WebElement startTerm = driver.findElement(byStartTerm);
        clickOptionInSelectList(startTerm, "kuali.atp.2011Spring");
        
        driver.findElement(By.id("gwt-debug-Save-and-Continue-anchor")).click();
    }

    private void completeAuthorsAndCollaboratorsSection() throws InterruptedException {
        By byNameAdvSearch = By.id("gwt-debug-Name-Advanced-Search-anchor");
        for (int second = 0;; second++) {
            if (second >= 60)
                fail("timeout");
            try {
                if (isElementPresent(byNameAdvSearch))
                    break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }
        driver.findElement(byNameAdvSearch).click();
        
        driver.findElement(By.id("gwt-debug-Search-anchor")).click();
        
        By byAdmin1 = By.id("Admin-1--Test--testadmin1--1101-Admin-1--Test-testadmin1-testadmin1");
        for (int second = 0;; second++) {
            if (second >= 60)
                fail("timeout");
            try {
                if (isElementPresent(byAdmin1))
                    break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }
        driver.findElement(byAdmin1).click();
        
        driver.findElement(By.id("gwt-debug-Search-anchor")).click();
        
        WebElement permission = driver.findElement(By.xpath("//select[@id='gwt-debug-Permission']"));
        clickOptionInSelectList(permission, "KS-SYS~Edit Document");
        
        WebElement actionRequest = driver.findElement(By.xpath("//select[@id='gwt-debug-Action-Request']"));
        clickOptionInSelectList(actionRequest, "F");
        
        driver.findElement(By.id("gwt-debug-Add-Collaborator-anchor")).click();
        
        driver.findElement(By.id("gwt-debug-Save-and-Continue-anchor")).click();
    }

    private void completeSupportingDocumentsSection() throws InterruptedException {
        Thread.sleep(1000);
        By byContinue = By.id("gwt-debug-Continue-anchor");
        for (int second = 0;; second++) {
            if (second >= 60)
                fail("timeout");
            try {
                if (isElementPresent(byContinue))
                    break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }
        driver.findElement(byContinue).click();
    }

    private void completeReviewProposalSection() throws InterruptedException {
        By byTopWFPanel = By.id("gwt-debug-Top-Workflow-Actions-Proposal-Actions-panel-0");
        for (int second = 0;; second++) {
            if (second >= 60)
                fail("timeout");
            try {
                if (isElementPresent(byTopWFPanel))
                    break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }
        driver.findElement(byTopWFPanel).click();
        
        By bySubmitProposalPanel = By.id("gwt-debug-Submit-Proposal-panel");
        for (int second = 0;; second++) {
            if (second >= 60)
                fail("timeout");
            try {
                if (isElementPresent(bySubmitProposalPanel))
                    break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }
        driver.findElement(bySubmitProposalPanel).click();
        
        By byConfirmAnchor = By.id("gwt-debug-Confirm-anchor");
        for (int second = 0;; second++) {
            if (second >= 60)
                fail("timeout");
            try {
                if (isElementPresent(byConfirmAnchor))
                    break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }
        driver.findElement(byConfirmAnchor).click();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        //        String verificationErrorString = verificationErrors.toString();
        //        if (!"".equals(verificationErrorString)) {
        //            fail(verificationErrorString);
        //        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private void typeValue(final WebElement webElement, CharSequence... keysToSend) {
        webElement.clear();
        webElement.sendKeys(keysToSend);
    }

    private void clickOptionInSelectList(final WebElement selectElement, String optionValue) {
        List<WebElement> selectOptions = selectElement.findElements(By.tagName("option"));
        for (WebElement option : selectOptions) {
            if (optionValue != null) {
                //select element by option value attribute or by actual text
                if (optionValue.equals(option.getAttribute("value")) || optionValue.equals(option.getText())) {
                    option.click();
                    break;
                }
            }
        }
    }
}
