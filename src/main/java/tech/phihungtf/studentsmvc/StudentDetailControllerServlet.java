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
 * Servlet implementation class ScoreControllerServlet
 */
public class StudentDetailControllerServlet extends HttpServlet {
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

	private void listScores(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String studentId = request.getPathInfo().replace("/", "");

		String yearStr = request.getParameter("year");
		int year = -1;
		if (yearStr != null && !yearStr.isEmpty() && !yearStr.equals("all")) {
			year = Integer.parseInt(yearStr);
		}		
		
		Student student = studentDBUtil.getStudent(Integer.parseInt(studentId));
		List<Score> scores = studentDBUtil.getScoresByStudentId(Integer.parseInt(studentId), year);
		List<Integer> years = studentDBUtil.getYearsByStudentId(Integer.parseInt(studentId));

		request.setAttribute("STUDENT", student);
		request.setAttribute("SCORES", scores);
		request.setAttribute("YEARS", years);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/student-detail.jsp");
        dispatcher.forward(request, response);
	}

}
