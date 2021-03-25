package library.engine.pdf.steps;

import io.cucumber.java.en.Given;
import library.common.PDFHelper;
import library.common.TestContext;
import library.engine.pdf.BaseStepsPdf;
import org.apache.pdfbox.pdmodel.PDDocument;
import static library.engine.core.EngConstants.PDF;
import static library.engine.core.EngParser.parseValue;

public class AutoEngPDFOpen extends BaseStepsPdf {

    @Given("^the user opens the pdf at \"([^\"]*)\" path$")
    public void thePdfIsOpened(String filepath){
        TestContext.getInstance().setActiveWindowType(PDF);
        filepath = parseValue(filepath);
        PDDocument document = PDFHelper.getDoc(filepath);
        if (document!=null){
            TestContext.getInstance().testdataPut(ACTIVE_PDF, document);
            TestContext.getInstance().testdata().put(CURRENT_PAGE_PDF, 0);
        }else {
            throw new IllegalArgumentException(String.format("could not find PDF at path: %s", filepath));
        }
    }
}
