package db.table;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import db.DB;

public class Storage implements Serializable {

    private static final long serialVersionUID = 1L;
    private Set<DB> dbs = null;

    private Storage() {
        dbs = new HashSet<DB>();
    }

    public static final Storage Instance = new Storage();

    public Set<DB> getDbs() {
        return dbs;
    }

}
