package dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import ch.demo.dom.Student;

@XmlRootElement
public class StudentsDto {

	@XmlElement
	private List<Student> students;

	public StudentsDto(List<Student> pStudents) {
		this.students = pStudents;
	}
	
	public StudentsDto() {
	}


	public List<Student> getStudents() {
		return this.students;
	}
}
