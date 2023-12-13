package org.dgp.hw;

import org.springframework.context.ApplicationContext;
import org.dgp.hw.service.TestRunnerService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {
    public static void main(String[] args) {

        //Прописать бины в spring-context.xml и создать контекст на его основе
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        var testRunnerService = context.getBean(TestRunnerService.class);
        testRunnerService.run();

    }
}