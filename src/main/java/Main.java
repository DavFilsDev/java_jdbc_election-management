import models.*;
import services.DataRetriever;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        DataRetriever dataRetriever = new DataRetriever();

        // Q1
        long totalVote = dataRetriever.countAllVotes();
        System.out.println("totalVote=" + totalVote);

        // Q2
        System.out.println("\n Q2: Votes by Type");
        List<VoteTypeCount> voteTypeCounts = dataRetriever.countVotesByType();
        System.out.println(voteTypeCounts);

        // Q3
        System.out.println("\n Q3: Valid Votes by Candidate");
        List<CandidateVoteCount> validVotes = dataRetriever.countValidVotesByCandidate();
        System.out.println(validVotes);

        // Q4
        System.out.println("\n Q4: Global Vote Summary");
        VoteSummary summary = dataRetriever.computeVoteSummary();
        System.out.println(summary);

        // Q5
        System.out.println("\n Q5: Turnout Rate");
        double turnout = dataRetriever.computeTurnoutRate();
        System.out.println("Turnout Rate = " + turnout + "%");
    }
}