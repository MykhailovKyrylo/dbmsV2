package db.table.field.base;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum BaseFieldType {
    STRING("STRING"),
    COLOR("COLOR"),
    ENUM("ENUM"),
    EMAIL("EMAIL"),
    INTEGER("INTEGER"),
    REAL("REAL"),
    CHAR("CHAR"),
    LONGINT("LONGINT");

    private final String tableFieldTypeName;
    private static final Map<String, BaseFieldType> lookup = new HashMap<String, BaseFieldType>();

    static {
        for (BaseFieldType baseFieldType : EnumSet.allOf(BaseFieldType.class)) {
            lookup.put(baseFieldType.tableFieldTypeName, baseFieldType);
        }
    }

    BaseFieldType(String tableFieldTypeName) {
        this.tableFieldTypeName = tableFieldTypeName;
    }

    public static boolean contains(String type) {
        return (lookup.get(type) != null);
    }

    public static BaseFieldType getTableFieldType(String tableFieldTypeName) {
        return lookup.get(tableFieldTypeName);
    }

    @Override
    public String toString() {
        return this.tableFieldTypeName;
    }
}
