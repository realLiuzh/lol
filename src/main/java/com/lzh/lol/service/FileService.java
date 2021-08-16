package com.lzh.lol.service;

import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @ClassName FileService
 * @Description TODO
 * @Author lzh
 * @Date 2021/8/2 17:21
 */
@Service
public class FileService {


    /**
     * @Description //TODO 图片传输
     * @Date 2021/8/2 17:29
     * @param fileName
     * @param response
     * @Return void
     */
    public void getFile(String fileName, HttpServletResponse response) throws UnsupportedEncodingException {
        if (fileName != null) {
            //设置文件路径
            String realPath = File.separator + "lol" + File.separator + "file" + File.separator;
            File file = new File(realPath, fileName);
            if (file.exists()) {
                //response.setContentType("application/force-download");
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                byte[] buffer = new byte[1024];
                try (
                        FileInputStream fis = new FileInputStream(file);
                        BufferedInputStream bis = new BufferedInputStream(fis);
                ) {
                    OutputStream outputStream = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        outputStream.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }


}
