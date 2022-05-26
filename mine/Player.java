package mine;

public class Player {
	public static double playerExp = 0;
	public static int playerLevel = 1 ;
	
	public void updateLevel() {
		playerLevel = 1 + (int) (playerExp / 1.0);
	}
	
	public int getPlayerLevel() {
		return playerLevel;
	}
	
	public double getPlayerExp() {
		return playerExp;
	}
	
	public void addPlayerExp(double exp) {
		playerExp += exp;
	}
}
