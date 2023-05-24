package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.UUID;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/5/24 11:05
 **/


@RestController
@RequestMapping("/admin/common")
@Slf4j
@ApiOperation("")
public class CommonController {
    @Resource
    private AliOssUtil aliOssUtil;



    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {

        int extendIndex = file.getOriginalFilename().lastIndexOf(".");
        String extendString = file.getOriginalFilename().substring(extendIndex);
        String ObjectName = UUID.randomUUID().toString() + extendString;
        System.out.println(ObjectName);
        String upload = aliOssUtil.upload(file.getBytes(), ObjectName);
        return Result.success(upload);
    }


}
