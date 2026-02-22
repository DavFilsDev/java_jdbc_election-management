import services.DataRetriever;

public class Main {

    public static void main(String[] args) {

        DataRetriever dataRetriever = new DataRetriever();

        long totalVote = dataRetriever.countAllVotes();
        System.out.println("totalVote=" + totalVote);
    }
}
