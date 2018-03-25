package rpg.kephren.game.blocks;

public class Floor extends Block {

	public Floor(int x, int y) {
		super(x, y);
		solid = false;
		floor = true;
		ceiling = false;
		textureF = 2;
		feuille = false;
		this.hauteur = 0;
		textureC = 0;
	}

}
