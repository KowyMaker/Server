package com.kowymaker.server.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.config.dbplatform.SQLitePlatform;
import com.avaje.ebeaninternal.server.lib.sql.TransactionIsolation;
import com.kowymaker.server.KowyMakerServer;

public class Database
{
    private KowyMakerServer server;
    private File            dbFile  = new File("game/database.db");
    private EbeanServer     ebean;
    private List<Class<?>>  classes = new ArrayList<Class<?>>();
    
    public Database(KowyMakerServer server)
    {
        this.server = server;
    }
    
    public void setup()
    {
        System.out.println("Setting up database...");
        
        ServerConfig db = new ServerConfig();
        db.setDatabasePlatform(new SQLitePlatform());
        db.setDefaultServer(false);
        db.setRegister(false);
        db.setName("KowyMakerDatabse");
        db.setClasses(classes);
        db.setDdlGenerate(true);
        db.setDdlRun(true);
        
        DataSourceConfig ds = db.getDataSourceConfig();
        ds.setDriver(server.getConfig().getString("database.driver"));
        ds.setIsolationLevel(TransactionIsolation.getLevel("SERIALIZABLE"));
        ds.setUrl("jdbc:sqlite:"
                + dbFile.getAbsolutePath().replaceAll("\\\\", "/"));
        ds.setUsername(server.getConfig().getString("database.user"));
        ds.setPassword(server.getConfig().getString("database.password"));
        
        ebean = EbeanServerFactory.create(db);
        
        System.out.println("Database ready for ops! DB file: " + dbFile.getAbsolutePath());
    }
    
    public File getDbFile()
    {
        return dbFile;
    }
    
    public void setDbFile(File dbFile)
    {
        this.dbFile = dbFile;
    }
    
    public KowyMakerServer getServer()
    {
        return server;
    }
    
    public EbeanServer getDatabase()
    {
        return ebean;
    }
    
    public List<Class<?>> getClasses()
    {
        return classes;
    }
    
    public void addClass(Class<?> clazz)
    {
        if(!classes.contains(clazz))
        {
            classes.add(clazz);
        }
    }
}
