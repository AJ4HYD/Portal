package com.PortalDB;

import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateTestRun {

    private DBConnect dbConnect = new DBConnect();


    @Test
    public void updateTestCaseToolId() throws SQLException {

        String testCase = "select * from springboot.test_case";
        String getCaseToolIDFromSuite = "SELECT test_tool_id FROM springboot.test_suite WHERE test_case_id = ?";
        String updateTestCaseToolID = "update springboot.test_case set test_tool_id = ? where id = ?";
        int testCaseId;
        int caseToolId = 0;
        int i = 0;

        Statement stmt = dbConnect.connectToDB().createStatement();

//        int n = stmt.executeUpdate("ALTER TABLE springboot.test_case ADD test_tool_id BIGINT");
//        System.out.println("Query OK, " + n + " rows affected");

        ResultSet rs = stmt.executeQuery(testCase);
        PreparedStatement ps = dbConnect.connectToDB().prepareStatement(getCaseToolIDFromSuite);
        PreparedStatement p = dbConnect.connectToDB().prepareStatement(updateTestCaseToolID);

        while (rs.next()) {
            if (rs.getString("test_tool_id") == null) {
                i++;
                testCaseId = Integer.parseInt(rs.getString("id"));


                ps.setInt(1, testCaseId);
                ResultSet r = ps.executeQuery();

                if (r.next())
                    caseToolId = Integer.parseInt(r.getString("test_tool_id"));

                p.setInt(1, caseToolId);
                p.setInt(2, testCaseId);

                p.executeUpdate();
                System.out.println("Updated Test Tool Record for Test Case " + rs.getString("id"));
            }
            else
                System.out.println("Test Tool Record Already Present " + rs.getString("test_tool_id"));
        }

    }


    @Test
    public void updateTestRunCaseId() throws SQLException {

        String testRun = "select * from springboot.test_run";
        String getSuiteCaseId = "SELECT test_case_id FROM springboot.test_suite WHERE id = ?";
        String updateTestRunCaseID = "update springboot.test_run set test_case_id = ? where test_suite_id = ?";
        int testSuiteId;
        int suiteCaseId = 0;
        int i = 0;

        Statement stmt = dbConnect.connectToDB().createStatement();

        int n = stmt.executeUpdate("ALTER TABLE springboot.test_run ADD test_case_id BIGINT, ADD phase_id BIGINT");
        System.out.println("Query OK, " + n + " rows affected");

        ResultSet rs = stmt.executeQuery(testRun);
        PreparedStatement ps = dbConnect.connectToDB().prepareStatement(getSuiteCaseId);
        PreparedStatement p = dbConnect.connectToDB().prepareStatement(updateTestRunCaseID);

        while (rs.next()) {
            if (rs.getString("test_case_id") == null) {
                i++;
                testSuiteId = Integer.parseInt(rs.getString("test_suite_id"));


                ps.setInt(1, testSuiteId);
                ResultSet r = ps.executeQuery();

                if (r.next())
                    suiteCaseId = Integer.parseInt(r.getString("test_case_id"));

                p.setInt(1, suiteCaseId);
                p.setInt(2, suiteCaseId);

                p.executeUpdate();
                System.out.println("Updated Test Case Record for Test Run " + rs.getString("id"));
            }
            else
                System.out.println("Test Case Record Already Present " + rs.getString("test_case_id"));
        }

    }
}
