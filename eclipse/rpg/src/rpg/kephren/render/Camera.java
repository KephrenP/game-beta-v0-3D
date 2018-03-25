package rpg.kephren.render;

import org.lwjgl.util.glu.GLU;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import rpg.kephren.maths.Vector3f;

public class Camera {

	private float fov;
	private float zNear;
	private float zFar;
	private float speed = 0.05f;

	private Vector3f position;
	private Vector3f rotation;

	public Camera(Vector3f position) {
		this.position = position;
		rotation = new Vector3f();
	}
	/*
	 * instancie la camera
	 */
	public Camera setPerspectiveProjection(float fov, float zNear, float zFar) {
		this.fov = fov;
		this.zNear = zNear;
		this.zFar = zFar;

		return this;
	}
	/*
	 * calcul exact des deplacement droite gauche en fonction de la ou regarde le joueur
	 */
	public Vector3f getRight() {
		Vector3f r = new Vector3f();
		Vector3f rot = new Vector3f(rotation);

		float cosY = (float) Math.cos(Math.toRadians(rot.getY()));
		float sinY = (float) Math.sin(Math.toRadians(rot.getY()));

		r.setX(cosY);
		r.setZ(sinY);

		return r;
	}
	/*
	 * calcul exact de l'axe de "yeux" en fonction de la ou regarde le joueur
	 */
	public Vector3f getForward() {
		Vector3f r = new Vector3f();
		Vector3f rot = new Vector3f(rotation);

		float cosY = (float) Math.cos(Math.toRadians(rot.getY() + 90));
		float sinY = (float) Math.sin(Math.toRadians(rot.getY() + 90));
		float cosP = (float) Math.cos(Math.toRadians(rot.getX()));
		//float sinP = (float) Math.sin(Math.toRadians(rot.getX()));

		r.setX(cosY * cosP);
		//r.setY(sinP); // a mettre pour le noclip, si réactiver, retirer les // de float sinP
		r.setY(0); //empeche de monter et descendre la ou on regarde
		r.setZ(sinY * cosP);

		return r;
	}
	/*
	 * definie se que l'on voit
	 */
	public void getPerspectiveProjection() {
		glEnable(GL_PROJECTION);
		glLoadIdentity();
		GLU.gluPerspective(fov, (float) Display.getWidth() / (float) Display.getHeight(), zNear, zFar);
		glEnable(GL_MODELVIEW);
	}

	public void update() {
		glPushAttrib(GL_TRANSFORM_BIT);
		glRotatef(rotation.getX(), 1, 0, 0);
		glRotatef(rotation.getY(), 0, 1, 0);
		glRotatef(rotation.getZ(), 0, 0, 1);
		glTranslatef(-position.getX(), -position.getY(), -position.getZ());
		glPopMatrix();
	}
	/*
	 * temporaire, key et mouselistener a faire !
	 */
	public void input() {
		rotation.addX(-Mouse.getDY()/4);
		rotation.addY(Mouse.getDX()/4);
		
		if (rotation.getX() > 90) rotation.setX(90); // limite l'angle de vue haut
		if (rotation.getX() < -90) rotation.setX(-90); // limite l'angle de vue bas

		if (Keyboard.isKeyDown(Keyboard.KEY_Z) || Keyboard.isKeyDown(Keyboard.KEY_UP))
			position.add(getForward().mul(new Vector3f(-speed, -speed, -speed)));
		if (Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN))
			position.add(getForward().mul(new Vector3f(speed, speed, speed)));
		if (Keyboard.isKeyDown(Keyboard.KEY_Q) || Keyboard.isKeyDown(Keyboard.KEY_LEFT))
			position.add(getRight().mul(new Vector3f(-speed, -speed, -speed)));
		if (Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
			position.add(getRight().mul(new Vector3f(speed, speed, speed)));

		/*if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))
			position.add(new Vector3f(0, speed, 0));
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
			position.add(new Vector3f(0, -speed, 0));*/
	}

	public Vector3f getPosition() {
		return position;
	}
	
	public void setPosition(Vector3f position) {
		this.position = position;
	}

}
