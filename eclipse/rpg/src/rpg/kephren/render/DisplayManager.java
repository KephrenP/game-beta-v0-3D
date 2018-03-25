package rpg.kephren.render;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

public class DisplayManager {

	public static void create(int width, int height, String title) {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle(title);
			Display.setResizable(true);
			Display.create();

			glEnable(GL_DEPTH_TEST); //active la 3D
			glEnable(GL_CULL_FACE); // optimise les rendu en affichant que les partie théoriquement visible
			glEnable(GL_TEXTURE_2D); // active l'ajout de texture 2D
			
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	public static void update() {
		Display.update();
	}
	/*
	 * efface les pixels
	 */
	public static void clearBuffers() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(0, 0.75f, 1, 1); // bleu clair
	}

	public static boolean isClosed() {
		return Display.isCloseRequested();
	}

	public static void dispose() {
		Display.destroy();
	}
}
