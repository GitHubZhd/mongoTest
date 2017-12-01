package com.springboot.study.util;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 如果是xls，使用HSSFWorkbook；如果是xlsx，使用XSSFWorkbook
 * Created by ps on 2017/12/1.
 */
public class ExcelUtil {

    /**
     * Excel 2003
     */
    private final static String XLS = "xls";
    /**
     * Excel 2007
     */
    private final static String XLSX = "xlsx";
    /**
     * 分隔符
     */
    private final static String SEPARATOR = "|";

    /**
     * 由Excel文件的Sheet导出至List
     *
     * @param file
     * @param sheetNum
     * @return
     */
    public static List<String> exportListFromExcel(File file, int sheetNum)
            throws IOException {
        return exportListFromExcel(new FileInputStream(file),
                FilenameUtils.getExtension(file.getName()), sheetNum);
    }

    /**
     * 由Excel流的Sheet导出至List
     *
     * @param is
     * @param extensionName 扩展名
     * @param sheetNum
     * @return
     * @throws IOException
     */
    public static List<String> exportListFromExcel(InputStream is,
                                                   String extensionName, int sheetNum) throws IOException {

        Workbook workbook = null;

        if (extensionName.toLowerCase().equals(XLS)) {
            workbook = new HSSFWorkbook(is);
        } else if (extensionName.toLowerCase().equals(XLSX)) {
            workbook = new XSSFWorkbook(is);
        }

        return exportListFromExcel(workbook, sheetNum);
    }

    /**
     * 由指定的Sheet导出至List
     * @param workbook
     * @param sheetNum
     * @return
     */
    private static List<String> exportListFromExcel(Workbook workbook,
                                                    int sheetNum){
        Sheet sheet = workbook.getSheetAt(sheetNum);

        // 解析公式结果
        FormulaEvaluator evaluator = workbook.getCreationHelper()
                .createFormulaEvaluator();

        List<String> list = new ArrayList<>();

        int minRowIx = sheet.getFirstRowNum();
        int maxRowIx = sheet.getLastRowNum();
        for (int rowIx = minRowIx; rowIx <= maxRowIx; rowIx++) {
            Row row = sheet.getRow(rowIx);
            StringBuilder sb = new StringBuilder();

            short minColIx = row.getFirstCellNum();
            short maxColIx = row.getLastCellNum();
            for (short colIx = minColIx; colIx <= maxColIx; colIx++) {
                Cell cell = row.getCell(new Integer(colIx));
                CellValue cellValue = evaluator.evaluate(cell);
                if (cellValue == null) {
                    continue;
                }
                // 经过公式解析，最后只存在Boolean、Numeric和String三种数据类型，此外就是Error了
                // 其余数据类型，根据官方文档，完全可以忽略http://poi.apache.org/spreadsheet/eval.html
                switch (cellValue.getCellTypeEnum()) {
                    case BOOLEAN:
                        sb.append(SEPARATOR + cellValue.getBooleanValue());
                        break;
                    case NUMERIC:
                        // 这里的日期类型会被转换为数字类型，需要判别后区分处理
                        if (DateUtil.isCellDateFormatted(cell)) {
                            sb.append(SEPARATOR + cell.getDateCellValue());
                        } else {
                            //把手机号码转换为字符串
                            DecimalFormat df = new DecimalFormat("#");
                            sb.append(SEPARATOR + df.format(cellValue.getNumberValue()));
                        }
                        break;
                    case STRING:
                        sb.append(SEPARATOR + cellValue.getStringValue());
                        break;
                    case FORMULA:
                        break;
                    case BLANK:
                        break;
                    case ERROR:
                        break;
                    default:
                        break;
                }
            }
            list.add(sb.toString());
        }
        return list;
    }


    public static void main(String[] args) {
        String path = "C:\\Users\\ps\\Desktop\\token\\today.xlsx";
        try {
            List<String> listS= exportListFromExcel(new XSSFWorkbook(new FileInputStream(new File(path))),0);
            for(int i=0;i<listS.size();i++){
                System.out.println(listS.get(i));
            }

            write();
            writexlsx();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public static void writexlsx(){
        //第一步，创建一个workbook对应一个excel文件
        XSSFWorkbook workbook = new XSSFWorkbook();
        //第二部，在workbook中创建一个sheet对应excel中的sheet
        XSSFSheet sheet = workbook.createSheet("用户表二");
        //第三部，在sheet表中添加表头第0行，老版本的poi对sheet的行列有限制
        XSSFRow row = sheet.createRow(0);
        //第四步，创建单元格，设置表头
        XSSFCell cell = row.createCell(0);
        cell.setCellValue("用户名");
        cell = row.createCell(1);
        cell.setCellValue("密码");

        //第五步，写入实体数据，实际应用中这些数据从数据库得到,对象封装数据，集合包对象。对象的属性值对应表的每行的值
        List<String> users=init();
        for (int i = 0; i < users.size(); i++) {
            XSSFRow row1 = sheet.createRow(i + 1);

            //创建单元格设值
            row1.createCell(0).setCellValue("abc"+i);
            row1.createCell(1).setCellValue("def"+i);
        }

        //将文件保存到指定的位置
        try {
            FileOutputStream fos = new FileOutputStream("D:\\user2.xlsx");
            workbook.write(fos);
            System.out.println("写入成功");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void write(){
        //第一步，创建一个workbook对应一个excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        //第二部，在workbook中创建一个sheet对应excel中的sheet
        HSSFSheet sheet = workbook.createSheet("用户表一");
        //第三部，在sheet表中添加表头第0行，老版本的poi对sheet的行列有限制
        HSSFRow row = sheet.createRow(0);
        //第四步，创建单元格，设置表头
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("用户名");
        cell = row.createCell(1);
        cell.setCellValue("密码");

        //第五步，写入实体数据，实际应用中这些数据从数据库得到,对象封装数据，集合包对象。对象的属性值对应表的每行的值
        List<String> users=init();
        for (int i = 0; i < users.size(); i++) {
            HSSFRow row1 = sheet.createRow(i + 1);

            //创建单元格设值
            row1.createCell(0).setCellValue("abc"+i);
            row1.createCell(1).setCellValue("def"+i);
        }

        //将文件保存到指定的位置
        try {
            FileOutputStream fos = new FileOutputStream("D:\\user1.xls");
            workbook.write(fos);
            System.out.println("写入成功");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List init(){

        List<String> list=new ArrayList<>();
        list.add("sdasdas");
        list.add("sdasdas");
        list.add("sdasdas");

        return list;
    }
}
