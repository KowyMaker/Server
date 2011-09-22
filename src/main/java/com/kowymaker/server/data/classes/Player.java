package com.kowymaker.server.data.classes;

import javax.persistence.*;

@Entity
@Table(name = "players")
public class Player
{
    @Id
    @Column(name = "id")
    private long   id;
    
    @Basic
    @Column(name = "name")
    private String name;
    
    @Basic
    @Column(name = "x")
    private String x;
    
    @Basic
    @Column(name = "y")
    private String y;
    
    @Basic
    @Column(name = "z")
    private String z;
    
    public long getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getX()
    {
        return x;
    }
    
    public void setX(String x)
    {
        this.x = x;
    }
    
    public String getY()
    {
        return y;
    }
    
    public void setY(String y)
    {
        this.y = y;
    }
    
    public String getZ()
    {
        return z;
    }
    
    public void setZ(String z)
    {
        this.z = z;
    }
}
