package rpg.kephren.render;


import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.BufferUtils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.IntBuffer;
import javax.imageio.ImageIO;

public class Texture {
	public static Texture env = new Texture("env.png", GL_NEAREST);
	
	private int id;
	private int width, height;
	
	public Texture(String path, int filter){
		int[] pixels = null;
		/*
		 * récuperation image
		 */
		try {
			BufferedImage image = ImageIO.read(Texture.class.getResource("/texture/" + path));
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		int[] data = new int[width * height];
		/*
		 * operation bitwise, recupération des valeurs binaires
		 */
		for (int i = 0; i < data.length; i++){
			int a = (pixels[i] & 0xff000000) >> 24;
			int r = (pixels[i] & 0xff0000) >> 16;
			int g = (pixels[i] & 0xff00) >> 8;
			int b = (pixels[i] & 0xff);
			
			data[i] = a << 24 | b << 16 | g << 8 | r; //insertion valeur pixels
		}
		int id = glGenTextures();
		/*
		 * création d'insertion de texture en 2D
		 */
		glBindTexture(GL_TEXTURE_2D, id);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filter);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filter);
		/*
		 * openGl fonctionne par buffer !
		 */
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer); //création de l'image dans openGl
		
		this.id = id;
	}
	
	public int getWidth(){
		return width;
	}

	public int getHeight(){
		return width;
	}
	
	public void bind(){
		glBindTexture(GL_TEXTURE_2D, id);
	}

}
