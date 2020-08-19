package com.dsmpharm.bidding.service;

import com.dsmpharm.bidding.mapper.BiddingFileAddcontentMapper;
import com.dsmpharm.bidding.pojo.BiddingFileAddcontent;
import com.dsmpharm.bidding.utils.DateUtils;
import com.dsmpharm.bidding.utils.IdWorker;
import com.dsmpharm.bidding.utils.Result;
import com.dsmpharm.bidding.utils.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/** 
 * <br/>
 * Created by Grant on 2020/08/15
 */
@Service
public class BiddingFileAddcontentService {

	@Resource
	private BiddingFileAddcontentMapper biddingFileAddcontentMapper;
	private static Logger log = LoggerFactory.getLogger(BiddingFileAddcontentService.class);


	@Resource
	private IdWorker idWorker;
	@Value("${upload.files_add}")
	private String uploadFilesAdd;//上传文件保存的本地目录，使用@Value获取全局配置文件中配置的属性值


	/**
	 * 项目编辑中接收新增的内容设置的文件类型
	 * @param files
	 * @return
	 */
	public Result insert(List<MultipartFile> files){
		if (files!=null&&files.size()>0){
			//判断文件夹是否存在，不存在则新建
			String docPath1 = uploadFilesAdd;
			File docPath = new File(docPath1);
			if (!docPath.exists() && !docPath.isDirectory()) {
				docPath.mkdirs();
			}
			ArrayList<BiddingFileAddcontent> arrayList=new ArrayList();
			BiddingFileAddcontent biddingFileAddcontent=null;
			for (MultipartFile multipartFile : files) {
				biddingFileAddcontent=new BiddingFileAddcontent();
				String fileName1 = multipartFile.getOriginalFilename();
				String fileName = DateUtils.format(new Date(), "yyyy-MM-dd-HH-mm-ss") + fileName1;
				File filePath = new File(docPath, fileName);
				try {
					multipartFile.transferTo(filePath);
				} catch (IOException e) {
					log.error(e.toString(), e);
				}
				biddingFileAddcontent.setFilePath(filePath.getPath());
				BiddingFileAddcontent biddingFileAddcontent1 = null;
				try {
					biddingFileAddcontentMapper.insertAddcontent(filePath.getPath());
					biddingFileAddcontent1 = biddingFileAddcontentMapper.selectOne(biddingFileAddcontent);
				} catch (Exception e) {
					e.printStackTrace();
					return new Result(false, StatusCode.ERROR,"呀! 服务器开小差了~");
				}
				arrayList.add(biddingFileAddcontent1);
			}
			return new Result(true, StatusCode.OK,"新增成功",arrayList);
		}
		return new Result(false, StatusCode.ERROR,"没有发现文件");
	}
}