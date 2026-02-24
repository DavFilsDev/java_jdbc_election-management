package services;

import db.DBConnection;
import models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {

    private final DBConnection dbConnection = new DBConnection();

    public long countAllVotes() {
        String sql = "SELECT COUNT(*) FROM vote";
        long totalVotes = 0;

        try (Connection conn = dbConnection.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                totalVotes = rs.getLong(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return totalVotes;
    }

    // Q2: Count votes by type
    public List<VoteTypeCount> countVotesByType() {

        String sql = "SELECT vote_type, COUNT(*) FROM vote GROUP BY vote_type ORDER BY vote_type";
        List<VoteTypeCount> results = new ArrayList<>();

        try (Connection conn = dbConnection.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String type = rs.getString(1);
                long count = rs.getLong(2);
                results.add(new VoteTypeCount(type, count));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }

    // Q3: Count valid votes per candidate
    public List<CandidateVoteCount> countValidVotesByCandidate() {

        String sql = """
        SELECT c.name, COUNT(v.id)
        FROM candidate c
        LEFT JOIN vote v 
            ON c.id = v.candidate_id AND v.vote_type = 'VALID'
        GROUP BY c.name
        ORDER BY c.name
        """;

        List<CandidateVoteCount> results = new ArrayList<>();

        try (Connection conn = dbConnection.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString(1);
                long count = rs.getLong(2);
                results.add(new CandidateVoteCount(name, count));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }

    // Q4 - Global vote summary in one row
    public VoteSummary computeVoteSummary() {

        String sql = """
        SELECT
            COUNT(CASE WHEN vote_type = 'VALID' THEN 1 END) AS valid_count,
            COUNT(CASE WHEN vote_type = 'BLANK' THEN 1 END) AS blank_count,
            COUNT(CASE WHEN vote_type = 'NULL' THEN 1 END)  AS null_count
        FROM vote;
        """;

        try (Connection conn = dbConnection.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                long valid = rs.getLong("valid_count");
                long blank = rs.getLong("blank_count");
                long nul   = rs.getLong("null_count");

                return new VoteSummary(valid, blank, nul);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Q5 - Turnout rate
    public double computeTurnoutRate() {

        String sql = """
        SELECT COUNT(DISTINCT voter_id) * 100.0 / (SELECT COUNT(*) FROM voter) AS turnout_rate
        FROM vote;
        """;

        try (Connection conn = dbConnection.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("turnout_rate");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    // Q6 - Find election winner
    public ElectionResult findWinner() {

        String sql = """
        SELECT c.name, COUNT(v.id) AS valid_vote_count
        FROM candidate c
        JOIN vote v ON c.id = v.candidate_id
        WHERE v.vote_type = 'VALID'
        GROUP BY c.name
        ORDER BY valid_vote_count DESC
        LIMIT 1;
        """;

        try (Connection conn = dbConnection.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                String name = rs.getString("name");
                long count = rs.getLong("valid_vote_count");
                return new ElectionResult(name, count);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}