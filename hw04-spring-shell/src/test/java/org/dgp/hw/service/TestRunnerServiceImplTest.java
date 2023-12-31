package org.dgp.hw.service;

import org.dgp.hw.domain.Student;
import org.dgp.hw.domain.TestResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.InOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;

@SpringBootTest
public class TestRunnerServiceImplTest {
    @MockBean
    private TestService testService;

    @MockBean
    private StudentService studentService;

    @MockBean
    private ResultService resultService;

    @Autowired
    private TestRunnerServiceImpl testRunnerService;

    private InOrder inOrderForTestService;
    private InOrder inOrderForStudentService;
    private InOrder inOrderForResultService;

    private final Student student = new Student("A", "B");
    private final TestResult testResult = new TestResult(student);

    @BeforeEach
    public void setup(){

        Mockito.when(studentService.determineCurrentStudent()).thenReturn(student);
        Mockito.when(testService.executeTestFor(student)).thenReturn(testResult);

        inOrderForTestService = inOrder(testService);
        inOrderForStudentService = inOrder(studentService);
        inOrderForResultService = inOrder(resultService);
    }

    @Test
    public void InvocationSequenceOfMethodsTest(){
        testRunnerService.run();

        inOrderForStudentService.verify(studentService, times(1)).determineCurrentStudent();
        inOrderForTestService.verify(testService, times(1)).executeTestFor(student);
        inOrderForResultService.verify(resultService, times(1)).showResult(testResult);
    }

    @Configuration
    @ComponentScan({"org.dgp.hw.service", "org.dgp.hw.config"})
    public static class TestConfiguration {

    }

}
