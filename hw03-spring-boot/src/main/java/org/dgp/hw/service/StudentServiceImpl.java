package org.dgp.hw.service;

import lombok.RequiredArgsConstructor;
import org.dgp.hw.domain.Student;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final LocalizedIOService ioService;

    @Override
    public Student determineCurrentStudent() {
        var firstName = ioService.readStringWithPromptLocalized("student.firstname");
        var lastName = ioService.readStringWithPromptLocalized("student.lastname");

        return new Student(firstName, lastName);
    }
}
