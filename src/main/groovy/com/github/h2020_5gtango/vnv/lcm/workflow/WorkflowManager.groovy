package com.github.h2020_5gtango.vnv.lcm.workflow

import com.github.h2020_5gtango.vnv.lcm.model.NetworkService
import com.github.h2020_5gtango.vnv.lcm.model.NetworkServiceInstance
import com.github.h2020_5gtango.vnv.lcm.model.TestSuite
import com.github.h2020_5gtango.vnv.lcm.model.TestPlan
import com.github.h2020_5gtango.vnv.lcm.model.TestSuiteResult
import com.github.h2020_5gtango.vnv.lcm.restclient.TestExecutionEngine
import com.github.h2020_5gtango.vnv.lcm.restclient.TestPlatformManager
import com.github.h2020_5gtango.vnv.lcm.restclient.TestResultRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class WorkflowManager {

    @Autowired
    TestResultRepository testResultRepository

    @Autowired
    TestPlatformManager testPlatformManager

    @Autowired
    TestExecutionEngine testExecutionEngine

    synchronized void execute(NetworkService networkService, List<TestSuite> testSuites) {
        def testPlan = createTestPlan(networkService, testSuites)
        testPlan = deployNsForTest(testPlan)
        testPlan = executeTests(testPlan)
        destroyNsAfterTest(testPlan)
    }

    TestPlan createTestPlan(NetworkService networkService, List<TestSuite> testSuites) {
        def testPlan = new TestPlan(
                networkServiceInstances: [new NetworkServiceInstance(networkServiceId: networkService.networkServiceId)],
                testSuiteResults: testSuites.collect {testSuite->
                    new TestSuiteResult(
                            testSuiteId: testSuite.testSuiteId,
                    )
                },
                status: 'CREATED',
        )
        testResultRepository.createTestPlan(testPlan)
    }

    TestPlan deployNsForTest(TestPlan testPlan) {
        testPlan = testPlatformManager.deployNsForTest(testPlan)
        testResultRepository.updatePlan(testPlan)
    }

    TestPlan executeTests(TestPlan testPlan) {
        testPlan = testExecutionEngine.executeTests(testPlan)
        testResultRepository.updatePlan(testPlan)
    }

    TestPlan destroyNsAfterTest(TestPlan testPlan) {
        testPlan = testPlatformManager.destroyNsAfterTest(testPlan)
        testResultRepository.updatePlan(testPlan)
    }

}
