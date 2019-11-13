package com;

import javax.annotation.PostConstruct;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;

import db.DB;
import db.table.Storage;
import db.table.Table;
import db.table.TableInstance;
import db.table.field.base.BaseField;
import db.table.field.base.BaseFieldInstance;
import db.table.field.base.BaseFieldType;
import server.service.DbService;
import server.service.TableService;
import service.IDbService;
import service.ITableService;

import java.util.Collections;

@ComponentScan({ "server" })
@SpringBootApplication
public class App {

    @Autowired
    private DbService dbService;

    @Autowired
    private TableService tableService;

    @Bean(name = "/dbservice")
    HttpInvokerServiceExporter dbService() {
        HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();
        exporter.setService(dbService);
        exporter.setServiceInterface(IDbService.class);
        return exporter;
    }

    @Bean(name = "/tableservice")
    HttpInvokerServiceExporter tableService() {
        HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();
        exporter.setService(tableService);
        exporter.setServiceInterface(ITableService.class);
        return exporter;
    }

    @Bean
    public Storage getStorage() {
        return Storage.Instance;
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(App.class);
        ((SpringApplication) app).setDefaultProperties(Collections.singletonMap("server.port", "8092"));
        app.run(args);

//        UserConsole.start();
    }

    @PostConstruct
    public void init() {
        DB db1 = new DB("firstDB");
        DB db2 = new DB("secondDB");
        DB db3 = new DB("thirdDB");
        Table tb1_1 = null;
        Table tb1_2 = null;
        try {
            tb1_1 = Table.tableBuilder().setTableName("table1")
                    .addTableBaseField(
                            new BaseField("field1_1", BaseFieldType.INTEGER))
                    .addTableBaseField(
                            new BaseField("field2_1", BaseFieldType.REAL))
                    .build();
            tb1_2 = Table.tableBuilder().setTableName("table2")
                    .addTableBaseField(
                            new BaseField("field1_2", BaseFieldType.INTEGER))
                    .addTableBaseField(
                            new BaseField("field2_2", BaseFieldType.COLOR))
                    .addTableBaseField(
                            new BaseField("field3_2", BaseFieldType.STRING))
                    .build();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        dbService.createDB(db1);
        dbService.createDB(db2);
        dbService.createDB(db3);

        tableService.addTable(db1, tb1_1);
        tableService.addTable(db1, tb1_2);

        try {
            tableService.addTableInstance(tb1_1, TableInstance
                    .tableInstanceBuilder()
                    .addBaseFieldInstance(new BaseFieldInstance("field1_1", 1,
                            BaseFieldType.INTEGER))
                    .addBaseFieldInstance(new BaseFieldInstance("field2_1",
                            1.23, BaseFieldType.REAL))
                    .build());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            tableService.addTableInstance(tb1_2, TableInstance
                    .tableInstanceBuilder()
                    .addBaseFieldInstance(new BaseFieldInstance("field1_2",
                            1123, BaseFieldType.INTEGER))
                    .addBaseFieldInstance(new BaseFieldInstance("field2_2",
                            "#32a852", BaseFieldType.COLOR))
                    .addBaseFieldInstance(new BaseFieldInstance("field3_2",
                            "asdasd", BaseFieldType.STRING))
                    .build());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
