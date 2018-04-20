package com.lifelineconnect.m8thubadmin.Pojo;

/**
 * Created by rana on 11/21/2017.
 */
public class UserLogin
{
    private String id;

    private String is_admin;

    private String email;

    private String name;

    private String mobile;

    private String jid;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getIs_admin ()
    {
        return is_admin;
    }

    public void setIs_admin (String is_admin)
    {
        this.is_admin = is_admin;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getJid ()
    {
        return jid;
    }

    public void setJid (String jid)
    {
        this.jid = jid;
    }

    public String getMobile ()
    {
        return mobile;
    }

    public void setMobile (String mobile)
    {
        this.mobile = mobile;
    }


    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", is_admin = "+is_admin+", email = "+email+", name = "+name+", jid = "+jid+"]";
    }
}

