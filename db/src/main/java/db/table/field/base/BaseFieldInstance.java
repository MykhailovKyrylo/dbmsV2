package db.table.field.base;

import java.awt.*;
import java.io.Serializable;
import java.math.BigDecimal;

public class BaseFieldInstance extends BaseField implements Serializable {

    private Object data;
    private static final long serialVersionUID = 1L;

    public static Object createValidFromString(String data, BaseFieldType baseFieldType) {
        Object crData;
        switch (baseFieldType) {
            case CHAR: {
                if (data.length() != 1)
                    try {
                        throw new Exception("Invalid input");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                crData = data.charAt(0);
                break;
            }
            case STRING: {
                crData = data;
                break;
            }
            case INTEGER: {
                crData = Integer.parseInt(data);
                break;
            }
            case LONGINT: {
                crData = Long.parseLong(data);
                break;
            }
            case REAL: {
                crData = Double.parseDouble(data);
                break;
            }
            case COLOR: {
                if (data.charAt(0) != '#' || data.length() < 7) { // formal HEX color #XXXXXX
                    try {
                        throw new Exception("Invalid input");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    crData = null;
                } else {
                    crData = Color.decode(data);
                }
                break;
            }
            default:
                crData = null;
        }
        return crData;
    }

    public BaseFieldInstance(String tableFieldName, Object data, BaseFieldType type) {
        super(tableFieldName, type);
        Object parseData = null;
        if(data != null) {
            String stringData = data.toString();
            switch (type) {
                case CHAR:
                    parseData = ((Character) stringData.charAt(0));
                    break;
                case STRING:
                    parseData = ((String) stringData);
                    break;
                case INTEGER:
                    parseData = Integer.parseInt(stringData);
                    break;
                case LONGINT:
                    parseData = Long.parseLong(stringData);
                    break;
                case REAL:
                    parseData = Double.parseDouble(stringData);
                    break;
                case COLOR:
                    parseData = Color.decode(stringData);
                    break;
            }
        }

        this.data = parseData;
    }

    public void setType(BaseFieldType type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public boolean equals(Object object) {
        if (object == null)
            return false;
        if (this == object)
            return true;
        if (object instanceof BaseFieldInstance) {
            BaseFieldInstance tmpTableFieldInstance = (BaseFieldInstance) object;
            if (!super.equals(object)) {
                return false;
            }
            return this.data.equals(tmpTableFieldInstance.data);
        }
        return false;
    }

    public int hashCode() {
        return super.hashCode() ^ data.hashCode();
    }

    @Override
    public String toString() {
        String res = super.toString();
        res += " Value: ";
        switch (super.type) {
            case CHAR:
                res += this.data + "\n";
                break;
            case STRING:
                res += this.data + "\n";
                break;
            case INTEGER:
                res += this.data + "\n";
                break;
            case LONGINT:
                res += this.data + "\n";
                break;
            case REAL:
                res += this.data + "\n";
                break;
            case COLOR:
                res += "$" + this.data + "\n";
        }
        return res;
    }
}
