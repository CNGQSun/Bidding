package com.dsmpharm.bidding.utils;


import org.apache.poi.poifs.filesystem.FileMagic;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class ExcelToTxt {
    private static Logger log = LoggerFactory.getLogger(ExcelToTxt.class);

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        // 如果只有一个Excel文件需要转换成txt时，用File
        File file = getFile_one("E:\\analyticservice\\BIDDING_SYSTEM\\user_province.xlsx");
        if (file.isFile()) {
            System.out.println("File == " + file.getPath() + " " + file.getName());
            publishTxt_one(file.getPath());
        } else {
            System.out.println(file.getPath() + "不是文件路径！");
        }

        // 如果含有多个Excel文件需要转换成txt时，用File[]
        File[] files = getFile_array("D:\\");
        for (int i = 0 ; i < files.length ; i++) {
            // 判断遍历到的files[i]是否为文件
            if (files[i].isFile()) {
                System.out.println("File == " + files[i].getPath() + " " + files[i].getName());
            }
            // 判断遍历到的files[i]是否为目录
            if (files[i].isDirectory()) {
                // 如果是目录的话，需将目录里的文件读出，即调用getFile()方法
                System.out.println("Directory == " + files[i].getPath() + " " + files[i].getName());
                File[] files2 = getFile_array(files[i].getPath());

            }
        }
    }
    // Excel转换成txt
    private static void publishTxt_one(String excelPath) {
        // TODO Auto-generated method stub
        String columns[] = {"AREAID", "NAMEEN", "NAMECN", "DISTRICTEN", "DISTRICTCN", "PROVEN", "PROVCN", "NATIONEC", "NATIONCN"};
        Workbook workbook = null;
        Sheet sheet = null;
        Row row = null;
        List<Map<String, String>> list = null;
        String cellData = null;
        String fileType = excelPath.substring(excelPath.indexOf(".") + 1);

        workbook = readExcel(excelPath);;

        if (workbook != null) {
            // list用来存放表中数据
            list = new ArrayList<Map<String,String>>();
            // sheet用于存第一张表的数据
            sheet = workbook.getSheetAt(0);
            // rownum用于存sheet中的最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            // row获取第二行
            row = sheet.getRow(1);
            // column获取最大列数
            int column = row.getPhysicalNumberOfCells();

            for (int i = 0 ; i < rownum ; i ++) {
                Map<String, String> map = new LinkedHashMap<String, String>();
                row = sheet.getRow(i);

                if (row != null) {
                    for (int j = 0 ; j < column ; j++) {
                        cellData = (String) getCellFormatValue(row.getCell(j));
                        map.put(columns[j], cellData);
                    }
                } else {
                    break;
                }
                list.add(map);
            }
        }
        // 遍历解析出来的list
        StringBuffer stringbuffer =  new StringBuffer();
        for (int i = 0 ; i < list.size() ; i ++) {
            for (Entry<String, String> entry : list.get(i).entrySet()) {
                String value = entry.getValue();
                stringbuffer.append(value + ",");
            }
            stringbuffer.append("\r\n");
        }
        try {
            WriteToTxt(stringbuffer.toString(), excelPath.replace(".xlsx", ".txt"));
        } catch (IOException e) {
            log.error(e.toString(),e);
        }
        System.out.println("----------------Excel转成txt成功！");
    }
    private static void WriteToTxt(String string, String filePath) throws IOException{
        // TODO Auto-generated method stub
        BufferedWriter bufferedwriter = null;
        try {
            FileOutputStream out = new FileOutputStream(filePath, true);
            bufferedwriter = new BufferedWriter(new OutputStreamWriter(out, "GBK"));
            bufferedwriter.write(string += "\r\n");
            bufferedwriter.flush();
        } catch (Exception e) {
            log.error(e.toString(),e);
        } finally {
            bufferedwriter.close();
        }
    }
    // 转换日期格式
    private static Object getCellFormatValue(Cell cell) {
        // TODO Auto-generated method stub
        Object cellValue = null;
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC: {
                    cell.setCellType(CellType.NUMERIC);
                    cellValue = String.valueOf(cell.getNumericCellValue());
                }
                case Cell.CELL_TYPE_FORMULA: {
                    cell.setCellType(CellType.FORMULA);
                    try {
                        // 判断cell是否为日期格式
                        if (DateUtil.isCellDateFormatted(cell)) {
                            // 转换日期格式为YYYY-mm-dd
                            cellValue = cell.getDateCellValue();
                            //cellValue = cell.getRichStringCellValue().getString();
                        } else {
                            // 转换为数据
                            cellValue = String.valueOf(cell.getNumericCellValue());
                        }
                    } catch (Exception e) {
                        log.error(e.toString(),e);
                    }
                }
                case Cell.CELL_TYPE_STRING: {
                    cell.setCellType(CellType.STRING);
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                }
                default :
                    cellValue = "";
            }
        } else {
            cellValue = "";
        }
        return cellValue;
    }
    private static Workbook readExcel(String excelPath) {
        // TODO Auto-generated method stub
        Workbook workbook = null;
        if (excelPath == null) {
            return null;
        }
        BufferedInputStream is = null;
        try {
            //is = new FileInputStream(excelPath);
            is = new BufferedInputStream( new FileInputStream( excelPath ) );
            if (isExcelFile(is)) {
                workbook = new XSSFWorkbook(is);
            } else {
                workbook = null;
            }
        } catch (FileNotFoundException e) {
            log.error(e.toString(),e);
        } catch (IOException e) {
            log.error(e.toString(),e);
        }
        return workbook;
    }
    // 判断文件是否为Excel，用POI3.0及以上的FileMagic类，
    private static boolean isExcelFile(InputStream is) {
        // TODO Auto-generated method stub
        boolean result = false;

        try {
            FileMagic fileMagic = FileMagic.valueOf(is);

            if (Objects.equals(fileMagic, FileMagic.OLE2) || Objects.equals(fileMagic, FileMagic.OOXML)) {
                result = true;
            }
        } catch (IOException e) {
            log.error(e.toString(),e);
        }
        return result;
    }
    private static File getFile_one(String path) {
        // TODO Auto-generated method stub
        File file = new File(path);
        return file;
    }

    private static File[] getFile_array(String path) {
        // TODO Auto-generated method stub
        File file = new File(path);
        File[] array = file.listFiles();
        return array;
    }

}
