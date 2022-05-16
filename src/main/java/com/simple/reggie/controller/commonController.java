package com.simple.reggie.controller;

import com.simple.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/common")
@Slf4j
class commonController {
    @Value("${reggieDemo.path}")
    private String basePath;

    @PostMapping("/upload")
    // Content-Disposition: form-data; name="file"; filename="Snipaste_2022-05-15_10-48-06.png"
    public R<String> upload(MultipartFile file) { // file 必须和该form-data的那么属性对上
        String originalFilename = file.getOriginalFilename();

        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        String str = UUID.randomUUID().toString() + suffix;
        if(!new File(basePath).exists()) {
            new File(basePath).mkdirs();
        }
        log.info(basePath);
        try {
            file.transferTo(new File(basePath + str));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(str);
    }
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));

            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }
            fileInputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
