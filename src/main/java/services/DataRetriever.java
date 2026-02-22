package services;

import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
}