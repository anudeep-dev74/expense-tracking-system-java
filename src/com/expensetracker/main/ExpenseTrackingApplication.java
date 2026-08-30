package com.expensetracker.main;

import com.expensetracker.menu.AuthenticationMenu;
import com.expensetracker.menu.ExpenseMenu;
import com.expensetracker.utility.AdminInitializer;

public class ExpenseTrackingApplication {
    public static void main(String[] args) {
        AdminInitializer adminInitializer = new AdminInitializer();
        adminInitializer.initializeAdmin();
        AuthenticationMenu authenticationMenu = new AuthenticationMenu();
        authenticationMenu.showMenu();
    }
}
