package cn.gov.nlc.controller.app;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gov.nlc.pojo.Readercompasscatalog;
import cn.gov.nlc.pojo.ReadercompasscatalogWithBLOBs;
import cn.gov.nlc.service.ReadercompasscatalogService;

@Controller
@RequestMapping("/app")
public class ApReaderCompassController {
	
	private static final Logger logger = Logger.getLogger(cn.gov.nlc.controller.app.ApReaderCompassController.class);
	
	@Autowired
	private ReadercompasscatalogService readercompasscatalogService;
	
	/**
	 * 一级目录
	 * @return
	 */
	@RequestMapping("/compass/catalist")
	@ResponseBody
	public List<Readercompasscatalog> getFirstCata() {
		List<Readercompasscatalog> list = readercompasscatalogService.getFirst();
		return list;
	}
	
	@RequestMapping("/compass/getcontent")
	@ResponseBody
	public ReadercompasscatalogWithBLOBs getContentByCataloguuid(String cataloguuid) {
		ReadercompasscatalogWithBLOBs po = readercompasscatalogService.getCatalongContentByCataloguuid(cataloguuid);
		return po;
	}
	
	@RequestMapping("/compass/getchild")
	@ResponseBody
	public List<Readercompasscatalog> getChild(String cataloguuid) {
		List<Readercompasscatalog> list = readercompasscatalogService.getChildCataloguuid(cataloguuid);
		return list;
	}

}
