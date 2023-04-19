package tech.phihungtf.studentsmvc;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.annotation.Resource;
import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

/**
 * Servlet implementation class CourseDetailControllerServlet
 */
public class CourseDetailControllerServlet extends HttpServlet {
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
			listScores(request, response);
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
					addStudent(request, response);
					break;
				case "update":
					updateStudent(request, response);
					break;
				case "delete":
					deleteStudent(request, response);
					break;
				default:
					listScores(request, response);
					break;
			}
		} catch (Exception exc) {
			throw new ServletException(exc);
		}
	}

	private void listScores(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String courseId = request.getPathInfo().replace("/", "");

		String sortStr = request.getParameter("sort");

		if (sortStr == null || (!sortStr.equals("name") && !sortStr.equals("score"))) {
			sortStr = "name";
		}
		sortStr = sortStr.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		
		Course course = studentDBUtil.getCourse(Integer.parseInt(courseId));
		List<Score> scores = studentDBUtil.getScoresByCourseId(Integer.parseInt(courseId), sortStr);
		List<Student> students = studentDBUtil.getStudentsNotInCourse(Integer.parseInt(courseId));

		request.setAttribute("COURSE", course);
		request.setAttribute("SCORES", scores);
		request.setAttribute("STUDENTS", students);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/course-detail.jsp");
        dispatcher.forward(request, response);
	}
	
	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String courseId = request.getPathInfo().replace("/", "");

		// read student info from form data
		String id = request.getParameter("id");	
		String score = request.getParameter("score");
		
		// check if the fields are empty
		if (id == null || id.trim().length() == 0) {
			listScores(request, response);
			return;
		}
		
		// create a new score object
		Score theScore = new Score(
			Integer.parseInt(id),
			Integer.parseInt(courseId),
			null,
			null,
			(score == null || score.trim().length() == 0) ? null : Float.parseFloat(score)
		);

		// add the student to the database
		studentDBUtil.addStudentInCourse(theScore);

		// send back to main page (the student list)
		listScores(request, response);
	}

	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String courseId = request.getPathInfo().replace("/", "");

		// read student info from form data
		String id = request.getParameter("id");	
		String score = request.getParameter("score");
		
		// check if the fields are empty
		if (id == null || id.trim().length() == 0) {
			listScores(request, response);
			return;
		}
		
		// create a new score object
		Score theScore = new Score(
			Integer.parseInt(id),
			Integer.parseInt(courseId),
			null,
			null,
			(score == null || score.trim().length() == 0) ? null : Float.parseFloat(score)
		);

		// update the student to the database
		studentDBUtil.updateStudentInCourse(theScore);

		// send back to main page (the student list)
		listScores(request, response);
	}

	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String courseId = request.getPathInfo().replace("/", "");

		// read student id from form data
		String theStudentId = request.getParameter("studentId");

		// delete student from database
		studentDBUtil.deleteStudentInCourse(courseId, theStudentId);

		// send back to main page (the student list)
		listScores(request, response);
	}
}
