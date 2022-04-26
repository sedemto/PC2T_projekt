package projekt;

import java.sql.*;
import java.util.*;

public class SQL {
	private Connection conn;
	public boolean connect() { 
	       conn= null; 
	       try {
	              conn = DriverManager.getConnection("jdbc:sqlite:data.db");                       
	       } 
	      catch (SQLException e) { 
	            System.out.println(e.getMessage());
		    return false;
	      }
	      return true;
	}
	public void disconnect() { 
		if (conn != null) {
			try {conn.close();
			} 
	        catch (SQLException ex) { 
	        	System.out.println(ex.getMessage()); 
	        }
		}
	}
	public boolean createTable() {
		if (conn == null)
			return false;
		String sql = "CREATE TABLE IF NOT EXISTS data("
			+"id integer PRIMARY KEY,"
			+"type varchar(255) NOT NULL,"
			+"name varchar(255) NOT NULL,"
			+"surname varchar(255) NOT NULL,"
			+"date varchar(255) NOT NULL,"
			+"marks varchar(255) NOT NULL"
			+");";
		try {
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
			return true;
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	public void insertStudent(int id,String type,String name, String surname,String date ,String marks ) {
        String sql = "INSERT INTO data(id,type,name,surname,date,marks) VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql); 
            pstmt.setInt(1, id);
            pstmt.setString(2, type);
            pstmt.setString(3, name);
            pstmt.setString(4, surname);
            pstmt.setString(5, date);
            pstmt.setString(6, marks);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	public void updateAll(int id,String type,String name,String surname,String date, String marks) {
        String sql = "UPDATE data SET type = ?, name = ?, surname = ?, date = ?, marks = ? WHERE id = ?";
        try {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, type);
                pstmt.setString(2, name);
                pstmt.setString(3, surname);
                pstmt.setString(4, date);
                pstmt.setString(5, marks);
                pstmt.setInt(6, id);
                pstmt.executeUpdate();
        } 
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	public void deleteStudent(int id) {
		String sql = "DELETE FROM data WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	public void deleteIds(List<Integer> ids) {
		String sql = "select id from data";
		try {
            PreparedStatement pstmt = conn.prepareStatement(sql); 
            ResultSet rs  = pstmt.executeQuery();
            while(rs.next()) {
        		if(!ids.contains(rs.getInt("id"))) {
        			deleteStudent(rs.getInt("id"));
        		}

                       	
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	public Database loadFromSQL(Database database) throws Exception{
        String sql = "SELECT id, type, name, surname, date, marks FROM data";
        try {
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql);
             while (rs.next()) {
            	 	database.setFileID(rs.getInt("id"));
                	database.addStudent(rs.getString("type"),rs.getString("name"),rs.getString("surname"),rs.getString("date"),true);
                	String[] marks_ = rs.getString("marks").split("");
                	for(String mark : marks_) {
    					database.newMark(rs.getInt("id"),Integer.parseInt(mark));
    				}
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return database;
	}
	public void saveToSQL(Database database) {
		createTable();
		List<Integer> ids = new ArrayList<Integer>();
		TreeMap<Integer,Student> archive = database.getAll();
		for(var st : archive.values()){
			String marks = "";
			int id = st.getID();
			for(Integer mark : st.getMarks()) {
				marks += mark.toString();
			}
			String sql = "select * from data where id=?";
			try {
	            PreparedStatement pstmt = conn.prepareStatement(sql); 
	            pstmt.setInt(1,id);
	            ResultSet rs  = pstmt.executeQuery();
	            if(!rs.next()) {
	            	insertStudent(id,st.getClass().getSimpleName(),st.getName(),st.getSurname(),st.getDate(),marks);
	            }
	            else {
	            	updateAll(id,st.getClass().getSimpleName(),st.getName(),st.getSurname(),st.getDate(),marks);
	            }
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
			ids.add(id);
			
		}
		deleteIds(ids);
	}


}
