package rpg.kephren.game.blocks;

public class Wall extends Block{
	public Wall(int x, int y, int z) {
		super(x, y);
		solid = true;
		floor = false;
		ceiling = false;
		textureF = 1;
		this.hauteur = z;
		feuille = false;
		textureC = 0;
	}

}
