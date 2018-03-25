package rpg.kephren.game;

import rpg.kephren.maths.Vector3f;
import rpg.kephren.render.Renderer;

public class Game {
	
	/*
	 * Pour l'instant vide.
	 * Sers a instancier les diff�rents niveaux (du moins leur rendu)
	 * ainsi que les Entit�e, Objets, etc ...
	 */
	
	Level level;
	
	/*
	 * lancement map, parametrage
	 */
	public Game(){
		level = new Level("map3");
	}

	/*
	 * repositionnement des pixels des objots anim�e, Entit�e
	 */
	public void update() {
		level.update();
	}
	
	/*
	 * rendu ecran
	 */
	public void render() {
		Renderer.addFog(0.05f, new Vector3f(1, 0, 1));

		level.render();
	}
}
