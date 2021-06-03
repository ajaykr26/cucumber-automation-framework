package library.common;

import io.cucumber.java.sl.Ce;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelHelper {
    private static final String EXCEPTION_PROCESSING_FILE = "exception processing file";

    private ExcelHelper() {

    }

    private static Logger getLogger() {
        return LogManager.getLogger(ExcelHelper.class);
    }

    public static List<ArrayList<Object>> getDataAsArrayList(String filepath, String worksheet, String... recordset) {
        ArrayList<ArrayList<Object>> data = new ArrayList<>();
        try (FileInputStream file = new FileInputStream(filepath);
             XSSFWorkbook workbook = new XSSFWorkbook(file)) {
            XSSFSheet sheet = workbook.getSheet(worksheet);
            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();

            int maxDataCount = 0;

            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                if (row.getRowNum() == 0) {
                    maxDataCount = row.getLastCellNum();
                }
                if (isRowEmpty(row)) {
                    break;
                }

                ArrayList<Object> singleRow = new ArrayList<>();

                for (int c = 0; c < maxDataCount; c++) {
                    Cell cell = row.getCell(c);
                    singleRow.add(getCellData(cell, formulaEvaluator));
                }

                if (recordset.length > 0) {
                    if (singleRow.get(0).toString().equalsIgnoreCase(recordset[0])) {
                        data.add(singleRow);
                    }
                } else {
                    data.add(singleRow);
                }
            }

        } catch (Exception exception) {
            getLogger().error(EXCEPTION_PROCESSING_FILE, exception);
        }
        return data;
    }

    public static Map<String, LinkedHashMap<String, Object>> getDataAsMap(String filepath, String worksheet) {
        Map<String, LinkedHashMap<String, Object>> dataMap = new LinkedHashMap<>();
        LinkedHashMap<String, Object> mapTemp = null;
        String dataset = null;
        Row headerRow = null;
        Object key = null;
        Object value = null;
        try (FileInputStream file = new FileInputStream(filepath);
             XSSFWorkbook workbook = new XSSFWorkbook(file)) {
            XSSFSheet sheet = workbook.getSheet(worksheet);
            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
            int lastRow = sheet.getLastRowNum();
            int currentRowNum = 0;
            while (currentRowNum < lastRow) {
                headerRow = sheet.getRow(currentRowNum);

                dataset = getCellData(sheet.getRow(headerRow.getRowNum()).getCell(0), formulaEvaluator).toString();
                mapTemp = new LinkedHashMap<>();
                for (int cellCounter = 1; cellCounter < sheet.getRow(currentRowNum).getLastCellNum(); cellCounter++) {
                    key = getCellData(sheet.getRow(headerRow.getRowNum()).getCell(cellCounter), formulaEvaluator);
                    value = getCellData(sheet.getRow(currentRowNum + 1).getCell(cellCounter), formulaEvaluator);
                    if (value != null && key != null) {
                        mapTemp.put(key.toString(), value);
                    }
                }
                currentRowNum += 2;
                dataMap.put(dataset, mapTemp);
            }
        } catch (Exception exception) {
            getLogger().error(EXCEPTION_PROCESSING_FILE);
        }
        return dataMap;
    }

//    public static Map<String, LinkedHashMap<String, Object>> getDataAsMap(String filepath, String worksheet) {
//        Map<String, LinkedHashMap<String, Object>> dataMap = new LinkedHashMap<>();
//        LinkedHashMap<String, Object> mapTemp = null;
//        String datasetKey = null;
//        Row headerRow = null;
//        Object key = null;
//        Object value = null;
//        try (FileInputStream file = new FileInputStream(filepath);
//             XSSFWorkbook workbook = new XSSFWorkbook(file)) {
//            XSSFSheet sheet = workbook.getSheet(worksheet);
//            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
//            int lastRow = sheet.getLastRowNum();
//            int currentRowNum = 0;
//            while (currentRowNum < lastRow) {
//                if (isRowEmpty(sheet.getRow(currentRowNum))) {
//                    currentRowNum++;
//                    continue;
//                }
//                headerRow = sheet.getRow(currentRowNum);
//
//                for (int j = currentRowNum + 1; j < lastRow; j++) {
//                    if (isRowEmpty(sheet.getRow(j))) {
//                        currentRowNum = j + 1;
//                        break;
//                    }
//
//                    datasetKey = getCellData(sheet.getRow(headerRow.getRowNum()).getCell(0), formulaEvaluator) + "." + getCellData(sheet.getRow(sheet.getRow(j).getRowNum()).getCell(0), formulaEvaluator);
//                    mapTemp = new LinkedHashMap<>();
//                    for (int cellCounter = 1; cellCounter < sheet.getRow(j).getLastCellNum(); cellCounter++) {
//                        key = getCellData(sheet.getRow(headerRow.getRowNum()).getCell(0), formulaEvaluator);
//                        value = getCellData(sheet.getRow(j).getCell(cellCounter), formulaEvaluator);
//                        if (value != null && key != null) {
//                            mapTemp.put(key.toString(), value);
//                        }
//                    }
//
//                }
//                currentRowNum++;
//                dataMap.put(datasetKey, mapTemp);
//            }
//        } catch (Exception exception) {
//            getlogger().error(EXCEPTION_PROCESSING_FILE);
//        }
//        return dataMap;
//    }

    public static List<Map<String, Object>> getDataAsMapWithoutIndex(String filepath, String worksheet) {
        List<Map<String, Object>> dataMap = new ArrayList<>();
        LinkedHashMap<String, Object> mapTemp = null;
        Row headerRow = null;
        Object key = null;
        Object value = null;
        try (FileInputStream file = new FileInputStream(filepath);
             XSSFWorkbook workbook = new XSSFWorkbook(file)) {
            XSSFSheet sheet = workbook.getSheet(worksheet);
            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
            int lastRow = sheet.getLastRowNum();
            int currentRowNum = 0;
            while (currentRowNum < lastRow) {
                if (isRowEmpty(sheet.getRow(currentRowNum))) {
                    currentRowNum++;
                    continue;
                }
                headerRow = sheet.getRow(currentRowNum);

                for (int j = currentRowNum + 1; j < lastRow; j++) {
                    if (isRowEmpty(sheet.getRow(j))) {
                        currentRowNum = j + 1;
                        break;
                    }

                    mapTemp = new LinkedHashMap<>();
                    for (int cellCounter = 1; cellCounter < sheet.getRow(j).getLastCellNum(); cellCounter++) {
                        key = getCellData(sheet.getRow(headerRow.getRowNum()).getCell(0), formulaEvaluator);
                        value = getCellData(sheet.getRow(j).getCell(cellCounter), formulaEvaluator);
                        if (value != null && key != null) {
                            mapTemp.put(key.toString(), value);
                        }
                    }

                }
                currentRowNum++;
                dataMap.add(mapTemp);
            }
        } catch (Exception exception) {
            getLogger().error(EXCEPTION_PROCESSING_FILE);
        }
        return dataMap;
    }

    private static Object getCellData(Cell cell, FormulaEvaluator formulaEvaluator) {
        Object obj = null;
        if (cell == null) return null;
        switch (formulaEvaluator.evaluateInCell(cell).getCellType()) {
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    obj = (new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue()));
                } else {
                    obj = (long) cell.getNumericCellValue();
                }
                break;
            case BLANK:
                obj = null;
                break;
            case STRING:
            default:
                obj = (cell.getStringCellValue());
        }
        return obj;
    }

    private static boolean isRowEmpty(Row row) {
        if (row == null) return true;
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }
}
