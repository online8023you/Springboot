package com.demo.controller;

import com.demo.aop.MyLogPast;
import com.demo.aop.SysLogPoint;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author Dawn
 * @ClassName com.demo.controller.FileUploadController
 * @Description
 * @date 2019/9/17 10:32
 */

@Api(tags = "文件上传管理")
@RestController
public class FileUploadController {

    @SysLogPoint(actionName = "文件上传")
    @ApiOperation("文件上传")
    @PostMapping(value = "/fileUpload")
    public String fileUpload(@ApiParam(required = true, name = "file", value = "要上传的文件") MultipartFile file) throws IOException {

        String fileName;
        if (!file.isEmpty()) {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String dateStr = sdf.format(date);
/*
            String filePath = request.getServletContext().getRealPath("/") +"/"+ dateStr + file.getOriginalFilename();
*/


            String filePath = "D:/Publish/Excel/" + dateStr + file.getOriginalFilename();
            File outputFile = new File(filePath);
            if (!outputFile.exists()) {
                outputFile.mkdirs();
            }
            file.transferTo(outputFile);
            int position = filePath.lastIndexOf("/");
            fileName = filePath.substring(position + 1);
            return file.getOriginalFilename() + "上传成功";

        } else {
            fileName = "";
            return file.getOriginalFilename() + "上传失败";

        }
    }


    @SysLogPoint(actionName = "批量文件上传")
    @ApiOperation("批量文件上传")
    @PostMapping(value = "/filesUpload")
    public List<String> filesUpload(@ApiParam(required = true, name = "file", value = "要上传的文件")MultipartFile[] files) throws IOException {


        String uploadPath = "D:/Publish/Excel/";
        File uploadFile = new File(uploadPath);
        List<String> results = new ArrayList<>();
        if (!uploadFile.exists()){
            uploadFile.mkdirs();
        }
        for (int i = 0; i < files.length; i++) {
            String fileName;
            if (!files[i].isEmpty()) {
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String dateStr = sdf.format(date);
                String filePath = uploadPath + dateStr + files[i].getOriginalFilename();
                File file = new File(filePath);
                files[i].transferTo(file);
                int position = filePath.lastIndexOf("/");
                fileName = filePath.substring(position + 1);
                results.add(files[i].getOriginalFilename() + "上传成功");

            } else {
                results.add(files[i].getOriginalFilename() + "上传失败");

            }

        }
        /*for (MultipartFile file : files
        ) {
            String fileName;
            if (!file.isEmpty()) {
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String dateStr = sdf.format(date);
*//*
            String filePath = request.getServletContext().getRealPath("/") +"/"+ dateStr + file.getOriginalFilename();
*//*


                String filePath = "D:/Publish/Excel/" + dateStr + file.getOriginalFilename();
                File outputFile = new File(filePath);
                if (!outputFile.exists()) {
                    outputFile.mkdirs();
                }
                file.transferTo(outputFile);
                int position = filePath.lastIndexOf("/");
                fileName = filePath.substring(position + 1);
                return file.getOriginalFilename() + "上传成功";

            } else {
                fileName = "";
                return file.getOriginalFilename() + "上传失败";

            }
        }*/
        return results;
    }

}
