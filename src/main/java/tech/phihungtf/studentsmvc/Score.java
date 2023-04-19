package tech.phihungtf.studentsmvc;

public class Score {
	private int studentId;
	private int courseId;
	private String studentName;
	private String courseName;
	private Float score;
	
	public Score(int studentId, int courseId, String studentName, String courseName, Float score) {
		super();
		this.studentId = studentId;
		this.courseId = courseId;
		this.studentName = studentName;
		this.courseName = courseName;
		this.score = score;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "Score [studentId=" + studentId + ", courseId=" + courseId + ", studentName=" + studentName
				+ ", courseName=" + courseName + ", score=" + score + "]";
	}

	public String toJSON() {
		return "{'studentId':" + studentId + ",'courseId':" + courseId + ",'studentName':'" + studentName
				+ "','courseName':'" + courseName + "','score':" + score + "}";
	}
}
