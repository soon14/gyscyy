package com.aptech.business.run.runLog.service;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.SysUnitLevelEnum;
import com.aptech.business.equip.equipLedger.domain.EquipLedgerEntity;
import com.aptech.business.equip.equipLedger.service.EquipLedgerService;
import com.aptech.business.overhaul.overhaulLog.domain.OverhaulLogEntity;
import com.aptech.business.run.joinLand.domain.JoinLandEntity;
import com.aptech.business.run.joinLand.service.JoinLandService;
import com.aptech.business.run.runLog.dao.RunLogDao;
import com.aptech.business.run.runLog.domain.RunLogEntity;
import com.aptech.business.run.runLog.domain.RunLogEnum;
import com.aptech.business.run.runLog.exception.RunLogException;
import com.aptech.business.run.runLog.exception.RunLogExceptionType;
import com.aptech.business.run.runWay.domain.RunWayEntity;
import com.aptech.business.run.runWay.service.RunWayService;
import com.aptech.business.run.safeMeeting.dao.SafeMeetingDao;
import com.aptech.business.run.safeMeeting.domain.SafeMeetingEntity;
import com.aptech.business.run.safeMeeting.domain.SafeMeetingEnum;
import com.aptech.business.run.safeMeeting.service.SafeMeetingService;
import com.aptech.business.teamMemApp.domain.TeamMemAppEntity;
import com.aptech.business.teamMemApp.service.TeamMemAppService;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.web.base.ResultListObj;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.EncryptUtil;
import com.aptech.framework.web.base.PageUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 运行日志应用管理服务实现类
 *
 * @author
 * @created 2017-06-05 10:52:45
 * @lastModified
 * @history
 *
 */
