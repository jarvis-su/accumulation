import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class UpdateResourceDate {

    def fileUrl = "/Users/Jarvis/Documents/testData/test.xls";

    void updateResourceDate(){
        FileInputStream is = new FileInputStream(fileUrl);
        HSSFWorkbook workbook = new HSSFWorkbook(is);
        workbook.setMissingCellPolicy(Row.CREATE_NULL_AS_BLANK);

        //循环sheet
        (0..<workbook.getSheets().iterator().collect {return it}.@size).each {s->
            HSSFSheet sheet = workbook.getSheetAt(s);
            int rows = sheet.physicalNumberOfRows;

            //忽略第一行,标题行
            (1..<rows).each{r->
                HSSFRow row = sheet.getRow(r);
                def cells = row.physicalNumberOfCells;

                (0..<cells).each{c->
                    HSSFCell cell = row.getCell(c);
                    if (c==4 || c==5) {
                        print "  "+getDataCellVal(cell);
                    }else{
                        print "  "+getCellVal(cell);
                    }
                }
                println "";
            }
        }
    }

    //取得日期列
    def getDataCellVal(HSSFCell cell) {
        Date date = cell.getDateCellValue();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    def getCellVal(HSSFCell cell) {
        if (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
            return ""
        } else if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
            return cell.getBooleanCellValue();
        } else if (cell.getCellType() == HSSFCell.CELL_TYPE_ERROR) {
            return cell.getErrorCellValue();
        } else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
            return cell.getNumericCellValue();
        } else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
            return cell.getStringCellValue();
        } else {
            return cell.getStringCellValue();
        }
    }

   public static main(args) {
        UpdateResourceDate a = new UpdateResourceDate();
        a.updateResourceDate();
    }
}