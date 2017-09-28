package com.xhj.bms.service.user.impl;

import com.xhj.bms.dao.DaoHelper;
import com.xhj.bms.entity.UserLevel;
import com.xhj.bms.security.util.WebUtils;
import com.xhj.bms.service.user.UserLevelService;
import org.nutz.dao.Cnd;
import org.nutz.dao.QueryResult;
import org.nutz.dao.sql.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

/**
 * Created by Jason on 2017/8/8.
 */
@Service("UserLevelService")
public class UserLevelServiceImpl implements UserLevelService {
    @Autowired
    private DaoHelper daoHelper;


    @Override
    public void save(UserLevel userLevel) throws Exception {
        HttpServletRequest request = WebUtils.getRequest();
        userLevel.setCreateTime(new Timestamp(System.currentTimeMillis()));
        daoHelper.save(userLevel);
    }

    @Override
    public void update(UserLevel userLevel) throws Exception {
        daoHelper.update(userLevel);
    }

    @Override
    public QueryResult search(UserLevel userLevel) throws Exception {
        Integer page = userLevel!=null&&userLevel.getPageIndex()!=null?userLevel.getPageIndex():0;
        Integer size = userLevel!=null&&userLevel.getPageSize()!=null?userLevel.getPageSize():10;
        HttpServletRequest request = WebUtils.getRequest();
        Criteria criteria = Cnd.cri();
        if(userLevel != null){
            if(userLevel.getLevelNum() != null){
                criteria.where().andEquals("levelNum",userLevel.getLevelNum());
            }

            if(userLevel.getExperienceNum() != null){
                criteria.where().andEquals("experienceNum",userLevel.getExperienceNum());
            }
            criteria.getOrderBy().desc("createTime");
        }
        return daoHelper.getPaginationByObject(UserLevel.class,criteria,page,size);
    }

    @Override
    public UserLevel getById(Integer Id) throws Exception {
        return daoHelper.getObjectByPOJO(UserLevel.class, Cnd.where("id","=",Id));
    }
    @Override
    public void delete(Integer Id)  throws Exception
    {
        this.daoHelper.delete(daoHelper.getObjectByPOJO(UserLevel.class, Cnd.where("id","=",Id)));
    }
}
