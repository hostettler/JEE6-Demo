package ch.demo.business.service;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import javax.naming.NamingException;

import org.glassfish.api.ActionReport;
import org.glassfish.api.admin.ParameterMap;
import org.glassfish.embeddable.CommandRunner;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.impl.base.exporter.zip.ZipExporterImpl;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.demo.dom.Discipline;
import ch.demo.dom.Grade;
import ch.demo.dom.PhoneNumber;
import ch.demo.dom.Student;

import com.sun.appserv.security.ProgrammaticLogin;

/**
 * Test the student service mock implementation.
 * 
 * @author hostettler
 */

public class JPAStudentServiceImplTest {

	private static final String TEST_RSC = "src/test/resources";

	@BeforeClass
	public static void start() throws Exception {

		JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "test.jar");
		archive.addAsManifestResource(new File(TEST_RSC, "META-INF/persistence.xml"));
		archive.addAsManifestResource(new File(TEST_RSC, "META-INF/beans.xml"));
		archive.addPackage(java.lang.Package
				.getPackage("ch.demo.business.service"));

		Properties p = new Properties();
		p.put("org.glassfish.ejb.embedded.glassfish.installation.root",
				"./src/test/resources/glassfish");
		p.put(EJBContainer.MODULES, new File[] { new File(archive.getName()) });

		new ZipExporterImpl(archive)
				.exportTo(new File(archive.getName()), true);

		container = EJBContainer.createEJBContainer(p);


	}

	@Before
	public void setUp() throws Exception {
		container.getContext().bind("inject", this);
	}

	private static EJBContainer container;

	@Inject
	StudentService service;

	@Test
	public void testAdd() throws NamingException {

		StudentService service = (StudentService) container
				.getContext()
				.lookup("java:global/test/StudentServiceJPAImpl!ch.demo.business.service.StudentService");
		int n = service.getAll().size();

		Student s = new Student("Hostettler", "Steve", new Date());
		s.setPhoneNumber(new PhoneNumber(0, 0, 0));
		for (Discipline d : Discipline.values()) {
			Grade g = new Grade(d, 10);
			s.getGrades().add(g);
		}

		service.add(s);

		Assert.assertEquals(n + 1, service.getAll().size());
		Assert.assertEquals("Hostettler", service.getStudentByKey(s.getKey())
				.getLastName());
	}

	@AfterClass
	public static void stop() {
		container.close();
	}
}
