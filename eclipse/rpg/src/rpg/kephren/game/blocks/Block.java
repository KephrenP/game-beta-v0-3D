package rpg.kephren.game.blocks;

public class Block {
	protected int x, y;
	protected boolean solid;
	protected boolean ceiling;
	protected boolean floor;
	protected boolean feuille;
	public int hauteur;
	public int textureF;
	public int textureC;
	
	public Block(int x, int y){
		this.x = x;
		this.y = y;
		solid = false;
		ceiling = false;
		floor = true;
		feuille = false;
		this.hauteur = 0;
		textureF = 5;
		textureC = 0;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isSolid() {
		return solid;
	}

	public boolean isFeuille() {
		return feuille;
	}
	
	public boolean haveCeiling() {
		return ceiling;
	}

	public boolean haveFloor() {
		return floor;
	}
}
