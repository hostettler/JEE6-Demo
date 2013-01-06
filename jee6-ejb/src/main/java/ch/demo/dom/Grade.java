package ch.demo.dom;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Represents a grade for a given discipline.
 * 
 * @author hostettler
 */
@Entity
@Table(name = "GRADES")
public class Grade implements Serializable {

    /** The serial-id. */
    private static final long serialVersionUID = -1369317473509616839L;

    /** The unique id. */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /** The discipline of this grade. */
    @Column(name = "DISCIPLINE", nullable = false)
    private Discipline discipline;

    /** The actual grade. */
    @Column(name = "GRADE", nullable = true)
    private Integer grade;

    /** The student that has obtained this grade. */
    @ManyToOne
    @JoinColumn(name = "STUDENT_ID", nullable = true)
    private Student student;

    /**
     * Constructor that builds a grade for a given discipline.
     * 
     * @param discipline
     *            to grade.
     */
    public Grade(final Discipline discipline) {
        this.discipline = discipline;
    }

    /**
     * Constructor that builds a grade for a given discipline.
     * 
     * @param discipline
     *            to grade.
     * @param grade
     *            the actual grade.
     */
    public Grade(final Discipline discipline, final Integer grade) {
        this.discipline = discipline;
        this.grade = grade;
    }

    /**
     * Constructor that builds a grade for a given discipline.
     * 
     */
    public Grade() {
    }

    /**
     * @return the discipline
     */
    public final Discipline getDiscipline() {
        return discipline;
    }

    /**
     * @param discipline
     *            to set
     */
    public final void setDiscipline(final Discipline pDiscipline) {
        this.discipline = pDiscipline;
    }

    /**
     * @return the grade
     */
    public final Integer getGrade() {
        return grade;
    }

    /**
     * @param grade
     *            the grade to set
     */
    public final void setGrade(final Integer pGrade) {
        this.grade = pGrade;
    }

    /**
     * @return the id
     */
    public final long getId() {
        return id;
    }

    /**
     * @return the student
     */
    public Student getStudent() {
        return student;
    }

    /**
     * @param student the student to set
     */
    public void setStudent(final Student pStudent) {
        this.student = pStudent;
    }

}
