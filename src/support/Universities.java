package support;

import java.util.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;

public class Universities {
    private Vector<String> uStates = null;
    private DataSource ds = null;

    public Universities() throws NamingException {
        Context initCtx = new InitialContext();
        ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/ClassesDBPool");
    }

    public int getUnivStatesNb() {
        return (getUnivStates().size());
    }

    public String getUnivState(int id) {
        return (getUnivStates().get(id));
    }

    public Vector<String> getUnivStates() {
        if (uStates == null) {
            Connection conn = null;
            PreparedStatement pstmt;
            ResultSet rs;
            try {
                conn = ds.getConnection();
                pstmt = conn.prepareStatement("SELECT country_state FROM universities GROUP BY country_state ORDER BY country_state ASC");
                rs = pstmt.executeQuery();
                uStates = new Vector();
                while (rs.next())
                    uStates.add(rs.getString(1));
            }
            catch (SQLException e) {
                return (null);
            }
            finally {
                try { if (conn != null) { conn.close(); } }
                catch (SQLException e) { }
            }
        }
        return (uStates);
    }

    public Vector getUnivByState(int id) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Vector uByState = null;
        try {
            conn = ds.getConnection();
            pstmt = conn.prepareStatement("SELECT u_id, university FROM universities WHERE country_state=?");
            pstmt.setString(1, getUnivState(id));
            rs = pstmt.executeQuery();
            uByState = new Vector();
            while (rs.next()) {
                Vector tuple = new Vector();
                tuple.add(rs.getInt(1));
                tuple.add(rs.getString(2));
                uByState.add(tuple);
            }
        }
        catch (SQLException e) {
        }
        finally {
            try { if (conn != null) { conn.close(); } }
            catch (SQLException e) { }
        }
        return (uByState);
    }

    public String getUnivById(int id) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        String  res = null;
        try {
            conn = ds.getConnection();
            pstmt = conn.prepareStatement("SELECT university FROM universities WHERE u_id=?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                res = rs.getString(1);
            }
        }
        catch (SQLException e) {
        }
        finally {
            try { if (conn != null) { conn.close(); } }
            catch (SQLException e) { }
        }
        return (res);
    }

    public int  getUnivId(int country, String name) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int id = -1;
        try {
            conn = ds.getConnection();
            pstmt = conn.prepareStatement("SELECT u_id FROM universities WHERE country_state=? AND university=?");
            pstmt.setString(1, getUnivState(country));
            pstmt.setString(2, name);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        }
        catch (SQLException e) {
        }
        finally {
            try { if (conn != null) { conn.close(); } }
            catch (SQLException e) { }
        }
        return (id);
    }
}
       

