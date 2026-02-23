import models.*;
import services.DataRetriever;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        DataRetriever dataRetriever = new DataRetriever();

        long totalVote = dataRetriever.countAllVotes();
        System.out.println("totalVote=" + totalVote);

        System.out.println("\n Q2: Votes by Type");
        List<VoteTypeCount> voteTypeCounts = dataRetriever.countVotesByType();
        System.out.println(voteTypeCounts);

        System.out.println("\n Q3: Valid Votes by Candidate");
        List<CandidateVoteCount> validVotes = dataRetriever.countValidVotesByCandidate();
        System.out.println(validVotes);

        System.out.println("\n Q4: Global Vote Summary");
        VoteSummary summary = dataRetriever.computeVoteSummary();
        System.out.println(summary);

        System.out.println("\n Q5: Turnout Rate");
        double turnout = dataRetriever.computeTurnoutRate();
        System.out.println("Turnout Rate = " + turnout + "%");

        System.out.println("\n Q6 Election Winner");
        ElectionResult winner = dataRetriever.findWinner();
        System.out.println(winner);
    }
}