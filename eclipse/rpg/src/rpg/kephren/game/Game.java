package rpg.kephren.game;

import rpg.kephren.maths.Vector3f;
import rpg.kephren.render.Renderer;

public class Game {
	
	/*
	 * Pour l'instant vide.
	 * Sers a instancier les différents niveaux (du moins leur rendu)
	 * ainsi que les Entitée, Objets, etc ...
	 */
	
	Level level;
	
	/*
	 * lancement map, parametrage
	 */
	public Game(){
		level = new Level("map3");
	}

	/*
	 * repositionnement des pixels des objots animée, Entitée
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
