package edu.pezzati.jwt;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

public class WeldTest extends BlockJUnit4ClassRunner {

    private WeldContainer weld;

    public WeldTest(Class<?> klass) throws InitializationError {
	super(klass);
	weld = new Weld().initialize();
    }

    @Override
    protected Object createTest() throws Exception {
	final Class<?> test = getTestClass().getJavaClass();
	return weld.select(test).get();
    }
}
