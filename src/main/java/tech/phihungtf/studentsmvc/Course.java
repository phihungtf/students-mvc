package tech.phihungtf.studentsmvc;

public class Course {
    private int id;
    private String name;
    private String lecturer;
    private int year;
    private String notes;
        
	public Course(int id, String name, String lecturer, int year, String notes) {
		super();
		this.id = id;
		this.name = name;
		this.lecturer = lecturer;
		this.year = year;
		this.notes = notes;
	}
	
	public Course(String name, String lecturer, int year, String notes) {
		super();
		this.name = name;
		this.lecturer = lecturer;
		this.year = year;
		this.notes = notes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLecturer() {
		return lecturer;
	}

	public void setLecturer(String lecturer) {
		this.lecturer = lecturer;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + ", lecturer=" + lecturer + ", year=" + year + ", notes=" + notes
				+ "]";
	}

	public String toJSON() {
		return "{'id':" + id + ",'name':'" + name + "','lecturer':'" + lecturer + "','year':" + year + ",'notes':'" + notes + "'}";
	}
}
