package com.demoApp.utilities;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelFile {

	public static FileInputStream fileInputStream;
	public static XSSFWorkbook workbook;
	public static XSSFSheet sheet;
	public static XSSFRow row;
	public static XSSFCell cell;
	
	public static String getCellValue(String fileName, String sheetName, int rowNum, int cellNum) throws IOException {
		fileInputStream = new FileInputStream(fileName);

		//create XSSFWorkBook Class object for excel file manipulation
		workbook = new XSSFWorkbook(fileInputStream);
		sheet = workbook.getSheet(sheetName);
		cell = workbook.getSheet(sheetName).getRow(rowNum).getCell(cellNum);
		
		workbook.close();
		
		return cell.getStringCellValue();		
		
	}
	

	public static int getRowCount(String fileName, String sheetName) throws IOException {
		fileInputStream = new FileInputStream(fileName);
		workbook = new XSSFWorkbook(fileInputStream);
		sheet = workbook.getSheet(sheetName);
		
		//get number of rows
		int rows = sheet.getLastRowNum() + 1;
		
		workbook.close();
		
		return rows;
		
	}
	
	
	public static int getColumnCount(String fileName, String sheetName) throws IOException {
		fileInputStream = new FileInputStream(fileName);
		workbook = new XSSFWorkbook(fileInputStream);
		sheet = workbook.getSheet(sheetName);
		
		//get number of columns
		int columns = sheet.getRow(0).getLastCellNum();
		
		workbook.close();
		
		return columns;
		
	}
	
	
}
