package db;

import db.DB;
import db.table.Table;
import db.table.TableInstance;
import db.table.field.base.BaseField;
import db.table.field.base.BaseFieldInstance;
import db.table.field.base.BaseFieldType;
import junit.framework.TestCase;

public class DBTest extends TestCase {

    private DB db = null;

    protected void setUp() throws Exception {
        db = new DB("FirstDb");
    }

    private Table createTestTable(String tableName) throws Exception {
        Table table = Table.tableBuilder().setTableName(tableName)
                .addTableBaseField(new BaseField("field1", BaseFieldType.INTEGER))
                .addTableBaseField(new BaseField("field2", BaseFieldType.REAL)).build();
        assertTrue(table != null);
        return table;
    }

    public void testContainsDbTable() throws Exception {
        Table table1 = createTestTable("table1");
        Table table2 = Table.tableBuilder().setTableName("table1")
                .addTableBaseField(new BaseField("field1", BaseFieldType.LONGINT)).build();
        db.createTable(table1);
        assertTrue(db.contains(table2.getTableName()) == true);
        Table table3 = createTestTable("table2");
        assertTrue(db.contains(table3.getTableName()) == false);
    }

    public void testCreateRemoveTableDb() throws Exception {

        Boolean result = null;

        Table table1 = createTestTable("Table1");
        Table table2 = createTestTable("Table2");
        Table table3 = createTestTable("Table3");

        result = db.createTable(table1);
        assertTrue(result == true);

        result = db.createTable(table1);
        assertTrue(result == false);

        result = db.createTable(table2);
        assertTrue(result == true);
        assertTrue(db.getTables().size() == 2);

        result = db.createTable(table3);

        result = db.removeTable(table2);
        assertTrue(result == true);
        assertTrue(db.getTables().size() == 2);

        result = db.removeTable(table2);
        assertTrue(result == false);
        assertTrue(db.getTables().size() == 2);

    }

    public void testTableInstanceDb() throws Exception {

        Table MainTable = Table.tableBuilder().setTableName("MainTable")
                .addTableBaseField(new BaseField("field1", BaseFieldType.INTEGER))
                .addTableBaseField(new BaseField("field2", BaseFieldType.REAL)).build();

        Table OneToMainTable = Table.tableBuilder().setTableName("OneToMainTable")
                .addTableBaseField(new BaseField("field1", BaseFieldType.REAL))
                .addTableBaseField(new BaseField("field2", BaseFieldType.CHAR)).build();

        TableInstance tiForMainTable_1 = TableInstance.tableInstanceBuilder()
                .addBaseFieldInstance(new BaseFieldInstance("tf1", new Integer(15), BaseFieldType.INTEGER))
                .addBaseFieldInstance(new BaseFieldInstance("tf2", new Double(15.5), BaseFieldType.REAL)).build();
        TableInstance tiForMainTable_2 = TableInstance.tableInstanceBuilder()
                .addBaseFieldInstance(new BaseFieldInstance("tf1", new Integer(17), BaseFieldType.INTEGER))
                .addBaseFieldInstance(new BaseFieldInstance("tf2", new Double(17.5), BaseFieldType.REAL)).build();

        TableInstance tiForOneToMainTable_1 = TableInstance.tableInstanceBuilder()
                .addBaseFieldInstance(new BaseFieldInstance("tf1_1", new Double(5.23), BaseFieldType.REAL))
                .addBaseFieldInstance(new BaseFieldInstance("tf2_2", new Character('a'), BaseFieldType.CHAR))
                .build();
        TableInstance tiForOneToMainTable_2 = TableInstance.tableInstanceBuilder()
                .addBaseFieldInstance(new BaseFieldInstance("tf1_1", new Double(4.23), BaseFieldType.REAL))
                .addBaseFieldInstance(new BaseFieldInstance("tf2_2", new Character('b'), BaseFieldType.CHAR))
                .build();

        MainTable.addTableInstance(tiForMainTable_1);
        MainTable.addTableInstance(tiForMainTable_2);

        OneToMainTable.addTableInstance(tiForOneToMainTable_1);
        OneToMainTable.addTableInstance(tiForOneToMainTable_2);

        tiForMainTable_1.addForeignKeyTableInstances(tiForOneToMainTable_1);
        tiForOneToMainTable_1.addForeignKeyTableInstances(tiForMainTable_1);
        tiForMainTable_2.addForeignKeyTableInstances(tiForOneToMainTable_2);
        tiForOneToMainTable_2.addForeignKeyTableInstances(tiForMainTable_2);

        assertTrue(tiForMainTable_1.getForeignKeyTableInstances().size() == 1);
        assertTrue(tiForMainTable_1.getForeignKeyTableInstances().contains(tiForOneToMainTable_1));
        assertTrue(tiForOneToMainTable_1.getForeignKeyTableInstances().size() == 1);
        assertTrue(tiForOneToMainTable_1.getForeignKeyTableInstances().contains(tiForMainTable_1));
    }

}
