package org.dgp.hw.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.dgp.hw.config.TestFileNameProvider;
import org.dgp.hw.dao.dto.QuestionDto;
import org.dgp.hw.domain.Question;
import org.dgp.hw.exceptions.QuestionReadException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {

        String questionCsvFileName = fileNameProvider.getTestFileName();
        List dtoList;

        try (Reader streamReader = getStreamReaderFor(questionCsvFileName)){
            dtoList = new CsvToBeanBuilder(streamReader)
                    .withType(QuestionDto.class).withSeparator(';').withSkipLines(1).build().parse();

        } catch (Exception exc) {
            throw new QuestionReadException("Error while reading questions", exc);
        }

        List<Question> questions = new ArrayList<>();

        for (Object o : dtoList) {
            QuestionDto qDto = (QuestionDto) o;
            questions.add(qDto.toDomainObject());
        }

        return questions;
    }

    private Reader getStreamReaderFor(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        return new InputStreamReader(inputStream);
    }
}
