/*
 *@包名：com.b505.web        
 *@文档名：ImportDataController.java 
 *@功能：导入数据     
 *@作者：李振强        
 *@创建时间：2014.4.20   
 *@版权：河北北方学院信息技术研究所 
 */
package com.b505.web;

import java.io.FileInputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.b505.bean.Roll_Alert;
import com.b505.bean.Roll_Alert_Head;
import com.b505.bean.Student;
import com.b505.tools.AnalyzeData;
import com.b505.tools.DataProcess;
import com.b505.tools.ImportData;
import com.b505.tools.ReturnStatus;
import com.b505.tools.TryCatchRollAlertHeadService;
import com.b505.tools.TryCatchRollAlertService;
import com.b505.tools.TryCatchStudentService;

@Controller
public class ImportDataController {
	@Autowired
	private ReturnStatus returnStatus;
	@Autowired
	private DataProcess dataProcess;
	@Autowired
	private AnalyzeData analyzeData;
	@Autowired
	private ImportData importData;
	@Autowired
	private TryCatchStudentService tryCatchStudentService;
	@Autowired
	private TryCatchRollAlertService tryCatchRollAlertService;
	@Autowired
	private TryCatchRollAlertHeadService tryCatchRollAlertHeadService;

	@RequestMapping(value ="/importData.html")
	public ModelAndView getStudentData(@RequestParam("file") CommonsMultipartFile file)throws IOException{
		List<Student> dataList = new ArrayList<Student>();
		try (FileInputStream fin = (FileInputStream) file.getInputStream()) {
			
			if(fin==null||"".equals(fin)){
				return new ModelAndView("ImportDataManager", "error", "您还没有选择文件或数据格式不正确");
			}
			// 创建对Excel工作簿文件的引用
			XSSFWorkbook wookbook = new XSSFWorkbook(fin);
			// 在Excel文档中，第一张工作表的缺省索引是0
			XSSFSheet sheet = wookbook.getSheet("Sheet1");
			// 获取到Excel文件中的所有行数
			int rows = sheet.getPhysicalNumberOfRows();
			// 遍历行
			for (int i = 0; i < rows; i++) {
				// 读取左上端单元格
				XSSFRow row = sheet.getRow(i);
				// 行不为空
				Student student = new Student();
				if (importData.getCellValue(row.getCell(0)) != null
						&& !"".equals(importData.getCellValue(row.getCell(0)))) {
					student.setId(importData.getCellValue(row.getCell(0)));
				}
				if (importData.getCellValue(row.getCell(1)) != null
						&& !"".equals(importData.getCellValue(row.getCell(1)))) {
					student.setStudentName(importData.getCellValue(row
							.getCell(1)));
				}
				if (importData.getCellValue(row.getCell(2)) != null
						&& !"".equals(importData.getCellValue(row.getCell(2)))) {
					student.setStudentCardId(importData.getCellValue(row
							.getCell(2)));
				}
				dataList.add(student);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			return new ModelAndView("ImportDataManager", "error", "您选择正确的文件格式或数据格式不正确");
		}
		if (dataProcess.dataIsNull(dataList)
				|| dataProcess.listHasNull(dataList)) {
			
		}
		final int dataListSize = dataList.size();
		
		for (int i = 0; i < dataListSize; i++) {
			Student student = new Student();
			student = dataList.get(i);
			if (!tryCatchStudentService.saveStudent(student)) {
				
				return new ModelAndView("ImportDataManager", "error", "数据格式不正确或学生信息已存在!");
			}
		}
		return new ModelAndView("ImportDataManager", "success", "插入成功!");
	}
	/**
	 * 读取“.xls”格式使用  import org.apache.poi.hssf.usermodel.*;包的内容，例如：HSSFWorkbook
	 * 读取“.xlsx”格式使用 import org.apache.poi.xssf.usermodel.*; 包的内容，例如：XSSFWorkbook
	 * 读取两种格式使用    import org.apache.poi.ss.usermodel.*    包的内容，例如：Workbook
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/importRollAlertData.html")
	public ModelAndView getRollAlertData(@RequestParam("file") CommonsMultipartFile file)throws IOException {
		     List<Roll_Alert> datalist=new ArrayList<Roll_Alert>();
		     //得到输入流
		     try(FileInputStream fin=(FileInputStream) file.getInputStream())
			{
				System.out.println("fin----->"+fin);
				if (fin==null||"".equals(fin))
				{
					return new ModelAndView("ImportDataManager", "error", "您还没有选择文件或数据格式不正确");
				}
				//HSSF － 提供读写Microsoft Excel XLS格式档案的功能。 
		        //XSSF － 提供读写Microsoft Excel OOXML XLSX格式档案的功能。
				// 创建对Excel工作簿文件的引用
				//Workbook wookbook = new XSSFWorkbook(fin);
				Workbook wookbook=null;
				try {
					//这种方式 Excel 2003/2007/2010 都是可以处理的
					wookbook = WorkbookFactory.create(fin);
				    } catch (InvalidFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				   }
				//在excel文档中，创建第一个工作表
				Sheet sheet = wookbook.getSheet("Sheet1");
				String heads = importData.getMergedRegionValue(sheet, 0, 0); 
				Roll_Alert_Head r_Allert_Hd = new Roll_Alert_Head();
				r_Allert_Hd.setHeading(heads);
				if (!"".equals(heads)&&heads!=null)
				{
					if (!tryCatchRollAlertHeadService.saveRoleAlertHead(r_Allert_Hd))
					{
						return new ModelAndView("ImportDataManager", "error", "数据格式不正确或该信息已存在!");
					}
				}else{
					return new ModelAndView("ImportDataManager", "error", "系统繁忙，请稍后再试!");
				}
				//获取标题的最大id号
				int id=tryCatchRollAlertHeadService.getMaxid();
				//获取总行数 
			    int rows=sheet.getPhysicalNumberOfRows();
			    Row row=null;
			    for(int i=2;i<=rows;i++){
			    	Roll_Alert rA = new Roll_Alert();
			    	row=sheet.getRow(i);
			    	//对变量进行判断
			    	if (row == null) {
		                  continue;
		              }
			    	for (Cell c : row){
			    		//判断是否具有合并单元格  
			    		boolean isMerge = importData.isMergedRegion(sheet, i, c.getColumnIndex()); 
			    		if (isMerge){    
			    			//获取合并单元格的值
			    			String rs = importData.getMergedRegionValue(sheet, row.getRowNum(), c.getColumnIndex());
						   
						  //由c.getColumnIndex()判断是哪一列				
							//判断合并的单元格是哪一列，并存入数据库
						    if (c.getColumnIndex()==0)
							{ 
						    	//student表和Roll_Alert表是一对一关联，所以new一个新的student对象
						    	Student sd=new Student();
						    	sd.setId(rs);
						    	rA.setStudent(sd);
							}else if (c.getColumnIndex()==1) {
								rA.setName(rs);
							}else if(c.getColumnIndex()==7) {
								rA.setOpinion(rs);
							}
						
			    		}else {
			    			//未合并的单元格
							if(c.getColumnIndex()==2){
								
								row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
								String score=c.getStringCellValue();
								
								rA.setScore(score);
								
							}else if(c.getColumnIndex()==3){
								String Course=c.getStringCellValue();
								
								rA.setCourse(Course);
							}else{
								String Coursetype=c.getStringCellValue();
								
							    rA.setCoursetype(Coursetype);
							}
						}
					}
			    	rA.setHeadid(id);
					datalist.add(rA);
				 }
		} catch(IOException e)
			{
				// TODO: handle exception
				System.out.println(e);
				return new ModelAndView("ImportDataManager", "error", "抛出异常！");
			}
		if (dataProcess.listHasNull(datalist)||dataProcess.dataIsNull(datalist))
		{
			return new ModelAndView("ImportDataManager", "error", "没有读取到数据！");
		}
		final int datalistSize = datalist.size();
		for (int i = 0; i < datalistSize; i++) {
			Roll_Alert r_Alert = new Roll_Alert();
			r_Alert = datalist.get(i);
			if (!tryCatchRollAlertService.saveRoleAlert(r_Alert)) {
				
				return new ModelAndView("ImportDataManager", "error", "数据格式不正确或该信息已存在!");
			}
		
	    }
		 return new ModelAndView("ImportDataManager", "error", "获取成功!");
   }
	
}
