package ru.job4j.dreamjob.repository;

import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CandidateStore {

    private static final CandidateStore INST = new CandidateStore();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private CandidateStore() {
        candidates.put(1, new Candidate(1, "Junior", "junior",
                LocalDateTime.of(2022, 1, 15, 12, 10)));
        candidates.put(2, new Candidate(2, "Middle", "middle",
                LocalDateTime.of(2022, 2, 25, 8, 15)));
        candidates.put(3, new Candidate(3, "Senior", "senior",
                LocalDateTime.of(2022, 2, 5, 10, 15)));
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
