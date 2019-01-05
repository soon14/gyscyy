package com.aptech.business.run.runWay.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.run.runWay.dao.RunWayDao;
import com.aptech.business.run.runWay.domain.RunWayEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 运行方式应用管理服务实现类
 * 
 * @author
 * @created 2017-06-20 09:26:27
 * @lastModified
 * @history
 * 
 */
@Service("runWayService")
@Transactional
public class RunWayServiceImpl extends
		AbstractBaseEntityOperation<RunWayEntity> implements RunWayService {

	@Autowired
	private RunWayDao runWayDao;
	@Autowired
	private OperateLogService operateLogService;
	@Override
	public IBaseEntityOperation<RunWayEntity> getDao() {
		return runWayDao;
	}

	@Override
	public ResultObj insertList(RunWayEntity runWayEntity) throws Exception {
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.STRING,
				MatchTypeEnum.EQ, runWayEntity.getUnitId()));
		conditions.add(new Condition("a.C_RL_ID", FieldTypeEnum.STRING,
				MatchTypeEnum.EQ, runWayEntity.getRlId()));
		List<RunWayEntity> runWayEntityDetailList = this.findByCondition(
				"findByCondition", conditions, null);
		for (int j = 0; j < runWayEntityDetailList.size(); j++) {
			this.deleteEntity(runWayEntityDetailList.get(j).getId()); // 先删除运行方式为空的数据再新增
		}
		String[] runWayTemp = runWayEntity.getRowspanNum().split(",");
		for (int i = 0; i < runWayTemp.length; i++) {
			String[] runWayArray = runWayTemp[i].split("_");
			RunWayEntity addrunWayEntity = new RunWayEntity();
			addrunWayEntity.setDeviceId(Integer.parseInt(runWayArray[0]));
			addrunWayEntity.setDeviceType(runWayArray[1]);
			addrunWayEntity.setRunWay(runWayArray[2]);
			addrunWayEntity.setRlId(runWayEntity.getRlId());
			addrunWayEntity.setUnitId(runWayEntity.getUnitId());
			this.addEntity(addrunWayEntity);
		}
		ResultObj resultObj = new ResultObj();
		resultObj.setData(runWayEntity);
		return resultObj;

	}

	@Override
	public ResultObj editList(RunWayEntity runWayEntity) throws Exception {
		String[] runWayTemp = runWayEntity.getRowspanNum().split(",");
		int flag = 0;
		for (int i = 0; i < runWayTemp.length; i++) {
			String[] runWayArray = runWayTemp[i].split("_");
			if ("0".equals(runWayArray[0])) { // 初始化上次运行方式的情况
				List<Condition> conditions = new ArrayList<Condition>();
				conditions.add(new Condition("a.C_UNIT_ID",
						FieldTypeEnum.STRING, MatchTypeEnum.EQ, runWayEntity
								.getUnitId()));
				conditions.add(new Condition("a.C_RL_ID", FieldTypeEnum.STRING,
						MatchTypeEnum.EQ, runWayEntity.getRlId()));
				List<RunWayEntity> runWayEntityDetailList = this
						.findByCondition("findByCondition", conditions, null);
				if (flag == 0) {
					for (int j = 0; j < runWayEntityDetailList.size(); j++) {
						this.deleteEntity(runWayEntityDetailList.get(j).getId()); // 先删除运行方式为空的数据再新增
						flag++;
					}
				}
				this.findByCondition("findByCondition", conditions, null);
				RunWayEntity addrunWayEntity = new RunWayEntity();
				addrunWayEntity.setDeviceId(Integer.parseInt(runWayArray[1]));
				addrunWayEntity.setDeviceType(runWayArray[2]);
				addrunWayEntity.setRunWay(runWayArray[3]);
				addrunWayEntity.setRlId(runWayEntity.getRlId());
				addrunWayEntity.setUnitId(runWayEntity.getUnitId());
				this.addEntity(addrunWayEntity);
			} else {// 修改本次运行方式
				RunWayEntity editrunWayEntity = new RunWayEntity();
				editrunWayEntity.setId(Long.parseLong(runWayArray[0]));
				editrunWayEntity.setDeviceId(Integer.parseInt(runWayArray[1]));
				editrunWayEntity.setDeviceType(runWayArray[2]);
				editrunWayEntity.setRunWay(runWayArray[3]);
				editrunWayEntity.setUnitId(runWayEntity.getUnitId());
				editrunWayEntity.setRlId(runWayEntity.getRlId());
				this.updateEntity(editrunWayEntity);
			}
		}
		ResultObj resultObj = new ResultObj();
		resultObj.setData(runWayEntity);
		return resultObj;

	}

	public List<RunWayEntity> changeList(List<RunWayEntity> list) {
		List<RunWayEntity> resultList = new ArrayList<RunWayEntity>();
		int rowshow = 0;// 是否显示
		int rowCount = 0;// 合并行数
		for (int i = 0; i < list.size(); i++) {
			RunWayEntity runWayEntity = list.get(i);
			if (i != 0
					&& (!runWayEntity.getUnitId().equals(
							resultList.get(resultList.size() - 1).getUnitId()))) {
				resultList.add(runWayEntity);
				resultList.get(i - rowCount).setRowspanNum(
						String.valueOf(rowCount));
				rowshow = 0;
				rowCount = 0;
			} else {
				resultList.add(runWayEntity);
			}
			// 合拼还是隐藏
			if (rowshow == 0) {
				runWayEntity.setTdHide("show");
			} else {
				runWayEntity.setTdHide("hide");
			}
			if (i == list.size() - 1) {
				resultList.get(i - rowCount).setRowspanNum(
						String.valueOf(rowCount + 1));
			}
			rowshow++;
			rowCount++;
		}
		return resultList;
	}

	/**
	 * @Description: 运行方式编辑
	 * @author changl
	 * @Date 2017年10月10日 上午11:38:38
	 * @throws Exception
	 */
	@Override
	public ResultObj save(String runWay) {
		Map<String, Object> runWayMap = JsonUtil.getMapFromJson(runWay);
		Iterator<Map.Entry<String, Object>> entries = runWayMap.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry<String, Object> entry = entries.next();
			long id= Long.parseLong(entry.getKey().split("-")[1]);
			RunWayEntity runWayEntity=runWayDao.findById(id);
			if(entry.getValue()!=null){
				runWayEntity.setRunWay(entry.getValue().toString());
			}
			runWayDao.updateEntity(runWayEntity);
		}
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.RUNWAY.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		return new ResultObj();
	}
}