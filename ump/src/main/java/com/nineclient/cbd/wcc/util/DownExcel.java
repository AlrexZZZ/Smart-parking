package com.nineclient.cbd.wcc.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Border;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;





public class DownExcel {
	
	
	
	public static void main(String args[]){
		 List l = getAllByExcel("c://aaa.xls");
		
		System.out.println("110");
//	 System.out.println(excelSave(null,"aaa"));	;
		
	}
	
	
	
	
    /**
     * 查询指定目录中电子表格中所有的数据
     * @param file 文件完整路径
     * @return
     */
    public static List<?> getAllByExcel(String filePath){
        List<?> list=new ArrayList();
        try {
            Workbook rwb=Workbook.getWorkbook(new File(filePath));
            Sheet rs=rwb.getSheet(0);//或者rwb.getSheet(0)
            int clos=rs.getColumns();//得到所有的列
            int rows=rs.getRows();//得到所有的行
            
            System.out.println(clos+" rows:"+rows);
            for (int i = 1; i < rows; i++) {
                for (int j = 0; j < clos; j++) {
                    //第一个是列数，第二个是行数
                    String id=rs.getCell(j++, i).getContents();//默认最左边编号也算一列 所以这里得j++
                    String name=rs.getCell(j++, i).getContents();
                    String sex=rs.getCell(j++, i).getContents();
                    
                    System.out.println("id:"+id+" name:"+name+" sex:"+sex);
                  //  list.add(new StuEntity(Integer.parseInt(id), name, sex, Integer.parseInt(num)));
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        return list;
        
    }
public static InputStream getExcel() {
	  HSSFWorkbook book=new HSSFWorkbook();
      HSSFSheet sheet=book.createSheet("sheet1");
     
      HSSFRow row=sheet.createRow(0);
      HSSFCell cell=row.createCell((short) 0);
  
cell.setCellValue("编号");
cell=row.createCell((short)1);

cell.setCellValue("姓名");
cell=row.createCell((short)2);

//list为要生成Excel的数据的集合
/*if(list.size()>0){
 for(int i=0;i<list.size();i++){
  Student stu=(Student)list.get(i);
  row=sheet.createRow(i+1);  //i=0,有个list(0),有一行数据
  SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
  
   //    //Id
  //    cell=row.createCell((short)0);
  //    cell.setCellValue(stu.getId());
             
        //编号
        cell=row.createCell((short)0);
        cell.setCellValue(stu.getStuId());
        
        //姓名
        cell=row.createCell((short)1);
        cell.setCellValue(stu.getName());
        
        }
}*/
 ByteArrayOutputStream arrayIo=new ByteArrayOutputStream();
       try {
           book.write(arrayIo);
       } catch (IOException e) {
           e.printStackTrace();
       }
      
       byte[] by=arrayIo.toByteArray();
       InputStream is = new  ByteArrayInputStream(by);
       if(arrayIo !=null){
           try {
               arrayIo.close();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
       return is;

  }
public static String excelSave(List<?> list,String excelName){  
    
    String path="C:\\" + excelName + ".xls";  
    try{  
          
        WritableWorkbook book = Workbook.createWorkbook(new File(  
                path));  
          
        WritableSheet sheet = book.createSheet("sheet_1", 0);  
        jxl.write.WritableFont font1 = new jxl.write.WritableFont(  
                jxl.write.WritableFont.TIMES, 16,  
                jxl.write.WritableFont.BOLD);  
        jxl.write.WritableFont font3 = new jxl.write.WritableFont(  
                jxl.write.WritableFont.TIMES, 10,  
                jxl.write.WritableFont.BOLD);  
        jxl.write.WritableCellFormat CBwcfF1 = new jxl.write.WritableCellFormat(  
                font1);  
        jxl.write.WritableCellFormat CBwcfF2 = new jxl.write.WritableCellFormat();  
        jxl.write.WritableCellFormat CBwcfF3 = new jxl.write.WritableCellFormat(  
                font3);  
        jxl.write.WritableCellFormat CBwcfF4 = new jxl.write.WritableCellFormat();  
        CBwcfF1.setAlignment(jxl.write.Alignment.CENTRE);  
        CBwcfF2.setAlignment(jxl.write.Alignment.RIGHT);  
        CBwcfF3.setAlignment(jxl.write.Alignment.CENTRE);  
        CBwcfF3.setBorder(Border.ALL, jxl.format.BorderLineStyle.THIN,  
                Colour.BLACK);  
        CBwcfF4.setBorder(Border.ALL, jxl.format.BorderLineStyle.THIN,  
                Colour.BLACK);  
      //  Label labelhead16 = new Label(0, 0, "透明球统计信息表", CBwcfF1);  
      
      //  sheet.addCell(labelhead16);  
          
          
//        sheet.mergeCells(0, 0, 12, 0);  
//        sheet.mergeCells(0, 1, 12, 1);  
          
        sheet.setColumnView(0, 25);  
        sheet.setColumnView(1, 25);  
        sheet.setColumnView(2, 25);  
   ///Label labelhead0 = new Label("当前列", "当前行", " 当前单元格的值", "引用的样式");     
        Label labelhead0 = new Label(0, 0, "    序    号", CBwcfF3);  
        Label labelhead1 = new Label(1, 0, "    日    期", CBwcfF3);  
        Label labelhead2 = new Label(2, 0, "    应召数量", CBwcfF3);  
        sheet.addCell(labelhead0);  
        sheet.addCell(labelhead1);  
        sheet.addCell(labelhead2);  
          
//        for (int i = 0; i < list.size(); i++) {  
 /*           ReportCallInfo data=list.get(i);  
              
            Label label0 = new Label(0, i + 3, i + 1 + "", CBwcfF4);  
            Label label1 = new Label(1, i + 3, formatString(data  
                    .getNowday()), CBwcfF4);  
            Label label2 = new Label(2, i + 3, formatString(data  
                    .getCallnum()), CBwcfF4);  
              
              
            sheet.addCell(label0);  
            sheet.addCell(label1);  
            sheet.addCell(label2);  */
//        }  
      //  log.debug("导出excel成功");  
          
          
        book.write();  
        book.close();  
          
    }catch (Exception e) {  
        e.printStackTrace();  
    }  
      
    return path;  
}  



}

