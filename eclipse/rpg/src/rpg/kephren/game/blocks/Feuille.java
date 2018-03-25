package rpg.kephren.game.blocks;

public class Feuille extends Block{
	public Feuille(int x, int y, int h) {
		super(x, y);
		this.hauteur= h;
		solid = false;
		floor = true;
		ceiling = true;
		feuille = true;
		textureF = 5;
		textureC = 3;
		
	}

}
