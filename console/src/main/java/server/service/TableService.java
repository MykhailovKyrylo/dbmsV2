package server.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import db.DB;
import db.table.Storage;
import db.table.Table;
import db.table.TableInstance;
import service.ITableService;

@Service
public class TableService implements ITableService {

    @Autowired
    private Storage storage;

    public Boolean addTable(DB db, Table table) {
        System.out.println("addTable");
        return storage.getDbs().stream().filter(x -> x.equals(db)).findAny()
                .get().createTable(table);
    }

    public Boolean addTableInstance(Table table, TableInstance tableInstance) {
        System.out.println("addTableInstance");
        return storage.getDbs().stream()
                .filter(x -> x.contains(table.getTableName())).findAny().get()
                .getTableByName(table.getTableName())
                .addTableInstance(tableInstance);

    }

    public Boolean deleteTableInstance(Table table,
                                       TableInstance tableInstance) {
        System.out.println("deleteTableInstance");
        return storage.getDbs().stream()
                .filter(x -> x.contains(table.getTableName())).findAny().get()
                .getTableByName(table.getTableName())
                .delteTableInstance(tableInstance);
    }

    public Boolean deleteDuplicates(Table table) {
        System.out.println("deleteDuplicates");
        storage.getDbs().stream()
                .filter(x -> x.contains(table.getTableName())).findAny().get()
                .getTableByName(table.getTableName())
                .deleteDuplicates();
        return true;
    }

    @Override
    public ArrayList<Table> getTables(DB db) {
        System.out.println("getTables(DB db)");
        return new ArrayList<Table>(storage.getDbs().stream()
                .filter(x -> x.equals(db)).findAny().get().getTables());
    }

    public Table getTable(DB db, String tableName) {
        System.out.println("getTable(DB db)");
        Optional<Table> optionalTable = getTables(db).stream()
                .filter(x -> x.getTableName().equals(tableName)).findAny();
        if (optionalTable.isPresent()) {
            return optionalTable.get();
        }
        return null;
    }

}
