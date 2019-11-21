package impl;

import dto.MatchdayDto;
import exceptions.MyException;
import genreic.Validator;
import model.football.Matchday;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class MatchdayDtoValidator implements Validator<MatchdayDto> {

    private Map<String, String> errors;

    public MatchdayDtoValidator() {
        this.errors = new HashMap<>();
    }

    @Override
    public boolean hasErrors() {
        return errors.size() > 0;
    }

    @Override
    public Map<String, String> getErrors() {
        return errors;
    }

    @Override
    public void validate(MatchdayDto matchdayDto) {
        if (matchdayDto.getMatches().isEmpty()) {
            errors.put("MATCHDAY - ", "EMPTY");
            throw new MyException("THERE IS NO MATCHDAY EXCEPTION - FOOTBALL API SERVICE");
        }
        if (matchdayDto.getMatches()
                .stream()
                .allMatch(x -> x.getDateOfMatch()
                        .isBefore(LocalDate.now()))) {
            errors.put("MATCHDAY - ", "FROM PAST");
            throw new MyException("MATCHDAY FROM PAST EXCEPTION - THERE ARE NO FLIGHTS");

        }
    }
}
