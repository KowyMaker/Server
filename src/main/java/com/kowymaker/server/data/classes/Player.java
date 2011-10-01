/**
 * This file is part of Kowy Maker.
 * 
 * Kowy Maker is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * Kowy Maker is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * Kowy Maker. If not, see <http://www.gnu.org/licenses/>.
 */
package com.kowymaker.server.data.classes;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
