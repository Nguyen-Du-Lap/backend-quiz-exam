package com.nlu.utils;

import com.nlu.model.dto.response.StudentResponse;
import com.nlu.model.entity.Student;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static org.apache.poi.ss.util.CellUtil.createCell;

public class ExcelStudentPointUtils {
    private List<StudentResponse> students;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private  String filePath;

    public ExcelStudentPointUtils(List<StudentResponse> students, String filePath) {
        this.students = students;
        workbook = new XSSFWorkbook();
        this.filePath = filePath;
    }
    private void writeHeader() {
        sheet = workbook.createSheet("Score students");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "ID", style);
        createCell(row, 1, "code", style);
        createCell(row, 2, "fullname", style);
        createCell(row, 3, "email", style);
        createCell(row, 4, "score", style);
    }

    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else if (valueOfCell instanceof Boolean) {
            cell.setCellValue((Boolean) valueOfCell);
        } else if (valueOfCell instanceof Double) { // Kiểm tra kiểu Double
            cell.setCellValue((Double) valueOfCell); // Không cần ép kiểu, gán giá trị trực tiếp
        }
        cell.setCellStyle(style);
    }

    private void write() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (StudentResponse record: students) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, record.getId(), style);
            createCell(row, columnCount++, record.getCode(), style);
            createCell(row, columnCount++, record.getFullname(), style);
            createCell(row, columnCount++, record.getEmail(), style);
            createCell(row, columnCount++, record.getPoint(), style);
        }
    }
    public ByteArrayResource generateExcelFile() throws IOException {
        writeHeader();
        write();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        workbook.close();
        return new ByteArrayResource(byteArrayOutputStream.toByteArray());
    }
}
