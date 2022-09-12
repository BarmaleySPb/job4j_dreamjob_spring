package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.repository.CandidateDBStore;

import java.util.Collection;

@Service
@ThreadSafe
public class CandidateService {
    private final CandidateDBStore candidateStore;

    public CandidateService(CandidateDBStore candidateStore) {
        this.candidateStore = candidateStore;
    }

    public void add(Candidate candidate) {
        candidateStore.add(candidate);
    }

    public Collection<Candidate> findAll() {
        return candidateStore.findAll();
    }

    public Candidate findById(int id) {
        return candidateStore.findById(id);
    }

    public void update(Candidate candidate) {
        candidateStore.update(candidate);
    }
}
