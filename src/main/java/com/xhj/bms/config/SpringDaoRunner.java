package com.xhj.bms.config;

import org.nutz.dao.impl.DaoRunner;
import java.sql.Connection;

import javax.sql.DataSource;

import org.nutz.dao.ConnCallback;
import org.springframework.jdbc.datasource.DataSourceUtils;

/**
 * Created by Projack
 */
public class SpringDaoRunner implements DaoRunner {

    public void run(DataSource dataSource, ConnCallback callback) {
        Connection con = DataSourceUtils.getConnection(dataSource);
        try {
            callback.invoke(con);
        }
        catch (Exception e) {
            if (e instanceof RuntimeException)
                throw (RuntimeException) e;
            else
                throw new RuntimeException(e);
        }
        finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
    }

}
