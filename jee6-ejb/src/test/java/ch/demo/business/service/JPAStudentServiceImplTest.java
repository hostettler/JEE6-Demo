package ch.demo.business.service;

import java.util.Date;
import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.persistence.NoResultException;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import ch.demo.dom.Discipline;
import ch.demo.dom.Grade;
import ch.demo.dom.PhoneNumber;
import ch.demo.dom.Student;

/**
 * Test the student service mock implementation.
 * 
 * @author hostettler
 */
@Ignore
public class JPAStudentServiceImplTest {

	@BeforeClass
	public static void start() {
		Properties p = new Properties();
		p.put("jdbc/StudentsDS__pm", "new://Resource?type=DataSource");
		p.put("jdbc/StudentsDS__pm.JdbcUrl",
				"org.apache.derby.jdbc.EmbeddedDriver");
		p.put("jdbc/StudentsDS__pm.JdbcDriver",
				"dbc:derby:memory:StudentsDB;create=true");
		container = EJBContainer.createEJBContainer(p);
	}

	@Before
	public void setUp() throws Exception {
		container.getContext().bind("inject", this);
	}

	/** Service retrieved by the Weld container. */
	@EJB
	private StudentService mService;

	private static EJBContainer container;

	/**
	 * Test the addition a new student to the repository.
	 */
	@Test
	public void testAdd() {

		int n = mService.getAll().size();

		Student s = new Student("Hostettler", "Steve", new Date());
		s.setPhoneNumber(new PhoneNumber(0, 0, 0));
		for (Discipline d : Discipline.values()) {
			Grade g = new Grade(d, 10);
			s.getGrades().add(g);
		}

		mService.add(s);

		Assert.assertEquals(n + 1, mService.getAll().size());
		Assert.assertEquals("Hostettler", mService.getStudentByKey(s.getKey())
				.getLastName());
	}

	/**
	 * Tests the retrieval of all the students.
	 */
	// @Test
	public void testAll() {
		for (Student s : mService.getAll()) {
			System.out.println(s);
		}
	}

	/**
	 * Tests the distribution of the grades among students.
	 */
	// @Test
	public void testDistribution() {
		Integer[] distribution = mService.getDistribution(5);
		Assert.assertEquals(mService.getNbStudent(), distribution[0].intValue());
		Assert.assertEquals(0, distribution[1].intValue());
		Assert.assertEquals(0, distribution[2].intValue());
		Assert.assertEquals(0, distribution[3].intValue());
		Assert.assertEquals(0, distribution[4].intValue());
	}

	/**
	 * Test the behavior when a non-existing student is requested.
	 */
	// @Test
	public void testNonExistingStudent() {
		Student s = new Student("MyLastName", "MyFirstName", new Date());
		try {
			mService.getStudentByKey(s.getKey());
			Assert.fail("There should be no student with that last name in the DB");
		} catch (NoResultException ex) {
			System.out.println(ex.getMessage());
		}
	}

	/**
	 * Test the behavior when a non-existing student is requested.
	 */
	// @Test
	public void testExistingStudent() {
		testAdd();
		Assert.assertNotNull(mService.getStudentByLastName("Hostettler"));
	}

	@AfterClass
	public static void stop() {
		container.close();
	}
}
