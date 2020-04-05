package xyz.xy718.controller;

import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.qos.logback.core.pattern.ConverterUtil;
import xyz.xy718.controller.jsonmodel.PasteBody;
import xyz.xy718.dao.PasteRepository;
import xyz.xy718.model.Paste;
import xyz.xy718.util.ConversionUtil;
import xyz.xy718.util.IpUtil;
import xyz.xy718.util.Murmurs;
import xyz.xy718.util.ResultBean;

@Controller
public class IndexController {

	private static final Logger LOGGER=LoggerFactory.getLogger(IndexController.class);
	
	@Resource
	private PasteRepository pasteRepository;
	
	@GetMapping("/")
	public String indexPage(Model model) {
		return "page";
	}
	
	@PostMapping("/")
	@ResponseBody
	public ResultBean postPsate(
			@RequestBody PasteBody paste
			,HttpServletRequest request
			) {
		LOGGER.info(IpUtil.getIpAddr(request));
		BigDecimal hash=Murmurs.hashUnsigned(System.currentTimeMillis()+""+IpUtil.getIpAddr(request)+paste.hashCode());
		if(hash.longValue()<0) {
			hash=new BigDecimal(-hash.longValue());
		}
		String link=ConversionUtil._10_to_62(hash.longValue(),15).replaceAll("^(0+)", "");
		LOGGER.info(link);
		pasteRepository.save(new Paste(link,paste.getPaste()));
		return ResultBean.success("上传成功",link);
	}
	
	@GetMapping("/p/**")
	public String pastePage(Model model) {
		return "result";
	}
}
