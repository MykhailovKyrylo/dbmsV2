package db.table.field.base;

import java.util.HashSet;
import java.util.Set;

import db.table.TableInstance;
import junit.framework.TestCase;

public class TableInstanceTest extends TestCase {

    public void testEqualsTableInstance() throws Exception {
        TableInstance ti1 = TableInstance.tableInstanceBuilder()
                .addBaseFieldInstance(new BaseFieldInstance("tf1", new Integer(1), BaseFieldType.INTEGER))
                .addBaseFieldInstance(new BaseFieldInstance("tf2", new Double(1.0), BaseFieldType.REAL)).build();

        TableInstance ti2 = TableInstance.tableInstanceBuilder()
                .addBaseFieldInstance(new BaseFieldInstance("tf1", new Integer(1), BaseFieldType.INTEGER))
                .addBaseFieldInstance(new BaseFieldInstance("tf2", new Double(1.0), BaseFieldType.REAL)).build();

        Set<TableInstance> st = new HashSet<TableInstance>();
        st.add(ti1);
        st.add(ti2);
        assertTrue(st.size() == 1);

        TableInstance ti3 = TableInstance.tableInstanceBuilder()
                .addBaseFieldInstance(new BaseFieldInstance("tf1", new Integer(1), BaseFieldType.INTEGER))
                .addBaseFieldInstance(new BaseFieldInstance("tf2", new Long(12), BaseFieldType.LONGINT)).build();

        st.add(ti3);
        assertTrue(st.size() == 2);
    }
}
