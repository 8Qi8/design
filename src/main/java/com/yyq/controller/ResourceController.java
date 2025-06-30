package com.yyq.controller;

import com.yyq.common.constant.MessageConstant;
import com.yyq.common.result.Result;
import com.yyq.common.utils.AliOssUtil;
import com.yyq.pojo.entity.Resource;
import com.yyq.pojo.vo.ResourceVO;
import com.yyq.service.IResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/resource")
public class ResourceController {
    @Autowired
    private IResourceService resourceService;
    @Autowired
    private AliOssUtil aliOssUtil;
    //上传资源
    @PostMapping
    public Result<String> upload(@RequestParam("file") MultipartFile file,
                                 @RequestParam("uploaderId") Integer uploaderId,
                                 @RequestParam("title") String title,
                                 @RequestParam("description") String description) {
        log.info("资源上传: {}", file);
        try {
            // 原始文件名
            String originalFilename = file.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String objectName = UUID.randomUUID().toString() + suffix;

            // 上传到 OSS 获取路径
            String filePath = aliOssUtil.upload(file.getBytes(), objectName);

            // 创建资源实体
            Resource resource = new Resource();
            resource.setTitle(title);
            resource.setDescription(description);
            resource.setFileUrl(filePath);
            resource.setUploaderId(uploaderId);
            resource.setUploadTime(LocalDateTime.now());
            resource.setDownloadCount(0);

            // 保存到数据库
            resourceService.save(resource);

            return Result.success(filePath);
        } catch (IOException e) {
            log.error("文件上传失败: {}", e.getMessage());
            return Result.error(MessageConstant.UPLOAD_FAILED);
        }
    }
    //查询所有资源
    @GetMapping
    public Result<List<ResourceVO>> getAllResources() {
        log.info("查询所有资源");
        return Result.success(resourceService.getList());
    }
    //条件查询
    @GetMapping("/search")
    public Result<List<ResourceVO>> search(@RequestParam String searchKey) {
        log.info("条件查询: {}", searchKey);
        return Result.success(resourceService.getListByTitleAndDescription(searchKey));
    }
    //下载资源
    @PutMapping("/download/{id}")
    public Result<String> download(@PathVariable Integer id) {
        log.info("下载资源: {}", id);
        Resource resource = resourceService.getById(id);
        if (resource == null) {
            return Result.error(MessageConstant.RESOURCE_NOT_FOUND);
        }
        resource.setDownloadCount(resource.getDownloadCount() + 1);
        resourceService.updateById(resource);
        return Result.success("下载成功");
    }
    // 获取视频资源列表
    @GetMapping("/video")
    public Result<List<ResourceVO>> getVideoResources() {
        log.info("获取视频资源列表");
        return Result.success(resourceService.getVideoList());
    }
}
