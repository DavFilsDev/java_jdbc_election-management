import models.*;
import services.DataRetriever;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        DataRetriever dataRetriever = new DataRetriever();

        // Q1 Test
        long totalVote = dataRetriever.countAllVotes();
        System.out.println("totalVote=" + totalVote);

        // Q2 Test
        System.out.println("\n=== Q2 - Votes by Type ===");
        List<VoteTypeCount> voteTypeCounts = dataRetriever.countVotesByType();
        System.out.println(voteTypeCounts);

        System.out.println("\n=== Q3 Valid Votes by Candidate ===");
        List<CandidateVoteCount> validVotes = dataRetriever.countValidVotesByCandidate();
        System.out.println(validVotes);
    }
}
