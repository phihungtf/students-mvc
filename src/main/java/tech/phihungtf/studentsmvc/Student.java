package tech.phihungtf.studentsmvc;

import java.util.Date;

public class Student {
    private int id;
    private String name;
    private int grade;
    private Date birthday;
    private String notes;

    public Student(String name, int grade, Date birthday, String notes) {
        this.name = name;
        this.grade = grade;
        this.birthday = birthday;
        this.notes = notes;
    }

    public Student(int id, String name, int grade, Date birthday, String notes) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.birthday = birthday;
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

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", grade=" + grade +
                ", birthday=" + birthday +
                ", notes='" + notes + '\'' +
                '}';
    }

	public String toJSON() {
		return "{'id':" + id + ",'name':'" + name + "','grade':" + grade + ",'birthday':'" + birthday + "','notes':'" + notes + "'}";
	}
}
