package service;

import java.util.ArrayList;

import db.DB;
import db.table.Table;
import db.table.TableInstance;

public interface ITableService {

    public Boolean addTable(DB db, Table table);

    public Boolean addTableInstance(Table table, TableInstance tableInstance);

    public Boolean deleteTableInstance(Table table, TableInstance tableInstance);

    public Boolean deleteDuplicates(Table table);

    public ArrayList<Table> getTables(DB db);
}

