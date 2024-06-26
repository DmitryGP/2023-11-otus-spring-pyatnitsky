package org.dgp.hw.datamigration.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

@Slf4j
public class ImportBooksJobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.atInfo()
                .setMessage("Job {} started.")
                .addArgument(jobExecution.getJobInstance().getJobName())
                .log();
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.atInfo()
                .setMessage("Job {} finished.")
                .addArgument(jobExecution.getJobInstance().getJobName())
                .log();
    }
}
