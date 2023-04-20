package tech.phihungtf.studentsmvc;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.annotation.Resource;

import javax.sql.DataSource;
import java.util.List;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
/**
 * Servlet implementation class StudentControllerServlet
 */
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private StudentDBUtil studentDBUtil;

    @Resource(name = "jdbc/student-management")
    private DataSource dataSource;
    
    @Override
    public void init() throws ServletException {
        super.init();

        try {
            studentDBUtil = new StudentDBUtil(dataSource);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			listStudents(request, response);
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String action = request.getParameter("action");
			if (action == null) {
				action = "list";
			}
			switch (action) {
				case "add":
					addStudent(request, response);
					break;
				case "update":
					updateStudent(request, response);
					break;
				case "delete":
					deleteStudent(request, response);
					break;
				default:
					listStudents(request, response);
					break;
			}
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
	}
	
	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Student> students;

		String nameStr = request.getParameter("nameStr");
		String sortStr = request.getParameter("sort");

		// prevent XSS
		if (nameStr == null) {
			nameStr = "";
		}

		if (sortStr == null || (!sortStr.equals("name") && !sortStr.equals("grade"))) {
			sortStr = "name";
		}
		nameStr = nameStr.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		sortStr = sortStr.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		
		students = studentDBUtil.getStudents(nameStr, sortStr);

        request.setAttribute("STUDENT_LIST", students);

        request.setAttribute("STUDENT_NAME", nameStr);

        // send to JSP page (view)
        RequestDispatcher dispatcher = request.getRequestDispatcher("/student.jsp");
        dispatcher.forward(request, response);
    }
    
	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read student info from form data
		String name = request.getParameter("name");	
		String grade = request.getParameter("grade");
		String birthday = request.getParameter("birthday");		
		String notes = request.getParameter("notes");	
		
		// check if the fields are empty
		if (name == null || grade == null || birthday == null) {
			listStudents(request, response);
			return;
		}
		if (name.trim().length() == 0 || grade.trim().length() == 0 || birthday.trim().length() == 0) {
			listStudents(request, response);
			return;
		}
		
		// create a new student object
		Student theStudent = new Student(
			name,
			Integer.parseInt(grade),
			(new SimpleDateFormat("yyyy-MM-dd")).parse(birthday),
			notes
		);
		
		// add the student to the database
		studentDBUtil.addStudent(theStudent);

		// send back to main page (the student list)
		listStudents(request, response);
	}

	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read student info from form data
		String id = request.getParameter("id");	
		String name = request.getParameter("name");	
		String grade = request.getParameter("grade");
		String birthday = request.getParameter("birthday");		
		String notes = request.getParameter("notes");	
		
		// check if the fields are empty
		if (id == null || name == null || grade == null || birthday == null) {
			listStudents(request, response);
			return;
		}
		if (id.trim().length() == 0 || name.trim().length() == 0 || grade.trim().length() == 0 || birthday.trim().length() == 0) {
			listStudents(request, response);
			return;
		}

		// create a new student object
		Student theStudent = new Student(
			Integer.parseInt(id),
			name,
			Integer.parseInt(grade),
			(new SimpleDateFormat("yyyy-MM-dd")).parse(birthday),
			notes
		);
		
		// add the student to the database
		studentDBUtil.updateStudent(theStudent);

		// send back to main page (the student list)
		listStudents(request, response);
		
	}

	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read student id from form data
		String theStudentId = request.getParameter("studentId");

		try {
			// delete student from database
			studentDBUtil.deleteStudent(theStudentId);
		} catch (SQLIntegrityConstraintViolationException e) {
			// if student has a course, so the student can't be deleted
			request.setAttribute("ERROR", "The student has courses, please remove all courses of the student before deleting.");		
		}

		// send back to main page (the student list)
		listStudents(request, response);
	}
}
