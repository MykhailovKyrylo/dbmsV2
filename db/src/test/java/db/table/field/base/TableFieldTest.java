package db.table.field.base;

import junit.framework.TestCase;

public class TableFieldTest extends TestCase {

    public void testTableFieldTypeContains() {
        assertTrue(BaseFieldType.contains("INTEGER"));
        assertTrue(BaseFieldType.contains("LONGINT"));
        assertTrue(!BaseFieldType.contains("APPLE"));
    }

    public void testEqualsTableField() {
        BaseFieldInstance tf1 = new BaseFieldInstance("tf1", new Integer(12), BaseFieldType.INTEGER);
        BaseFieldInstance tf1_1 = new BaseFieldInstance("tf1", new Integer(12), BaseFieldType.INTEGER);
        BaseFieldInstance tf2 = new BaseFieldInstance("tf2", new Integer(15), BaseFieldType.INTEGER);

        BaseFieldInstance tf3 = new BaseFieldInstance("tf3", new Character('a'), BaseFieldType.CHAR);
        BaseFieldInstance tf3_3 = new BaseFieldInstance("tf3", new Character('a'), BaseFieldType.CHAR);
        BaseFieldInstance tf4 = new BaseFieldInstance("tf4", new Character('b'), BaseFieldType.CHAR);

        BaseFieldInstance tf5 = new BaseFieldInstance("tf5", new Double(1.5), BaseFieldType.REAL);
        BaseFieldInstance tf5_5 = new BaseFieldInstance("tf5", new Double(1.5), BaseFieldType.REAL);
        BaseFieldInstance tf6 = new BaseFieldInstance("tf6", new Double(2.5), BaseFieldType.REAL);

        BaseFieldInstance tf7 = new BaseFieldInstance("tf7", new Long(15), BaseFieldType.LONGINT);
        BaseFieldInstance tf7_7 = new BaseFieldInstance("tf7", new Long(15), BaseFieldType.LONGINT);
        BaseFieldInstance tf8 = new BaseFieldInstance("tf8", new Long(17), BaseFieldType.LONGINT);

        assertTrue(tf1.equals(tf1_1));
        assertTrue(!tf1.equals(tf2));

        assertTrue(tf3.equals(tf3_3));
        assertTrue(!tf3.equals(tf4));

        assertTrue(tf5.equals(tf5_5));
        assertTrue(!tf5.equals(tf6));

        assertTrue(tf7.equals(tf7_7));
        assertTrue(!tf7.equals(tf8));

        assertTrue(!tf1.equals(tf3));
        assertTrue(!tf3.equals(tf5));
        assertTrue(!tf5.equals(tf7));
        assertTrue(!tf1.equals(tf7));

        assertTrue(tf1.equals(tf1));
    }
}
