package rpg.kephren.render;

import static org.lwjgl.opengl.GL11.*;

import rpg.kephren.maths.Vector3f;

public class Renderer {
	
	private static float offs = 0.0f; // pour l'instant inutile
	private static float envWidth = 4f; // nombre de texture sur l'axe x
	private static float envHeight = 4f; // nombre de texture sur l'axe y
	/*
	 * glTexCoord2f(offsetX, offsetY) => selectionne une partie de l'image
	 * glVertex3f(x, y, z) positionne un point de repère dans l'espace
	 */
	public static void setFloorData(float x, float z, int texture){ //sol
		float xo = texture % envWidth;
		float yo = (int) (texture / envHeight);
		
		glTexCoord2f((0 + xo) / envWidth + offs, (0 + yo) / envWidth + offs); glVertex3f(x + 1, 0, z);
		glTexCoord2f((1 + xo) / envWidth - offs, (0 + yo) / envWidth + offs); glVertex3f(x, 0, z);
		glTexCoord2f((1 + xo) / envWidth - offs, (1 + yo) / envWidth - offs); glVertex3f(x, 0, z + 1);
		glTexCoord2f((0 + xo) / envWidth + offs, (1 + yo) / envWidth - offs); glVertex3f(x + 1, 0, z + 1);
	}

	public static void setCeilingData(float x, float z, float y, int texture){ //plafond
		float xo = texture % envWidth;
		float yo = (int) (texture / envHeight);
		
		glTexCoord2f((0 + xo) / envWidth + offs, (0 + yo) / envWidth + offs); glVertex3f(x, y, z);
		glTexCoord2f((1 + xo) / envWidth - offs, (0 + yo) / envWidth + offs); glVertex3f(x + 1, y, z);
		glTexCoord2f((1 + xo) / envWidth - offs, (1 + yo) / envWidth - offs); glVertex3f(x + 1, y, z + 1);
		glTexCoord2f((0 + xo) / envWidth + offs, (1 + yo) / envWidth - offs); glVertex3f(x, y, z + 1);
	}
	
	public static void setWallData(float x0, float z0, float x1, float z1, float y0, float y1, int texture){ //mur

		float xo = texture % envWidth;
		float yo = (int) (texture / envHeight);
		
		glTexCoord2f((0 + xo) / envWidth + offs, (0 + yo) / envWidth + offs); glVertex3f(x0, y0, z0);
		glTexCoord2f((1 + xo) / envWidth - offs, (0 + yo) / envWidth + offs); glVertex3f(x1, y0, z1);
		glTexCoord2f((1 + xo) / envWidth - offs, (1 + yo) / envWidth - offs); glVertex3f(x1, y1, z1);
		glTexCoord2f((0 + xo) / envWidth + offs, (1 + yo) / envWidth - offs); glVertex3f(x0, y1, z0);
	}
	/*
	 * brouillard
	 */
	public static void addFog(float density, Vector3f color) {
		Shader.MAIN.setUniform("fogColor", color);
		Shader.MAIN.setUniform("fogDensity", density);
	}
}
