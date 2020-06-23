package com.jkk.service.picture.controller;

import com.jkk.service.picture.util.RestfulRes;
import com.jkk.service.picture.util.UUIDUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@RestController
public class PictureController {

	@Value("${img.path}")
	private String filePath;
	@PostMapping("upload")
	public RestfulRes modifyAvatar(@RequestParam("file") MultipartFile file, HttpServletRequest req){
		if (file.isEmpty()) {
			return RestfulRes.fail("文件不能为空");
		}
		String fileName = UUIDUtil.getUUID();

		File dest = new File(filePath + fileName);
		try {
			file.transferTo(dest);
			return RestfulRes.success("http://"+req.getServerName()+":"+req.getServerPort()+"/img/"+fileName);
		} catch (IOException e) {
			e.printStackTrace();
			return RestfulRes.fail("上传文件失败");
		}
	}

}
