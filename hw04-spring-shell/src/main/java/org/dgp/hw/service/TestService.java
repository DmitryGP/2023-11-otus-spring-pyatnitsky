package org.dgp.hw.service;

import org.dgp.hw.domain.Student;
import org.dgp.hw.domain.TestResult;

public interface TestService {
    TestResult executeTestFor(Student student);
}
