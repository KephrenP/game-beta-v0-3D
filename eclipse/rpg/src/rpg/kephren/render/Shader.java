package rpg.kephren.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import rpg.kephren.maths.Vector3f;

public class Shader {
	public static final Shader MAIN = new Shader("/shaders/main.vert", "/shaders/main.frag");
	public int program;
	/*
	 * Envoie des calcule aux GPU
	 */
	public Shader(String vertex, String fragment) {
		program = glCreateProgram();
		
		if (program == GL_FALSE){
			System.err.println("Shader program error !");
			System.exit(1);
		}
		createShader(loadShader(vertex), GL_VERTEX_SHADER);
		createShader(loadShader(fragment), GL_FRAGMENT_SHADER);
		
		glLinkProgram(program);
		glValidateProgram(program);
	}
	
	/*
	 * créer les shaders
	 */
	private void createShader(String source, int type) {
		int shader = glCreateShader(type);
		
		if (shader == GL_FALSE){
			System.err.println("Shader program error !");
			System.exit(1);
		}
		glShaderSource(shader, source);
		glCompileShader(shader);
		
		if(glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println(glGetShaderInfoLog(shader, 2048));
			System.exit(1);
		}
		glAttachShader(program, shader);
	}

	/*
	 * récupère les shaders
	 */
	private String loadShader(String input) {
		String r = "";
		try {
			InputStream is = Shader.class.getResourceAsStream(input);
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String buffer = "";
			while((buffer = reader.readLine()) !=null){
				r += buffer + "\n";
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return r;
	}
	/*
	 * uniform fait le lien CPU - GPU
	 */
	public void setUniform(String name, float v){
		glUniform1f(glGetUniformLocation(program, name), v);
	}

	public void setUniform(String name, Vector3f v){
		glUniform3f(glGetUniformLocation(program, name), v.getX(), v.getY(), v.getZ());
	}
	
	public void bind() {
		glUseProgram(program);
	}
}
