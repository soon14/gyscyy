package com.aptech.business.run.joinLand.service;

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
import com.aptech.business.run.joinLand.dao.JoinLandDao;
import com.aptech.business.run.joinLand.domain.JoinLandEntity;
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
 * 接地线（刀闸）情况应用管理服务实现类
 *
 * @author 
 * @created 2017-06-06 09:48:08
 * @lastModified 
 * @history
 *
 */
@Service("joinLandService")
@Transactional
public class JoinLandServiceImpl extends AbstractBaseEntityOperation<JoinLandEntity> implements JoinLandService {
	
	@Autowired
	private JoinLandDao joinLandDao;
	@Autowired
	private OperateLogService operateLogService;
	@Override
	public IBaseEntityOperation<JoinLandEntity> getDao() {
		return joinLandDao;
	}
	@Override
    public ResultObj insertList(JoinLandEntity joinLandEntity) throws Exception {
        List<Condition> conditions=new ArrayList<Condition>();
        conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,joinLandEntity.getUnitId()));
        conditions.add(new Condition("a.C_RL_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,joinLandEntity.getRlId()));
        List<JoinLandEntity> runWayEntityDetailList=this.findByCondition("findByCondition",conditions, null);
        for(int j=0;j<runWayEntityDetailList.size();j++){
            this.deleteEntity(runWayEntityDetailList.get(j).getId()); //先删除运行方式为空的数据再新增
        }
        String [] deviceIdArray=joinLandEntity.getClosedPosition().split(",");
        String [] deviceTypeArray=joinLandEntity.getDepositGroup().split(",");
     for(int i=0;i<joinLandEntity.getState().length;i++){
         joinLandEntity.setClosedName(joinLandEntity.getState()[i]);
         joinLandEntity.setClosedPosition(joinLandEntity.getCode()[i]);
         joinLandEntity.setDeviceId(new Integer(deviceIdArray[i]).intValue());
         joinLandEntity.setDeviceType(deviceTypeArray[i]);
         joinLandEntity.setRlId(joinLandEntity.getRlId());
         joinLandEntity.setUnitId(joinLandEntity.getUnitId());
         this.addEntity(joinLandEntity);
     }
       ResultObj resultObj = new ResultObj();
       resultObj.setData(joinLandEntity);
       return resultObj;

    }
    @Override
    public ResultObj editList(JoinLandEntity joinLandEntity) throws Exception {
        int flag=0;
        String[] jLId=joinLandEntity.getClosedName().split(",");
        String [] deviceIdArray=joinLandEntity.getClosedPosition().split(",");
        String [] deviceTypeArray=joinLandEntity.getDepositGroup().split(",");
        if("0".equals(jLId[0])){   //初始化上次运行方式的情况
                List<Condition> conditions=new ArrayList<Condition>();
                conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,joinLandEntity.getUnitId()));
                conditions.add(new Condition("a.C_RL_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,joinLandEntity.getRlId()));
                List<JoinLandEntity> runWayEntityDetailList=this.findByCondition("findByCondition",conditions, null); 
                if(flag==0){
                    for(int j=0;j<runWayEntityDetailList.size();j++){
                        this.deleteEntity(runWayEntityDetailList.get(j).getId()); //先删除运行方式为空的数据再新增
                        flag++;
                    }
                }               
                
             for(int i=0;i<joinLandEntity.getState().length;i++){
                 joinLandEntity.setClosedName(joinLandEntity.getState()[i]);
                 joinLandEntity.setClosedPosition(joinLandEntity.getCode()[i]);
                 joinLandEntity.setDeviceId(new Integer(deviceIdArray[i]).intValue());
                 joinLandEntity.setDeviceType(deviceTypeArray[i]);
                 joinLandEntity.setRlId(joinLandEntity.getRlId());
                 joinLandEntity.setUnitId(joinLandEntity.getUnitId());
                 this.addEntity(joinLandEntity);
             }
            }else {//修改本次运行方式
                JoinLandEntity editrunWayEntity=new JoinLandEntity();
                for(int i=0;i<joinLandEntity.getState().length;i++){
                editrunWayEntity.setId(Long.parseLong(jLId[i]));
                editrunWayEntity.setClosedName(joinLandEntity.getState()[i]);
                editrunWayEntity.setClosedPosition(joinLandEntity.getCode()[i]);
                editrunWayEntity.setDeviceId(new Integer(deviceIdArray[i]).intValue());
                editrunWayEntity.setDeviceType(deviceTypeArray[i]);
                editrunWayEntity.setRlId(joinLandEntity.getRlId());
                editrunWayEntity.setUnitId(joinLandEntity.getUnitId());
                this.updateEntity(editrunWayEntity);
                }
            }            
        
       ResultObj resultObj = new ResultObj();
       resultObj.setData(joinLandEntity);
       return resultObj;

    }
    public  List<JoinLandEntity>  changeList(List<JoinLandEntity> list){
           List<JoinLandEntity> resultList =new ArrayList<JoinLandEntity>();
           int rowshow=0;//是否显示
           int rowCount=0;//合并行数
           for(int i=0;i<list.size();i++){
               JoinLandEntity runWayEntity=list.get(i);
               if(i!=0&&(!runWayEntity.getUnitId().equals(resultList.get(resultList.size()-1).getUnitId()))){
                   resultList.add(runWayEntity);
                   resultList.get(i-rowCount).setRowspanNum(String.valueOf(rowCount));
                   rowshow=0;
                   rowCount=0;
               }else {
                   resultList.add(runWayEntity);
               }
             //合拼还是隐藏
               if(rowshow==0){
                   runWayEntity.setTdHide("show");
               }else{
                   runWayEntity.setTdHide("hide");
               }
               if(i==list.size()-1){
                   resultList.get(i-rowCount).setRowspanNum(String.valueOf(rowCount+1));
               }
               rowshow++;
               rowCount++;
           }
        return resultList;
    }
    
    /**
	 * @Description: 运行方式编辑
	 * @author wangcc
	 * @Date 2017年10月11日 上午17:50:38
	 * @throws Exception
	 */
	@Override
	public ResultObj save(String joinLand) {
		Map<String, Object> joinLandMap = JsonUtil.getMapFromJson(joinLand);
		Iterator<Map.Entry<String, Object>> entries = joinLandMap.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry<String, Object> entry = entries.next();
			long id= Long.parseLong(entry.getKey().split("-")[1]);
			JoinLandEntity joinlandentity=joinLandDao.findById(id);
			if(entry.getValue()!=null){
				joinlandentity.setSwordbrake_status(entry.getValue().toString());
			}
			joinLandDao.updateEntity(joinlandentity);
		}
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.JOINLAND.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		return new ResultObj();
	}
}