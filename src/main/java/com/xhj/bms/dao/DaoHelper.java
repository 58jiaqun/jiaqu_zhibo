package com.xhj.bms.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nutz.dao.*;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Criteria;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.VarSet;
import org.nutz.dao.util.Daos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author hui.peng
 * @date 2016-03-03
 */
@Repository
public class DaoHelper {
    private static final Log logger = LogFactory.getLog(DaoHelper.class);

    @Autowired
    private Dao dao;

    public void save(Object obj) throws Exception {
        try {
            dao.insert(obj);
        } catch (Exception e) {
            logger.error("save Exception", e);
            throw new Exception(e);
        }
    }

    public void saveWithChild(Object obj) throws Exception {
        try {
            dao.insertWith(obj,null);
        } catch (Exception e) {
            logger.error("save Exception", e);
            throw new Exception(e);
        }
    }

    public void update(Object obj) throws Exception {
        try {
            dao.update(obj);
        } catch (Exception e) {
            logger.error("update Exception", e);
            throw new Exception(e);
        }
    }

    public void delete(Object obj) throws Exception {
        try {
            dao.delete(obj);
        } catch (Exception e) {
            logger.error("delete Exception", e);
            throw new Exception(e);
        }
    }

    public Object saveReturn(Object obj) throws Exception {
        try {
            return dao.insert(obj);
        } catch (Exception e) {
            logger.error("Exception", e);
            throw new Exception(e);
        }
    }

    public void update(String sqlStr) throws Exception {
        try {
            logger.info(sqlStr);
            Sql sql = Sqls.create(sqlStr);
            dao.execute(sql);
        } catch (Exception e) {
            logger.error("update Exception", e);
            throw new Exception(e);
        }
    }

    public void update(String[] sqlStr) throws Exception {
        try {
            int len = sqlStr.length;
            Sql[] sqls = new Sql[len];
            int index = 0;
            for (String sql : sqlStr) {
                logger.info(sql);
                sqls[index++] = Sqls.create(sql);
            }
            dao.execute(sqls);
        } catch (Exception e) {
            logger.error("update Exception", e);
            throw new Exception(e);
        }
    }

    public Record getRecord(String sqlString) throws Exception {
        try {
            logger.info(sqlString);
            Sql sql = Sqls.create(sqlString);
            sql.setCallback(Sqls.callback.record());
            dao.execute(sql);
            return sql.getObject(Record.class);
        } catch (Exception e) {
            logger.error("getRecord Exception", e);
            throw new Exception(e);
        }
    }

    /**
     * @return
     */
    public List<Record> getByCnd(String tables, String fields, Condition cnd, int pageNumber, int pageSize) throws Exception {
        try {
            logger.info(tables);
            return dao.query(tables, cnd, null);
        } catch (Exception e) {
            logger.error("getByCnd Exception", e);
            throw new Exception(e);
        }
    }

    /**
     * 分页
     *
     * @param tables
     * @param fields
     * @param cnd
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public QueryResult getPagerList(String tables, String fields, Condition cnd, int pageNumber, int pageSize) throws Exception {
        try {
            logger.info(tables);
            logger.info(fields);
            pageNumber = pageNumber <= 0 ? 1 : pageNumber;
            Pager pager = null;
            List<Record> list = null;
            int count = dao.count(tables, cnd);
            if (count > 0) {
                fields = fields == null || fields.trim().length() <= 0 ? "*" : fields;
                pager = dao.createPager(pageNumber, pageSize);
                pager.setRecordCount(count);
                list = dao.query(tables, cnd, pager);
                return new QueryResult(list, pager);
            }
        } catch (Exception e) {
            logger.error("getPagerList Exception", e);
            throw new Exception(e);
        }
        return null;
    }

    /**
     * 获取单个数据对象
     *
     * @param sqlString
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Map getItem(String sqlString) throws Exception {
        try {
            logger.info(sqlString);
            Sql sql = Sqls.create(sqlString);
            sql.setCallback(Sqls.callback.record());
            dao.execute(sql);
            return sql.getObject(Map.class);
        } catch (Exception e) {
            logger.error("getItem Exception", e);
            throw new Exception(e);
        }
    }

    /**
     * 获取单个数据对象
     *
     * @param sqlString
     * @return
     */
    @SuppressWarnings("rawtypes")
    public <T> T getItem(String sqlString,Class<T> c) throws Exception {
        try {
            logger.info(sqlString);
            Sql sql = Sqls.create(sqlString);
            sql.setCallback(Sqls.callback.entities());
            dao.execute(sql);
            return sql.getObject(c);
        } catch (Exception e) {
            logger.error("getItem Exception", e);
            throw new Exception(e);
        }
    }

