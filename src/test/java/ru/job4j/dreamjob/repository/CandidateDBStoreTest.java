package ru.job4j.dreamjob.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CandidateDBStoreTest {
    private static CandidateDBStore store;
    private static BasicDataSource pool;

    @BeforeEach
    public void initStore() {
        pool = new Main().loadPool();
        store = new CandidateDBStore(pool);
    }

    @AfterEach
    public void clearStore() {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("delete from candidate")
        ) {
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void whenAddCandidate() {
        Candidate candidate = new Candidate(0, "Candidate", "Description",
                LocalDateTime.of(2022, 1, 15, 12, 10));
        store.add(candidate);
        Candidate postInDb = store.findById(candidate.getId());
        assertThat(postInDb.getName()).isEqualTo(candidate.getName());
    }

    @Test
    void whenFindAllCandidates() {
        Candidate firstCandidate = new Candidate(0, "Candidate", "Description",
                LocalDateTime.of(2022, 1, 15, 12, 10));
        Candidate secondCandidate = new Candidate(1, "Candidate", "Description",
                LocalDateTime.of(2022, 1, 15, 12, 10));
        Candidate thirdCandidate = new Candidate(2, "Candidate", "Description",
                LocalDateTime.of(2022, 1, 15, 12, 10));
        store.add(firstCandidate);
        store.add(secondCandidate);
        store.add(thirdCandidate);
        assertThat(store.findAll()).isEqualTo(
                List.of(firstCandidate, secondCandidate, thirdCandidate)
        );
    }

    @Test
    void whenFindCandidateById() {
        Candidate firstCandidate = new Candidate(0, "Candidate", "Description",
                LocalDateTime.of(2022, 1, 15, 12, 10));
        Candidate secondCandidate = new Candidate(0, "Candidate", "Description",
                LocalDateTime.of(2022, 1, 15, 12, 10));
        Candidate thirdCandidate = new Candidate(0, "Candidate", "Description",
                LocalDateTime.of(2022, 1, 15, 12, 10));
        store.add(firstCandidate);
        store.add(secondCandidate);
        store.add(thirdCandidate);
        Candidate findCandidate = store.findById(secondCandidate.getId());
        assertThat(findCandidate).isEqualTo(secondCandidate);
    }

    @Test
    void whenUpdateCandidate() {
        Candidate candidate = new Candidate(0, "Candidate", "Description",
                LocalDateTime.of(2022, 1, 15, 12, 10));
        store.add(candidate);
        candidate.setName("new Name");
        store.update(candidate);
        Candidate updatedCandidate = store.findById(candidate.getId());
        assertThat(updatedCandidate.getName()).isEqualTo("new Name");
    }
}