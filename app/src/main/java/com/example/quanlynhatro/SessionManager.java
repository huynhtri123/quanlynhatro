package com.example.quanlynhatro;

import com.example.quanlynhatro.Entity.Account;
import com.example.quanlynhatro.Entity.Admin;
import com.example.quanlynhatro.Entity.Tenant;

//session
public class SessionManager {
    private static SessionManager instance;
    private Tenant currentTenant;
    private Account account;
    private Admin admin;

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    private SessionManager() {

    }
    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setCurrentTenant(Tenant tenant) {
        this.currentTenant = tenant;
    }

    public Tenant getCurrentTenant() {
        return currentTenant;
    }

    public void clearSession() {
        currentTenant = null;
        account = null;
        admin = null;
    }
}
