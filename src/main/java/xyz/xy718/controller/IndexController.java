package xyz.xy718.controller;

import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.qos.logback.core.pattern.ConverterUtil;
import cn.hutool.core.codec.Base64;
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
		pasteRepository.save(new Paste(link,Base64.decodeStr(paste.getPaste())));
		return ResultBean.success("上传成功",link);
	}
	
	@GetMapping("/p/{link}")
	public String pastePage(
			Model model
			,@PathVariable String link
			) {
		Paste paste= pasteRepository.findByLink(link);
		if(paste==null) {
			model.addAttribute("empty", true);
		}
		String pastePage=paste.getPaste();
		
		
		
		model.addAttribute("paste", this.countStr(pastePage,"net.minecraft.")+"-"+pastePage);
		return "result";
	}
	/**
     * @param str 原字符串
     * @param sToFind 需要查找的字符串
     * @return 返回在原字符串中sToFind出现的次数
     */
    private int countStr(String str,String sToFind) {
        int num = 0;
        while (str.contains(sToFind)) {
            str = str.substring(str.indexOf(sToFind) + sToFind.length());
            num ++;
        }
        return num;
    }
}
