/*
*@包名：com.b505.tools        
*@文档名：ImportData.java 
*@功能：导入数据的工具类     
*@作者：李振强        
*@创建时间：2014.4.20   
*@版权：河北北方学院信息技术研究所 
*/
package com.b505.tools;



import org.apache.poi.hssf.usermodel.HSSFCell;



import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.springframework.stereotype.Component;

@Component
public class ImportData {
	public  String getCellValue(XSSFCell xssfCell){  
	        String value = null;  
	        //简单的查检列类型  
	        switch(xssfCell.getCellType())  
	        {  
	            case HSSFCell.CELL_TYPE_STRING://字符串  
	                value = xssfCell.getRichStringCellValue().getString();  
	                break;  
	            case HSSFCell.CELL_TYPE_NUMERIC://数字  
	                long dd = (long)xssfCell.getNumericCellValue();  
	                value = dd+"";  
	                break;  
	            case HSSFCell.CELL_TYPE_BLANK:  
	                value = "";  
	                break;     
	            case HSSFCell.CELL_TYPE_FORMULA:  
	                value = String.valueOf(xssfCell.getCellFormula());  
	                break;  
	            case HSSFCell.CELL_TYPE_BOOLEAN://boolean型值  
	                value = String.valueOf(xssfCell.getBooleanCellValue());  
	                break;  
	            case HSSFCell.CELL_TYPE_ERROR:  
	                value = String.valueOf(xssfCell.getErrorCellValue());  
	                break;  
	            default:  
	                break;  
	        }  
	        return value;  
	    }  
	
	/**   
	* 获取合并单元格的值   
	* @param sheet   
	* @param row   
	* @param column   
	* @return   
	*/    
	public String getMergedRegionValue(Sheet sheet ,int row , int column){    
	    int sheetMergeCount = sheet.getNumMergedRegions();    
	        
	    for(int i = 0 ; i < sheetMergeCount ; i++){    
	        CellRangeAddress ca = sheet.getMergedRegion(i);    
	        int firstColumn = ca.getFirstColumn();    
	        int lastColumn = ca.getLastColumn();    
	        int firstRow = ca.getFirstRow();    
	        int lastRow = ca.getLastRow();    
	            
	        if(row >= firstRow && row <= lastRow){    
	                
	            if(column >= firstColumn && column <= lastColumn){    
	                Row fRow = sheet.getRow(firstRow);    
	                Cell fCell = fRow.getCell(firstColumn);    
	                return getCellValue1(fCell);    
	            }    
	        }    
	    }    
	        
	    return null ;    
	} 
	  /**   
	    * 获取单元格的值   
	    * @param cell   
	    * @return   
	    */    
	    public String getCellValue1(Cell cell){    
	            
	        if(cell == null) return "";    
	            
	        if(cell.getCellType() == Cell.CELL_TYPE_STRING){    
	                
	            return cell.getStringCellValue();    
	                
	        }else if(cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){    
	                
	            return String.valueOf(cell.getBooleanCellValue());    
	                
	        }else if(cell.getCellType() == Cell.CELL_TYPE_FORMULA){    
	                
	            return cell.getCellFormula() ;    
	                
	        }else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){    
	                
	            return String.valueOf(cell.getNumericCellValue());    
	                
	        }    
	        return "";    
	    }    
	    
	    /**  
		* 判断指定的单元格是否是合并单元格  
		* @param sheet   
		* @param row 行下标  
		* @param column 列下标  
		* @return  
		*/  
		
		public boolean isMergedRegion(Sheet sheet,int row ,int column) {  
		  int sheetMergeCount = sheet.getNumMergedRegions();  
		  for (int i = 0; i < sheetMergeCount; i++) {  
		CellRangeAddress range = sheet.getMergedRegion(i);  
		int firstColumn = range.getFirstColumn();  
		int lastColumn = range.getLastColumn();  
		int firstRow = range.getFirstRow();  
		int lastRow = range.getLastRow();  
		if(row >= firstRow && row <= lastRow){  
		if(column >= firstColumn && column <= lastColumn){  
		return true;  
		}  
		}  
		  }  
		  return false;  
		} 
		
		/**  
		* 判断合并了行  
		* @param sheet  
		* @param row  
		* @param column  
		* @return  
		*/  
		@SuppressWarnings("unused")
		private boolean isMergedRow(Sheet sheet,int row ,int column) {  
		  int sheetMergeCount = sheet.getNumMergedRegions();  
		  for (int i = 0; i < sheetMergeCount; i++) {  
		CellRangeAddress range = sheet.getMergedRegion(i);  
		int firstColumn = range.getFirstColumn();  
		int lastColumn = range.getLastColumn();  
		int firstRow = range.getFirstRow();  
		int lastRow = range.getLastRow();  
		if(row == firstRow && row == lastRow){  
		if(column >= firstColumn && column <= lastColumn){  
		return true;  
		}  
		}  
		  }  
		  return false;  
		}  
		
}
