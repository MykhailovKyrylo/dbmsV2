package server.web.editor;

import java.beans.PropertyEditorSupport;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import db.table.Table;
import db.table.Table.TableBuilder;
import db.table.field.base.BaseField;
import db.table.field.base.BaseFieldType;

public class TableEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        ObjectMapper mapper = new ObjectMapper();

        Table table = null;

        try {
            JsonNode root = mapper.readTree(text);
            TableBuilder tableBuilder = Table.tableBuilder();

            JsonNode tableBaseFields = root.path("tableBaseFields");

            tableBuilder.setTableName(root.get("tableName").asText());

            tableBaseFields.forEach(x -> tableBuilder.addTableBaseField(
                    new BaseField(x.path("tableFieldName").asText(),
                            BaseFieldType.getTableFieldType(
                                    x.path("type").asText()))));

            table = tableBuilder.build();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        setValue(table);
    }
}

