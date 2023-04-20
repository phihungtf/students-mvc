package tech.phihungtf.studentsmvc;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.annotation.Resource;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.sql.DataSource;

/**
 * Servlet implementation class CourseControllerServlet
 */
public class CourseControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	StudentDBUtil studentDBUtil;

	@Resource(name="jdbc/student-management")
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
			listCourses(request, response);
		} catch (Exception e) {
			throw new ServletException(e);
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
					addCourse(request, response);
					break;
				case "update":
					updateCourse(request, response);
					break;
				case "delete":
					deleteCourse(request, response);
					break;
				default:
					listCourses(request, response);
					break;
			}
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
	}

	private void listCourses(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Course> courses;

		String nameStr = request.getParameter("nameStr");
		String sortStr = request.getParameter("sort");

		// prevent XSS
		if (nameStr == null) {
			nameStr = "";
		}

		if (sortStr == null || (!sortStr.equals("name") && !sortStr.equals("year"))) {
			sortStr = "name";
		}
		nameStr = nameStr.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		sortStr = sortStr.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		
		courses = studentDBUtil.getCourses(nameStr, sortStr);

        request.setAttribute("COURSE_LIST", courses);

        request.setAttribute("COURSE_NAME", nameStr);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/course.jsp");
		dispatcher.forward(request, response);
	}

	private void addCourse(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read student info from form data
		String name = request.getParameter("name");	
		String lecturer = request.getParameter("lecturer");
		String year = request.getParameter("year");		
		String notes = request.getParameter("notes");	
		
		// check if the fields are empty
		if (name == null || lecturer == null || year == null) {
			listCourses(request, response);
			return;
		}
		if (name.trim().length() == 0 || lecturer.trim().length() == 0 || year.trim().length() == 0) {
			listCourses(request, response);
			return;
		}
		
		// create a new course object
		Course theCourse = new Course(
			name,
			lecturer,
			Integer.valueOf(year),
			notes
		);
		// add the course to the database
		studentDBUtil.addCourse(theCourse);

		// send back to main page (the course list)
		listCourses(request, response);
	}

	private void updateCourse(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read course info from form data
		String id = request.getParameter("id");	
		String name = request.getParameter("name");	
		String lecturer = request.getParameter("lecturer");
		String year = request.getParameter("year");		
		String notes = request.getParameter("notes");	
		
		// check if the fields are empty
		if (id == null || name == null || lecturer == null || year == null) {
			listCourses(request, response);
			return;
		}
		if (id.trim().length() == 0 || name.trim().length() == 0 || lecturer.trim().length() == 0 || year.trim().length() == 0) {
			listCourses(request, response);
			return;
		}

		// create a new course object
		Course theCourse = new Course(
			Integer.parseInt(id),
			name,
			lecturer,
			Integer.valueOf(year),
			notes
		);
		
		// add the course to the database
		studentDBUtil.updateCourse(theCourse);

		// send back to main page (the course list)
		listCourses(request, response);
		
	}

	private void deleteCourse(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read course id from form data
		String theCourseId = request.getParameter("courseId");

		try {
			// delete course from database
			studentDBUtil.deleteCourse(theCourseId);
		} catch (SQLIntegrityConstraintViolationException e) {
			request.setAttribute("ERROR", "The course has students, please remove all students of the course before deleting.");		
		}
		// send back to main page (the course list)
		listCourses(request, response);
	}
}
