package rpg.kephren.main;

import static org.lwjgl.opengl.GL11.*; //import static, pour pas avoir a écrire tout le temps "GL11." .

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import rpg.kephren.game.Game;
import rpg.kephren.maths.Vector3f;
import rpg.kephren.render.Camera;
import rpg.kephren.render.DisplayManager;
import rpg.kephren.render.Shader;

public class Main {

	public static final int FRAME_CAP = 50;

	boolean running = false;
	
	Camera cam;
	Game game;

	public Main() {
		DisplayManager.create(720, 480, "jeu 3D test");
		cam = new Camera(new Vector3f(1.5f, 0.7f, 1.5f)); //placement du point de départ de la camera x, y, z
		cam.setPerspectiveProjection(70.0f, 0.1f, 100.0f); //fov, vueMin, vueMax
		game = new Game();
	}

	/*
	 * action de lancement de la fenetre LWJGL
	 */
	public void start() {
		running = true;
		loop();
	}

	/*
	 * action de fermeture de la fenetre LWJGL
	 */
	public void stop() {
		running = false;
	}

	/*
	 * action de fermeture de l'application
	 */
	public void exit() {
		DisplayManager.dispose();
		System.exit(0);
	}
	
	/*
	 * Boucle principale
	 * Gestion du temps du jeu !
	 */
	public void loop() {
		long lastTickTime = System.nanoTime();
		long lastRenderTime = System.nanoTime();

		double tickTime = 1000000000 / 60;
		double renderTime = 1000000000 / FRAME_CAP;

		int ticks = 0;
		int frames = 0;

		long timer = System.currentTimeMillis();

		while (running) {
			if (DisplayManager.isClosed())
				stop();

			if (System.nanoTime() - lastTickTime > tickTime) {
				update();
				ticks++;
				lastTickTime += tickTime;
			} else if (System.nanoTime() - lastRenderTime > renderTime) {
				render();
				frames++;
				DisplayManager.update();
				lastRenderTime += renderTime;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(ticks + " ticks, " + frames + " fps.");
				ticks = 0;
				frames = 0;
			}
		}
		exit();
	}

	/*
	 * sers a repositionné les pixels
	 */
	public void update() {
		// temporaire / TODO : class Keylistener et mouselistener a creer
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) // Entreé ou sortir de la fenetre
			Mouse.setGrabbed(false);
		if (Mouse.isButtonDown(0))
			Mouse.setGrabbed(true); // efface la souris
		if (!Mouse.isGrabbed()) // si pas en jeu, actualise pas le jeu ! (mode pause)
			return;
		cam.input();
		game.update();
	}

	/*
	 * sers a afficher les pixels
	 */
	public void render() {
		if (Display.wasResized()) { // redimensionnement des image a la fenetre
			glViewport(0, 0, Display.getWidth(), Display.getHeight());
		}
		DisplayManager.clearBuffers(); // evite les residu de pixels
		cam.getPerspectiveProjection();
		cam.update();
		
		Shader.MAIN.bind();
		
		game.render();
	}

	public static void main(String[] agrs) {
		Main main = new Main();
		main.start();
	}
}
