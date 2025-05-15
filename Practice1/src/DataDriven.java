import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class DataDriven {

	public ArrayList<String> getData(String testcase,String sheetName) throws IOException {
		
		FileInputStream fis = new FileInputStream("./InputFile.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		ArrayList<String> list = new ArrayList<String>();
		
		int sheetsCount = workbook.getNumberOfSheets();
		XSSFSheet sheet = null;
		for(int i=0;i<sheetsCount;i++)
		{
			if(workbook.getSheetName(i).equals(sheetName))
			{
				sheet = workbook.getSheetAt(i);
				break;
			}
		}
		
		Iterator<Row> rows = sheet.iterator();
		Row firstrow = rows.next();
		Iterator<Cell> cells = firstrow.cellIterator();
		int k=0,column=0;
		Cell cell;
		while(cells.hasNext())
		{
			cell = cells.next();
			if(cell.getStringCellValue().equals("TestCases"))
			{
				column=k;
				break;
			}
			k++;
		}
		System.out.println(column);
		
		Row row;
		while(rows.hasNext())
		{
			row = rows.next();
			cell = row.getCell(column);
			if(cell.getStringCellValue().equals(testcase))
			{
				cells = row.cellIterator();
			}
		}
		while(cells.hasNext())
		{
			cell = cells.next();
			if(cell.getCellType() == CellType.STRING)
				list.add(cell.getStringCellValue());
			else
				list.add(NumberToTextConverter.toText(cell.getNumericCellValue()));
		}
		
		//System.out.println(sheet.getRow(2).getCell(3).getStringCellValue());
		
		return list;
	}
	
	public static void main(String[] args) throws IOException {
		DataDriven dd = new DataDriven();
		ArrayList<String> list = dd.getData("TestCase3","TestData");
		System.out.println(list.get(0));
		System.out.println(list.get(1));
		System.out.println(list.get(2));
		System.out.println(list.get(3));
	}

}