    /**
     * @param sqlString SQL
     * @return
     */
    @SuppressWarnings("rawtypes")
    public List<Map> getList(String sqlString) throws Exception {
        try {
            logger.info(sqlString);
            Sql sql = Sqls.create(sqlString);
            sql.setCallback(Sqls.callback.records());
            dao.execute(sql);
            return sql.getList(Map.class);
        } catch (Exception e) {
            logger.error("getList Exception", e);
            throw new Exception(e);
        }
    }

    public List<Record> getRecordList(String sqlString) throws Exception {
        try {
            logger.info(sqlString);
            Sql sql = Sqls.create(sqlString);
            sql.setCallback(Sqls.callback.records());
            dao.execute(sql);
            return sql.getList(Record.class);
        } catch (Exception e) {
            logger.error("getRecordList Exception", e);
            throw new Exception(e);
        }
    }

    @SuppressWarnings("rawtypes")
    public List<Map> getRecordAsList(String sqlString) throws Exception {
        try {
            logger.info(sqlString);
            Sql sql = Sqls.create(sqlString);
            sql.setCallback(Sqls.callback.maps());
            dao.execute(sql);
            return sql.getList(Map.class);
        } catch (Exception e) {
            logger.error("getRecordAsList Exception", e);
            throw new Exception(e);
        }
    }

    /**
     * 动态传参查询
     */
    public Object getObject(String sqlString, Object... params) throws Exception {
        try {
            logger.info(sqlString);
            Sql sql = Sqls.create(sqlString);
            VarSet varSet = sql.params();
            for (Object obj : params) {
                varSet.putAll(obj);
            }
            sql.setCallback(Sqls.callback.record());
            dao.execute(sql);
            return sql.getResult();
        } catch (Exception e) {
            logger.error("getObject Exception", e);
            throw new Exception(e);
        }
    }

    /**
     * 动态传参查询
     */
    public List<Map> getList(String sqlString, Object... params) throws Exception {
        try {
            logger.info(sqlString);
            Sql sql = Sqls.create(sqlString);
            VarSet varSet = sql.params();
            for (Object obj : params) {
                varSet.putAll(obj);
            }
            sql.setCallback(Sqls.callback.records());
            dao.execute(sql);
            return sql.getList(Map.class);
        } catch (Exception e) {
            logger.error("getObject Exception", e);
            throw new Exception(e);
        }
    }

    public Record getItem(String table, String fields, Cnd cnd) throws Exception {
        try {
            logger.info(table);
            logger.info(fields);
            return dao.fetch(table, cnd);
        } catch (Exception e) {
            logger.error("getItem Exception", e);
            throw new Exception(e);
        }
    }

    /**
     * 查询的结果数
     *
     * @param sqlCount
     * @return
     */
    public Integer getCount(String sqlCount) throws Exception {
        try {
            logger.info(sqlCount);
            return dao.count(sqlCount);
        } catch (Exception e) {
            logger.error("getCount Exception", e);
            throw new Exception(e);
        }
    }

    /**
     * 查询的结果数
     *
     * @param sqlCount
     * @return
     */
    public Long getCountBySql(String sqlCount) throws Exception {
        try {
            logger.info(sqlCount);
            return Daos.queryCount(dao, sqlCount);
        } catch (Exception e) {
            logger.error("getCountBySql Exception", e);
            throw new Exception(e);
        }
    }