@Service("runLogService")
@Transactional
public class RunLogServiceImpl extends
		AbstractBaseEntityOperation<RunLogEntity> implements RunLogService {

	@Autowired
	private RunLogDao runLogDao;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private JoinLandService joinLandService;
	@Autowired
	private RunWayService runWayService;
	@Autowired
	private EquipLedgerService equipLedgerService;
	@Autowired
	private SafeMeetingService safeMeetingService;
	@Autowired
	private TeamMemAppService teamMemAppService;
	@Autowired
	private SafeMeetingDao safeMeetingDao;
	@Override
	public IBaseEntityOperation<RunLogEntity> getDao() {
		return runLogDao;
	}

	@Override
	public ResultObj joinTeam(RunLogEntity runLogEntity) throws Exception {
		SysUserEntity userEntity = RequestContext.get().getUser();
		ResultObj resultObj = new ResultObj();
		if (validate(runLogEntity)) {
			/*
			 * 修改当前运行日志信息，增加交班信息。
			 */
			RunLogEntity updateRunLogEntity = this.findById(runLogEntity
					.getId());
			updateRunLogEntity.setGiveDate(runLogEntity.getGiveDate());
			updateRunLogEntity.setGiveTeamId(runLogEntity.getGiveTeamId());
			updateRunLogEntity.setGiveDutyId(runLogEntity.getGiveDutyId());
			updateRunLogEntity.setGiveChargeId(runLogEntity.getGiveChargeId());
			updateRunLogEntity.setGivePersonsIds(runLogEntity
					.getGivePersonsIds());
			updateRunLogEntity.setGrState("done");
			updateRunLogEntity.setJfState(runLogEntity.getJfState());
			updateRunLogEntity.setGivePassword(new BigInteger(EncryptUtil
					.encryptSHA(runLogEntity.getGivePassword().getBytes()))
					.toString(32));
			updateRunLogEntity.setReceivePassword(new BigInteger(EncryptUtil
					.encryptSHA(runLogEntity.getReceivePassword().getBytes()))
					.toString(32));
			updateRunLogEntity.setReceivePassword(runLogEntity.getRemark());
			updateRunLogEntity.setFileId(runLogEntity.getFileId());
			updateRunLogEntity.setCreateDate(new Date());
			updateRunLogEntity.setCheck(runLogEntity.getCheck());
			updateRunLogEntity.setUnit(Integer.parseInt(userEntity.getUnitId()
					.toString()));
			this.updateEntity(updateRunLogEntity);
			// 新增一条运行日志信息，信息为接班相关
			RunLogEntity insertRunLogEntity = new RunLogEntity();
			insertRunLogEntity.setDate(runLogEntity.getGiveDate());
			insertRunLogEntity.setTeamId(runLogEntity.getTeamId());
			insertRunLogEntity.setDutyId(runLogEntity.getDutyId());
			insertRunLogEntity.setChargeId(runLogEntity.getChargeId());
			insertRunLogEntity.setPersonsIds(runLogEntity.getPersonsIds());
			insertRunLogEntity.setUnit(Integer.parseInt(userEntity.getUnitId()
					.toString()));
			insertRunLogEntity.setFileId("[]");
			if (userEntity.getUnitId() == 132) {
				TeamMemAppEntity teamMemAppEntity = teamMemAppService
						.findById(runLogEntity.getTeamId().longValue());
				if (teamMemAppEntity != null) {
					// 中班 带回会前内容
					if (teamMemAppEntity.getCode().toString().equals("1002")) {
						insertRunLogEntity.setBtmContent(updateRunLogEntity
								.getBtmContent());
						insertRunLogEntity.setBtmPersonsIds(updateRunLogEntity
								.getBtmPersonsIds());
					}
					// 晚班 带回会前会后内容
					if (teamMemAppEntity.getCode().toString().equals("1003")) {
						insertRunLogEntity.setBtmContent(updateRunLogEntity
								.getBtmContent());
						insertRunLogEntity.setBtmPersonsIds(updateRunLogEntity
								.getBtmPersonsIds());
						insertRunLogEntity.setAtmContent(updateRunLogEntity
								.getAtmContent());
						insertRunLogEntity.setAtmPersonsIds(updateRunLogEntity
								.getAtmPersonsIds());
					}
				}
			}
			insertRunLogEntity.setGrState("undo");
			this.addEntity(insertRunLogEntity);
			SafeMeetingEntity safeMeetingEntityfixone = new SafeMeetingEntity();
			safeMeetingEntityfixone.setRlId(insertRunLogEntity.getId()
					.intValue());
			safeMeetingEntityfixone
					.setMeetingContent(SafeMeetingEnum.MACHINEROOMINSPECTION
							.getName());
			safeMeetingEntityfixone.setCheckState("uncheck");
			safeMeetingEntityfixone.setMeetingFlag(2);
			safeMeetingEntityfixone.setReceptDate(updateRunLogEntity.getDate());
			safeMeetingService.addEntity(safeMeetingEntityfixone);
			SafeMeetingEntity safeMeetingEntityfixtwo = new SafeMeetingEntity();
			safeMeetingEntityfixtwo.setRlId(insertRunLogEntity.getId()
					.intValue());
			safeMeetingEntityfixtwo
					.setMeetingContent(SafeMeetingEnum.CONTROLCENTERINSPECTION
							.getName());
			safeMeetingEntityfixtwo.setCheckState("uncheck");
			safeMeetingEntityfixtwo.setMeetingFlag(2);
			safeMeetingEntityfixtwo.setReceptDate(updateRunLogEntity.getDate());
			safeMeetingService.addEntity(safeMeetingEntityfixtwo);
			SafeMeetingEntity safeMeetingEntityfixthree = new SafeMeetingEntity();
			safeMeetingEntityfixthree.setRlId(insertRunLogEntity.getId()
					.intValue());
			safeMeetingEntityfixthree.setMeetingContent(SafeMeetingEnum.TWORULE
					.getName());
			safeMeetingEntityfixthree.setCheckState("uncheck");
			safeMeetingEntityfixthree.setMeetingFlag(2);
			safeMeetingEntityfixthree.setReceptDate(updateRunLogEntity.getDate());
			safeMeetingService.addEntity(safeMeetingEntityfixthree);
			// 获取上一个班未完成的工作安排
			int rl_id = insertRunLogEntity.getId().intValue() - 1;
			List<Condition> conditions = new ArrayList<Condition>();
			conditions.add(new Condition("a.C_RL_ID", FieldTypeEnum.STRING,
					MatchTypeEnum.EQ, String.valueOf(rl_id)));
			conditions.add(new Condition("a.C_CHECK_STATE",
					FieldTypeEnum.STRING, MatchTypeEnum.EQ,
					SafeMeetingEnum.UNCHECK.getCode()));
			conditions.add(new Condition("a.C_MEETING_FLAG",
					FieldTypeEnum.STRING, MatchTypeEnum.EQ, SafeMeetingEnum.ONE
							.getId()));
			List<SafeMeetingEntity> meetingEntities = safeMeetingService
					.findByCondition(conditions, null);
			if (!meetingEntities.isEmpty()) {
				for (SafeMeetingEntity entity : meetingEntities) {
					SafeMeetingEntity safeMeetingEntity = new SafeMeetingEntity();
					safeMeetingEntity.setRlId(insertRunLogEntity.getId()
							.intValue());
					safeMeetingEntity.setMeetingContent(entity
							.getMeetingContent());
					safeMeetingEntity.setCheckState("uncheck");
					safeMeetingEntity.setMeetingFlag(1);
					safeMeetingService.addEntity(safeMeetingEntity);
				}
			}
			// 关联初始化接地线刀闸表信息
			conditions.clear();
			conditions.add(new Condition("C_RL_ID", FieldTypeEnum.STRING,
					MatchTypeEnum.EQ, String.valueOf(rl_id)));
			List<JoinLandEntity> joinLandList = joinLandService
					.findByCondition(conditions, null);
			conditions.clear();
			conditions.add(new Condition("C_TREE_TYPE", FieldTypeEnum.STRING,
					MatchTypeEnum.EQ, SysUnitLevelEnum.LEVEL2.getCode()));
			conditions.add(new Condition("T1.C_STATUS", FieldTypeEnum.STRING,
					MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL
							.ordinal())));
			conditions.add(new Condition(
					"T.C_BUSINESS_TYPE = '02' OR  T.C_BUSINESS_TYPE = '03' "));
			List<EquipLedgerEntity> equipListForJoinLand = equipLedgerService
					.findByCondition("findLedgerByCondition", conditions, null);
			for (int i = 0; i < equipListForJoinLand.size(); i++) {
				EquipLedgerEntity equipLedgerEntity = equipListForJoinLand
						.get(i);
				JoinLandEntity joinLandEntity = new JoinLandEntity();
				joinLandEntity
						.setDeviceId(equipLedgerEntity.getId().intValue());
				joinLandEntity.setDeviceType(equipLedgerEntity.getEquipType()
						+ "");
				joinLandEntity.setUnitId(Integer.parseInt(equipLedgerEntity
						.getCode()));
				joinLandEntity.setRlId(insertRunLogEntity.getId().intValue());
				for (JoinLandEntity jlEntity : joinLandList) {
					if (jlEntity.getDeviceId().toString()
							.equals(joinLandEntity.getDeviceId().toString())) {
						joinLandEntity.setSwordbrake_status(jlEntity
								.getSwordbrake_status());
					}
				}
				joinLandService.addEntity(joinLandEntity);
			}
			// 关联初始化运行方式信息
			List<Condition> conditions1 = new ArrayList<Condition>();
			conditions1.add(new Condition("C_RL_ID", FieldTypeEnum.STRING,
					MatchTypeEnum.EQ, String.valueOf(rl_id)));
			List<RunWayEntity> runWayList = runWayService.findByCondition(
					conditions1, null);
			conditions1.clear();
			conditions1.add(new Condition("C_TREE_TYPE", FieldTypeEnum.STRING,
					MatchTypeEnum.EQ, SysUnitLevelEnum.LEVEL2.getCode()));
			conditions1.add(new Condition("T1.C_STATUS", FieldTypeEnum.STRING,
					MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL
							.ordinal())));
			conditions1.add(new Condition(
					"T.C_BUSINESS_TYPE = '01' OR  T.C_BUSINESS_TYPE = '03' "));
			List<EquipLedgerEntity> equipList = equipLedgerService
					.findByCondition("findLedgerByCondition", conditions1, null);
			for (int i = 0; i < equipList.size(); i++) {
				EquipLedgerEntity equipLedgerEntity = equipList.get(i);
				RunWayEntity runWayEntity = new RunWayEntity();
				runWayEntity.setDeviceId(equipLedgerEntity.getId().intValue());
				runWayEntity.setDeviceType(equipLedgerEntity.getEquipType()
						+ "");
				runWayEntity.setUnitId(Integer.parseInt(equipLedgerEntity
						.getCode()));
				runWayEntity.setRlId(insertRunLogEntity.getId().intValue());
				for (RunWayEntity rwEntity : runWayList) {
					if (rwEntity.getDeviceId().toString()
							.equals(runWayEntity.getDeviceId().toString())) {
						runWayEntity.setRunWay(rwEntity.getRunWay());
					}
				}
				runWayService.addEntity(runWayEntity);

			}
			resultObj.setData(runLogEntity);
		}
		return resultObj;
	}

	@Override
	public void updateMeeting(RunLogEntity runLogEntity) throws Exception {
		RunLogEntity updateRunLogEntity = this.findById(runLogEntity.getId());
		updateRunLogEntity.setBtmContent(runLogEntity.getBtmContent());
		updateRunLogEntity.setBtmPersonsIds(runLogEntity.getBtmPersonsIds());
		updateRunLogEntity.setAtmContent(runLogEntity.getAtmContent());
		updateRunLogEntity.setAtmPersonsIds(runLogEntity.getAtmPersonsIds());
		this.updateEntity(updateRunLogEntity);
	}

	// 基本验证
	public boolean validate(RunLogEntity t) throws Exception {
		SysUserEntity gSysUserEntity = sysUserService.findById(new Long(t
				.getGiveChargeId()));
		SysUserEntity rSysUserEntity = sysUserService.findById(new Long(t
				.getChargeId()));
		String gPWDTemp = EncryptUtil.encryptSHA1(t.getGivePassword()
				.getBytes());
		String rPWDTemp = EncryptUtil.encryptSHA1(t.getReceivePassword()
				.getBytes());
		if (!gPWDTemp.equals(gSysUserEntity.getPassword())) {
			throw new RunLogException(RunLogExceptionType.RUNLOG_CODE_GPWD);
		}
		if (!rPWDTemp.equals(rSysUserEntity.getPassword())) {
			throw new RunLogException(RunLogExceptionType.RUNLOG_CODE_RPWD);
		}
		DateFormatUtil df = DateFormatUtil.getInstance("yyyy-MM-dd");
		String dateStr = df.format(t.getGiveDate());
		String startDate = dateStr + " 00:00:00";
		String endDate = dateStr + " 23:59:59";
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("a.C_TEAM_ID", FieldTypeEnum.INT,
				MatchTypeEnum.EQ, t.getTeamId()));
		conditions.add(new Condition("a.C_DATE", FieldTypeEnum.DATE,
				MatchTypeEnum.GE, startDate));
		conditions.add(new Condition("a.C_DATE", FieldTypeEnum.DATE,
				MatchTypeEnum.LE, endDate));
		List<RunLogEntity> runLogEntityList = this.findByCondition(conditions,
				null);
		if (runLogEntityList.size() != 0) {
			throw new RunLogException(RunLogExceptionType.RUNLOG_CODE_DATE);
		}
		conditions.clear();
		conditions.add(new Condition("a.C_TEAM_ID", FieldTypeEnum.INT,
				MatchTypeEnum.EQ, t.getTeamId()));
		conditions.add(new Condition("a.C_GIVE_DATE", FieldTypeEnum.DATE,
				MatchTypeEnum.GE, startDate));
		conditions.add(new Condition("a.C_GIVE_DATE", FieldTypeEnum.DATE,
				MatchTypeEnum.LE, endDate));
		List<RunLogEntity> runLogEntityGiveList = this.findByCondition(
				conditions, null);
