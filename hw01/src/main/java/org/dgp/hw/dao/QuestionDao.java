package org.dgp.hw.dao;

import org.dgp.hw.domain.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> findAll();
}
