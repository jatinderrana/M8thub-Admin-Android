package com.lifelineconnect.m8thubadmin.Pojo;

/**
 * Created by rana on 11/21/2017.
 */
public class RegisterPojo
{
    private String id;

    private String first_name;

    private String is_approved_publisher;

    private String is_admin;

    private String email;

    private String password;

    private String last_name;

    private String jid;

    private String mobile;

    private String avatar;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getFirst_name ()
    {
        return first_name;
    }

    public void setFirst_name (String first_name)
    {
        this.first_name = first_name;
    }

    public String getIs_approved_publisher ()
    {
        return is_approved_publisher;
    }

    public void setIs_approved_publisher (String is_approved_publisher)
    {
        this.is_approved_publisher = is_approved_publisher;
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

    public String getLast_name ()
    {
        return last_name;
    }

    public void setLast_name (String last_name)
    {
        this.last_name = last_name;
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

    public String getAvatar ()
    {
        return avatar;
    }

    public void setAvatar (String avatar)
    {
        this.avatar = avatar;
    }

    public String getPassword ()
    {
        return password;
    }

    public void setPassword (String password)
    {
        this.password = password;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", first_name = "+first_name+", is_approved_publisher = "+is_approved_publisher+", is_admin = "+is_admin+", email = "+email+", last_name = "+last_name+", jid = "+jid+", mobile = "+mobile+"]";
    }
}
