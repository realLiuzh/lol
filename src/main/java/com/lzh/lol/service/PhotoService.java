package com.lzh.lol.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @ClassName PhotoService
 * @Description TODO
 * @Author lzh
 * @Date 2021/8/1 15:26
 */
@Service
public class PhotoService {


    /**
     * @param filename
     * @param response
     * @Description //TODO 传输文件
     * @Date 2021/8/1 15:41
     * @Return void
     */
    public void download(String filename, HttpServletResponse response) throws Exception {
        if (filename != null) {
            //设置文件路径
            String realPath = File.separator + "file" + File.separator;
            File file = new File(realPath, filename);
            if (file.exists()) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
                byte[] buffer = new byte[1024];
                try (
                        FileInputStream fis = new FileInputStream(file);
                        BufferedInputStream bis = new BufferedInputStream(fis);
                ) {
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("success");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
