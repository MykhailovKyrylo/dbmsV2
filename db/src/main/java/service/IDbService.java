package service;

import java.util.ArrayList;

import db.DB;

public interface IDbService {

    public Boolean createDB(DB db);

    public ArrayList<DB> getDbs();

    public DB getDB(String dbName);
}

