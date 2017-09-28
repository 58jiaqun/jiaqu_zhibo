package com.xhj.bms.service.user;

import com.xhj.bms.entity.UserLevel;
import org.nutz.dao.QueryResult;

/**
 * Created by Jason on 2017/8/8.
 */
public interface UserLevelService {
    /**
     * 保存对象
     * @param userLevel
     * @return
     */
    public void save(UserLevel userLevel)  throws Exception;

    /**
     * 修改对象
     * @param userLevel
     * @return
     */
    public void update(UserLevel userLevel)  throws Exception;

    /**
     * 搜索
     * @param userLevel
     * @return
     */
    public QueryResult search(UserLevel userLevel)  throws Exception;

    /**
     * 根据id获取详情
     * @param Id
     * @return
     */
    public UserLevel getById(Integer Id)  throws Exception;
    /**
     * 根据id删除
     * @param Id
     * @return
     */
    public void delete(Integer Id)  throws Exception;
}
