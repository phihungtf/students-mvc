package tech.phihungtf.studentsmvc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
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

	public Student getStudent(int id) throws Exception {
		Student student = null;

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			// get connection to database
			myConn = dataSource.getConnection();

			// create sql to delete student
			String sql = "select * from students where id=?";

			// prepare statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, id);

			// execute sql statement
			myRs = myStmt.executeQuery();

			// retrieve data from result set row
			if (myRs.next()) {
				String name = myRs.getString("name");
				int grade = myRs.getInt("grade");
				Date birthday = myRs.getDate("birthday");
				String notes = myRs.getString("notes");

				// create new student object
				student = new Student(id, name, grade, birthday, notes);
			} else {
				throw new Exception("Could not find student id: " + id);
			}

			return student;
		} finally {
			// clean up JDBC code
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

	public List<Course> getCourses(String nameStr, String sortStr) throws Exception {
		List<Course> courses = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			// get a connection
			myConn = dataSource.getConnection();

            // create sql statement
            String sql = "select * from courses order by " + sortStr;
			if (nameStr != null && nameStr.trim().length() > 0) {
				sql = "select * from courses where name like '%" + nameStr + "%' order by " + sortStr;
			}

			myStmt = myConn.createStatement();

			// execute query
			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {
				// retrieve data from result set row
				int id = myRs.getInt("id");
				String name = myRs.getString("name");
				String lecturer = myRs.getString("lecturer");
				int year = myRs.getInt("year");
				String notes = myRs.getString("notes");

				// create new course object
				Course tempCourse = new Course(id, name, lecturer, year, notes);

				// add it to the list of courses
				courses.add(tempCourse);
			}

			return courses;
		} finally {
			// close JDBC objects
			close(myConn, myStmt, myRs);
		}
	}

	public Course getCourse(int id) throws Exception {
		Course course = null;

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			// get connection to database
			myConn = dataSource.getConnection();

			// create sql to delete student
			String sql = "select * from courses where id=?";

			// prepare statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, id);

			// execute sql statement
			myRs = myStmt.executeQuery();

			// retrieve data from result set row
			if (myRs.next()) {
				String name = myRs.getString("name");
				String lecturer = myRs.getString("lecturer");
				int year = myRs.getInt("year");
				String notes = myRs.getString("notes");

				// create new course object
				course = new Course(id, name, lecturer, year, notes);
			} else {
				throw new Exception("Could not find course id: " + id);
			}

			return course;
		} finally {
			// clean up JDBC code
			close(myConn, myStmt, myRs);
		}
	}

	public void addCourse(Course theCourse) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			// get db connection
			myConn = dataSource.getConnection();
			
			// create sql for insert
			String sql = "insert into courses "
					   + "(name, lecturer, year, notes) "
					   + "values (?, ?, ?, ?)";
			
			myStmt = myConn.prepareStatement(sql);
			
			// set the param values for the course
			myStmt.setString(1, theCourse.getName());
			myStmt.setString(2, theCourse.getLecturer());
			myStmt.setInt(3, theCourse.getYear());
			myStmt.setString(4, theCourse.getNotes());
			
			// execute sql insert
			myStmt.execute();
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
	}

	public void updateCourse(Course theCourse) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn = dataSource.getConnection();
			
			// create SQL update statement
			String sql = "update courses "
						+ "set name=?, lecturer=?, year=?, notes=? "
						+ "where id=?";
			
			// prepare statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setString(1, theCourse.getName());
			myStmt.setString(2, theCourse.getLecturer());
			myStmt.setInt(3, theCourse.getYear());
			myStmt.setString(4, theCourse.getNotes());
			myStmt.setInt(5, theCourse.getId());
			
			// execute SQL statement
			myStmt.execute();
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
	}

	public void deleteCourse(String theCourseId) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			// convert course id to int
			int courseId = Integer.parseInt(theCourseId);
			
			// get connection to database
			myConn = dataSource.getConnection();
			
			// create sql to delete course
			String sql = "delete from courses where id=?";
			
			// prepare statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, courseId);
			
			// execute sql statement
			myStmt.execute();
		}
		finally {
			// clean up JDBC code
			close(myConn, myStmt, null);
		}	
	}

	public List<Score> getScoresByCourseId(int courseId, String sortStr) throws Exception {
		List<Score> scores = new ArrayList<>();

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			// get a connection
			myConn = dataSource.getConnection();

			// create sql statement
			String sql = "select * from score_board "
					+ "join students on score_board.student_id = students.id "
					+ "where score_board.course_id = ? "
					+ "order by " + sortStr + ((sortStr.equals("score")) ? " desc" : " asc");

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, courseId);
			
			// execute query
			myRs = myStmt.executeQuery();

			// process result set
			while (myRs.next()) {
				// retrieve data from result set row
				int studentId = myRs.getInt("student_id");
				String studentName = myRs.getString("name");
				Float score = myRs.getFloat("score");
				if (myRs.wasNull()) {
					score = null;
				}
				// create new score object
				Score tempScore = new Score(studentId, -1, studentName, null, score);

				// add it to the list of scores
				scores.add(tempScore);
			}

			return scores;
		} finally {
			// close JDBC objects
			close(myConn, myStmt, myRs);
		}
	}

	public List<Student> getStudentsNotInCourse(int courseId) throws Exception {
		List<Student> students = new ArrayList<>();

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			// get a connection
			myConn = dataSource.getConnection();

			// create sql statement
			String sql = "select distinct id, name from students"
					+ " where id not in (select student_id from score_board where course_id = ?)"
					+ " order by name";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, courseId);
			
			// execute query
			myRs = myStmt.executeQuery();

			// process result set
			while (myRs.next()) {
				// retrieve data from result set row
				int studentId = myRs.getInt("id");
				String studentName = myRs.getString("name");

				// create new student object
				Student tempStudent = new Student(studentId, studentName, -1, null, null);

				// add it to the list of students
				students.add(tempStudent);
			}

			return students;
		} finally {
			// close JDBC objects
			close(myConn, myStmt, myRs);
		}
	}

	public void addStudentInCourse(Score score) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn = dataSource.getConnection();
			
			// create sql for insert
			String sql = "insert into score_board "
					   + "(student_id, course_id, score) "
					   + "values (?, ?, ?)";
			
			myStmt = myConn.prepareStatement(sql);
			
			// set the param values for the score
			myStmt.setInt(1, score.getStudentId());
			myStmt.setInt(2, score.getCourseId());
			if (score.getScore() == null) {
				myStmt.setNull(3, Types.FLOAT);
			} else {
				myStmt.setFloat(3, score.getScore());
			}
			
			// execute sql insert
			myStmt.execute();
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
	}

	public void updateStudentInCourse(Score score) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn = dataSource.getConnection();
			
			// create sql for update
			String sql = "update score_board "
					   + "set score=? "
					   + "where student_id=? and course_id=?";
			myStmt = myConn.prepareStatement(sql);
			
			// set the param values for the score
			if (score.getScore() == null) {
				myStmt.setNull(1, Types.FLOAT);
			} else {
				myStmt.setFloat(1, score.getScore());
			}
			myStmt.setInt(2, score.getStudentId());
			myStmt.setInt(3, score.getCourseId());
			
			// execute sql insert
			myStmt.execute();
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
	}
	
	public void deleteStudentInCourse(String theCourseId, String theStudentId) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			// convert student id to int
			int studentId = Integer.parseInt(theStudentId);

			int courseId = Integer.parseInt(theCourseId);
			
			// get connection to database
			myConn = dataSource.getConnection();
			
			// create sql to delete course
			String sql = "delete from score_board where course_id=? and student_id=?";
			
			// prepare statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, courseId);
			myStmt.setInt(2, studentId);
			
			// execute sql statement
			myStmt.execute();
		}
		finally {
			// clean up JDBC code
			close(myConn, myStmt, null);
		}	
	}

	public List<Score> getScoresByStudentId(int studentId, int year) throws Exception {
		List<Score> scores = new ArrayList<>();

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			// get a connection
			myConn = dataSource.getConnection();

			// create sql statement
			String sql = "select * from score_board "
					+ "join courses on score_board.course_id = courses.id "
					+ "where score_board.student_id = ? "
					+ (year == -1 ? "" : "and year = ? ");

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, studentId);
			if (year != -1) {
				myStmt.setInt(2, year);
			}
			
			// execute query
			myRs = myStmt.executeQuery();

			// process result set
			while (myRs.next()) {
				// retrieve data from result set row
				int courseId = myRs.getInt("course_id");
				String courseName = myRs.getString("name");
				Float score = myRs.getFloat("score");
				if (myRs.wasNull()) {
					score = null;
				}
				// create new score object
				Score tempScore = new Score(-1, courseId, null, courseName, score);

				// add it to the list of scores
				scores.add(tempScore);
			}

			return scores;
		} finally {
			// close JDBC objects
			close(myConn, myStmt, myRs);
		}
	}
	
	public List<Integer> getYearsByStudentId(int studentId) throws Exception {
		List<Integer> years = new ArrayList<>();

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			// get a connection
			myConn = dataSource.getConnection();

			// create sql statement
			String sql = "select distinct courses.year from score_board "
					+ "join courses on score_board.course_id = courses.id "
					+ "where score_board.student_id = ? ";

			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setInt(1, studentId);
			
			// execute query
			myRs = myStmt.executeQuery();

			// process result set
			while (myRs.next()) {
				// retrieve data from result set row
				Integer year = myRs.getInt("year");

				// add it to the list of years
				years.add(year);
			}

			return years;
		} finally {
			// close JDBC objects
			close(myConn, myStmt, myRs);
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
