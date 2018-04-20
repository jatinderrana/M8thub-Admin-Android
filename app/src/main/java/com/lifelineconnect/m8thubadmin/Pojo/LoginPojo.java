package com.lifelineconnect.m8thubadmin.Pojo;

/**
 * Created by rana on 11/21/2017.
 */
public class LoginPojo
{
    private String token;

    private UserLogin userLogin;

    public String getToken ()
    {
        return token;
    }

    public void setToken (String token)
    {
        this.token = token;
    }

    public UserLogin getUserLogin()
    {
        return userLogin;
    }

    public void setUserLogin(UserLogin userLogin)
    {
        this.userLogin = userLogin;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [token = "+token+", userLogin = "+ userLogin +"]";
    }
}
