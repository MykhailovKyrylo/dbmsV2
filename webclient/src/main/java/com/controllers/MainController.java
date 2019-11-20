package com.controllers;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import db.table.Table;
import db.table.TableInstance;
import db.table.field.base.BaseField;
import db.table.field.base.BaseFieldInstance;
import db.table.field.base.BaseFieldType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.IDbService;
import service.ITableService;

import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private IDbService dbService;

    @Autowired
    private ITableService tableService;

    final static int port = 8080;

    @GetMapping("/db")
    public String dbs(Map<String, Object> model) {

        System.out.println("HERE");

        model.put("port", port);
        model.put("dbs", dbService.getDbs());
        return "dbs";
    }

    @GetMapping("/{dbName}")
    public String db(Map<String, Object> model,
                     @PathVariable String dbName) {

        model.put("port", port);
        model.put("dbName", dbName);
        model.put("tables", dbService.getDB(dbName).getTables());

        return "db";
    }

    @GetMapping("/{dbName}/{tableName}")
    public String table(Map<String, Object> model,
                     @PathVariable String dbName,
                     @PathVariable String tableName) {

        model.put("port", port);
        model.put("dbName", dbName);

        Table table = dbService.getDB(dbName).getTableByName(tableName);
        model.put("table", table);
        model.put("baseFields", table.getTableBaseFields());
        model.put("instances", table.getTableInstances());

        return "table";
    }

    @PostMapping("/{dbName}/{tableName}")
    public String addTableInstance(Map<String, Object> model,
                                   @PathVariable String dbName,
                                   @PathVariable String tableName,
                                   @RequestParam Map<String, String> data) {


        model.put("port", port);
        model.put("dbName", dbName);

        Table table = dbService.getDB(dbName).getTableByName(tableName);
        List<BaseField> baseFields = table.getTableBaseFields();

        boolean flag = true;

        for (BaseField baseField : baseFields) {
            String baseFieldName = baseField.getTableFieldName();
            System.out.println("Base field " + baseFieldName + " = ");
            if (data.containsKey(baseFieldName)) {
                System.out.println(data.get(baseFieldName));
            } else {
                System.out.println("hui");
                flag = false;
                break;
            }
        }

        if (flag) {
            TableInstance.TableInstanceBuilder instanceBuilder = TableInstance.tableInstanceBuilder();
            for (BaseField baseField : baseFields) {
                String baseFieldName = baseField.getTableFieldName();
                instanceBuilder.addBaseFieldInstance(new BaseFieldInstance(baseFieldName, data.get(baseFieldName), baseField.getType()));
            }
            try {
                tableService.addTableInstance(table, instanceBuilder.build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "redirect:/" + dbName + "/" + tableName;
    }
}
