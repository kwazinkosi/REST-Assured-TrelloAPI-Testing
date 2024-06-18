package utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataProvider {
	
    public static List<List<String>> readExcelData(String filePath) throws IOException {
    	
        List<List<String>> data = new ArrayList<>();
        File file = new File(filePath);
        FileInputStream inputStream = new FileInputStream(file);

        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet

        // Iterate through rows and columns to extract data
        for (Row row : sheet) {
            List<String> rowData = new ArrayList<>();
            for (Cell cell : row) {
                rowData.add(cell.getStringCellValue()); // Handle different cell types as needed
            }
            data.add(rowData);
        }

        workbook.close();
        inputStream.close();
        return data;
    }
}
