package com.PortalDB;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

import static com.PortalDB.Support.getConfigData;

class DBConnect {

    private static String db_host = "db_host";
    private static String db_username = "db_username";
    private static String db_password = "db_password";

    Connection connectToDB() throws SQLException {
        try {
            if ("db_host".equals(db_host)) {
                db_host = getConfigData(db_host);
                db_username = getConfigData(db_username);
                db_password = getConfigData(db_password);
            }

            Class.forName("com.mysql.cj.jdbc.Driver");

            //con.close();
            return DriverManager.getConnection(db_host, db_username, db_password);
        }
        catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
}
