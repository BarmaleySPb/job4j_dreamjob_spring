package ru.job4j.dreamjob.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

@Repository
public class CandidateDBStore {
    private static final String FIND_ALL_CANDIDATES = "SELECT * FROM candidate ORDER BY ID";
    private static final String FIND_CANDIDATE_BY_ID = "SELECT * FROM candidate WHERE id = ?";
    private static final String ADD_CANDIDATE = "INSERT INTO candidate("
            + "name, description, created, photo) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_CANDIDATE = "UPDATE candidate SET "
            + "name = ?, description = ?, created = ?, photo = ? WHERE id = ?";
    private static final Logger LOG = LoggerFactory.getLogger(CandidateDBStore.class.getName());
    private final BasicDataSource pool;

    public CandidateDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Candidate add(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     ADD_CANDIDATE, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(candidate.getCreated()));
            ps.setBytes(4, candidate.getPhoto());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Candidate is not added.", e);
        }
        return candidate;
    }

    public Collection<Candidate> findAll() {
        Collection<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_ALL_CANDIDATES)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(new Candidate(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getTimestamp("created").toLocalDateTime(),
                            it.getBytes("photo")
                    ));
                }
            }
        } catch (Exception e) {
            LOG.error("Candidates is not found.", e);
        }
        return candidates;
    }

    public Candidate findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_CANDIDATE_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new Candidate(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getTimestamp("created").toLocalDateTime(),
                            it.getBytes("photo")
                    );
                }
            }
        } catch (Exception e) {
            LOG.error("Candidate is not found.", e);
        }
        return null;
    }

    public void update(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(UPDATE_CANDIDATE)
        ) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(candidate.getCreated()));
            ps.setBytes(4, candidate.getPhoto());
            ps.setInt(5, candidate.getId());
            ps.execute();
        } catch (Exception e) {
            LOG.error("Candidate is not updated.", e);
        }
    }
}
