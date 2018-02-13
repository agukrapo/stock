package ar.com.ak.util;

import org.apache.poi.hssf.record.formula.eval.ErrorEval;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class MSOfficeUtil {

    private MSOfficeUtil() {
    }

    public static List<List<Object>> getLinesExcel(File excelFile) {
        List<List<Object>> result = new ArrayList<List<Object>>();

        try {
            POIFSFileSystem fileSystem = new POIFSFileSystem(
                    new FileInputStream(excelFile));

            HSSFWorkbook workBook = new HSSFWorkbook(fileSystem);

            workBook.setMissingCellPolicy(HSSFRow.RETURN_BLANK_AS_NULL);

            for (int sheetIndex = 0; sheetIndex < workBook.getNumberOfSheets(); sheetIndex++) {
                HSSFSheet sheet = workBook.getSheetAt(sheetIndex);

                if (sheet == null) {
                    continue;
                }

                Integer firstCellIndex = null;
                Integer lastCellIndex = null;

                for (int rowIndex = sheet.getFirstRowNum(); rowIndex < sheet.getLastRowNum() + 1; rowIndex++) {
                    HSSFRow row = sheet.getRow(rowIndex);

                    List<Object> rowContent = new ArrayList<Object>();

                    if (row == null) {
                        continue;
                    } else {
                        if (firstCellIndex == null && lastCellIndex == null) {
                            firstCellIndex = (int) row.getFirstCellNum();
                            lastCellIndex = (int) row.getLastCellNum();
                        }
                    }

                    for (int cellIndex = firstCellIndex; cellIndex < lastCellIndex; cellIndex++) {
                        HSSFCell cell = row.getCell(cellIndex);

                        if (cell == null) {
                            rowContent.add(null);

                        } else {
                            switch (cell.getCellType()) {
                                case HSSFCell.CELL_TYPE_STRING: {
                                    rowContent.add(cell.getRichStringCellValue()
                                            .getString());
                                    break;

                                }
                                case HSSFCell.CELL_TYPE_NUMERIC: {
                                    rowContent.add(cell.getNumericCellValue());
                                    break;

                                }
                                case HSSFCell.CELL_TYPE_BOOLEAN: {
                                    rowContent.add(cell.getBooleanCellValue());
                                    break;

                                }
                                case HSSFCell.CELL_TYPE_ERROR: {
                                    rowContent.add(ErrorEval.getText(
                                            cell.getErrorCellValue()));
                                    break;

                                }
                                case HSSFCell.CELL_TYPE_FORMULA: {

                                    switch (cell.getCachedFormulaResultType()) {

                                        case HSSFCell.CELL_TYPE_STRING: {
                                            HSSFRichTextString str = cell
                                                    .getRichStringCellValue();

                                            if (str != null && str.length() > 0) {
                                                rowContent.add(str.toString().trim());
                                            } else {
                                                rowContent.add(null);
                                            }

                                            break;

                                        }
                                        case HSSFCell.CELL_TYPE_NUMERIC: {
                                            rowContent.add(cell.getNumericCellValue());
                                            break;

                                        }
                                        case HSSFCell.CELL_TYPE_BOOLEAN: {
                                            rowContent.add(cell.getBooleanCellValue());
                                            break;

                                        }
                                        case HSSFCell.CELL_TYPE_ERROR: {
                                            rowContent.add(ErrorEval.getText(
                                                    cell.getErrorCellValue()));
                                            break;
                                        }
                                    }

                                    break;
                                }
                                default: {
                                    throw new RuntimeException(
                                            "Unexpected cell type ("
                                                    + cell.getCellType() + ")");
                                }
                            }
                        }
                    }

                    Boolean emptyContent = Boolean.TRUE;

                    for (Object c : rowContent) {
                        if (c != null) {
                            emptyContent = Boolean.FALSE;
                        }
                    }

                    if (!emptyContent) {
                        result.add(rowContent);
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
