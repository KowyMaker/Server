package com.kowymaker.server.data;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.config.dbplatform.SQLitePlatform;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.server.ddl.DdlGenerator;
import com.avaje.ebeaninternal.server.lib.sql.TransactionIsolation;
import com.kowymaker.server.KowyMakerServer;

public class Database
{
    private final KowyMakerServer server;
    private EbeanServer           ebean;
    private final List<Class<?>>  classes = new ArrayList<Class<?>>();
    
    public Database(KowyMakerServer server)
    {
        this.server = server;
    }
    
    public void setup()
    {
        System.out.println("Setting up database...");
        
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
        
        final SpiEbeanServer serv = (SpiEbeanServer) ebean;
        final DdlGenerator gen = serv.getDdlGenerator();
        gen.runScript(false, gen.generateCreateDdl());
        
        System.out.println("Database ready for ops!");
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
}
