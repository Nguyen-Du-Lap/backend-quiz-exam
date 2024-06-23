package com.nlu.utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.nlu.model.entity.Student;

public class ExcelUtils {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
      static String[] HEADERs = { "ID", "Fullname", "Email"};
      static String SHEET = "student";
      public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
          return false;
        }
        return true;
      }
      public static List<Student> toListStudents(InputStream is) {
        try {
          Workbook workbook = new XSSFWorkbook(is);
          Sheet sheet = workbook.getSheet(SHEET);
          Iterator<Row> rows = sheet.iterator();
          List<Student> stuList = new ArrayList<Student>();
          int rowNumber = 0;
          while (rows.hasNext()) {
            Row currentRow = rows.next();
            // skip header
            if (rowNumber == 0) {
              rowNumber++;
              continue;
            }
            Iterator<Cell> cellsInRow = currentRow.iterator();
            Student stu = new Student();
            stu.setActive(true);
            int cellIndex = 0;
            while (cellsInRow.hasNext()) {
              Cell currentCell = cellsInRow.next();
              switch (cellIndex) {
              case 0:
                  stu.setCode(currentCell.getStringCellValue());
                break;
              case 1:
                  stu.setFullname(currentCell.getStringCellValue());
                break;
              case 2:
                  stu.setEmail(currentCell.getStringCellValue());
                break;
              default:
                break;
              }
              cellIndex++;
            }
            stuList.add(stu);
          }
          workbook.close();
          return stuList;
        } catch (IOException e) {
          throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
      }
      
      public static void main(String[] args) {
    	    try {
    	        File file = new File("test_upload.xlsx");
    	        FileInputStream fis = new FileInputStream(file);
    	        List<Student> students = ExcelUtils.toListStudents(fis);
    	        
    	        for (Student student : students) {
    	            System.out.println(student.toString());
    	        }
    	        
    	        fis.close(); // Đóng luồng sau khi sử dụng xong
    	    } catch (IOException e) {
    	        e.printStackTrace();
    	    }
    	}

}