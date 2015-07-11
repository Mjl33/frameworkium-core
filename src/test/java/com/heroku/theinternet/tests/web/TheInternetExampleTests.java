package com.heroku.theinternet.tests.web;

import static com.google.common.truth.Truth.assertThat;

import java.io.File;

import com.frameworkium.annotations.Issue;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import com.frameworkium.tests.internal.BaseTest;
import com.heroku.theinternet.pages.web.BasicAuthSuccessPage;
import com.heroku.theinternet.pages.web.CheckboxesPage;
import com.heroku.theinternet.pages.web.DragAndDropPage;
import com.heroku.theinternet.pages.web.DropdownPage;
import com.heroku.theinternet.pages.web.DynamicLoadingExamplePage;
import com.heroku.theinternet.pages.web.FileDownloadPage;
import com.heroku.theinternet.pages.web.FileUploadPage;
import com.heroku.theinternet.pages.web.FileUploadSuccessPage;
import com.heroku.theinternet.pages.web.FormAuthenticationPage;
import com.heroku.theinternet.pages.web.FormAuthenticationSuccessPage;
import com.heroku.theinternet.pages.web.HoversPage;
import com.heroku.theinternet.pages.web.JQueryUIMenuPage;
import com.heroku.theinternet.pages.web.JQueryUIPage;
import com.heroku.theinternet.pages.web.JavaScriptAlertsPage;
import com.heroku.theinternet.pages.web.KeyPressesPage;
import com.heroku.theinternet.pages.web.SecureFileDownloadPage;
import com.heroku.theinternet.pages.web.SortableDataTablesPage;
import com.heroku.theinternet.pages.web.WelcomePage;

public class TheInternetExampleTests extends BaseTest {

    @Issue("HEROKU-1")
    @Test(description = "Basic Auth")
    public void basicAuth() {

        // Navigate to the basic auth page
        BasicAuthSuccessPage basicAuthSuccess =
                WelcomePage.open().then().clickBasicAuth("admin", "admin");

        // Assert that the returned page has the text present
        assertThat(basicAuthSuccess.getSource())
                .contains("Congratulations! You must have the proper credentials.");
    }

    @Issue("HEROKU-2")
    @Test(description = "Checkboxes")
    public void checkBoxes() {

        // Navigate to the checkboxes page
        CheckboxesPage checkboxesPage = WelcomePage.open().then().clickCheckboxesLink();

        // Set all checkboxes to checked via alternative method
        checkboxesPage.checkAllCheckboxes();

        // Assert that all checkboxes are checked
        assertThat(checkboxesPage.getAllCheckboxCheckedStatus())
                .named("check status of checkboxes")
                .doesNotContain(false);
    }

    @Issue("HEROKU-3")
    @Test(description = "Drag and Drop")
    public void dragAndDrop() {

        // Navigate to the checkboxes page
        DragAndDropPage dragAndDropPage = WelcomePage.open().then().clickDragAndDropLink();

        // Drag A onto B
        dragAndDropPage.dragAontoB();

        // Assert on the order of the headings
        assertThat(dragAndDropPage.getListOfHeadings())
                .named("Order of headings")
                .containsExactly("B", "A");
    }

    @Issue("HEROKU-4")
    @Test(description = "Dropdown")
    public void dropdown() {

        // Navigate to the checkboxes page
        DropdownPage dropdownPage = WelcomePage.open().then().clickDropdownLink();

        // Drag A onto B
        dropdownPage.selectFromDropdown("Option 1");

        // Assert selected
        assertThat(dropdownPage.getSelectedOptionText()).named("selected option in dropdown").isEqualTo("Option 1");
    }

    @Issue("HEROKU-5")
    @Test(description = "Dynamic loading")
    public void dynamicLoading() {

        // Navigate to the dynamic loading hidden element page
        DynamicLoadingExamplePage dynamicLoadingExamplePage =
                WelcomePage.open().then().clickDynamicLoading().then().clickExample1();

        // Assert that the element is hidden
        assertThat(dynamicLoadingExamplePage.isElementDisplayed()).named("element visibility").isFalse();

        // Click start and wait for element to be displayed
        dynamicLoadingExamplePage.clickStart().then().waitForElementToBeDisplayed();

        // Assert that the element is indeed displayed
        assertThat(dynamicLoadingExamplePage.isElementDisplayed()).named("element visibility").isTrue();
    }

    @Issue("HEROKU-6")
    @Test(description = "File Download", dependsOnMethods = "fileUpload")
    public void fileDownload() {

        // Navigate to the download page
        FileDownloadPage downloadPage = WelcomePage.open().then().clickFileDownloadLink();

        // Confirm that the textfile.txt file in the list (as other people might be using it!)
        assertThat(downloadPage.getDownloadableFileLinkNames()).contains("textfile.txt");

        // If you know the size to expect
        int size = 0;

        // Confirm size of the downloaded file is as expected
        assertThat(downloadPage.getSizeOfFile("textfile.txt")).isEqualTo(size);
    }

