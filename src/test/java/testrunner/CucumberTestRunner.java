//import io.cucumber.core.cli.Main;
//import io.qameta.allure.Allure;
//import io.qameta.allure.AllureLifecycle;
//import io.qameta.allure.model.Status;
//import io.qameta.allure.model.StatusDetails;
//import io.qameta.allure.util.ResultsUtils;
//import org.junit.jupiter.api.Test;
//
//import java.util.Optional;
//
//public class CucumberTestRunner {
//
//    @Test
//    public void runCucumberTests() {
//        String[] argv = {
//            "--glue", "com.example.steps",
//            "--plugin", "io.qameta.allure.cucumber5jvm.AllureCucumber5Jvm",
//            "src/test/resources/features"
//        };
//
//        int exitStatus = Main.run(argv);
//
//        if (exitStatus != 0) {
//            AllureLifecycle allureLifecycle = Allure.getLifecycle();
//            StatusDetails details = ResultsUtils.getStatusDetails(
//                new RuntimeException("Cucumber execution failed")
//            );
//            allureLifecycle.getCurrentTestCase().ifPresent(testResult -> {
//                testResult.setStatus(Status.FAILED);
//                Optional<StatusDetails> existingDetails = testResult.getStatusDetails();
//                if (existingDetails.isPresent()) {
//                    existingDetails.get().setDetails(details.getDetails());
//                } else {
//                    testResult.setStatusDetails(details);
//                }
//            });
//        }
//
//        // Write Allure report
//        AllureLifecycle lifecycle = Allure.getLifecycle();
//        lifecycle.writeTestContainers();
//        lifecycle.writeTestResults();
//        lifecycle.cleanUp();
//    }
//}
