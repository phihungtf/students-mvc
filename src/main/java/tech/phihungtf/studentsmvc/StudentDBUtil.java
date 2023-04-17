package tech.phihungtf.studentsmvc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class StudentDBUtil {
    private DataSource dataSource;

    public StudentDBUtil(DataSource theDataSource) {
        dataSource = theDataSource;
    }

    public List<Student> getStudents(String nameStr, String sortStr) throws Exception {
        List<Student> students = new ArrayList<>();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            // get a connection
            myConn = dataSource.getConnection();

            // create sql statement
            String sql = "select * from students order by " + sortStr;
			if (nameStr != null && nameStr.trim().length() > 0) {
				sql = "select * from students where name like '%" + nameStr + "%' order by " + sortStr;
			}

            myStmt = myConn.createStatement();

            // execute query
            myRs = myStmt.executeQuery(sql);

            // process result set
            while (myRs.next()) {
                // retrieve data from result set row
                int id = myRs.getInt("id");
                String name = myRs.getString("name");
                int grade = myRs.getInt("grade");
                Date birthday = myRs.getDate("birthday");
                String notes = myRs.getString("notes");

                // create new student object
                Student tempStudent = new Student(id, name, grade, birthday, notes);

                // add it to the list of students
                students.add(tempStudent);
            }

            return students;
        } finally {
            // close JDBC objects
            close(myConn, myStmt, myRs);
        }
    }

	public void addStudent(Student theStudent) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			// get db connection
			myConn = dataSource.getConnection();
			
			// create sql for insert
			String sql = "insert into students "
					   + "(name, grade, birthday, notes) "
					   + "values (?, ?, ?, ?)";
			
			myStmt = myConn.prepareStatement(sql);
			
			// set the param values for the student
			myStmt.setString(1, theStudent.getName());
			myStmt.setInt(2, theStudent.getGrade());
			myStmt.setDate(3, new Date(theStudent.getBirthday().getTime()));
			myStmt.setString(4, theStudent.getNotes());
			
			// execute sql insert
			myStmt.execute();
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
	}
	
	public void updateStudent(Student theStudent) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn = dataSource.getConnection();
			
			// create SQL update statement
			String sql = "update students "
						+ "set name=?, grade=?, birthday=?, notes=? "
						+ "where id=?";
			
			// prepare statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setString(1, theStudent.getName());
			myStmt.setInt(2, theStudent.getGrade());
			myStmt.setDate(3, new Date(theStudent.getBirthday().getTime()));
			myStmt.setString(4, theStudent.getNotes());
			myStmt.setInt(5, theStudent.getId());
			
			// execute SQL statement
			myStmt.execute();
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
	}

	public void deleteStudent(String theStudentId) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			// convert student id to int
			int studentId = Integer.parseInt(theStudentId);
			
			// get connection to database
			myConn = dataSource.getConnection();
			
			// create sql to delete student
			String sql = "delete from students where id=?";
			
			// prepare statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, studentId);
			
			// execute sql statement
			myStmt.execute();
		}
		finally {
			// clean up JDBC code
			close(myConn, myStmt, null);
		}	
	}

    private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
        try {
            if (myRs != null) {
                myRs.close();
            }

            if (myStmt != null) {
                myStmt.close();
            }

            if (myConn != null) {
                myConn.close();
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}
