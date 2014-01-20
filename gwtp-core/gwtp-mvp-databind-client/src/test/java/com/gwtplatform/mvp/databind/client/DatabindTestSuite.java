package com.gwtplatform.mvp.databind.client;

import com.gwtplatform.mvp.databind.client.validation.EmailValidatorTest;
import com.gwtplatform.mvp.databind.client.validation.RequiredValidatorTest;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Danilo Reinert
 */
public class DatabindTestSuite {

    public static Test suite() {
        TestSuite suite = new TestSuite("Databind tests");

        suite.addTestSuite(RequiredValidatorTest.class);
        suite.addTestSuite(EmailValidatorTest.class);

        suite.addTestSuite(DatabindViewEngineTest.class);
        suite.addTestSuite(BindingTest.class);

        return suite;
    }

    private DatabindTestSuite() {
    }
}
