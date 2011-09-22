package com.kowymaker.server.data;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.Query;
import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.config.dbplatform.SQLitePlatform;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.server.ddl.DdlGenerator;
import com.avaje.ebeaninternal.server.lib.sql.TransactionIsolation;
import com.kowymaker.server.KowyMakerServer;
import com.kowymaker.server.data.sources.DataSource;

public class Database
{
    private final KowyMakerServer  server;
    private EbeanServer            ebean;
    private final List<Class<?>>   classes = new ArrayList<Class<?>>();
    private final List<DataSource> sources = new ArrayList<DataSource>();
    
    public Database(KowyMakerServer server)
    {
        this.server = server;
    }
    
    public void setup()
    {
        System.out.println("Setting up database...");
        
        // Register basic classes
        DataManager.registerClasses(classes);
        
        final ServerConfig db = new ServerConfig();
        db.setDefaultServer(false);
        db.setRegister(false);
        db.setName("KowyMakerDatabase");
        db.setClasses(classes);
        
        final DataSourceConfig ds = new DataSourceConfig();
        ds.setDriver(server.getConfig().getString("database.driver"));
        ds.setIsolationLevel(TransactionIsolation.getLevel("SERIALIZABLE"));
        ds.setUrl(server.getConfig().getString("database.url")
                .replaceAll("\\\\", "/"));
        ds.setUsername(server.getConfig().getString("database.user"));
        ds.setPassword(server.getConfig().getString("database.password"));
        
        if (ds.getDriver().contains("sqlite"))
        {
            db.setDatabasePlatform(new SQLitePlatform());
            db.getDatabasePlatform().getDbDdlSyntax().setIdentity("");
        }
        
        db.setDataSourceConfig(ds);
        
        ebean = EbeanServerFactory.create(db);
        
        System.out.println("Database ready for ops!");
    }
    
    public void createDatabase()
    {
        
        final SpiEbeanServer serv = (SpiEbeanServer) ebean;
        final DdlGenerator gen = serv.getDdlGenerator();
        gen.runScript(false, gen.generateCreateDdl());
        
        System.out.println("Database created!");
    }
    
    public boolean isDatabaseCreated()
    {
        final SpiEbeanServer serv = (SpiEbeanServer) ebean;
        try
        {
            if(!classes.isEmpty())
            {
                serv.find(classes.get(0)).findRowCount();
            }
            return true;
        }
        catch (Exception e)
        {
            
        }
        
        return false;
    }
    
    public void load()
    {
        System.out.println("Loading database...");
        
        for (final DataSource source : sources)
        {
            source.loadDatabase();
        }
        
        System.out.println("Database loaded!");
    }
    
    public KowyMakerServer getServer()
    {
        return server;
    }
    
    public EbeanServer getEbeanServer()
    {
        return ebean;
    }
    
    public List<Class<?>> getClasses()
    {
        return classes;
    }
    
    public void registerClass(Class<?> clazz)
    {
        if (!classes.contains(clazz))
        {
            classes.add(clazz);
        }
    }
    
    public void registerSource(DataSource source)
    {
        if (!sources.contains(source))
        {
            sources.add(source);
        }
    }
    
    public <T> Query<T> query(Class<T> clazz)
    {
        return ebean.find(clazz);
    }
    
    public <T> List<T> select(Class<T> clazz)
    {
        return select(clazz, "*");
    }
    
    public <T> List<T> select(Class<T> clazz, String columns)
    {
        return ebean.find(clazz).select(columns).findList();
    }
    
    public void save(Object o)
    {
        ebean.save(o);
    }
}
