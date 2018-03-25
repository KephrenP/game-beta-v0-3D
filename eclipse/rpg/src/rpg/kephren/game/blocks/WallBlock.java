package rpg.kephren.game.blocks;

public class WallBlock extends Block{
	public WallBlock(int x, int y, int z) {
		super(x, y);
		solid = true;
		floor = false;
		ceiling = false;
		textureF = 0;
		this.hauteur = z;
		feuille = false;
		textureC = 0;
	}
}
