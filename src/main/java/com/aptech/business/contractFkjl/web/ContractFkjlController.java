package com.aptech.business.contractFkjl.web;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.contractFkjl.domain.ContractFkjlEntity;
import com.aptech.business.contractFkjl.service.ContractFkjlService;
import com.aptech.business.contractManage.domain.ContractManageEntity;
import com.aptech.business.contractManage.service.ContractManageService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.JsonUtil;

/**
 * 
 * 合同付款记录配置控制器
 *
 * @author 
 * @created 2018-09-11 15:39:29
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/contractFkjl")
public class ContractFkjlController extends BaseController<ContractFkjlEntity> {
	
	@Autowired
	private ContractFkjlService contractFkjlService;
	@Autowired
	private ContractManageService contractManageService;
	@Override
	public IBaseEntityOperation<ContractFkjlEntity> getService() {
		return contractFkjlService;
	}
	
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/index/{id}")
	public ModelAndView list(HttpServletRequest request, Map<String, Object> params, @PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("zbid", id);
		return this.createModelAndView("contractFkjl/contractFkjlList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd/{id}")
	public ModelAndView getAddPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		List<ContractFkjlEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("contractFkjlTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboContractFkjlVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("contractFkjlCombobox", JsonUtil.toJson(comboContractFkjlVO.getOptions()));
		model.put("zbidAdd", id);
		ContractManageEntity contractManageEntity=contractManageService.findById(id);
		//查询付款记录列表
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_CONTRACT_MANAGE_I_D", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(id)));
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		List<ContractFkjlEntity> list=contractFkjlService.findByCondition(conditions, null);
		Double money=0.00;
		if(list.isEmpty()){
			model.put("nopayMoney", contractManageEntity.getContractMoney());
			model.put("xh", "1");
		}else{
			for (ContractFkjlEntity contractFkjlEntity : list) {
				String fkjr=contractFkjlEntity.getFkje();
				money+=Double.parseDouble(fkjr);
			}
			DecimalFormat df = new DecimalFormat("#0.00");
			model.put("nopayMoney",df.format(Double.valueOf(contractManageEntity.getContractMoney())- money));
			model.put("xh", list.size()+1);
		}
		
		return this.createModelAndView("contractFkjl/contractFkjlAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		ContractFkjlEntity contractFkjlEntity = (ContractFkjlEntity)contractFkjlService.findById(id);
		model.put("entity", contractFkjlEntity);
		model.put("entityJson", JsonUtil.toJson(contractFkjlEntity));
		
		List<ContractFkjlEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("contractFkjlTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboContractFkjlVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("contractFkjlCombobox", JsonUtil.toJson(comboContractFkjlVO.getOptions()));
		
		return this.createModelAndView("contractFkjl/contractFkjlEdit", model);
	}
}