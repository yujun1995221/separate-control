package com.free.decision.server.config;


import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.sql.Connection;

/**
 * jfinal 数据源整合
 */
//@Configuration
public class ActiveRecordConfig {

    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.url}")
    private String url;

    @Bean
    public ActiveRecordPlugin ininitActiveRecordPlugin(){
        DruidPlugin druidPlugin= new DruidPlugin(url,username,password, driverClassName);
        // 加强数据库安全
        WallFilter wallFilter=new WallFilter();
        wallFilter.setDbType("mysql");
        druidPlugin.addFilter(wallFilter);
        // 添加 StatFilter 才会有统计数据
        // druidPlugin.addFilter(new StatFilter());


        Slf4jLogFilter slf4jLogFilter = new Slf4jLogFilter();
        slf4jLogFilter.setDataSourceLogEnabled(false);
        slf4jLogFilter.setConnectionLogEnabled(false);
        slf4jLogFilter.setConnectionLogErrorEnabled(true);
        slf4jLogFilter.setStatementLogEnabled(false);
        slf4jLogFilter.setStatementLogErrorEnabled(true);
        slf4jLogFilter.setResultSetLogEnabled(false);
        slf4jLogFilter.setResultSetLogErrorEnabled(true);
        slf4jLogFilter.setConnectionConnectBeforeLogEnabled(false);
        slf4jLogFilter.setConnectionConnectAfterLogEnabled(false);
        slf4jLogFilter.setConnectionCommitAfterLogEnabled(false);
        slf4jLogFilter.setConnectionRollbackAfterLogEnabled(true);
        slf4jLogFilter.setConnectionCloseAfterLogEnabled(false);
        slf4jLogFilter.setStatementCreateAfterLogEnabled(false);
        slf4jLogFilter.setStatementPrepareAfterLogEnabled(false);
        slf4jLogFilter.setStatementPrepareCallAfterLogEnabled(false);
        slf4jLogFilter.setStatementExecuteAfterLogEnabled(false);
        slf4jLogFilter.setStatementExecuteQueryAfterLogEnabled(false);
        slf4jLogFilter.setStatementExecuteUpdateAfterLogEnabled(false);
        slf4jLogFilter.setStatementExecuteBatchAfterLogEnabled(false);
        slf4jLogFilter.setStatementCloseAfterLogEnabled(false);
        slf4jLogFilter.setStatementParameterSetLogEnabled(false);
        slf4jLogFilter.setResultSetNextAfterLogEnabled(false);
        slf4jLogFilter.setResultSetOpenAfterLogEnabled(false);
        slf4jLogFilter.setResultSetCloseAfterLogEnabled(false);
        slf4jLogFilter.setStatementExecutableSqlLogEnable(true);
        druidPlugin.addFilter(slf4jLogFilter);


        // 必须手动调用start
        druidPlugin.start();
        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        arp.setTransactionLevel(Connection.TRANSACTION_READ_COMMITTED);
        //_MappingKit.mapping(arp);
        arp.setShowSql(true);
        //arp.getEngine().setSourceFactory(new ClassPathSourceFactory());
        //arp.addSqlTemplate("/sql/all_sqls.sql");
        // 必须手动调用start
        arp.start();
        return arp;
    }
}
