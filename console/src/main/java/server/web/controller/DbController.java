package server.web.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import db.DB;
import db.table.Storage;
import server.service.DbService;

@RestController
// @RequestMapping("/db")
public class DbController {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private DbService dbService;

    @RequestMapping(method = RequestMethod.GET, value = "/db")
    public ResponseEntity all() throws Exception {
        logger.info("GET /db/ method: all");
        return new ResponseEntity(dbService.getDbs(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/db")
    public ResponseEntity createDb(@RequestParam String dbName)
            throws Exception {
        logger.info("POST /db/ method: createDb");
        DB db = new DB(dbName);
        Boolean result = dbService.createDB(db);
        if (result == true) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<String>("Db " + dbName + " already exists",
                HttpStatus.CONFLICT);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/db")
    public ResponseEntity deleteDb(@RequestParam String dbName)
            throws Exception {
        logger.info("DELETE /db/ method: deleteDb");
        Boolean result = dbService.deleteDB(dbName);
        if (result == true) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<String>("Db " + dbName + " does not exist",
                HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/db/{dbName}")
    public ResponseEntity getDb(@PathVariable(value = "dbName") String dbName)
            throws Exception {
        logger.info("GET /db/ " + dbName + " method: getDb");
        DB result = dbService.getDB(dbName);
        if (result != null) {
            return new ResponseEntity<DB>(result, HttpStatus.OK);
        }
        return new ResponseEntity<String>("Db " + dbName + " does not exist",
                HttpStatus.BAD_REQUEST);
    }
}

