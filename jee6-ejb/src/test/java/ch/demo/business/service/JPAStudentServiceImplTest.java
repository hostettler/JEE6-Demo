package ch.demo.business.service;

import java.io.File;
import java.util.Date;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;

import ch.demo.dom.Discipline;
import ch.demo.dom.Grade;
import ch.demo.dom.PhoneNumber;
import ch.demo.dom.Student;
import ch.demo.dom.jpa.JPAPhoneNumberConverter;
import ch.demo.helpers.LoggerProducer;
import ch.demo.test.utils.AbstractEJBTest;

import com.sun.appserv.security.ProgrammaticLogin;

/**
 * Test the student service mock implementation.
 * 
 * @author hostettler
 */

public class JPAStudentServiceImplTest extends
		AbstractEJBTest<StudentServiceJPAImpl, StudentService> {

	private static final String TEST_RSC = "src/test/resources";

	public JPAStudentServiceImplTest() {
		super(StudentServiceJPAImpl.class, StudentService.class);
	}

	@Test
	public void testAdd() throws Exception {

		ProgrammaticLogin pgLogin = new ProgrammaticLogin();
		pgLogin.login("admin", "user1", "jee6-tutorial-file-realm", true);

		int n = getEJBUnderTest().getAll().size();

		Student s = new Student("Hostettler", "Steve", new Date());
		s.setPhoneNumber(new PhoneNumber(0, 0, 0));
		for (Discipline d : Discipline.values()) {
			Grade g = new Grade(d, 10);
			s.getGrades().add(g);
		}

		getEJBUnderTest().add(s);

		Assert.assertEquals(n + 1, getEJBUnderTest().getAll().size());
		Assert.assertEquals("Hostettler",
				getEJBUnderTest().getStudentByKey(s.getKey()).getLastName());
	}

	@Override
	protected JavaArchive createArchive() {
		JavaArchive archive = ShrinkWrap.create(JavaArchive.class,
				"target/test.jar");
		archive.addAsManifestResource(new File(TEST_RSC,
				"META-INF/persistence.xml"));
		archive.addAsManifestResource(new File(TEST_RSC, "META-INF/beans.xml"));
		archive.addAsManifestResource(new File(TEST_RSC,
				"META-INF/glassfish-ejb-jar.xml"));
		archive.addPackage(StudentService.class.getPackage());
		archive.addPackage(Student.class.getPackage());
		archive.addPackage(JPAPhoneNumberConverter.class.getPackage());
		archive.addPackage(LoggerProducer.class.getPackage());

		return archive;
	}
}
