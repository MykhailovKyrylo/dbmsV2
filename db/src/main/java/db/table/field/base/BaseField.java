package db.table.field.base;

import java.io.Serializable;

import db.table.field.IField;

public class BaseField implements IField, Serializable {

    protected BaseFieldType type;
    protected String tableFieldName;
    private static final long serialVersionUID = 1L;

    public BaseField(String tableFieldName, BaseFieldType type) {
        this.tableFieldName = tableFieldName;
        this.type = type;
    }

    public BaseFieldType getType() {
        return type;
    }

    public void setType(BaseFieldType type) {
        this.type = type;
    }

    public String getTableFieldName() {
        return tableFieldName;
    }

    public void setTableFieldName(String tableFieldName) {
        this.tableFieldName = tableFieldName;
    }

    public boolean equals(Object object) {
        if (object == null)
            return false;
        if (this == object)
            return true;
        if (object instanceof BaseField) {
            BaseField tmpTableField = (BaseField) object;
            if (!this.tableFieldName.equals(tmpTableField.tableFieldName)) {
                return false;
            }
            if (this.type != tmpTableField.type) {
                return false;
            }
            return true;
        }
        return false;
    }

    public int hashCode() {
        return tableFieldName.hashCode() ^ type.hashCode();
    }

    @Override
    public String toString() {
        String res = tableFieldName + "\n";
        res += "[-]Field type: " + this.type + "\n";
        return res;
    }
}
