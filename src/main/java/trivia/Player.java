package trivia;

public class Player {
	private final String name;
	private int place;
	private int purse;
	private boolean inPenaltyBox;
	
	public Player(String name) {
		this.name = name;
		this.place = 0;
		this.purse = 0;
		this.inPenaltyBox = false;
	}
	
	public boolean isInPenaltyBox() {
		return inPenaltyBox;
	}
	
	
	// TODO unde e moveOut?!!
	public void putInPenaltyBox() {
		this.inPenaltyBox = true;
	}

	public int getPlace() {
		return place;
	}
	
	public void move(int roll) {
//		place = (place + roll) % 12;
		place += roll;
		if (place >= 12) {
			place -= 12;
		}
	}
	
	
	public String getName() {
		return name;
	}
	
	public int getPurse() {
		return purse;
	}
	
	public void addPurse() {
		purse++;
	}

	public int roll(int value) {
		place = place + value;
		if (place > 11) place = place - 12;

		return place;
	}
}
