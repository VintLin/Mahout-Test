package com.felink.workflow.model;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.GenericBooleanPrefDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.model.DataModel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReModel {
    public DataModel dataModel;

    public static DataModel DataModel(String filePath, boolean havePreference) throws IOException, TasteException {
        DataModel dataModel = new FileDataModel(new File(filePath));
        if(!havePreference) {
            dataModel = new GenericBooleanPrefDataModel(GenericBooleanPrefDataModel.toDataMap(dataModel));
        }
        return dataModel;
    }

    public static DataModel DataModel(String table, String userIDColumn, String itemIDColumn) throws IOException, TasteException {
        return new GenericBooleanPrefDataModel(GenericBooleanPrefDataModel.toDataMap(
                DataModel(table, userIDColumn, itemIDColumn, null, null)));
    }

    public static DataModel DataModel(String table, String userIDColumn, String itemIDColumn, String preferenceColumn, String timestampColumn) throws IOException{
        Properties prop = new Properties();
        InputStream in = ReModel.class.getClassLoader().getResourceAsStream("common.properties");
        prop.load(in);
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName((String)prop.get("server"));
        dataSource.setUser((String)prop.get("user"));
        dataSource.setPassword((String)prop.get("password"));
        dataSource.setDatabaseName((String)prop.get("database"));
        dataSource.setPort((int)prop.get("port"));
        dataSource.setUseSSL(false);
        dataSource.setAutoReconnect(true);
        dataSource.getFailOverReadOnly();
        dataSource.setCacheCallableStatements(true);
        return new MySQLJDBCDataModel(dataSource,table, userIDColumn, itemIDColumn, preferenceColumn, timestampColumn);
    }

}
