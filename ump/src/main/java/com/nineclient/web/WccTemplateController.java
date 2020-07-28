package com.nineclient.web;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nineclient.constant.WccTemplateType;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.WccTemplate;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.PageModel;
import com.nineclient.utils.QueryModel;

/**
 * 
 * 常用文本
 * 
 * @author Brian
 *
 */
@RequestMapping("/wcctemplates")
@Controller
@RooWebScaffold(path = "wcctemplates", formBackingObject = WccTemplate.class)
public class WccTemplateController {

	@RequestMapping(produces = "text/html")
	public String list(
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "displayId", required = false) Long displayId,
			@RequestParam(value = "limit", required = false) Integer limit,
			HttpServletRequest httpServletRequest, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		List<WccPlatformUser> list = WccPlatformUser
				.findAllWccPlatformUsers(CommonUtils
						.getCurrentPubOperator(httpServletRequest));
		if (list.size() > 0) {
			WccPlatformUser plat = list.get(0);
			List<WccTemplate> templates = WccTemplate
					.findWccTemplateByPidAndPlatform(plat.getId());
			if (templates.size() > 0) {
				uiModel.addAttribute("defultPId", templates.get(0).getId() + "");
				uiModel.addAttribute("defultTitle", templates.get(0).getTitle());
			}
		}
		if (displayId == null) {
			displayId = CommonUtils.getCurrentDisPlayId(httpServletRequest);
		}
		httpServletRequest.getSession().setAttribute("displayId", displayId);
		return "wcctemplates/showList";
	}

	@RequestMapping(value = "deleteTemplate", produces = "text/html")
	@ResponseBody
	public String deleteTemplate(@RequestParam("id") Long id,
			@RequestParam("type") int type) {
		WccTemplate wccTemplate = WccTemplate.findWccTemplate(id);
		List<WccTemplate> childrenList = WccTemplate.findWccTemplatesByPid(id
				.toString());
		if (type == 3) {// 验证是否存在下属类或文本
			if (childrenList.size() > 0) {
				return "1";// 返回有子类或文本的提示
			} else {
				return "2";// 返回是否删除提示
			}
		} else {
			wccTemplate.remove();
			if (!wccTemplate.getParentId().equals("0")) {// 如果父节点吥为0 则为第三层类
															// 直接删除下属所有类与文本
				if (childrenList.size() > 0) {
					for (WccTemplate tem : childrenList) {
						tem.remove();
					}
				}
			} else {// 判断两层 当前节点下 以及 下级子节点
				if (childrenList.size() > 0) {
					for (WccTemplate tem : childrenList) {
						if (tem.getType().equals(WccTemplateType.TEMPLATE)) {// 如果文本
																				// 直接删除
							tem.remove();
						} else {// 如果是类 则查询是否下属类中的文本一起删除
							List<WccTemplate> children = WccTemplate
									.findWccTemplatesByPid(tem.getId() + "");
							if (children.size() > 0) {
								for (WccTemplate t : childrenList) {
									t.remove();
								}
							}
							tem.remove();
						}
					}
				}
			}
			return "1";
		}
	}

	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute(
				"wccTemplate_inserttime_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
	}

	void populateEditForm(Model uiModel, WccTemplate wccTemplate) {
		uiModel.addAttribute("wccTemplate", wccTemplate);
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("wcctemplatetypes",
				Arrays.asList(WccTemplateType.values()));
	}

	/**
	 * 
	 * 加载常用文本分类的树
	 * 
	 * @param uiModel
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "tree", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String tree(Model uiModel, HttpServletRequest request,
			HttpServletResponse response) {
		StringBuffer strs = new StringBuffer();
		int num = 0;
		// 查找当前用户所拥有的平台
		List<WccPlatformUser> platList = WccPlatformUser
				.findAllWccPlatformUsers(CommonUtils
						.getCurrentPubOperator(request));
		if (platList.size() > 0) {
			// 根据平台查询类型为分类的常用分类
			List<WccTemplate> templates = WccTemplate
					.findWccTemplatesByTypeAndPlat(
							CommonUtils.getCurrentCompanyId(request), platList);
			for (WccTemplate template : templates) {
				String str = "id:" + template.getId() + ", pId:"
						+ template.getParentId() + ",name:\""
						+ template.getTitle() + "\"" + ",open:true";
				if (num == 0) {
					strs.append("{" + str + "}");
				} else {
					strs.append(",{" + str + "}");
				}
				num++;
			}
		}
		return strs.toString();
	}

	@RequestMapping(value = "ptree", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String ptree(Model uiModel, HttpServletRequest request,
			HttpServletResponse response) {
		StringBuffer strs = new StringBuffer();
		int num = 0;
		// 查找当前用户所拥有的平台
		List<WccPlatformUser> platList = WccPlatformUser
				.findAllWccPlatformUsers(CommonUtils.getCurrentPubOperator(request));
		if (platList!=null && platList.size() > 0) {
			List<WccTemplate> templates = WccTemplate.findWccTemplates(CommonUtils.getCurrentCompanyId(request), platList);
			for (WccTemplate template : templates) {
				/*if(template.getType() == WccTemplateType.TEMPLATE){
					String str = "id:" + template.getId() + ", pId:"
							+ template.getParentId() + ",name:\"";
							if(template.getContent().length() > 50){
								str = str + template.getContent().substring(0, 50) +"...."+ "\"" + ",open:true";
							}else{
								str = str + template.getContent() + "\"" + ",open:true";
							}
					if (num == 0) {
						strs.append("{" + str + "}");
					} else {
						strs.append(",{" + str + "}");
					}
					num++;
				}else{
					String str = "id:" + template.getId() + ", pId:"
							+ template.getParentId() + ",name:\""
							+ template.getTitle() + "\"" + ",open:true";
					if (num == 0) {
						strs.append("{" + str + "}");
					} else {
						strs.append(",{" + str + "}");
					}
					num++;
				}*/
				String str = "id:" + template.getId() + ", pId:"
						+ template.getParentId() + ",name:\""
						+ template.getTitle() + "\"" + ",open:true";
				if (num == 0) {
					strs.append("{" + str + "}");
					if (template.getType() == WccTemplateType.TEMPLATE) {
						str = "id:" + 1000000 + template.getId() + ", pId:"
								+ template.getId() + ",name:\"";
								if(template.getContent().length() > 50){
									str = str + template.getContent().substring(0, 50) +"...."+ "\"" + ",open:true";
								}else{
									str = str + template.getContent() + "\"" + ",open:true";
								}
						strs.append(",{" + str + "}");
					}
				} else {
					strs.append(",{" + str + "}");
					if (template.getType() == WccTemplateType.TEMPLATE) {
						str = "id:" + 1000000 + template.getId() + ", pId:"
								+ template.getId() + ",name:\"";
								if(template.getContent().length() > 50){
									str = str + template.getContent().substring(0, 50) +"...."+ "\"" + ",open:true";
								}else{
									str = str + template.getContent() + "\"" + ",open:true";
								}
						strs.append(",{" + str + "}");
					}
				}
				num++;
			}
		}
		return strs.toString();
	}

	/**
	 * 保存分类
	 * 
	 * @param pid
	 * @param title
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "saveClassify", produces = "text/html")
	@ResponseBody
	public String saveClassify(@RequestParam("id") String id,
			@RequestParam("pid") String pid,
			@RequestParam("title") String title, HttpServletRequest request) {
		// 判断保存的分类不超过三级
		if (!pid.equals("0")) {// 如果分类的父节点不为0 则判断是不是第三级 如果为0 则不用考虑，肯定不会超过第三级
			if (!WccTemplate.findWccTemplate(Long.parseLong(pid)).getParentId()
					.equals("0")) {
				if (!WccTemplate
						.findWccTemplate(
								Long.parseLong(WccTemplate.findWccTemplate(
										Long.parseLong(pid)).getParentId()))
						.getParentId().equals("0")) {
					return "4";// 提示超过最大分类级数
				}
			}
		}
		if(id==""){
			if (WccTemplate.findWccTemplatesByPidAndTitle(Long.parseLong(pid),
					title).size() > 0) {
				return "5";// 提示分类名称重复
			}
		}else{
			if (WccTemplate.findWccTemplatesByPidAndTitle(Long.parseLong(id),Long.parseLong(pid),
					title).size() > 0) {
				return "5";// 提示分类名称重复
			}
		}
		if (id == "") {// 新增
			WccTemplate wccTemplate = new WccTemplate();
			wccTemplate.setParentId(pid);
			wccTemplate.setTitle(title);
			wccTemplate.setType(WccTemplateType.CLASSIFY);
			wccTemplate.setInsertTime(new Date());
			wccTemplate.setName(CommonUtils.getCurrentPubOperator(request));
			wccTemplate.setPlatformUser(WccTemplate.findWccTemplate(
					Long.parseLong(pid)).getPlatformUser());
			try {
				wccTemplate.persist();
			} catch (Exception e) {
				e.printStackTrace();
				return "1";// 添加失败
			}
			return "0";
		} else {// 修改
			WccTemplate wccTemplate = WccTemplate.findWccTemplate(Long
					.parseLong(id));
			wccTemplate.setTitle(title);
			try {
				wccTemplate.merge();
			} catch (Exception e) {
				e.printStackTrace();
				return "3";// 修改失败
			}
			return "2";
		}
	}

	@RequestMapping(value = "saveText", produces = "text/html")
	@ResponseBody
	public String saveText(@RequestParam("id") String id,
			@RequestParam("pid") String pid,
			@RequestParam("title") String title,
			@RequestParam("sort") String sort,
			@RequestParam("content") String content, HttpServletRequest request) {
		if (id == "") {
			WccTemplate wccTemplate = new WccTemplate();
			wccTemplate.setParentId(pid);
			wccTemplate.setTitle(title);
			wccTemplate.setType(WccTemplateType.TEMPLATE);
			wccTemplate.setInsertTime(new Date());
			wccTemplate.setName(CommonUtils.getCurrentPubOperator(request));
			wccTemplate.setSort(sort);
			wccTemplate.setContent(content);
			wccTemplate.setPlatformUser(WccTemplate.findWccTemplate(
					Long.parseLong(pid)).getPlatformUser());
			try {
				wccTemplate.persist();
			} catch (Exception e) {
				return "1";
			}
			return "0";
		} else {
			WccTemplate wccTemplate = WccTemplate.findWccTemplate(Long
					.parseLong(id));
			wccTemplate.setTitle(title);
			wccTemplate.setSort(sort);
			wccTemplate.setContent(content);
			try {
				wccTemplate.merge();
			} catch (Exception e) {
				return "3";
			}
			return "2";
		}
	}

	/**
	 * 加载右面常用文本列表
	 * 
	 * @param pid
	 * @param uiModel
	 * @param start
	 * @param limit
	 * @param request
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/loadList", produces = "text/html")
	public String loadList(@RequestParam("pid") String pid, Model uiModel,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "limit", required = false) Integer limit,
			HttpServletRequest request) throws ServletException, IOException {
		if (pid.equals("0")) {// 如果传过来pid是0 则为默认加载列表
			List<WccPlatformUser> list = WccPlatformUser
					.findAllWccPlatformUsers(CommonUtils
							.getCurrentPubOperator(request));
			if (list.size() > 0) {
				WccPlatformUser plat = list.get(0);
				List<WccTemplate> templates = WccTemplate
						.findWccTemplateByPidAndPlatform(plat.getId());
				if (templates.size() > 0) {
					pid = templates.get(0).getId() + "";
				}
			} else {
				return "wcctemplates/list";
			}
		}
		WccTemplate model = new WccTemplate();
		QueryModel<WccTemplate> qm = new QueryModel<WccTemplate>(model, start,
				limit);
		PageModel<WccTemplate> pm = WccTemplate.getQueryTemplate(qm, pid);
		pm.setPageSize(limit);
		pm.setStartIndex(start);
		uiModel.addAttribute("maxPages", pm.getTotalPage());
		uiModel.addAttribute("page", pm.getPageNo());
		uiModel.addAttribute("limit", pm.getPageSize());
		uiModel.addAttribute("wcctemplates", pm.getDataList());
		addDateTimeFormatPatterns(uiModel);
		return "wcctemplates/list";
	}
	
	@RequestMapping(value = "/findIdByContent", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public  String findIdByContent(HttpServletRequest request,
			@RequestParam(value = "id", required = false) Long id,
			Model uiModel) {
		long tpId = id;
		if(null != id){
			if(tpId > 100000000l){
				tpId = tpId - 100000000l;
			}
		}
		WccTemplate wccTemplate = WccTemplate.findWccTemplate(tpId);
		if(wccTemplate != null){
			if(wccTemplate.getContent() == null || "".equals(wccTemplate.getContent())){
				System.out.println(1111);
				return "isTitle";//表示没有文本，只有分类，或者只有标题
			}else{
				System.out.println(222);
				return wccTemplate.getContent();
			}
		}
		System.out.println(333);
		return null;
	}
	
}
