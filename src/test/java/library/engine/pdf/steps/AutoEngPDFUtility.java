package library.engine.pdf.steps;

import io.cucumber.java.en.Given;
import library.engine.pdf.AutoEngBasePDFSteps;
import org.apache.pdfbox.pdmodel.PDDocument;

public class AutoEngPDFUtility extends AutoEngBasePDFSteps {
    @Given("^the user takes a screenshot of the active pdf$")
    public void takePdfScreenshot() throws IllegalAccessException {
        PDDocument document = getActivePDF();
        addPDFScreenshots(document);
    }

}
