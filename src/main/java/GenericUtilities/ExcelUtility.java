package GenericUtilities;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtility {

    // ============================================================
    // Excel File Path
    // ============================================================

    private static final String EXCEL_PATH =
            "./src/test/resources/TestData.xlsx";


    // ============================================================
    // Get Test Data Based on Test Case ID
    // ============================================================

    public Object[][] getDataByTestCaseId(
            String sheetName,
            String testCaseId)
            throws EncryptedDocumentException, IOException {

        FileInputStream fis = new FileInputStream(EXCEL_PATH);

        Workbook workbook = WorkbookFactory.create(fis);

        Sheet sheet = workbook.getSheet(sheetName);

        if (sheet == null) {
            workbook.close();
            fis.close();

            throw new IllegalArgumentException(
                    "Sheet not found: " + sheetName
            );
        }

        DataFormatter formatter = new DataFormatter();

        int rowCount = 0;

        // --------------------------------------------------------
        // Count matching rows
        // Column 0 = TestCaseID
        // --------------------------------------------------------

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {

            Row row = sheet.getRow(i);
            if (row == null) {
                continue;
            }

            String currentTestCaseId =
                    formatter.formatCellValue(row.getCell(0));

            if (currentTestCaseId.equalsIgnoreCase(testCaseId)) {
                rowCount++;
            }
        }

        // --------------------------------------------------------
        // Excel contains 8 columns:
        //
        // 0 - TestCaseID
        // 1 - Dataset
        // 2 - Gender
        // 3 - FirstName
        // 4 - LastName
        // 5 - Email
        // 6 - Password
        // 7 - ConfirmPassword
        // --------------------------------------------------------

        Object[][] data = new Object[rowCount][8];

        int dataIndex = 0;

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {

            Row row = sheet.getRow(i);

            if (row == null) {
                continue;
            }

            String currentTestCaseId =
                    formatter.formatCellValue(row.getCell(0));

            if (currentTestCaseId.equalsIgnoreCase(testCaseId)) {

                for (int j = 0; j < 8; j++) {

                    data[dataIndex][j] =
                            formatter.formatCellValue(row.getCell(j));
                }

                dataIndex++;
            }
        }

        workbook.close();
        fis.close();

        return data;
    }
    
    public Object[][] getDataByTestCaseId(
            String sheetName,
            String testCaseId,
            int columnCount)
            throws EncryptedDocumentException, IOException {

        FileInputStream fis = new FileInputStream(EXCEL_PATH);

        Workbook workbook = WorkbookFactory.create(fis);

        Sheet sheet = workbook.getSheet(sheetName);

        if (sheet == null) {
            workbook.close();
            fis.close();

            throw new IllegalArgumentException(
                    "Sheet not found: " + sheetName
            );
        }

        DataFormatter formatter = new DataFormatter();

        int rowCount = 0;

        // Count matching rows
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {

            Row row = sheet.getRow(i);

            if (row == null) {
                continue;
            }

            String currentTestCaseId =
                    formatter.formatCellValue(row.getCell(0));

            if (currentTestCaseId.equalsIgnoreCase(testCaseId)) {
                rowCount++;
            }
        }

        Object[][] data = new Object[rowCount][columnCount];

        int dataIndex = 0;

        // Read matching rows
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {

            Row row = sheet.getRow(i);

            if (row == null) {
                continue;
            }

            String currentTestCaseId =
                    formatter.formatCellValue(row.getCell(0));

            if (currentTestCaseId.equalsIgnoreCase(testCaseId)) {

                for (int j = 0; j < columnCount; j++) {

                    data[dataIndex][j] =
                            formatter.formatCellValue(row.getCell(j));
                }

                dataIndex++;
            }
        }

        workbook.close();
        fis.close();

        return data;
    }
}