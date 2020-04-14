package com.barclays.hellomoney.client;


import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/*BUSINESS_ID	PRODUCT_CD	PRODUCT_DESC	EFFECTIVE_DTM	CCY	CHANNEL	CUSTOMER_SEGMENT	TENURE_MONTH
TENURE_DAY	AMT_SLAB_FROM	AMT_SLAB_TO	INTEREST_RATE	INTEREST_VARIANCE	PENALTY_RATE	PENALTY_VARIANCE	TENURE_TYPE
*/

public class ReadExcel_C_INTEREST_RATE_MST {
static String sql ="insert into C_INTEREST_RATE_MST (BUSINESS_ID, PRODUCT_CD, EFFECTIVE_DTM, CCY, CHANNEL, CUSTOMER_SEGMENT, TENURE_MONTH," +
		" TENURE_DAY, AMT_SLAB_FROM, AMT_SLAB_TO, INTEREST_RATE, INTEREST_VARIANCE, PENALTY_RATE, PENALTY_VARIANCE, TENURE_TYPE)";
  private static String inputFile;
  public static void main(String[] args) throws IOException, SQLException {
	     String inputFile1 ="C:/softtag/bmgmessages/Copy of td_rates_UAT (2).xls";
	     String inputFile ="C:/softtag/ugandaDBSetup/td_rates_UAT_BBU.xls";

	     readC_INTEREST_RATE_MSTFromExcelSheet(inputFile);
	  }
  public static  void setInputFile(String inputFile) {
        //inputFile = inputFile;
  }
  public HashMap<String, String > keyMap = new HashMap<String, String >();

  public static void readC_INTEREST_RATE_MSTFromExcelSheet(String inputFile)
			throws IOException, SQLException {
		File inputWorkbook = new File(inputFile);
		Workbook w;
		 //DBUtility.prepareDBConnection();
		try {
			w = Workbook.getWorkbook(inputWorkbook);
			// Get the first sheet
			Sheet sheet = w.getSheet(0);
			// Loop over first 10 column and lines
			for (int i = 1; i < sheet.getRows(); i++) {
				StringBuffer sbf = new StringBuffer();
				// System.out.println(" Row number "+(i+1));

				sbf.append(sql + "   " + "values (");
				int columns = sheet.getColumns();
				for (int j = 0; j < columns; j++) {
					/*if (j == 2)
						continue;*/
					if (j == 15)
						continue;
					Cell cell = sheet.getCell(j, i);
					CellType type = cell.getType();
					String cellValue = cell.getContents();
					if(cellValue != null ) {
						cellValue =cellValue.replaceAll("%","");
					}
					if (j == 2) {
						cellValue = "to_timestamp_tz('20-02-13 21:27:09+00:00', 'YYYY-MM-DD HH24:MI:SSTZH:TZM'),";
						//20-FEB-13 21.27.09.000000006
						sbf.append("" + cellValue + "");
						continue;
					}
					if (cellValue == null || cellValue.length() == 0) {
						cellValue = "null";
						if (j == 14) {
							sbf.append("" + cellValue + "");
							break;
						} else {
							sbf.append("" + cellValue + ",");
						}

					} else {
						sbf.append("'" + cellValue + "',");
					}

					if (j == 14) {
						sbf.append("'" + cellValue + "'");
						break;
					}

				}
				 sbf.append(");");
				// System.out.println( sbf );

				  /* try { DBUtility.executeAnySqlInsert(sbf.toString()); } catch
				   (SQLException e) { // TODO Auto-generated catch block
				   e.printStackTrace(); }*/


			}
			 // DBUtility.commit();

			// string = string.substring(0,string.lastIndexOf(")")-2);
			// System.out.println(string);
		} catch (BiffException e) {
			e.printStackTrace();
		}
	}

  public void read() throws IOException {
		File inputWorkbook = new File(inputFile);
		Workbook w;


		try {
			w = Workbook.getWorkbook(inputWorkbook);
			// Get the first sheet
			Sheet sheet = w.getSheet(0);
			// Loop over first 10 column and lines
			for (int i = 1; i < sheet.getRows(); i++) {

				Cell cell = sheet.getCell(0, i);
				CellType type = cell.getType();
				if (type == CellType.LABEL) {
					String contents ="BEM"+ cell.getContents();
					if(keyMap.containsKey(contents)){
                           // System.out.println("Duplicate keys "+contents);
					}
					keyMap.put(contents,contents);
					//System.out.println("BEM"+contents);

				}
			}
		} catch (BiffException e) {
			e.printStackTrace();
		}
	}



}