package server.web.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import db.DB;
import db.table.Storage;
import db.table.Table;
import db.table.TableInstance;
import server.service.DbService;
import server.service.TableService;
import server.web.editor.TableEditor;
import server.web.editor.TableInstanceEditor;

@RestController
public class TableController {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Table.class, new TableEditor());
        binder.registerCustomEditor(TableInstance.class,
                new TableInstanceEditor());
    }

    @Autowired
    private DbService dbService;

    @Autowired
    private TableService tableService;

    @RequestMapping(method = RequestMethod.GET, value = "/db/{dbName}/table")
    public ResponseEntity getTablesByDB(
            @PathVariable(value = "dbName") String dbName) throws Exception {
        logger.info("GET /db/" + dbName + "/table method: getDb");
        DB result = dbService.getDB(dbName);
        if (result != null) {
            return new ResponseEntity(result.getTables(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("Db " + dbName + " does not exist",
                HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/db/{dbName}/table")
    public ResponseEntity createTableByDB(
            @PathVariable(value = "dbName") String dbName,
            @RequestParam(value = "tableJson") Table table) throws Exception {
        logger.info("POST /db/" + dbName + "/table method: getDb");
        Boolean result = false;
        DB db = dbService.getDB(dbName);
        if (db != null) {
            result = tableService.addTable(db, table);
        }
        if (result != false) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<String>("Something went wrong",
                HttpStatus.BAD_REQUEST);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/db/{dbName}/table/{tableName}")
    public ResponseEntity getTableByDB(
            @PathVariable(value = "dbName") String dbName,
            @PathVariable(value = "tableName") String tableName)
            throws Exception {
        logger.info(
                "GET /db/" + dbName + "/table/" + tableName + " method: getDb");
        DB db = dbService.getDB(dbName);
        Table table = tableService.getTable(db, tableName);
        if (table != null) {
            return new ResponseEntity(table, HttpStatus.OK);
        }
        return new ResponseEntity<String>("Db " + dbName + " does not exist",
                HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/db/{dbName}/table/{tableName}")
    public ResponseEntity addTableInstanceByTableAndDB(
            @PathVariable(value = "dbName") String dbName,
            @PathVariable(value = "tableName") String tableName,
            @RequestParam(value = "tableInstanceJson") TableInstance tableInstance)
            throws Exception {
        logger.info("POST /db/" + dbName + "/table/" + tableName
                + " method: getDb");
        DB db = dbService.getDB(dbName);
        Table table = tableService.getTable(db, tableName);
        if (table != null) {
            Boolean res = table.addTableInstance(tableInstance);
            if (res == true) {
                return new ResponseEntity(table, HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Db " + dbName + " does not exist",
                HttpStatus.BAD_REQUEST);
    }
}

