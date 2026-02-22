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

    // Q1: Count all votes (push-down processing)
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
}