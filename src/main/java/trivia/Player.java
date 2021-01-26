package trivia;

public class Player {
	private final String name;
	private int place;
	private int coins;
	private boolean inPenaltyBox;
	
	public Player(String name) {
		this.name = name;
	}
	
	public boolean isInPenaltyBox() {
		return inPenaltyBox;
	}

	public void putInPenaltyBox() {
		this.inPenaltyBox = true;
	}

	public void takeOutPenaltyBox() {
		this.inPenaltyBox = false;
	}

	public int getPlace() {
		return place;
	}
	
	public void move(int roll) {
		place = (place + roll) % 12;
	}

	public String getName() {
		return name;
	}
	
	public int getCoins() {
		return coins;
	}
	
	public void addCoin() {
		coins ++;
	}
	
	public String toString() {
		return name;
	}
}
