package com.zen.smartweather.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jack-pc on 2015/9/8.
 */
public class ExcelUtils {

    private ExcelUtils() {
    }

    /**
     * Load Area Ids from excel file in resources
     *
     * @return Set of Area Id
     * @throws IOException Error when reading excel file
     */
    public static Set<String> loadAreaIds() throws IOException {
        Set<String> set = new HashSet<>();
        // load excel file from resources
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try (InputStream is = classloader.getResourceAsStream("areaid_f.xlsx")) {
            if (is == null) {
                throw new IllegalStateException("AreaId's excel file not found");
            }

            // load workbook
            XSSFWorkbook wb = new XSSFWorkbook(is);
            //HSSFWorkbook wb=new HSSFWorkbook(is);
            // first sheet
            Sheet sheet = wb.getSheetAt(0);
            for (Row row : sheet) {
                // skip first row
                if (row.getRowNum() == 0) {
                    continue;
                }
                // only need first column
                Cell cell = row.getCell(0);
                if (cell != null && cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    Double d = cell.getNumericCellValue();
                    String areaId = String.valueOf(d.intValue());
                    if (StringUtils.isNotBlank(areaId)) {
                        set.add(areaId);
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            return set;
        }
    }

    //test
    public static void main(String[] args) throws IOException {
        Set<String> areaIds = ExcelUtils.loadAreaIds();
        System.out.println("迭代出的城市数量为："+areaIds.size());
    }

}
