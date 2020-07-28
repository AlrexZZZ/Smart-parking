package com.nineclient.qycloud.wcc.web;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import me.chanjar.weixin.common.exception.WxErrorException;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.nineclient.cbd.wcc.util.MyMail;
import com.nineclient.model.PubOperator;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.WccStore;
import com.nineclient.qycloud.wcc.dto.ScoreDTO;
import com.nineclient.qycloud.wcc.model.Score;
import com.nineclient.qycloud.wcc.util.DtgridPageRender;
import com.nineclient.qycloud.wcc.util.DtgridQueryCondition;
import com.nineclient.qycloud.wcc.util.PageCondition;
import com.nineclient.tailong.wcc.dto.WccSaQrCodeDTO;
import com.nineclient.tailong.wcc.model.WccSaQrCode;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.CreateQrcode;
import com.nineclient.utils.Global;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.Pager;
import com.nineclient.utils.PagerPropertyUtils;
import com.nineclient.utils.QueryModel;

@RequestMapping("/scoreController")
@Controller
public class scoreController   {

	/***
	 * ajax动态填充联系人数据
	 * @param dtGridPager
	 * @param request
	 * @param model
	 * @param response
	 * @throws UnsupportedEncodingException
	 * @throws ParseException
	 */
	@ResponseBody
	@RequestMapping(value = "loadData", produces = "application/json")
	public void loadData(String dtGridPager, HttpServletRequest request,
			Model model, HttpServletResponse response)
			throws UnsupportedEncodingException, ParseException {
		//new page dtgird对象
		QueryModel<Score> qm = new QueryModel<Score>();
		PageCondition  condition = DtgridQueryCondition.makeCondition(dtGridPager, qm,request);
		PageModel<Score> pagerModel = Score.getRecord((QueryModel<Score>)condition.getQm());
		List<Score> modelList  = pagerModel.getDataList();
		List<ScoreDTO> dtoList = null;
		ScoreDTO dto = null;
		if(null != modelList & modelList.size() > 0){
			dtoList = new ArrayList<ScoreDTO>();
			for(Score c:modelList){
				dto = new ScoreDTO(c.getId(), c.getStuNo(), c.getStuName(), 
						c.getTestScore(), c.getAddScore(), c.getMiScore(), c.getScore(), c.getSrange(),
						c.getSstage(), c.getItems(),c.getInsertTime());
				dtoList.add(dto);
			}
		}
		///page 封装写到页面
		condition.getPager().setExhibitDatas(dtoList);
		DtgridPageRender.writeResponseJsonToPageFromPager(response, pagerModel, condition.getPager());
	}

	/**
	 * 粉丝记录页面
	 * 
	 * @param
	 * @return
	 */

	@RequestMapping(value = "showScoreRecord", produces = "text/html")
	public String showRecord(HttpServletRequest request, Model uiModel,
			HttpServletResponse response) {
/*		List<WccPlatformUser> list = WccPlatformUser
				.findAllWccPlatformUsers(CommonUtils
						.getCurrentPubOperator(request));
		uiModel.addAttribute("plats", list);*/

		return "score/scoreList";
	}

	
	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(HttpServletRequest request, Model uiModel,
			@RequestParam(value = "dataId", required = false) Long dataId,
			HttpServletResponse response) {

		if (null != dataId && !"".equals(dataId)) {
			Score findScore =Score.findScore(dataId);
			uiModel.addAttribute("findScoreObject", findScore);
		}
		return "score/scoreCreate";
	}

	@RequestMapping(params = "delete", produces = "text/html")
	public String deleteForm(HttpServletRequest request,
			@RequestParam(value = "dataId", required = true) String dataId,
			HttpServletResponse response) {
		if (null != dataId && !"".equals(dataId)) {

			Score score = new Score();
			score.setId(Long.parseLong(dataId));
			score.remove();
		}

		return null;
	}

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid Score score,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest)
			throws UnsupportedEncodingException {
		System.out.println("this is create method!");
		// PubOperator pub =
		// CommonUtils.getCurrentPubOperator(httpServletRequest);
		if (bindingResult.hasErrors()) {
			return "redirect:/scoreController?form";
		}
		if (null != score.getId() && !"".equals(score.getId())) {

			// 先查询出实体
			Score scoreUpdate = Score.findScore(score.getId());
			scoreUpdate.setStuNo(score.getStuNo());
			scoreUpdate.setStuName(score.getStuName());
			scoreUpdate.setTestScore(score.getTestScore());
			scoreUpdate.setAddScore(score.getAddScore());
			scoreUpdate.setMiScore(score.getMiScore());
			scoreUpdate.setScore(score.getScore());
			scoreUpdate.setSrange(score.getSrange());
			scoreUpdate.setSstage(score.getSstage());
			scoreUpdate.setItems(score.getItems());
			uiModel.asMap().clear();
			// 再更新数据
			scoreUpdate.merge();
		} else {
			score.setInsertTime(new Date());
			score.persist();
		}

		return "redirect:/scoreController/showScoreRecord";
	}
}
