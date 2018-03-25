package rpg.kephren.game.blocks;

public class Arbre extends Block {

	public Arbre(int x, int y) {
		super(x, y);
		this.hauteur = 3;
		solid = true;
		floor = false;
		ceiling = false;
		feuille = false;
		textureF = 4;
		textureC = 0;
	}

}
