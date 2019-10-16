package db.table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import db.table.field.base.BaseFieldInstance;

public class TableInstance implements Serializable{

    private String index = null;
    private static final long serialVersionUID = 1L;
    private List<BaseFieldInstance> baseFields = null;
    private Set<TableInstance> foreignKeyTableInstances = null;

    private TableInstance() {
        index = UUID.randomUUID().toString();
    }

    public String getIndex() {
        return index;
    }

    public List<BaseFieldInstance> getBaseFields() {
        return baseFields;
    }

    public Set<TableInstance> getForeignKeyTableInstances() {
        return foreignKeyTableInstances;
    }

    public void addForeignKeyTableInstances(TableInstance oneMoreForeignKeyTableInstance) {
        foreignKeyTableInstances.add(oneMoreForeignKeyTableInstance);
    }

    public boolean equals(Object object) {
        if (object == null)
            return false;
        if (this == object)
            return true;
        if (object instanceof TableInstance) {
            TableInstance tmpTableInstance = (TableInstance) object;
            for (int i = 0; i < this.baseFields.size(); ++i) {
                if (!this.baseFields.get(i).equals(tmpTableInstance.getBaseFields().get(i)))
                    return false;
            }
            return true;
        }
        return false;
    }

    public int hashCode() {
        int res = 0;
        for (BaseFieldInstance tableField : baseFields) {
            res ^= tableField.hashCode();
        }
        return res;
    }

    public static TableInstanceBuilder tableInstanceBuilder() {
        return new TableInstance().new TableInstanceBuilder();
    }

    public class TableInstanceBuilder {

        private TableInstanceBuilder() {
            baseFields = new ArrayList<BaseFieldInstance>();
            foreignKeyTableInstances = new HashSet<TableInstance>();
        }

        public TableInstanceBuilder addBaseFieldInstance(BaseFieldInstance tableFieldInstance) {
            TableInstance.this.baseFields.add(tableFieldInstance);
            return this;
        }

        public TableInstance build() throws Exception {
            return TableInstance.this;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Index: " + index + "\n");
        for (BaseFieldInstance tableField : baseFields) {
            stringBuilder.append(tableField);
        }
        return stringBuilder.toString();
    }
}