    @Issue("HEROKU-7")
    @Test(description = "File Upload")
    public void fileUpload() {

        // Navigate to the upload page
        FileUploadPage fileUploadPage = WelcomePage.open().then().clickFileUploadLink();

        // Pick a local file we're going to upload
        File fileToUpload = new File("textfile.txt");

        // Upload the file and confirm we land on the success page
        FileUploadSuccessPage successPage = fileUploadPage.uploadFile(fileToUpload);

        // Confirm that the uploaded files list contains our filename
        assertThat(successPage.getUploadedFiles()).contains(fileToUpload.getName());
    }

    @Issue("HEROKU-8")
    @Test(description = "Form Authentication")
    public void formAuthentication() {

        // Navigate to the form authentication page
        FormAuthenticationPage formAuthenticationPage = WelcomePage.open().then().clickFormAuthenticationLink();

        // Log in with the username password provided
        FormAuthenticationSuccessPage successPage =
                formAuthenticationPage.validLogin("tomsmith", "SuperSecretPassword!");

        // Confirm that we're on the success page
        assertThat(successPage.getSource()).contains("Welcome to the Secure Area");
    }

    @Issue("HEROKU-9")
    @Test(description = "Hovers")
    public void hovers() {

        // Navigate to the hovers page
        HoversPage hoversPage = WelcomePage.open().then().clickHoversLink();

        // Confirm that the caption under the first figure contains expected text
        assertThat(hoversPage.getFirstFigureCaption()).contains("name: user1");
    }

    @Issue("HEROKU-10")
    @Test(description = "JQuery UI")
    public void jqueryUI() {

        // Navigate to the jQuery UI page
        JQueryUIMenuPage jqueryUIMenuPage = WelcomePage.open().then().clickJQueryUILink();

        // Browse to the UI page
        JQueryUIPage UIPage = jqueryUIMenuPage.clickBackToUI();

        // Click the menu link to return to the menu page
        jqueryUIMenuPage = UIPage.clickMenuLink();

        // Check that the excel file link matches the string
        assertThat(jqueryUIMenuPage.getExcelFileURLAsString()).isEqualTo(
                "http://the-internet.herokuapp.com/download/jqueryui/menu/menu.xls");
    }

    @Issue("HEROKU-11")
    @Test(description = "Javascript Alerts")
    public void javascriptAlerts() {

        // Navigate to the javascript alerts page
        JavaScriptAlertsPage javascriptAlerts = WelcomePage.open().then().clickjavascriptAlertsLink();

        // N.B. Mis-spelling of successfully is 'expected'
        javascriptAlerts.clickAlertButtonAndAccept();
        assertThat(javascriptAlerts.getResultText()).isEqualTo("You successfuly clicked an alert");

        javascriptAlerts.clickAlertButtonAndDismiss();
        assertThat(javascriptAlerts.getResultText()).isEqualTo("You successfuly clicked an alert");

        javascriptAlerts.clickConfirmButtonAndAccept();
        assertThat(javascriptAlerts.getResultText()).isEqualTo("You clicked: Ok");

        javascriptAlerts.clickConfirmButtonAndDismiss();
        assertThat(javascriptAlerts.getResultText()).isEqualTo("You clicked: Cancel");

        javascriptAlerts.clickPromptButtonAndEnterPrompt("Blah blah blah");
        assertThat(javascriptAlerts.getResultText()).isEqualTo("You entered: Blah blah blah");
    }

    @Issue("HEROKU-12")
    @Test(description = "Key Presses")
    public void keyPresses() {

        // Navigate to the key presses page
        KeyPressesPage keyPressesPage = WelcomePage.open().then().clickKeyPressesLink();

        keyPressesPage.enterKeyPress(Keys.ENTER);

        assertThat(keyPressesPage.getResultText()).isEqualTo("You entered: " + Keys.ENTER.name());
    }

    @Issue("HEROKU-13")
    @Test(description = "Secure file Download")
    public void secureFileDownload() {

        // Navigate to the secure file downloads page
        SecureFileDownloadPage secureFileDownloadPage =
                WelcomePage.open().then().clickSecureFileDownloadsLink("admin", "admin");

        // Assert that the page contains the text
        assertThat(secureFileDownloadPage.getHeadingText()).isEqualTo("Secure File Downloader");
    }

    @Issue("HEROKU-14")
    @Test(description = "Table Manipulation & Validation")
    public void sortDataTable() {

        // Navigate to the sortable data tables page
        SortableDataTablesPage sortableDataTablesPage =
                WelcomePage.open().then().clickSortableDataTablesLink();

        //Assert that Table 1 contains "http://www.jdoe.com" in the web site column
        assertThat(sortableDataTablesPage.getTable1ColumnContents("Web Site"))
                .contains("http://www.jdoe.com");

        //Sort Table 2 by last name column
        sortableDataTablesPage.sortTable2ByColumnName("Last Name");

        //Confirm that the column is then ordered by the last name
        assertThat(sortableDataTablesPage.getTable2ColumnContents("Last Name"))
                .isStrictlyOrdered();

        //Confirm that "Bach" is then the first surname in table 2
        assertThat(sortableDataTablesPage.getTable2ColumnContents("Last Name").get(0)).isEqualTo("Bach");
    }
}
