package com;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import db.DB;
import db.table.Table;
import db.table.TableInstance;
import db.table.Table.TableBuilder;
import db.table.TableInstance.TableInstanceBuilder;
import db.table.field.base.BaseField;
import db.table.field.base.BaseFieldInstance;
import db.table.field.base.BaseFieldType;

public class UserConsole {

    private final static Scanner in = new Scanner(System.in);
    private final static Map<String, DB> dbs = new HashMap<String, DB>();

    private static void sep() {
        System.out.println();
        for (int i = 0; i < 80; ++i)
            System.out.print("-");
        System.out.println();
    }

    public static Boolean inTableProcessing(DB db, String tableName) {
        Table table = db.getTableByName(tableName);
        System.out.println("<--- Table: " + table.getTableName() + " --->");
        System.out.println("1. Add instance");
        System.out.println("2. Remove instance");
        // System.out.println("3. Choose Table");
        System.out.println("4. Show table\n");
        System.out.println("5. Delete duplicates\n");
        System.out.println("-1. Exit");
        int res = in.nextInt();
        in.nextLine();
        switch (res) {
            case 1: {
                TableInstanceBuilder tableInstanceBuilder = TableInstance
                        .tableInstanceBuilder();
                for (int i = 0; i < table.getTableBaseFields().size(); ++i) {
                    String name = table.getTableBaseFields().get(i)
                            .getTableFieldName();
                    BaseFieldType type = table.getTableBaseFields().get(i)
                            .getType();
                    System.out.println(
                            "Enter value for field " + name + " (" + type + "): ");
                    String inputData = in.nextLine();
                    switch (type) {
                        case CHAR:
                            tableInstanceBuilder
                                    .addBaseFieldInstance(new BaseFieldInstance(name,
                                            inputData.charAt(0), type));
                            break;
                        case STRING:
                            tableInstanceBuilder.addBaseFieldInstance(
                                    new BaseFieldInstance(name, inputData, type));
                            break;
                        case INTEGER:
                            tableInstanceBuilder
                                    .addBaseFieldInstance(new BaseFieldInstance(name,
                                            new Integer(Integer.parseInt(inputData)),
                                            type));
                            break;
                        case LONGINT:
                            tableInstanceBuilder
                                    .addBaseFieldInstance(new BaseFieldInstance(name,
                                            new Long(Long.parseLong(inputData)), type));
                            break;
                        case REAL:
                            tableInstanceBuilder
                                    .addBaseFieldInstance(new BaseFieldInstance(name,
                                            new Double(Double.parseDouble(inputData)),
                                            type));
                            break;
                        default:
                            return false;
                    }
                }

                try {
                    TableInstance tableInstance = tableInstanceBuilder.build();
                    Boolean OK = table.addTableInstance(tableInstance);
                    if (OK)
                        System.out.println(
                                "Table instance is successful added to table");
                    else
                        System.out.println("Wrong input!");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return true;
            }

            case 4: {
                System.out.println(table);
                return true;
            }
            case 5: {
                table.deleteDuplicates();
                System.out.println("Duplicates are deleted!");
                return true;
            }
            case -1: {
                return false;
            }
        }
        return true;
    }

    public static Boolean inDbProcessing(String dbName) {
        DB db = dbs.get(dbName);
        System.out.println("<--- DB: " + dbName + " --->");
        System.out.println("1. Create Table");
        System.out.println("2. Remove Table");
        System.out.println("3. Choose Table");
        System.out.println("4. Show all Tables\n");
        System.out.println("-1. Exit");

        int res = in.nextInt();
        in.nextLine();

        switch (res) {
            case 1: {
                System.out.println("Enter table name: ");
                String tableName = in.nextLine();
                if (db.contains(tableName)) {
                    System.out.println("DB " + dbName + " already contains table "
                            + tableName);
                    sep();
                    return true;
                }
                System.out.println("Enter number of fields in table " + tableName);
                int number = in.nextInt();
                in.nextLine();
                if (number < 0) {
                    System.out.println("Wrong number");
                    return true;
                }
                System.out.println("Ented for each field its type and value");
                System.out.println(
                        "Avalible types: INTEGER REAL CHAR LONGINT STRING CHARINTV");
                TableBuilder newTable = Table.tableBuilder()
                        .setTableName(tableName);
                for (int i = 0; i < number; ++i) {
                    System.out.println("Field name: ");
                    String inputName = in.nextLine();
                    System.out.println("Type: ");
                    String inputType = in.nextLine();
                    if (BaseFieldType.contains(inputType)) {
                        newTable = newTable.addTableBaseField(new BaseField(
                                inputName,
                                BaseFieldType.getTableFieldType(inputType)));
                    } else {
                        System.out.println("Wrong type. Try again");
                        return true;
                    }
                }
                try {
                    Table createdTable = newTable.build();
                    db.createTable(createdTable);
                    System.out.println("Table " + createdTable.getTableName()
                            + " successful created!");

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return true;
            }
            case 2: {
                return true;
            }
            case 3: {
                System.out.println("Enter table name: ");
                String tableName = in.nextLine();
                if (!db.contains(tableName)) {
                    System.out.println(
                            "Table with name " + tableName + " doesn't exist.");
                    sep();
                }
                System.out.println("OK. You now work with table : " + tableName);
                sep();
                while (inTableProcessing(db, tableName)) {
                }
                return true;
            }
            case 4: {
                System.out.println("Tables in db: " + db.getDbName());
                for (Table table : db.getTables()) {
                    System.out.println(table);
                }
                sep();
                return true;
            }
            case -1: {
                return false;
            }
        }
        return false;
    }

    public static void start() {
        do {
            System.out.println("<--- DB Manipulate Menu --->");
            System.out.println("1. Create DB");
            System.out.println("2. Remove DB");
            System.out.println("3. Choose DB");
            System.out.println("4. Show all DB's\n");
            System.out.println("-1. Exit");

            int res = in.nextInt();
            in.nextLine();
            sep();

            switch (res) {
                case 1: {
                    System.out.println("Enter db name: ");
                    String dbName = in.nextLine();
                    if (dbs.containsKey(dbName)) {
                        System.out.println(
                                "Db with name " + dbName + " already exist.");
                        sep();
                        continue;
                    }
                    dbs.put(dbName, new DB(dbName));
                    System.out.println(
                            "Db with name " + dbName + " successful created!");
                    sep();
                    break;
                }
                case 2: {
                    System.out.println("Enter db name: ");
                    String dbName = in.nextLine();
                    if (!dbs.containsKey(dbName)) {
                        System.out.println(
                                "Db with name " + dbName + " doesn't exist.");
                        sep();
                        continue;
                    }
                    dbs.remove(dbName);
                    System.out.println(
                            "Db with name " + dbName + " successful removed!");
                    sep();
                    break;
                }
                case 3: {
                    System.out.println("Enter db name: ");
                    String dbName = in.nextLine();
                    if (!dbs.containsKey(dbName)) {
                        System.out.println(
                                "Db with name " + dbName + " doesn't exist.");
                        sep();
                        continue;
                    }
                    System.out.println("OK. You now work with db : " + dbName);
                    sep();
                    while (inDbProcessing(dbName)) {
                    }
                    break;
                }
                case 4: {
                    System.out.println("Db's : ");
                    int index = 1;
                    for (Map.Entry < String, DB > entry : dbs.entrySet()) {
                        System.out.println(
                                index++ + ") " + "dbName: " + entry.getKey());
                    }
                    sep();
                    break;
                }
                case -1: {
                    break;
                }
                default:
                    break;
            }

        } while (true);
    }
}

