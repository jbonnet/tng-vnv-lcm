package com.github.h2020_5gtango.vnv.lcm.restmock

import com.github.h2020_5gtango.vnv.lcm.model.NetworkService
import com.github.h2020_5gtango.vnv.lcm.model.PackageMetadata
import com.github.h2020_5gtango.vnv.lcm.model.TestSuite
import com.github.h2020_5gtango.vnv.lcm.scheduler.SchedulerTest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class TestCatelogogueMock {


    @GetMapping('/mock/catalogue/packages/{packageId:.+}')
    PackageMetadata loadPackageMetadata(@PathVariable('packageId') String packageId) {
        if (packageId == SchedulerTest.MULTIPLE_TEST_PLANS_PACKAGE_ID) {
            new PackageMetadata(
                    networkServices: [
                            new NetworkService(networkServiceId: 'multiple_ns_1', name: 'multiple_ns_1', vendor: 'vendor', version: 'version'),
                            new NetworkService(networkServiceId: 'multiple_ns_2', name: 'multiple_ns_2', vendor: 'vendor', version: 'version'),
                    ],
                    testSuites: [
                            new TestSuite(testSuiteId: 'multiple_test_1', name: 'multiple_test_1', version: 'version'),
                            new TestSuite(testSuiteId: 'multiple_test_2', name: 'multiple_test_2', version: 'version'),
                    ],
            )
        } else {
            new PackageMetadata(
                    networkServices: [new NetworkService(networkServiceId: 'networkServiceInstanceId', name: 'name', vendor: 'vendor', version: 'version')],
                    testSuites: [new TestSuite(testSuiteId: 'testSuiteId', name: 'name', version: 'version')],
            )
        }
    }

    @GetMapping('/mock/catalogue/network-services/{networkServiceId}/matched-test-suites')
    List<TestSuite> findTestsApplicableToNs(@PathVariable('networkServiceId') String networkServiceId) {
        if (networkServiceId.startsWith('multiple_')) {
            [
                    new TestSuite(testSuiteId: 'multiple_test_3', name: 'multiple_test_3', version: 'version'),
                    new TestSuite(testSuiteId: 'multiple_test_4', name: 'multiple_test_4', version: 'version'),
            ]
        } else {
            [new TestSuite(testSuiteId: 'testSuiteId', name: 'name', version: 'version')]
        }
    }

    @GetMapping('/mock/catalogue/test-suites/{testSuiteId}/matched-network-services')
    List<NetworkService> findNsApplicableToTest(@PathVariable('testSuiteId') String testSuiteId) {
        if (testSuiteId.startsWith('multiple_')) {
            [
                    new NetworkService(networkServiceId: 'multiple_ns_3', name: 'multiple_ns_3', vendor: 'vendor', version: 'version'),
                    new NetworkService(networkServiceId: 'multiple_ns_4', name: 'multiple_ns_4', vendor: 'vendor', version: 'version'),
            ]
        } else {
            [new NetworkService(networkServiceId: 'networkServiceInstanceId', name: 'name', vendor: 'vendor', version: 'version')]
        }
    }

}
