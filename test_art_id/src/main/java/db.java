import oracle.jdbc.OracleTypes;
import oracle.jdbc.pool.OracleDataSource;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class db {
    public db() {
    }

    String jdbcUrl = "jdbc:oracle:thin:@localhost:1521/XE";
    String userid = "c##Hurin";
    String password = "1947";
    Connection conn;

    public Connection getDBConnection() throws SQLException{
        OracleDataSource ds;
        ds = new OracleDataSource();
        ds.setURL(jdbcUrl);
        Connection conn=ds.getConnection(userid,password);
        
        System.out.println("Connect to db complete");
        return conn;
    }

    public int insertIntoPar_list(Connection conn,String value) throws SQLException {

        String sql = "BEGIN Insert into par_value(value) VALUES(?) returning id into ?; END;";
        CallableStatement stmt=  conn.prepareCall(sql);
        stmt.setString(1,value);
        stmt.registerOutParameter(2, OracleTypes.NUMBER);
        stmt.executeUpdate();
        return stmt.getInt(2);
    }

    public int insertIntoPar(Connection conn, String step, String name, String fullname, String comment,
                              int isEditable, int isScannable, int isVisible, int isRequired,
                              int isPrinted, int isVOL, String type, String min_l, String max_l,
                              String double_inp, String value, String reg_exp, String from_debt) throws SQLException {
        String sql = "Begin Insert into par(step,name,fullname,comment_,isEditable,isScannable,isVisible,isRequired,isPrinted,isValidateOnLine,type,min_length,max_length,double_input,value,reg_exp,from_debt)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" +
                "returning id into ?; END;";
        CallableStatement stmt=conn.prepareCall(sql);
        stmt.setString(1,step);
        stmt.setString(2,name);
        stmt.setString(3,fullname);
        stmt.setString(4,comment);
        stmt.setInt(5,isEditable);
        stmt.setInt(6,isScannable);
        stmt.setInt(7,isVisible);
        stmt.setInt(8,isRequired);
        stmt.setInt(9,isPrinted);
        stmt.setInt(10,isVOL);
        stmt.setString(11,type);
        stmt.setString(12,min_l);
        stmt.setString(13,max_l);
        stmt.setString(14,double_inp);
        stmt.setString(15,value);
        stmt.setString(16,reg_exp);
        stmt.setString(17,from_debt);
        stmt.registerOutParameter(18, OracleTypes.NUMBER);
        stmt.executeUpdate();
        return stmt.getInt(18);
    }

    public int insertIntoServ(Connection conn, String serv_id, int isClosed, String bic, String schet,
                               String corr_schet, int pars, String sys_message) throws SQLException {
        String sql = "BEGIN Insert into serv (isClosed,bic,schet,corr_schet,pars,sys_message) VALUES(?,?,?,?,?,?) returning serv_id into ?; END;";
        CallableStatement stmt=conn.prepareCall(sql);
        stmt.setInt(1,isClosed);
        stmt.setString(2,bic);
        stmt.setString(3,schet);
        stmt.setString(4,corr_schet);
        stmt.setInt(5,pars);
        stmt.setString(6,sys_message);
        stmt.registerOutParameter(7, OracleTypes.NUMBER);
        stmt.executeUpdate();
        return stmt.getInt(7);
    }

    public int insertIntoOrder(Connection conn, int services, String summa) throws SQLException {
        String sql = "BEGIN Insert into order_(services,summa) VALUES(?,?) returning id into ?; END;";
        CallableStatement stmt=conn.prepareCall(sql);
        stmt.setInt(1,services);
        stmt.setString(2,summa);
        stmt.registerOutParameter(3, OracleTypes.NUMBER);
        stmt.executeUpdate();
        return stmt.getInt(3);
    }
}
