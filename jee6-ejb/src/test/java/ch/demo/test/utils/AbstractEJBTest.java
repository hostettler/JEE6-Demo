/**
 * 
 */
package ch.demo.test.utils;

import java.io.File;
import java.util.Properties;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;

import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.impl.base.exporter.zip.ZipExporterImpl;

/**
 * @author hostettler
 * 
 */

public abstract class AbstractEJBTest<T, V> {

	private static final String TARGET_TMP_DERBY_LOG = "./target/tmp/derby.log";
	private static final String TARGET_TMP = "./target/tmp";
	private static final String DERBY_STREAM_ERROR_FILE = "derby.stream.error.file";
	private static final String GLASSFISH_EMBEDDED_TMPDIR = "glassfish.embedded.tmpdir";
	private static final String SRC_TEST_RESOURCES_GLASSFISH = "./src/test/resources/glassfish";
	private static final String ORG_GLASSFISH_EJB_EMBEDDED_GLASSFISH_INSTALLATION_ROOT = "org.glassfish.ejb.embedded.glassfish.installation.root";

	private Class<T> impl;
	private Class<V> facade;

	@SuppressWarnings("unchecked")
	protected V getEJBUnderTest() throws NamingException {
		return (V) container.getContext()
				.lookup("java:global/test/" + impl.getSimpleName() + "!"
						+ facade.getCanonicalName());
	}

	private EJBContainer container;

	public AbstractEJBTest(Class<T> impl, Class<V> facade) {

		this.impl = impl;
		this.facade = facade;
		JavaArchive archive = createArchive();
		System.setProperty(GLASSFISH_EMBEDDED_TMPDIR, TARGET_TMP);
		System.setProperty(DERBY_STREAM_ERROR_FILE, TARGET_TMP_DERBY_LOG);
		Properties p = new Properties();
		p.put(ORG_GLASSFISH_EJB_EMBEDDED_GLASSFISH_INSTALLATION_ROOT,
				SRC_TEST_RESOURCES_GLASSFISH);
		p.put(EJBContainer.MODULES, new File[] { new File(archive.getName()) });

		new ZipExporterImpl(archive)
				.exportTo(new File(archive.getName()), true);
		container = EJBContainer.createEJBContainer(p);
	}
	
	protected abstract JavaArchive createArchive();
}
