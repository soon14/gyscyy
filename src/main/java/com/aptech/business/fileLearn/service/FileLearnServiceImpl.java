package com.aptech.business.fileLearn.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.FileLearnApproveStatusEnum;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.fileLearn.dao.FileLearnDao;
import com.aptech.business.fileLearn.domain.FileLearnEntity;
import com.aptech.business.fileLearn.domain.FileTreeEntity;
import com.aptech.business.fileLearn.exception.FileLearnException;
import com.aptech.business.fileLearn.exception.FileLearnExceptionType;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.workflow.modelEditor.domain.CandidateMarkEnum;
import com.aptech.common.workflow.todoTask.domain.ExamMarkEnum;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 文件学习应用管理服务实现类
 *
 * @author
 * @created 2018-04-02 16:50:12
 * @lastModified
 * @history
 *
 */
@Service("fileLearnService")
@Transactional
public class FileLearnServiceImpl extends
		AbstractBaseEntityOperation<FileLearnEntity> implements
		FileLearnService {

	@Autowired
	private FileLearnDao fileLearnDao;

	@Autowired
	private ActTaskService actTaskService;

	@Autowired
	private SysUnitService sysUnitService;

	@Autowired
	private SysUserService sysUserService;

	@Override
	public IBaseEntityOperation<FileLearnEntity> getDao() {
		return fileLearnDao;
	}

	/**
	 * 提交流程，开始流程
	 */
	@Override
	public ResultObj submit(Serializable id, Map<String, Object> params) {
		ResultObj resultObj = new ResultObj();
		FileLearnEntity t = this.findById(id);
		if (validate(t)) {
			t.setApproveStatus(FileLearnApproveStatusEnum.LEADER.getCode());
			this.updateEntity(t);
			// 准备启动流程
			Map<String, Object> vars = new HashMap<String, Object>();
			vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),
					params.get("userList"));
			if(t.getIsNotification().equals("0")){
				actTaskService.startProcess(
						ProcessMarkEnum.FILE_LEARN_PROCESS_KEY.getName(),
						id.toString(), vars);
			}else{
				actTaskService.startProcess(
						ProcessMarkEnum.FILE_LEARN_NOTIFICATION_PROCESS.getName(),
						id.toString(), vars);
			}
			
		}
		return resultObj;
	}

	/**
	 * 基本验证
	 * 
	 * @param @param t
	 * @param @return
	 * @return boolean
	 * @throws
	 * @author liweiran
	 * @created 2017年7月31日 下午1:23:42
	 * @lastModified
	 */
	public boolean validate(FileLearnEntity t) {
		if (t == null) {
			throw new FileLearnException(
					FileLearnExceptionType.FILE_LEARN_CODE_NULL);
		}
		if (!(t.getApproveStatus().equals(
				FileLearnApproveStatusEnum.PENDING.getCode()) || t
				.getApproveStatus().equals(
						FileLearnApproveStatusEnum.REJECT.getCode()))) {
			throw new FileLearnException(
					FileLearnExceptionType.FILE_LEARN_CODE_STATUS);
		}
		return true;
	}

	/**
	 * 审核后继续下一步流程
	 */
	@Override
	public ResultObj approve(FileLearnEntity t, Map<String, Object> params) {
		if (validateStatus(t)) {
			// 修改状态
			FileLearnEntity fileLearnEntity = this.findById(t.getId());
			fileLearnEntity.setApproveStatus(params.get("status").toString());
			this.updateEntity(fileLearnEntity);
			// 设置当前流程,当前任务节点的下一个节点的办理人
			actTaskService.setVarialble(t.getTaskId(), params);
			// 调用流程接口
			params.put(ExamMarkEnum.RESULT.getCode(), params.get("result")
					.toString());
			actTaskService.complete(t.getTaskId(), t.getProcInstId(), params);
		}
		return new ResultObj();
	}

	/**
	 * 流程基本验证
	 * 
	 * @param @param t
	 * @param @return
	 * @return boolean
	 * @throws
	 * @author liweiran
	 * @created 2017年7月31日 下午1:23:52
	 * @lastModified
	 */
	public boolean validateStatus(FileLearnEntity t) {
		FileLearnEntity tEntity = this.findById(t.getId());
		if (tEntity == null) {
			throw new FileLearnException(
					FileLearnExceptionType.FILE_LEARN_CODE_NULL);
		}
		if (!t.getApproveStatus().equals(tEntity.getApproveStatus())) {
			throw new FileLearnException(
					FileLearnExceptionType.FILE_LEARN_CODE_REPEAT);
		}
		return true;
	}

	@Override
	public List<FileTreeEntity> getRecipientTree() {
		List<Condition> conditions = new ArrayList<Condition>();
		// 组织机构
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING,
				MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL
						.ordinal())));
		conditions.add(new Condition("C_LEVEL", FieldTypeEnum.STRING,
				MatchTypeEnum.NE, "4"));
		/*conditions.add(new Condition("C_GENERATION_TYPE", FieldTypeEnum.STRING,
				MatchTypeEnum.NE, "2"));*/
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(
				conditions, null);

		// user
		conditions.clear();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING,
				MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL
						.ordinal())));
		List<SysUserEntity> userList = sysUserService.findByCondition(
				conditions, null);

		List<FileTreeEntity> treeList = new ArrayList<FileTreeEntity>();
		if (unitList != null && unitList.size() > 0) {
			for (SysUnitEntity unitEntity : unitList) {
				if (unitEntity != null) {

					FileTreeEntity treeEntity = new FileTreeEntity();
					treeEntity.setId("unit"+unitEntity.getId().toString());
					treeEntity.setCode(unitEntity.getCode());
					treeEntity.setName(unitEntity.getName());
					treeEntity.setParentId("unit"+unitEntity.getParentId().toString());
					treeEntity.setNodeType("1");
					treeList.add(treeEntity);
					if (userList != null && userList.size() > 0) {
						for (SysUserEntity userEntity : userList) {
							if (userEntity != null
									&& unitEntity
											.getId()
											.toString()
											.equals(userEntity.getUnitId()
													.toString())) {
								FileTreeEntity tEntity = new FileTreeEntity();
								tEntity.setId("user"+userEntity.getId().toString());
								tEntity.setCode(userEntity.getLoginName());
								tEntity.setName(userEntity.getName());
								tEntity.setParentId("unit"+unitEntity.getId()
										.toString());
								tEntity.setNodeType("2");
								treeList.add(tEntity);
							}
						}
					}
				}
			}
		}

		return treeList;
	}

}