package server.web.editor;

import java.beans.PropertyEditorSupport;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import db.table.Table;
import db.table.Table.TableBuilder;
import db.table.TableInstance;
import db.table.TableInstance.TableInstanceBuilder;
import db.table.field.base.BaseField;
import db.table.field.base.BaseFieldInstance;
import db.table.field.base.BaseFieldType;

public class TableInstanceEditor extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        ObjectMapper mapper = new ObjectMapper();

        System.out.println("In tableInst edit");

        TableInstance tableInstance = null;

        try {
            JsonNode root = mapper.readTree(text);
            TableInstanceBuilder tableInstanceBuilder = TableInstance
                    .tableInstanceBuilder();

            JsonNode tableInstanceBaseFields = root.path("baseFields");

            tableInstanceBaseFields.forEach(x -> tableInstanceBuilder
                    .addBaseFieldInstance(new BaseFieldInstance(
                            x.path("tableFieldName").textValue(),
                            x.path("data").textValue(), BaseFieldType.getTableFieldType(
                            x.path("type").textValue()))));

            tableInstance = tableInstanceBuilder.build();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        setValue(tableInstance);
    }
}

