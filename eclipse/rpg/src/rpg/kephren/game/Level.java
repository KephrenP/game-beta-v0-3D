package rpg.kephren.game;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import rpg.kephren.game.blocks.Arbre;
import rpg.kephren.game.blocks.Block;
import rpg.kephren.game.blocks.Feuille;
import rpg.kephren.game.blocks.Floor;
import rpg.kephren.game.blocks.Wall;
import rpg.kephren.game.blocks.WallBlock;
import rpg.kephren.render.Renderer;
import rpg.kephren.render.Texture;

public class Level {
	int renderingList;
	
	Block[] blocks;
	private int width, height;
	
	/*
	 * Cette fonction sert a gerer les option de création de la map
	 */
	public Level(String level){
		compile(level);
	}
	/*
	 * génère les Objets et leur position
	 */
	public void compile(String level) {
		BufferedImage map = null;
		try {
			map = ImageIO.read(Level.class.getResource("/" + level + ".png")); // récupère l'image
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		width = map.getWidth();
		height = map.getHeight();
		int[] pixels = new int[width * height];
		map.getRGB(0, 0, width, height, pixels, 0, width); // récupère leur couleur
		
		blocks = new Block[pixels.length];
		
		/*
		 * place les bloc en fonction des couleurs
		 * les couleur s'écrivent en héxa (0x--------
		 */
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				int i = x + y * width;
				
				if(pixels[i] == 0xFF4CFF00) // si vert clair
					blocks[i] = new Block(x, y);

				if(pixels[i] == 0xFF000000) // si noir
					blocks[i] = new WallBlock(x, y, 5);

				if(pixels[i] == 0xFF7F3300) // si marron
					blocks[i] = new Arbre(x, y);

				if(pixels[i] == 0xFF267F00) // si vert foncé
					blocks[i] = new Feuille(x, y, 4);

				if(pixels[i] == 0xFF007F0E) // si vert très foncé
					blocks[i] = new Feuille(x, y, 5);

				if(pixels[i] == 0xFF004A0E) // si vert vraiment très foncé
					blocks[i] = new Feuille(x, y, 6);
				
				if(pixels[i] == 0xFF404040) // si gris foncé
					blocks[i] = new Wall(x, y, 2);
				
				if(pixels[i] == 0xFF808080) // si gris clair
					blocks[i] = new Floor(x, y);
				
				

				if(pixels[i] == 0xFFFFFFFF) // si blanc
					blocks[i] = new Block(x, y);

			}
		}

		for (int x=0; x < width; x++){
			for (int y=0; y < width; y++){

				int n = blocks[x+y*width].hauteur;
				System.out.println(n);
			}
		}
		compileRendering();
	}

	/*
	 * procéde au positionnement et aux rendu openGL
	 */
	private void compileRendering(){
		renderingList = glGenLists(1);
		
		glNewList(renderingList, GL_COMPILE);
		glBegin(GL_QUADS); // création de dimension carré ou on insère les texture
		
		for (int x=0; x < width; x++){
			for (int y=0; y < width; y++){
				
				Block block = getBlock(x, y);
				
				if (!block.isSolid()){
					if(block.haveFloor())
						Renderer.setFloorData(x,  y, block.textureF);
					if(block.haveCeiling())
						if (block.isFeuille()){
							Renderer.setCeilingData(x,  y, 3, block.textureC);
						}else{
							Renderer.setCeilingData(x,  y, block.hauteur-1, 2);
						}
				}
				Block right = getBlock(x + 1, y);
				Block down = getBlock(x, y + 1);

				if (block.isFeuille()){
					int n = block.hauteur;
					if (!right.isFeuille()){
							Renderer.setWallData(x + 1, y + 1, x + 1, y, n-1, n, block.textureC);
					}
					if (!down.isFeuille()){
							Renderer.setWallData(x, y + 1, x + 1, y + 1, n-1, n, block.textureC);
					}
					if (right.isFeuille() && right.hauteur < block.hauteur){
							Renderer.setWallData(x + 1, y + 1, x + 1, y, n-1, n, right.textureC);
					}
					if (down.isFeuille() && down.hauteur < block.hauteur){
							Renderer.setWallData(x, y + 1, x + 1, y + 1, n-1, n, down.textureC);
					}
					if (right.isFeuille() && right.hauteur > block.hauteur){
						n = right.hauteur;
							Renderer.setWallData(x + 1, y, x + 1, y + 1, n-1, n, right.textureC);
					}
					if (down.isFeuille() && down.hauteur > block.hauteur){
						n = down.hauteur;
							Renderer.setWallData(x + 1, y + 1, x, y + 1, n-1, n, down.textureC);
					}
				}else {
					if (right.isFeuille()){
						int n = right.hauteur;
							Renderer.setWallData(x + 1, y, x + 1, y + 1, n-1, n, right.textureC);
					}
					if (down.isFeuille()){
						int n = down.hauteur;
							Renderer.setWallData(x + 1, y + 1, x, y + 1, n-1, n, down.textureC);
					}
				}
				

				/*
				 * placement des face visible des texture en fonction des deplacement théorique des entitée
				 */
				if (block.isSolid()){
					int n = block.hauteur;
					if (!right.isSolid()){
						while(n-1>=0){
							Renderer.setWallData(x + 1, y + 1, x + 1, y, n-1, n, block.textureF);
							n--;
						}
					}
					n = block.hauteur;
					if (!down.isSolid()){
						while(n-1>=0){
							Renderer.setWallData(x, y + 1, x + 1, y + 1, n-1, n, block.textureF);
							n--;
						}
					}
				}else {
					if (right.isSolid()){
						int n = right.hauteur;
						while(n-1>=0){
							Renderer.setWallData(x + 1, y, x + 1, y + 1, n-1, n, right.textureF);
							n--;
						}
					}
					if (down.isSolid()){
						int n = down.hauteur;
						while(n-1>=0){
							Renderer.setWallData(x + 1, y + 1, x, y + 1, n-1, n, down.textureF);
							n--;
						}
					}
				}
			}
		}
		glEnd();
		glEndList();

		Texture.env.bind();
	}
	
	public Block getBlock(int x, int y) {
		if(x < 0 || y < 0 || x >= width || y >= height){
			return new WallBlock(x, y, 0);
		}

		return blocks[x+y*width];
	}
	
	/*
	 * objet animé, entitée, ...
	 */
	public void update() {
		
	}
	/*
	 * appelle les liste d'objets créer par openGL.
	 */
	public void render() {
		glCallList(renderingList);
	}

}
