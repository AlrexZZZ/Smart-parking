
package com.nineclient.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;









import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelUtil
{

	/**
	 * 导出复合数据到Excel (支持把map中各元素一个sheet)
	 * 
	 * @param title
	 *            各sheet中共同的标题
	 * @param colName
	 *            数据list中对应的列名，顺序与title对应。
	 * @param mutilSheetDataMap
	 *            mutilSheetDataMap中一个map元素对应一个sheet，并sheet名称为map中key
	 * @param os
	 *            输出流
	 */
	public static void exportToExcel(String[] title, String[] colName,
			Map<String, List<Map>> mutilSheetDataMap, OutputStream os)
	{
		if (title == null || title.length == 0)
		{
			throw new NullPointerException("没有定义标题集合，或者没有提供get方法");
		}
		if (colName == null || colName.length == 0)
		{
			throw new NullPointerException("没有定义字段集合，或者没有提供get方法");
		}
		if (mutilSheetDataMap == null)
		{
			throw new NullPointerException("没有定义导出数据结果集合，或者没有提供get方法");
		}
		HSSFWorkbook wwb = null;
		try
		{
			// 创建工作表
			wwb = new HSSFWorkbook();
			HSSFSheet ws = null;
			HSSFRow row = null;
			HSSFCell cell = null;
			String sheetName;
			List<Map> sheetDataList = null;
			Iterator<String> it = mutilSheetDataMap.keySet().iterator();
			while (it.hasNext())
			{
				// 按照sheet数据map的key来创建sheet
				sheetName = it.next();
				ws = wwb.createSheet(replaceStr(sheetName));
				// 获取sheet数据list
				sheetDataList = mutilSheetDataMap.get(sheetName);
				/**
				 * 写标题
				 */
				row = ws.createRow(0);
				for (int m = 0; m < title.length; m++)
				{
					cell = row.createCell((short) m);
					cell.setCellValue(new HSSFRichTextString(title[m]));
				}
				/**
				 * 写数据
				 */
				int rownum = 1;
				for (Map dataMap : sheetDataList)
				{
					row = ws.createRow(rownum);
					for (int j = 0; j < colName.length; j++)
					{
						cell = row.createCell((short) j);
						// 按字段取值
						String columnName = colName[j];
						if(columnName.equals("dateTime")){
							Date date =	(Date) dataMap.get(columnName);
							String dst =DateUtil.YEAR_MONTH_DAY_FORMATER.format(date);
							cell.setCellValue(new HSSFRichTextString(dst));
						}else{
							cell.setCellValue(new HSSFRichTextString(String.valueOf(dataMap
									.get(columnName)==null?"":dataMap.get(columnName))));
						}
						
					}
					rownum++;
				}
			}
			// 写入流
			wwb.write(os);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				os.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	
	
	/**
	 * 导出复合数据到Excel (支持把map中各元素一个sheet)
	 * 
	 * @param title
	 *            各sheet中共同的标题
	 * @param colName
	 *            数据list中对应的列名，顺序与title对应。
	 * @param mutilSheetDataMap
	 *            mutilSheetDataMap中一个map元素对应一个sheet，并sheet名称为map中key
	 * @param os
	 *            输出流
	 */
	public static void getExcelFile(String[] title, String[] colName,
			Map<String, List<Map>> mutilSheetDataMap,String fileName,String wxCtxPath)
	{
		
		if (title == null || title.length == 0)
		{
			throw new NullPointerException("没有定义标题集合，或者没有提供get方法");
		}
		if (colName == null || colName.length == 0)
		{
			throw new NullPointerException("没有定义字段集合，或者没有提供get方法");
		}
		if (mutilSheetDataMap == null)
		{
			throw new NullPointerException("没有定义下载数据结果集合，或者没有提供get方法");
		}
		if (fileName == null || "".equals(fileName))
		{
			throw new NullPointerException("没有定义下载文件");
		}
		File file = null;
		FileOutputStream os = null;
		HSSFWorkbook wwb = null;
		try
		{
			
			file = new File(wxCtxPath);
			if (!file.exists()) {
				file.mkdirs();
			}
		
			File wxFile = new File(wxCtxPath + "/" + fileName);
			os = new FileOutputStream(wxFile);
		
			// 创建工作表
			wwb = new HSSFWorkbook();
			HSSFSheet ws = null;
			HSSFRow row = null;
			HSSFCell cell = null;
			String sheetName;
			List<Map> sheetDataList = null;
			Iterator<String> it = mutilSheetDataMap.keySet().iterator();
			while (it.hasNext())
			{
				// 按照sheet数据map的key来创建sheet
				sheetName = it.next();
				ws = wwb.createSheet(replaceStr(sheetName));
				// 获取sheet数据list
				sheetDataList = mutilSheetDataMap.get(sheetName);
				/**
				 * 写标题
				 */
				row = ws.createRow(0);
				for (int m = 0; m < title.length; m++)
				{
					cell = row.createCell((short) m);
					cell.setCellValue(new HSSFRichTextString(title[m]));
				}
				/**
				 * 写数据
				 */
				int rownum = 1;
				for (Map dataMap : sheetDataList)
				{
					row = ws.createRow(rownum);
					for (int j = 0; j < colName.length; j++)
					{
						cell = row.createCell((short) j);
						// 按字段取值
						String columnName = colName[j];
						if(columnName.equals("dateTime")){
							Date date =	(Date) dataMap.get(columnName);
							String dst =DateUtil.YEAR_MONTH_DAY_FORMATER.format(date);
							cell.setCellValue(new HSSFRichTextString(dst));
						}else{
							cell.setCellValue(new HSSFRichTextString(String.valueOf(dataMap
									.get(columnName)==null?"":dataMap.get(columnName))));
						}
						
					}
					rownum++;
				}
			}
			// 写入流
			wwb.write(os);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				os.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * 导出错误提示到Excel (支持把map中各元素一个sheet)
	 * 
	 * @param title
	 *            各sheet中共同的标题
	 * @param colName
	 *            数据list中对应的列名，顺序与title对应。
	 * @param mutilSheetDataMap
	 *            mutilSheetDataMap中一个map元素对应一个sheet，并sheet名称为map中key
	 * @param os
	 *            输出流
	 */
	public static void exportToErrorExcel(String[] title, String[] colName,
			Map<String, List<Map>> mutilSheetDataMap,OutputStream os)
	{
		if (title == null || title.length == 0)
		{
			throw new NullPointerException("没有定义标题集合，或者没有提供get方法");
		}
		if (colName == null || colName.length == 0)
		{
			throw new NullPointerException("没有定义字段集合，或者没有提供get方法");
		}
		if (mutilSheetDataMap == null)
		{
			throw new NullPointerException("没有定义导出数据结果集合，或者没有提供get方法");
		}
		if (title == null || "".equals(title))
		{
			throw new NullPointerException("没有定义标题集合，或者没有提供get方法");
		}
		
		if (mutilSheetDataMap == null)
		{
			throw new NullPointerException("没有定义导出数据结果集合，或者没有提供get方法");
		}
		HSSFWorkbook wwb = null;
		try
		{
			// 创建工作表
			wwb = new HSSFWorkbook();
			HSSFSheet ws = null;
			HSSFRow row = null;
			HSSFCell cell = null;
			String sheetName;
			List<Map> sheetDataList = null;
			Iterator<String> it = mutilSheetDataMap.keySet().iterator();
			while (it.hasNext())
			{
				// 按照sheet数据map的key来创建sheet
				sheetName = it.next();
				ws = wwb.createSheet(replaceStr(sheetName));
				// 获取sheet数据list
				sheetDataList = mutilSheetDataMap.get(sheetName);
				/**
				 * 写标题
				 */
				row = ws.createRow(0);
				for (int m = 0; m < title.length; m++)
				{
					cell = row.createCell((short) m);
					cell.setCellValue(new HSSFRichTextString(title[m]));
				}
				/**
				 * 写数据
				 */
				int rownum = 1;
				for (Map dataMap : sheetDataList)
				{
					row = ws.createRow(rownum);
					for (int j = 0; j < colName.length; j++)
					{
						cell = row.createCell((short) j);
						// 按字段取值
						String columnName = colName[j];
						cell.setCellValue(new HSSFRichTextString(String.valueOf(dataMap
									.get(columnName)==null?"":dataMap.get(columnName))));
						
					}
					rownum++;
				}
			}
			// 写入流
			wwb.write(os);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				os.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 替换特殊字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceStr(String str)
	{
		String msg = str.replaceAll(":", "").replaceAll("/", "").replaceAll("\\?", "")
				.replaceAll("\\*", "").replaceAll("\\[", "{").replaceAll("\\]", "}");
		if (msg.length() > 31)
		{
			msg = msg.substring(0, 30);
		}
		return msg;
	}

	/**
	 * 根据传入的数组生成excel文件流，每10000行分一页 （使用poi方法）
	 * 
	 * @param title
	 *            表格显示的标题，默认作为每一页的第一行
	 * @param dataList
	 *            结果集数组
	 * @param os
	 *            输出流
	 */
	public static void exportToExcel_poi(String[] title, String[][] dataList,
			OutputStream os)
	{
		// 将结果集转化为Excel输出
		int k = -1;
		HSSFWorkbook wwb = null;
		try
		{
			// 创建工作表
			wwb = new HSSFWorkbook();
			HSSFSheet ws = null;
			HSSFRow row = null;
			HSSFCell cell = null;
			// 逐行添加数据
			for (int i = 0; i < dataList.length; i++)
			{
				// 每10000条记录分一页
				if (i / 10000 > k)
				{
					k = i / 10000;
					ws = wwb.createSheet("Sheet" + k);
					row = ws.createRow(0);
					// 在每页的第一行输入标题
					for (int l = 0; l < title.length; l++)
					{
						cell = row.createCell((short) l);
						cell.setCellValue(new HSSFRichTextString(title[l]));
					}
				}
				// 输出数据
				for (int j = 0; j < dataList[i].length; j++)
				{
					row = ws.createRow(i - 10000 * k + 1);
					cell = row.createCell((short) j);
					cell.setCellValue(new HSSFRichTextString(dataList[i][j]));
				}
			}
			wwb.write(os);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				os.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * 传入excel文件，解析后返回ArrayList
	 * 
	 * @param file
	 * @param startIndex
	 *            从第几行开始解析
	 */
	public static ArrayList<ArrayList> importExcel_poi(File file, int startIndex)
	{
		ArrayList<ArrayList> arr = new ArrayList<ArrayList>();
		// 初始化
		FileInputStream readFile = null;
		HSSFWorkbook wb = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		try
		{
			// 读取文件
			readFile = new FileInputStream(file);
			wb = new HSSFWorkbook(readFile);
			// 文档的页数
			int numOfSheets = wb.getNumberOfSheets();
			System.out.println("numOfSheets:" + numOfSheets);
			for (int k = 0; k < numOfSheets; k++)
			{
				// 获取当前的
				HSSFSheet st = wb.getSheetAt(k);
				// 当前页的行数
				int rows = st.getLastRowNum();
				// 分行解析
				for (int i = startIndex; i <= rows; i++)
				{
					// 行为空则执行下一行
					if (st.getRow(i) == null)
					{
						continue;
					}
					row = st.getRow(i);
					int cells = row.getLastCellNum();
					ArrayList<String> data = new ArrayList<String>();
					// 分列
					for (int j = 0; j < cells; j++)
					{
						// 列为空则输入空字符串
						if (row.getCell((short) j) == null)
						{
							data.add("");
							continue;
						}
						cell = row.getCell((short) j);
						// 对字段分类处理
						switch (cell.getCellType())
						{
							case HSSFCell.CELL_TYPE_NUMERIC:
							{
								Integer num = new Integer((int) cell
										.getNumericCellValue());
								data.add(String.valueOf(num));
								break;
							}
							case HSSFCell.CELL_TYPE_STRING:
							{
								data.add(cell.getRichStringCellValue().toString());
								break;
							}
							default:
								data.add("");
						}
					}
					arr.add(data);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				readFile.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return arr;
	}

	/**
	 * 传入excel文件，解析后返回ArrayList。文件的第一行表示字段的名称
	 * 
	 * @param file
	 */
	public static ArrayList<Map> importExcel_poi(File file)
	{
		ArrayList<Map> arr = new ArrayList<Map>();
		String[] title;
		// 初始化
		FileInputStream readFile = null;
		HSSFWorkbook wb = null;
		HSSFRow row = null;
		HSSFSheet st = null;
		HSSFCell cell = null;
		try
		{
			// 读取文件
			readFile = new FileInputStream(file);
			wb = new HSSFWorkbook(readFile);
			// 文档的页数
			int numOfSheets = wb.getNumberOfSheets();
			System.out.println("numOfSheets:" + numOfSheets);
			for (int k = 0; k < numOfSheets; k++)
			{
				// 获取当前的
				st = wb.getSheetAt(k);
				// 当前页的行数
				int rows = st.getLastRowNum();
				// 如果行数大于0，则先取第一行为字段名
				if (rows > 0)
				{
					row = st.getRow(0);
					int cells = row.getLastCellNum();
					title = new String[cells];
					for (int j = 0; j < cells; j++)
					{
						// 列为空则输入空字符串
						if (row.getCell((short) j) == null)
						{
							title[j] = "";
							continue;
						}
						cell = row.getCell((short) j);
						switch (cell.getCellType())
						{
							case HSSFCell.CELL_TYPE_NUMERIC:
							{
								Integer num = new Integer((int) cell
										.getNumericCellValue());
								title[j] = String.valueOf(num);
								break;
							}
							case HSSFCell.CELL_TYPE_STRING:
							{
								title[j] = cell.getRichStringCellValue().toString();
								break;
							}
							default:
								title[j] = "";
						}
					}
					// 分行解析
					for (int i = 1; i <= rows; i++)
					{
						// 行为空则执行下一行
						if (st.getRow(i) == null)
						{
							continue;
						}
						// 将每行数据放入map中
						row = st.getRow(i);
						arr.add(getCellMap(row, cells, title));
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				readFile.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return arr;
	}

	

	/**
	 * 根据传入的ｅｘｃｅｌ行数据得到数据Ｍａｐ
	 * 
	 * @param row
	 * @param cells
	 * @param title
	 * @return
	 */
	private static Map getCellMap(HSSFRow row, int cells, String[] title)
	{
		DecimalFormat df = new DecimalFormat("#");
		// 初始化
		HSSFCell cell = null;
		Map<String, String> data = new HashMap<String, String>();
		// 分列
		for (int j = 0; j < cells; j++)
		{
			// 列为空则输入空字符串
			if (row.getCell((short) j) == null)
			{
				data.put(title[j], "");
				continue;
			}
			cell = row.getCell((short) j);
			// 对字段分类处理
			switch (cell.getCellType())
			{
				case HSSFCell.CELL_TYPE_NUMERIC:
				{
					data.put(title[j], df.format(cell.getNumericCellValue()));
					break;
				}
				case HSSFCell.CELL_TYPE_STRING:
				{
					data.put(title[j], cell.getRichStringCellValue().toString());
					break;
				}
				default:
					data.put(title[j], "");
			}
		}
		return data;
	}

	/**
	 * 返回数组中有几个重复纪录
	 * 
	 * @param arr
	 * @param value
	 * @return
	 */
	private static int checkExit(String[] arr, String value)
	{
		int j = 0;
		for (int i = 0; i < arr.length; i++)
		{
			if (arr[i] != null && value.equals(arr[i]))
			{
				j++;
			}
		}
		return j;
	}

	/**
	 * 导出复合数据到Excel (支持把map中各元素一个sheet)
	 * 
	 * @param title
	 *            各sheet中各个对应的标题
	 * @param colName
	 *            数据list中对应的列名，顺序与title对应。
	 * @param mutilSheetDataMap
	 *            mutilSheetDataMap中一个map元素对应一个sheet，并sheet名称为map中key
	 * @param os
	 *            输出流
	 */
	public static void exportToExcel_templete(String[] title,
			OutputStream os)
	{
		if (title == null || title.length == 0)
		{
			throw new NullPointerException("没有定义标题集合，或者没有提供get方法");
		}
		HSSFWorkbook wwb = null;
		try
		{
			// 创建工作表
			wwb = new HSSFWorkbook();
			HSSFSheet ws =wwb.createSheet() ;
			HSSFRow row = null;
			HSSFCell cell = null;
			row = ws.createRow(0);
			for (int m = 0; m < title.length; m++)
			{
				cell = row.createCell((short) m);
				cell.setCellValue(new HSSFRichTextString(title[m]));
			}
			// 写入流
			wwb.write(os);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				os.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 * @param file
	 * @return
	 */
	public static List<Map<String,Object>> parseExcel(InputStream in){

		ArrayList<Map<String,Object>> arr = new ArrayList<Map<String,Object>>();
		String[] title;
		// 初始化
		HSSFWorkbook wb = null;
		HSSFRow row = null;
		HSSFSheet st = null;
		HSSFCell cell = null;
		try
		{
			// 读取文件
			wb = new HSSFWorkbook(in);
			// 文档的页数
			int numOfSheets = wb.getNumberOfSheets();
			System.out.println("numOfSheets:" + numOfSheets);
			for (int k = 0; k < numOfSheets; k++)
			{
				// 获取当前的
				st = wb.getSheetAt(k);
				// 当前页的行数
				int rows = st.getLastRowNum();
				// 如果行数大于0，则先取第一行为字段名
				if (rows > 0)
				{
					row = st.getRow(0);
					int cells = row.getLastCellNum();
					title = new String[cells];
					for (int j = 0; j < cells; j++)
					{
						// 列为空则输入空字符串
						if (row.getCell((short) j) == null)
						{
							title[j] = "";
							continue;
						}
						cell = row.getCell((short) j);
						switch (cell.getCellType())
						{
							case HSSFCell.CELL_TYPE_NUMERIC:
							{
								title[j] = cell.getRichStringCellValue().toString();
								break;
							}
							case HSSFCell.CELL_TYPE_STRING:
							{
								title[j] = cell.getRichStringCellValue().toString();
								break;
							}
							default:
								title[j] = "";
						}
					}
					// 分行解析
					for (int i = 1; i <= rows; i++)
					{
						// 行为空则执行下一行
						if (st.getRow(i) == null)
						{
							continue;
						}
						// 将每行数据放入map中
						row = st.getRow(i);
						arr.add(getCellMap(row, cells, title));
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(in!=null)
				in.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return arr;
	
	}
	
	
	/**
	 * 
	 * @param file
	 * @return
	 */
	public static List<Map<String,Object>> parseCheckExcel(InputStream in){

		ArrayList<Map<String,Object>> arr = new ArrayList<Map<String,Object>>();
		String[] title;
		// 初始化
		HSSFWorkbook wb = null;
		HSSFRow row = null;
		HSSFSheet st = null;
		HSSFCell cell = null;
		try
		{
			// 读取文件
			wb = new HSSFWorkbook(in);
			// 文档的页数
			int numOfSheets = wb.getNumberOfSheets();
			System.out.println("numOfSheets:" + numOfSheets);
			for (int k = 0; k < numOfSheets; k++)
			{
				// 获取当前的
				st = wb.getSheetAt(k);
				// 当前页的行数
				int rows = st.getLastRowNum();
				// 如果行数大于0，则先取第一行为字段名
				if (rows > 0)
				{
					row = st.getRow(0);
					int cells = row.getLastCellNum();
					title = new String[cells];
					for (int j = 0; j < cells; j++)
					{
						// 列为空则输入空字符串
						if (row.getCell((short) j) == null)
						{
							title[j] = "";
							continue;
						}
						cell = row.getCell((short) j);
						switch (cell.getCellType())
						{
							case HSSFCell.CELL_TYPE_NUMERIC:
							{
								title[j] = cell.getRichStringCellValue().toString();
								break;
							}
							case HSSFCell.CELL_TYPE_STRING:
							{
								title[j] = cell.getRichStringCellValue().toString();
								break;
							}
							default:
								title[j] = "";
						}
					}
					// 分行解析
					for (int i = 1; i <= rows; i++)
					{
						// 行为空则执行下一行
						if (st.getRow(i) == null)
						{
							continue;
						}
						// 将每行数据放入map中
						row = st.getRow(i);
						arr.add(getCellCheckMap(row, cells, title));
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(in!=null)
				in.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return arr;
	}
	
	
	/**
	 * 根据传入的ｅｘｃｅｌ行数据得到数据Ｍａｐ
	 * 
	 * @param row
	 * @param cells
	 * @param title
	 * @return
	 */
	private static Map getCellCheckMap(HSSFRow row, int cells, String[] title)
	{
		// 初始化
		HSSFCell cell = null;
		Map<String, String> data = new HashMap<String, String>();
		// 分列
		for (int j = 0; j < cells; j++)
		{
			// 列为空则输入空字符串
			if (row.getCell((short) j) == null)
			{
				data.put(title[j], "");
				continue;
			}
			cell = row.getCell((short) j);
			// 对字段分类处理
			switch (cell.getCellType())
			{
				case HSSFCell.CELL_TYPE_NUMERIC:
				{
					//Integer num = new Integer((int) cell.getNumericCellValue());
					data.put(title[j], String.valueOf(cell.getNumericCellValue()));
					break;
				}
				case HSSFCell.CELL_TYPE_STRING:
				{
					data.put(title[j], cell.getRichStringCellValue().toString());
					break;
				}
				default:
					data.put(title[j], "");
			}
		}
		return data;
	}
}
