package com.nineclient.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import com.nineclient.constant.NotifyMsg;
import com.nineclient.model.PubOperator;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.UmpOperator;
import com.nineclient.model.VocEmail;
import com.nineclient.model.VocEmailGroup;
import com.nineclient.utils.CommonUtils;
import com.nineclient.utils.Global;
import com.nineclient.utils.TreeModel;

@RequestMapping("/vocemailgroups")
@Controller
@RooWebScaffold(path = "vocemailgroups", formBackingObject = VocEmailGroup.class)
public class VocEmailGroupController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid VocEmailGroup vocEmailGroup,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, vocEmailGroup);
			return "vocemailgroups/create";
		}
		uiModel.asMap().clear();
		vocEmailGroup.persist();
		return "redirect:/vocemailgroups/"
				+ encodeUrlPathSegment(vocEmailGroup.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new VocEmailGroup());
		return "vocemailgroups/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		uiModel.addAttribute("vocemailgroup",
				VocEmailGroup.findVocEmailGroup(id));
		uiModel.addAttribute("itemId", id);
		return "vocemailgroups/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
			@RequestParam(value = "sortOrder", required = false) String sortOrder,
			Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1)
					* sizeNo;
			uiModel.addAttribute("vocemailgroups", VocEmailGroup
					.findVocEmailGroupEntries(firstResult, sizeNo,
							sortFieldName, sortOrder));
			float nrOfPages = (float) VocEmailGroup.countVocEmailGroups()
					/ sizeNo;
			uiModel.addAttribute(
					"maxPages",
					(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
							: nrOfPages));
		} else {
			uiModel.addAttribute("vocemailgroups", VocEmailGroup
					.findAllVocEmailGroups(sortFieldName, sortOrder));
		}
		return "vocemailgroups/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid VocEmailGroup vocEmailGroup,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, vocEmailGroup);
			return "vocemailgroups/update";
		}
		uiModel.asMap().clear();
		vocEmailGroup.merge();
		return "redirect:/vocemailgroups/"
				+ encodeUrlPathSegment(vocEmailGroup.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		populateEditForm(uiModel, VocEmailGroup.findVocEmailGroup(id));
		return "vocemailgroups/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Long id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {
		VocEmailGroup vocEmailGroup = VocEmailGroup.findVocEmailGroup(id);
		vocEmailGroup.remove();
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/vocemailgroups";
	}

	/**
	 * 添加
	 * 
	 * @param name
	 * @param requset
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "addEmailGroup", produces = "text/html;charset=utf-8")
	public String addEmailGroup(
			@RequestParam(value = "name", required = false) String name,
			HttpServletRequest requset) {
		String msg = "";
		try {
			PubOperator user = CommonUtils.getCurrentPubOperator(requset);
			UmpCompany company = user.getCompany();
			List<VocEmailGroup> vocEmailGroups = VocEmailGroup
					.findVocEmailGroupByName(name, company.getId());
			if (vocEmailGroups != null && vocEmailGroups.size() > 0) {
				msg = "邮件组名称不能重复";
				return msg;
			}
			VocEmailGroup vocEmailGroup = new VocEmailGroup();
			vocEmailGroup.setName(name);
			vocEmailGroup.setUmpCompanyId(company.getId());
			vocEmailGroup.persist();
			msg = "成功";
		} catch (Exception e) {
			e.printStackTrace();
			msg = "失败";
		}
		return msg;

	}

	/**
	 * for select
	 * 
	 * @param requset
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryEmailGroup", produces = "text/html;charset=utf-8")
	public String queryEmailGroup(HttpServletRequest requset) {
		String json = "";
		PubOperator user = CommonUtils.getCurrentPubOperator(requset);
		UmpCompany company = user.getCompany();
		List<VocEmailGroup> list = VocEmailGroup.findVocEmailGroupByName(null,
				company.getId());
		String[] aa = { "name", "id" };
		json = VocEmailGroup.toJsonArray(list, aa);
		return json;
	}

	/**
	 * for ul tree
	 * 
	 * @param requset
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "emailGroupUl", produces = "text/html;charset=utf-8")
	public String emailGroupUl(HttpServletRequest requset) {
		String json = "[";
		PubOperator user = CommonUtils.getCurrentPubOperator(requset);
		UmpCompany company = user.getCompany();
		List<VocEmailGroup> list = VocEmailGroup.findVocEmailGroupByName(null,
				company.getId());

		boolean flag = true;
		for (VocEmailGroup vocEmailGroup : list) {
			if (flag) {
				flag = false;
				json += "{id:" + vocEmailGroup.getId() + ",name:\'"
						+ vocEmailGroup.getName() + "\'";
				List<VocEmail> emailList = VocEmail
						.findVocEmailEntriesByGrouId(vocEmailGroup);
				if (emailList != null && emailList.size() > 0) {
					json += ",vocEmails:"
							+ VocEmail.toJsonArrayNameAndId(emailList);
				}
				json += "}";
			} else {
				json += ",{id:" + vocEmailGroup.getId() + ",name:\'"
						+ vocEmailGroup.getName() + "\'";
				List<VocEmail> emailList = VocEmail
						.findVocEmailEntriesByGrouId(vocEmailGroup);
				if (emailList != null && emailList.size() > 0) {
					json += ",vocEmails:"
							+ VocEmail.toJsonArrayNameAndId(emailList);
				}
				json += "}";
			}
		}
		json += "]";
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "delete", produces = "text/html;charset=utf-8")
	public String deletGroup(HttpServletRequest request,
			@RequestParam(value = "id", required = false) Long id) {
		String msg = "";
		try {
			if (id != null) {
				VocEmailGroup group = VocEmailGroup.findVocEmailGroup(id);
				group.remove();
				msg = "成功";
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "失败";

		}
		return msg;
	}

	/**
	 * 编辑邮件组名称
	 * 
	 * @param request
	 * @param id
	 * @param name
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "editeEmailGroup", produces = "text/html;charset=utf-8")
	public String editeEmailGroup(HttpServletRequest request,
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "name", required = false) String name) {
		String msg = "";
		try {
			VocEmailGroup group = VocEmailGroup.findVocEmailGroup(id);
			group.setName(name);
			group.merge();
			msg = "成功";
		} catch (Exception e) {
			e.printStackTrace();
			msg = "失败";
		}
		return msg;
	}

	/**
	 * 邮件添加至邮件组
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "addEmailToGroup", produces = "text/html;charset=utf-8")
	public String addEmailToGroup(HttpServletRequest request,
			@RequestParam(value = "emailIds") String emailIds,
			@RequestParam(value = "groupId") Long groupId) {
		String msg = "";
		try {
			List<VocEmail> newlist = VocEmail.findAllVocEmailsByIds(emailIds);
			VocEmailGroup group = VocEmailGroup.findVocEmailGroup(groupId);
			List<VocEmail> havelist = VocEmail
					.findVocEmailEntriesByGrouId(group);
			Set<VocEmail> set = new HashSet<VocEmail>();
			for (VocEmail email : newlist) {
				if (!havelist.contains(email)) {
					havelist.add(email);
				}
			}
			set.addAll(havelist);
			group.setVocEmails(set);
			group.merge();
			msg = "成功";
		} catch (Exception e) {
			e.printStackTrace();
			msg = "失败";
		}
		return msg;
	}

	/**
	 * 加载邮件组树
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "loadTree", produces = "text/html;charset=utf-8")
	public String loadTree(HttpServletRequest request) {
		PubOperator user = CommonUtils.getCurrentPubOperator(request);
		UmpCompany company = user.getCompany();
		List<VocEmailGroup> listGroup = VocEmailGroup
				.findAllVocEmailGroupsByCompany(company);
		List<TreeModel> treeModel = new ArrayList<TreeModel>();
		for (VocEmailGroup vocEmailGroup : listGroup) {
			TreeModel model = new TreeModel();
			model.setId(vocEmailGroup.getId());
			model.setName(vocEmailGroup.getName());
			List<VocEmail> emails = new ArrayList<VocEmail>();
			emails = CommonUtils.set2List(vocEmailGroup.getVocEmails());
			List<TreeModel> ctreeModel = new ArrayList<TreeModel>();
			for (VocEmail vocEmail : emails) {
				TreeModel cmodel = new TreeModel();
				cmodel.setId(vocEmail.getId());
				cmodel.setName(vocEmail.getName());
				ctreeModel.add(cmodel);
			}
			model.setIsParent(true);
			model.setChildren(ctreeModel);
			model.setOpen(true);
			treeModel.add(model);
		}
		return JSONArray.fromObject(treeModel).toString();
	}

	/**
	 * 移除邮件组
	 * 
	 * @param request
	 * @param id
	 * @param name
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "removeEmail", produces = "text/html;charset=utf-8")
	public String removeEmail(HttpServletRequest request,
			@RequestParam(value = "groupId") Long groupId,
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "name", required = false) String name) {
		VocEmailGroup group = VocEmailGroup.findVocEmailGroup(groupId);
		VocEmail email = VocEmail.findVocEmail(id);
		try {
			if (email != null) {
				group.getVocEmails().remove(email);
				group.merge();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return NotifyMsg.EXCEPTION;
		}
		return NotifyMsg.SUCCESS;
	}

	void populateEditForm(Model uiModel, VocEmailGroup vocEmailGroup) {
		uiModel.addAttribute("vocEmailGroup", vocEmailGroup);
	}

	String encodeUrlPathSegment(String pathSegment,
			HttpServletRequest httpServletRequest) {
		String enc = httpServletRequest.getCharacterEncoding();
		if (enc == null) {
			enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
		}
		try {
			pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
		} catch (UnsupportedEncodingException uee) {
		}
		return pathSegment;
	}
}
