package com.wang.vueadmin.utils;

import com.wang.vueadmin.pojo.Item;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;


public class ToExclUtils {
    public ToExclUtils() {

    }


    public static HSSFCell getCell(HSSFSheet sheet, int row, int col) {

        HSSFRow sheetRow = sheet.getRow(row);

        if (sheetRow == null) {

            sheetRow = sheet.createRow(row);

        }

        HSSFCell cell = sheetRow.getCell(col);

        if (cell == null) {

            cell = sheetRow.createCell(col);

        }

        return cell;

    }


    public static void setText(HSSFCell cell, String text) {

        cell.setCellType(CellType.STRING);

        cell.setCellValue(text);

    }


    public static void toExcel(HttpServletResponse response, List<String> titles, List<Item> list) throws Exception {

        HSSFWorkbook wb = new HSSFWorkbook(); // 定义一个新的工作簿

        HSSFSheet sheet = wb.createSheet("人员信息"); // 创建第一个Sheet页

        // 第四步，创建单元格，并设置值表头 设置表头居中

        HSSFCellStyle style = wb.createCellStyle();

        style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式

        HSSFRow row = sheet.createRow(0); // 创建一个行

        HSSFCell cell = row.createCell(0); // 创建一个单元格 第1列
        // cell.setCellValue(new Date()); // 给单元格设置值

        for (int i = 0; i < titles.size(); i++) { // 设置标题

            String title = titles.get(i);

            cell = getCell(sheet, 0, i);

            setText(cell, title);

            cell.setCellStyle(style);

        }

        int rowadd = 2;
        int rownum = 1;
        for (int i = 0; i < list.size(); i++) {
            HSSFRow rows = sheet.createRow(rownum);
            rows.createCell(0).setCellValue(list.get(i).getId());
            rows.createCell(1).setCellValue(list.get(i).getName());
            rows.createCell(2).setCellValue(list.get(i).getGender());
            rows.createCell(7).setCellValue(list.get(i).getAddress());
            rows.createCell(3).setCellValue(null != list.get(i).getIdCard() ? list.get(i).getIdCard() : "");
            rows.createCell(4).setCellValue(null != list.get(i).getAge() ? list.get(i).getAge() : 0);
            rows.createCell(5).setCellValue(null != list.get(i).getPhone() ? list.get(i).getPhone() : "");
            rows.createCell(6).setCellValue(null != list.get(i).getCity() ? list.get(i).getCity() : "");
            rows.createCell(8).setCellValue(null != list.get(i).getIsOut() ? list.get(i).getIsOut() : "");
            rows.createCell(9).setCellValue(null != list.get(i).getIsHost() ? list.get(i).getIsHost() : "");
//            FileOutputStream fileOut = null;
//            BufferedImage bufferImg = null;
//            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
//
//            bufferImg = ImageIO.read(new File("C:\\Users\\laowang\\Desktop\\微信图片_20201101151436.jpg"));
//            ImageIO.write(bufferImg, "jpg", byteArrayOut);
//            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
//            HSSFClientAnchor hssfClientAnchor = new HSSFClientAnchor(0, 0, 1023, 100, (short) 2, rowadd, (short) 5, rowadd+6);
//            patriarch.createPicture(hssfClientAnchor,wb.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG));
//            rowadd = rowadd+9;
//            rownum = rownum +9;
            rownum++;
        }
        String fileName = new String("人员信息表.xls".getBytes(), "ISO8859-1");
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
        OutputStream os = response.getOutputStream();
//        FileOutputStream fileOut = new FileOutputStream("D://成员信息表.xlsx");

        wb.write(os);
        os.flush();
        os.close();
        wb.close();
    }

}
