package com.nineclient.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nineclient.constant.UmpCompanyServiceStatus;
import com.nineclient.constant.UmpCompanyStatus;
import com.nineclient.model.UmpCompany;
import com.nineclient.model.UmpCompanyService;
import com.nineclient.utils.DateUtil;

@Component
public class UmpCompanyServiceTask {
	private static final Logger logger = Logger
			.getLogger(UmpCompanyServiceTask.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 公司服务到期状态更新定时任务
	 */
	@Scheduled(cron = "*/30 * * * * *")
	// 每天零点任务
	public void companyService() {
		logger.info("试用--服务状态更新开始-----start");
		Date date = DateUtil.getDateTimeYYYYMMDD(new Date(),
				DateUtil.YEAR_MONTH_DAY_FORMATER);
		// 不包含停止的
		// 1.需要跟新成停止的状态
		// a/查询审核通过的公司
		// b/查询该公司下的服务 结束时间等于当前时间
		List<Long> list = null;
		try {
			logger.info("试用--服务状态更新开始-----start");
			list = this.findUmpCompanyByStatus(UmpCompanyStatus.审核通过, null,
					date);
			if (list != null && list.size() > 0)
				this.updateSerivceStatus(list, UmpCompanyServiceStatus.到期);
		} catch (Exception e) {
			logger.error("试用---更新服务状态异常", e);
		}
		logger.info("试用 --服务状态更新结束-----end");
		try {
		logger.info("预到期 --服务状态更新结束-----start");
			// 2.开通状态 需要更新成预到期的 服务
			list = this.findUmpCompanyByStatus(UmpCompanyStatus.审核通过,
					UmpCompanyServiceStatus.开通,
					DateUtil.getNextMONTHDateYYYYMMDD(date));
			if (list != null && list.size() > 0)
				this.updateSerivceStatus(list, UmpCompanyServiceStatus.预到期);
		} catch (Exception e) {
			logger.error("预到期----更新服务状态异常", e);
		}
		logger.info("预到期 --服务状态更新结束-----end");
	}

	/**
	 * 查询试用期的服务 公司 审核通过
	 * 
	 * @param status
	 * @return
	 */
	public List<Long> findUmpCompanyByStatus(UmpCompanyStatus status,
			UmpCompanyServiceStatus companyServiceStatus, Date nowDate) {
		List<Object> listParam = new ArrayList<Object>();
		String sql = "select s.id from ump_company_service s inner join ump_company c where s.company_code=c.company_code and s.service_end_time=?"
				+ " and c.is_visable=1 and c.is_deleted=0 ";
		listParam.add(nowDate);
		if (companyServiceStatus != null) {
			sql += " and s.company_service_status ="+companyServiceStatus.ordinal();
		}
		sql += " and s.company_service_status !="+UmpCompanyServiceStatus.停止.ordinal()+" and c.status="+status.ordinal();
		logger.info("查询待更新的服务：" + sql);
		List<Long> list = jdbcTemplate.queryForList(sql, Long.class, listParam);
		return list;
	}

	/**
	 * 更新服务状态
	 * 
	 * @param list
	 * @param status
	 */
	public void updateSerivceStatus(List<Long> list,
			UmpCompanyServiceStatus status) {
		List<Object> paramList = new ArrayList<Object>();
		String sql = " update Ump_Company_Service o set o.company_Service_Status="+status.ordinal()+" where o.id in (?)";
		paramList.add(list);
		logger.info("更新服务状态：" + sql);
		jdbcTemplate.update(sql, paramList);
	}

}