    /**
     * 根据条件统计
     *
     * @param sqlCount
     * @param cnd
     * @return
     */
    public Integer getCountByCnd(String sqlCount, Condition cnd) throws Exception {
        try {
            logger.info(sqlCount);
            return dao.count(sqlCount, cnd);
        } catch (Exception e) {
            logger.error("getCountByCnd Exception", e);
            throw new Exception(e);
        }
    }

    /**
     * 查询结果集
     *
     * @param <T>
     * @param c
     * @param cnd
     * @return
     */
    public <T> List<T> getListByPOJO(Class<T> c, Condition cnd) throws Exception {
        try {
            return dao.query(c, cnd, null);
        } catch (Exception e) {
            logger.error("getListByPOJO Exception", e);
            throw new Exception(e);
        }
    }

    public <T> T getObjectByPOJO(Class<T> c, Condition cnd) throws Exception {
        try {
            return dao.fetch(c, cnd);
        } catch (Exception e) {
            logger.error("getObjectByPOJO Exception", e);
            throw new Exception(e);
        }
    }

    /**
     * 查询结果集
     *
     * @param <T>
     * @param c
     * @param cnd
     * @return
     */
    public <T> List<T> getListByPOJO(Class<T> c, Condition cnd, Integer len) throws Exception {
        try {
            Pager pager = dao.createPager(1, len);
            return dao.query(c, cnd, pager);
        } catch (Exception e) {
            logger.error("getListByPOJO Exception", e);
            throw new Exception(e);
        }
    }

    public QueryResult getPaginationBySqls(String sqls, int page, int size) {
        //定义分页
        Pager pager = dao.createPager(page, size);
        //回调查询
        Sql sql = Sqls.create(sqls);
        sql.setCallback(Sqls.callback.records());
        sql.setEntity(dao.getEntity(Record.class));
        sql.setPager(pager);
        dao.execute(sql);
        List<Record> list = sql.getList(Record.class);
        //封装QueryResult对象
        Long counts = Daos.queryCount(dao, sqls);
        pager.setRecordCount(counts.intValue());
        return new QueryResult(list, pager);
    }

    public <T> QueryResult getPaginationBySqls(Class<T> c, String sqls, int page, int size) {
        //定义分页
        Pager pager = dao.createPager(page, size);
        //回调查询
        Sql sql = Sqls.create(sqls);
        sql.setCallback(Sqls.callback.entities());
        sql.setEntity(dao.getEntity(c));
        sql.setPager(pager);
        dao.execute(sql);
        List<T> list = sql.getList(c);
        dao.fetchLinks(list,null);
        //封装QueryResult对象
        Long counts = Daos.queryCount(dao, sqls);
        pager.setRecordCount(counts.intValue());
        return new QueryResult(list, pager);
    }

    public <T> QueryResult getPaginationByObject(Class<T> c, Criteria criteria, int page, int size) {
        //定义分页
        Pager pager = dao.createPager(page, size);
        //回调查询
        List<T> list = dao.query(c, criteria, pager);
        dao.fetchLinks(list, null);
        //封装QueryResult对象
        int counts = dao.count(c, criteria);
        pager.setRecordCount(counts);
        return new QueryResult(list, pager);
    }

    public <T> List<T> getOnlyObjectList(Class<T> c, String sqlString) throws Exception {
        try {
            logger.info(sqlString);
            Sql sql = Sqls.create(sqlString);
            sql.setCallback(Sqls.callback.entities());
            sql.setEntity(dao.getEntity(c));
            dao.execute(sql);
            return sql.getList(c);
        } catch (Exception e) {
            logger.error("getObject Exception", e);
            throw new Exception(e);
        }
    }

    public <T> List<T> getFullObjectList(Class<T> c, String sqlString) throws Exception {
        try {
            logger.info(sqlString);
            Sql sql = Sqls.create(sqlString);
            sql.setCallback(Sqls.callback.entities());
            sql.setEntity(dao.getEntity(c));
            dao.execute(sql);
            List<T> list = sql.getList(c);
            dao.fetchLinks(list, null);
            return list;
        } catch (Exception e) {
            logger.error("getObject Exception", e);
            throw new Exception(e);
        }
    }
}