//		if (runLogEntityGiveList.size() != 0) {
//			throw new RunLogException(RunLogExceptionType.RUNLOG_CODE_GIVEDATE);
//		}
		return true;
	}

	public boolean validateDel(RunLogEntity t) throws Exception {
		if ("done".equals(t.getGrState())) {
			throw new RunLogException(RunLogExceptionType.RUNLOG_CODE_GRDONE);
		}
		return true;
	}

	public boolean validateJoin(RunLogEntity t) throws Exception {
		SysUserEntity userEntity = RequestContext.get().getUser();
		if (!String.valueOf(userEntity.getId()).equals(
				t.getChargeId().toString())
				&& !userEntity.getLoginName().equals("admin")) {
			throw new RunLogException(RunLogExceptionType.RUNLOG_CODE_JOIN);
		}
		return true;
	}

	@Override
	public ResultObj delete(Serializable id) throws Exception {
		ResultObj resultObj = new ResultObj();
		RunLogEntity t = this.findById(id);
		if (validateDel(t)) {
			this.deleteEntity(id);
		}
		return resultObj;
	}

	@Override
	public ResultObj invalidValidate(Long id) throws Exception {
		ResultObj resultObj = new ResultObj();
		RunLogEntity t = this.findById(id);
		validateJoin(t);
		return resultObj;
	}

	@Override
	public ResultObj addForWind(RunLogEntity t, HttpServletRequest request)
			throws Exception {
		SysUserEntity userEntity = RequestContext.get().getUser();
		ResultObj resultObj = new ResultObj();
		runLogDao.setFlag(false);
//		t.setDate(t.getGiveDate());
		t.setCreateDate(new Date());
		t.setDutyId(t.getGiveDutyId());
		t.setChargeId(t.getGiveChargeId());
		t.setGivePassword(RunLogEnum.PASSWORD.getCode());
		t.setReceivePassword(RunLogEnum.PASSWORD.getCode());
		t.setGrState(RunLogEnum.GRSTATEUNDO.getCode());
		t.setUnit(Integer.parseInt(userEntity.getUnitId() + ""));
		t.setTeamId(1);
		runLogDao.addEntity(t);
		runLogDao.setFlag(true);
		resultObj.setData(t);
		return resultObj;
	}

	@Override
	public ResultListObj searchDataForWind(HttpServletRequest request,
			Map<String, Object> params) throws Exception {
		ResultListObj resultObj = new ResultListObj();
		SysUserEntity userEntity = RequestContext.get().getUser();
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("a.C_UNIT", FieldTypeEnum.INT,MatchTypeEnum.EQ, userEntity.getUnitId()));
		Page<OverhaulLogEntity> page = PageUtil.getPage(params);
		page.setOrders(OrmUtil.changeMapToOrders(params));
		page.addOrder(Sort.desc("C_DATE"));
		runLogDao.setFlag(false);
		List<OverhaulLogEntity> overhaulLogEntities = new ArrayList<OverhaulLogEntity>();
		if (userEntity.getLoginName().equals("super") || userEntity.getLoginName().equals("admin")) {
			conditions.clear();
			overhaulLogEntities = (List<OverhaulLogEntity>)runLogDao.findByCondition(conditions, page);
		}else {
			overhaulLogEntities =  (List<OverhaulLogEntity>) runLogDao.findByCondition(conditions, page);
		}
