package br.ufba.jnose.test;

import br.ufba.jnose.core.Config;
import br.ufba.jnose.core.JNoseCore;
import br.ufba.jnose.dto.TestClass;
import org.junit.Test;

import java.util.List;

import static br.ufba.jnose.test.JNose.getInstanceJNoseCore;


class JNose {

    private static JNoseCore jNoseCore;

    public static JNoseCore getInstanceJNoseCore() {

        Config conf = new Config() {
            public Boolean assertionRoulette() {
                return TestSmellDetectorConfig.assertionRoulette;
            }

            public Boolean conditionalTestLogic() {
                return TestSmellDetectorConfig.conditionalTestLogic;
            }

            public Boolean constructorInitialization() {
                return TestSmellDetectorConfig.constructorInitialization;
            }

            public Boolean defaultTest() {
                return TestSmellDetectorConfig.defaultTest;
            }

            public Boolean dependentTest() {
                return TestSmellDetectorConfig.dependentTest;
            }

            public Boolean duplicateAssert() {
                return TestSmellDetectorConfig.duplicateAssert;
            }

            public Boolean eagerTest() {
                return TestSmellDetectorConfig.eagerTest;
            }

            public Boolean emptyTest() {
                return TestSmellDetectorConfig.emptyTest;
            }

            public Boolean exceptionCatchingThrowing() {
                return TestSmellDetectorConfig.exceptionCatchingThrowing;
            }

            public Boolean generalFixture() {
                return TestSmellDetectorConfig.generalFixture;
            }

            public Boolean mysteryGuest() {
                return TestSmellDetectorConfig.mysteryGuest;
            }

            public Boolean printStatement() {
                return TestSmellDetectorConfig.printStatement;
            }

            public Boolean redundantAssertion() {
                return TestSmellDetectorConfig.redundantAssertion;
            }

            public Boolean sensitiveEquality() {
                return TestSmellDetectorConfig.sensitiveEquality;
            }

            public Boolean verboseTest() {
                return TestSmellDetectorConfig.verboseTest;
            }

            public Boolean sleepyTest() {
                return TestSmellDetectorConfig.sleepyTest;
            }

            public Boolean lazyTest() {
                return TestSmellDetectorConfig.lazyTest;
            }

            public Boolean unknownTest() {
                return TestSmellDetectorConfig.unknownTest;
            }

            public Boolean ignoredTest() {
                return TestSmellDetectorConfig.ignoredTest;
            }

            public Boolean resourceOptimism() {
                return TestSmellDetectorConfig.resourceOptimism;
            }

            public Boolean magicNumberTest() {
                return TestSmellDetectorConfig.magicNumberTest;
            }

            @Override
            public Integer maxStatements() {
                return 30;
            }
        };

        if (jNoseCore == null) {
            jNoseCore = new JNoseCore(conf, Runtime.getRuntime().availableProcessors() * 2);
        }

        return jNoseCore;
    }

    static {
        jNoseCore = getInstanceJNoseCore();
    }

    public JNose() {
        jNoseCore = getInstanceJNoseCore();
    }
}


public class JNoseCoreTest {

    @Test
    public void jNoseCoreTest() {
        String pathProjeto = System.getProperty("user.dir");
        try {
            List<TestClass> listFileTests = getInstanceJNoseCore().getFilesTest(pathProjeto);
            System.out.println(listFileTests.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
