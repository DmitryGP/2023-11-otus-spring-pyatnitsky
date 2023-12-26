package org.dgp.hw.service;

import org.dgp.hw.domain.Student;
import org.springframework.stereotype.Service;

@Service

public class StudentServiceImpl implements StudentService {

    private final IOService ioService;

    private final String firstNamePrompt;

    private final String lastNamePrompt;

    public StudentServiceImpl(IOService ioService, LocalizedStringService localizedStringService) {
        this.ioService = ioService;

        firstNamePrompt = localizedStringService.getString("student.firstname");
        lastNamePrompt = localizedStringService.getString("student.lastname");
    }

    @Override
    public Student determineCurrentStudent() {
        var firstName = ioService.readStringWithPrompt(firstNamePrompt);
        var lastName = ioService.readStringWithPrompt(lastNamePrompt);
        return new Student(firstName, lastName);
    }
}