//		List<OverhaulLogEntity> overhaulLogEntities = (List<OverhaulLogEntity>) runLogDao.findByCondition(conditions, page);
		runLogDao.setFlag(true);
		resultObj.setDraw((Integer) params.get("draw"));
		if (overhaulLogEntities != null) {
			resultObj.setData(overhaulLogEntities);
			resultObj.setRecordsTotal(page.getTotal());
		}
		return resultObj;
	}
	
	@Override
	public void updateMeetingForWind(RunLogEntity runLogEntity) throws Exception {
		runLogDao.setFlag(false);
		RunLogEntity updateRunLogEntity = this.findById(runLogEntity.getId());
		updateRunLogEntity.setBtmContent(runLogEntity.getBtmContent());
		updateRunLogEntity.setBtmPersonsIds(runLogEntity.getBtmPersonsIds());
		updateRunLogEntity.setAtmContent(runLogEntity.getAtmContent());
		updateRunLogEntity.setAtmPersonsIds(runLogEntity.getAtmPersonsIds());
		runLogDao.updateEntity(updateRunLogEntity);
		runLogDao.setFlag(true);
	}

	@Override
	public RunLogEntity getDataById(Long rlId) {
		runLogDao.setFlag(false);
		RunLogEntity runLogEntity = (RunLogEntity)runLogDao.findById(rlId);
		runLogDao.setFlag(true);
		return runLogEntity;
	}

	@Override
	public ResultObj invalidValidateWind(Long id) throws Exception {
		// TODO Auto-generated method stub
		ResultObj resultObj = new ResultObj();
		runLogDao.setFlag(false);
		RunLogEntity t = (RunLogEntity)runLogDao.findById(id);
		validateJoin(t);
		return resultObj;
	}

	@Override
	public ResultObj joinTeamWind(RunLogEntity runLogEntity) throws Exception {
		// TODO Auto-generated method stub
		runLogDao.setFlag(false);
		SysUserEntity userEntity = RequestContext.get().getUser();
		ResultObj resultObj = new ResultObj();
		if (validate(runLogEntity)) {
			/*
			 * 修改当前运行日志信息，增加交班信息。
			 */
			RunLogEntity updateRunLogEntity = this.findById(runLogEntity
					.getId());
			updateRunLogEntity.setGiveDate(runLogEntity.getGiveDate());
			updateRunLogEntity.setGiveTeamId(runLogEntity.getGiveTeamId());
			updateRunLogEntity.setGiveDutyId(runLogEntity.getGiveDutyId());
			updateRunLogEntity.setGiveChargeId(runLogEntity.getGiveChargeId());
			updateRunLogEntity.setGivePersonsIds(runLogEntity
					.getGivePersonsIds());
			updateRunLogEntity.setGrState("done");
			updateRunLogEntity.setJfState(runLogEntity.getJfState());
			updateRunLogEntity.setGivePassword(new BigInteger(EncryptUtil
					.encryptSHA(runLogEntity.getGivePassword().getBytes()))
					.toString(32));
			updateRunLogEntity.setReceivePassword(new BigInteger(EncryptUtil
					.encryptSHA(runLogEntity.getReceivePassword().getBytes()))
					.toString(32));
			updateRunLogEntity.setReceivePassword(runLogEntity.getRemark());
			updateRunLogEntity.setFileId(runLogEntity.getFileId());
			updateRunLogEntity.setCreateDate(new Date());
			updateRunLogEntity.setCheck(runLogEntity.getCheck());
			updateRunLogEntity.setUnit(Integer.parseInt(userEntity.getUnitId()
					.toString()));
			this.updateEntity(updateRunLogEntity);
			// 新增一条运行日志信息，信息为接班相关
			RunLogEntity insertRunLogEntity = new RunLogEntity();
			insertRunLogEntity.setDate(runLogEntity.getGiveDate());
			insertRunLogEntity.setTeamId(runLogEntity.getTeamId());
			insertRunLogEntity.setDutyId(runLogEntity.getDutyId());
			insertRunLogEntity.setChargeId(runLogEntity.getChargeId());
			insertRunLogEntity.setPersonsIds(runLogEntity.getPersonsIds());
			insertRunLogEntity.setUnit(Integer.parseInt(userEntity.getUnitId()
					.toString()));
			insertRunLogEntity.setFileId("[]");
			if (userEntity.getUnitId() == 132) {
				TeamMemAppEntity teamMemAppEntity = teamMemAppService
						.findById(runLogEntity.getTeamId().longValue());
				if (teamMemAppEntity != null) {
					// 中班 带回会前内容
					if (teamMemAppEntity.getCode().toString().equals("1002")) {
						insertRunLogEntity.setBtmContent(updateRunLogEntity
								.getBtmContent());
						insertRunLogEntity.setBtmPersonsIds(updateRunLogEntity
								.getBtmPersonsIds());
					}
					// 晚班 带回会前会后内容
					if (teamMemAppEntity.getCode().toString().equals("1003")) {
						insertRunLogEntity.setBtmContent(updateRunLogEntity
								.getBtmContent());
						insertRunLogEntity.setBtmPersonsIds(updateRunLogEntity
								.getBtmPersonsIds());
						insertRunLogEntity.setAtmContent(updateRunLogEntity
								.getAtmContent());
						insertRunLogEntity.setAtmPersonsIds(updateRunLogEntity
								.getAtmPersonsIds());
					}
				}
			}
			insertRunLogEntity.setGrState("undo");
			this.addEntity(insertRunLogEntity);
			safeMeetingDao.setFlag(false);
			SafeMeetingEntity safeMeetingEntityfixone = new SafeMeetingEntity();
			safeMeetingEntityfixone.setRlId(insertRunLogEntity.getId()
					.intValue());
			safeMeetingEntityfixone
					.setMeetingContent(SafeMeetingEnum.MACHINEROOMINSPECTION
							.getName());
			safeMeetingEntityfixone.setCheckState("uncheck");
			safeMeetingEntityfixone.setMeetingFlag(2);
			safeMeetingService.addEntity(safeMeetingEntityfixone);
			safeMeetingDao.setFlag(false);
			SafeMeetingEntity safeMeetingEntityfixtwo = new SafeMeetingEntity();
			safeMeetingEntityfixtwo.setRlId(insertRunLogEntity.getId()
					.intValue());
			safeMeetingEntityfixtwo
					.setMeetingContent(SafeMeetingEnum.CONTROLCENTERINSPECTION
							.getName());
			safeMeetingEntityfixtwo.setCheckState("uncheck");
			safeMeetingEntityfixtwo.setMeetingFlag(2);
			safeMeetingService.addEntity(safeMeetingEntityfixtwo);
			safeMeetingDao.setFlag(false);
			SafeMeetingEntity safeMeetingEntityfixthree = new SafeMeetingEntity();
			safeMeetingEntityfixthree.setRlId(insertRunLogEntity.getId()
					.intValue());
			safeMeetingEntityfixthree.setMeetingContent(SafeMeetingEnum.TWORULE
					.getName());
			safeMeetingEntityfixthree.setCheckState("uncheck");
			safeMeetingEntityfixthree.setMeetingFlag(2);
			safeMeetingService.addEntity(safeMeetingEntityfixthree);
			// 获取上一个班未完成的工作安排
			int rl_id = insertRunLogEntity.getId().intValue() - 1;
			List<Condition> conditions = new ArrayList<Condition>();
			conditions.add(new Condition("a.C_RL_ID", FieldTypeEnum.STRING,
					MatchTypeEnum.EQ, String.valueOf(rl_id)));
			conditions.add(new Condition("a.C_CHECK_STATE",
					FieldTypeEnum.STRING, MatchTypeEnum.EQ,
					SafeMeetingEnum.UNCHECK.getCode()));
			conditions.add(new Condition("a.C_MEETING_FLAG",
					FieldTypeEnum.STRING, MatchTypeEnum.EQ, SafeMeetingEnum.ONE
							.getId()));
			List<SafeMeetingEntity> meetingEntities = safeMeetingService
					.findByCondition(conditions, null);
			if (!meetingEntities.isEmpty()) {
				for (SafeMeetingEntity entity : meetingEntities) {
					SafeMeetingEntity safeMeetingEntity = new SafeMeetingEntity();
					safeMeetingEntity.setRlId(insertRunLogEntity.getId()
							.intValue());
					safeMeetingEntity.setMeetingContent(entity
							.getMeetingContent());
					safeMeetingEntity.setCheckState("uncheck");
					safeMeetingEntity.setMeetingFlag(1);
					safeMeetingService.addEntity(safeMeetingEntity);
				}
			}
			// 关联初始化接地线刀闸表信息
			conditions.clear();
			conditions.add(new Condition("C_RL_ID", FieldTypeEnum.STRING,
					MatchTypeEnum.EQ, String.valueOf(rl_id)));
			List<JoinLandEntity> joinLandList = joinLandService
					.findByCondition(conditions, null);
			conditions.clear();
			conditions.add(new Condition("C_TREE_TYPE", FieldTypeEnum.STRING,
					MatchTypeEnum.EQ, SysUnitLevelEnum.LEVEL2.getCode()));
			conditions.add(new Condition("T1.C_STATUS", FieldTypeEnum.STRING,
					MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL
							.ordinal())));
			conditions.add(new Condition(
					"T.C_BUSINESS_TYPE = '02' OR  T.C_BUSINESS_TYPE = '03' "));
			List<EquipLedgerEntity> equipListForJoinLand = equipLedgerService
					.findByCondition("findLedgerByCondition", conditions, null);
			for (int i = 0; i < equipListForJoinLand.size(); i++) {
				EquipLedgerEntity equipLedgerEntity = equipListForJoinLand
						.get(i);
				JoinLandEntity joinLandEntity = new JoinLandEntity();
				joinLandEntity
						.setDeviceId(equipLedgerEntity.getId().intValue());
				joinLandEntity.setDeviceType(equipLedgerEntity.getEquipType()
						+ "");
				joinLandEntity.setUnitId(Integer.parseInt(equipLedgerEntity
						.getCode()));
				joinLandEntity.setRlId(insertRunLogEntity.getId().intValue());
				for (JoinLandEntity jlEntity : joinLandList) {
					if (jlEntity.getDeviceId().toString()
							.equals(joinLandEntity.getDeviceId().toString())) {
						joinLandEntity.setSwordbrake_status(jlEntity
								.getSwordbrake_status());
					}
				}
				joinLandService.addEntity(joinLandEntity);
			}
			// 关联初始化运行方式信息
			List<Condition> conditions1 = new ArrayList<Condition>();
			conditions1.add(new Condition("C_RL_ID", FieldTypeEnum.STRING,
					MatchTypeEnum.EQ, String.valueOf(rl_id)));
			List<RunWayEntity> runWayList = runWayService.findByCondition(
					conditions1, null);
			conditions1.clear();
			conditions1.add(new Condition("C_TREE_TYPE", FieldTypeEnum.STRING,
					MatchTypeEnum.EQ, SysUnitLevelEnum.LEVEL2.getCode()));
			conditions1.add(new Condition("T1.C_STATUS", FieldTypeEnum.STRING,
					MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL
							.ordinal())));
			conditions1.add(new Condition(
					"T.C_BUSINESS_TYPE = '01' OR  T.C_BUSINESS_TYPE = '03' "));
			List<EquipLedgerEntity> equipList = equipLedgerService
					.findByCondition("findLedgerByCondition", conditions1, null);
			for (int i = 0; i < equipList.size(); i++) {
				EquipLedgerEntity equipLedgerEntity = equipList.get(i);
				RunWayEntity runWayEntity = new RunWayEntity();
				runWayEntity.setDeviceId(equipLedgerEntity.getId().intValue());
				runWayEntity.setDeviceType(equipLedgerEntity.getEquipType()
						+ "");
				runWayEntity.setUnitId(Integer.parseInt(equipLedgerEntity
						.getCode()));
				runWayEntity.setRlId(insertRunLogEntity.getId().intValue());
				for (RunWayEntity rwEntity : runWayList) {
					if (rwEntity.getDeviceId().toString()
							.equals(runWayEntity.getDeviceId().toString())) {
						runWayEntity.setRunWay(rwEntity.getRunWay());
					}
				}
				runWayService.addEntity(runWayEntity);

			}
			resultObj.setData(runLogEntity);
		}
		return resultObj;
	}
	
	/**
	 * @Description: 查询
	 * @author changl
	 * @Date 2016年11月7日 下午6:00:01
	 * @throws Exception
	 */
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if (page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
		page.setOrders(OrmUtil.changeMapToOrders(params));
		page.addOrder(Sort.desc("date"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		return findByCondition(conditions, page);
	}

}