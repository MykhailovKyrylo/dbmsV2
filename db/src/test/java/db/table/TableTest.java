package db.table;

import db.table.field.base.BaseField;
import db.table.field.base.BaseFieldInstance;
import db.table.field.base.BaseFieldType;
import junit.framework.TestCase;

public class TableTest extends TestCase {

    private Table testTable = null;
    private Table testTable2 = null;

    protected void setUp() throws Exception {
        testTable = Table.tableBuilder().setTableName("TestTable")
                .addTableBaseField(new BaseField("field1", BaseFieldType.INTEGER))
                .addTableBaseField(new BaseField("field2", BaseFieldType.REAL)).build();

        testTable2 = Table.tableBuilder().setTableName("TestTable")
                .addTableBaseField(new BaseField("field1", BaseFieldType.INTEGER))
                .addTableBaseField(new BaseField("field2", BaseFieldType.STRING)).build();


        assertTrue(testTable != null);
    }

    protected void tearDown() throws Exception {

    }

    public void testDeleteDuplicates() throws Exception{
        testTable.addTableInstance(TableInstance.tableInstanceBuilder()
                .addBaseFieldInstance(new BaseFieldInstance("field1", 1, BaseFieldType.INTEGER))
                .addBaseFieldInstance(new BaseFieldInstance("field2", 1.23, BaseFieldType.REAL))
                .build());

        testTable.addTableInstance(TableInstance.tableInstanceBuilder()
                .addBaseFieldInstance(new BaseFieldInstance("field1", 1, BaseFieldType.INTEGER))
                .addBaseFieldInstance(new BaseFieldInstance("field2", 1.23, BaseFieldType.REAL))
                .build());

        assertTrue(testTable.getTableInstances().size() == 2);

        testTable.deleteDuplicates();
        assertTrue(testTable.getTableInstances().size() == 1);

        testTable2.addTableInstance(TableInstance.tableInstanceBuilder()
                .addBaseFieldInstance(new BaseFieldInstance("field1", 1, BaseFieldType.INTEGER))
                .addBaseFieldInstance(new BaseFieldInstance("field2", "asd", BaseFieldType.STRING))
                .build());

        testTable2.addTableInstance(TableInstance.tableInstanceBuilder()
                .addBaseFieldInstance(new BaseFieldInstance("field1", 1, BaseFieldType.INTEGER))
                .addBaseFieldInstance(new BaseFieldInstance("field2", "asd", BaseFieldType.STRING))
                .build());

        assertTrue(testTable2.getTableInstances().size() == 2);

        testTable2.deleteDuplicates();
        assertTrue(testTable2.getTableInstances().size() == 1);

    }


    public void testAddTableInstance() throws Exception {
        Boolean result = null;

        result = testTable.addTableInstance(TableInstance.tableInstanceBuilder().addBaseFieldInstance(null).build());
        assertTrue(result == false);

        result = testTable.addTableInstance(TableInstance.tableInstanceBuilder()
                .addBaseFieldInstance(new BaseFieldInstance("tf1", null, BaseFieldType.STRING))
                .addBaseFieldInstance(new BaseFieldInstance("tf2", null, BaseFieldType.REAL)).build());
        assertTrue(result == false);

        result = testTable.addTableInstance(TableInstance.tableInstanceBuilder()
                .addBaseFieldInstance(new BaseFieldInstance("tf1", null, BaseFieldType.INTEGER))
                .addBaseFieldInstance(new BaseFieldInstance("tf2", null, BaseFieldType.REAL)).build());
        assertTrue(result == true);
    }
}
