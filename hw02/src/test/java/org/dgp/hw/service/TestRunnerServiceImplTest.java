package org.dgp.hw.service;

import org.dgp.hw.domain.Student;
import org.dgp.hw.domain.TestResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.InOrder;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;

public class TestRunnerServiceImplTest {
    private TestService testService;

    private StudentService studentService;

    private ResultService resultService;

    private TestRunnerServiceImpl testRunnerService;

    private InOrder inOrderForTestService;
    private InOrder inOrderForStudentService;
    private InOrder inOrderForResultService;

    private final Student student = new Student("A", "B");
    private final TestResult testResult = new TestResult(student);

    @BeforeEach
    public void setup(){
        testService = Mockito.mock(TestService.class);
        studentService = Mockito.mock(StudentService.class);
        resultService = Mockito.mock(ResultService.class);

        Mockito.when(studentService.determineCurrentStudent()).thenReturn(student);
        Mockito.when(testService.executeTestFor(student)).thenReturn(testResult);

        inOrderForTestService = inOrder(testService);
        inOrderForStudentService = inOrder(studentService);
        inOrderForResultService = inOrder(resultService);

        testRunnerService = new TestRunnerServiceImpl(testService, studentService, resultService);
    }

    @Test
    public void InvocationSequenceOfMethodsTest(){
        testRunnerService.run();

        inOrderForStudentService.verify(studentService, times(1)).determineCurrentStudent();
        inOrderForTestService.verify(testService, times(1)).executeTestFor(student);
        inOrderForResultService.verify(resultService, times(1)).showResult(testResult);
    }

}
