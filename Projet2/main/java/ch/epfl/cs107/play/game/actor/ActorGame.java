
package ch.epfl.cs107.play.game.actor;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.io.FileSystem;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.WheelConstraintBuilder;
import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.math.RevoluteConstraintBuilder;
import ch.epfl.cs107.play.math.RopeConstraintBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.Constraint;
import ch.epfl.cs107.play.math.Entity;

public abstract class ActorGame implements Game {
	// liste d'Actor que le jeu fait intervenir
	private ArrayList<Actor> actorList;
	// liste d'Actor à ajouter au jeu 
	private ArrayList<Actor> actorAajouter;
	// liste d'Actor à supprimer du jeu 
	private ArrayList<Actor> actorAsupprimer;
	// store context
	private World world;
	// we need our physics engine
	private Window window;
	private FileSystem fileSystem;
	// personnage central du jeu 
	protected GameEntity playLoad;
	// by default the game is not in pause
	private boolean pause;
	
	// Viewport properties
	private Vector viewCenter;
	private Vector viewTarget;
	private Vector shiftedViewCenter ;
	private Positionable viewCandidate;
	private static final float VIEW_TARGET_VELOCITY_COMPENSATION = 0.2f;
	private static final float VIEW_INTERPOLATION_RATIO_PER_SECOND = 0.1f;
	private static final float VIEW_SCALE = 10.0f;

    // ajoute les acteurs à ajouter au jeu dans un tableau actorAajouter
	public void ajouterActor(Actor actor) {
		if (actor != null) {
			actorAajouter.add(actor);
		}
	}
	
	// ajoute les acteurs à supprimer du jeu dans un tableau actorAsupprimer
	public void supprimerActor(Actor actor) {
		if (actor != null) {
			actorAsupprimer.add(actor);
	    }
	}
	
	// pour supprimer tous les acteurs du jeu, utile pour changer de niveau
	public void removeAllActors() {
		for (int i = 0; i < actorList.size(); ++i) {
		   supprimerActor(actorList.get(i));
		}
	}
	
	// pour détruire tous les acteurs du jeu, utile pour changer de niveau
	public void destroyAllActors() {
		for (int i = 0; i < actorList.size(); ++i) {
			actorList.get(i).destroy();
		}
	}
	
    // permet l'accès au clavier
	public Keyboard getKeyboard() {
		return window.getKeyboard();
	}

	// permet l'accès à la fenêtre d'affichage utilisée
	public Canvas getCanvas() {
		return window;
	}
	
	public void setViewCandidate(Positionable viewCandidate) {
		this.viewCandidate = viewCandidate; 
	}
	
	// permet de créer une Entity sans donner accès à l'objet world à GameEntity
	public Entity createEntity(boolean fixed, Vector position) {
		EntityBuilder entityBuilder = world.createEntityBuilder(); 
		entityBuilder.setFixed(fixed);
		entityBuilder.setPosition(position);
		return entityBuilder.build();
	}
	
	// surchage de la méthode précédente due à la surchage des constructeurs de GameEntity
	public Entity createEntity(boolean fixed) {
		EntityBuilder entityBuilder = world.createEntityBuilder(); 
		entityBuilder.setFixed(fixed);
		return entityBuilder.build();
	}
    
	/*Ces 3 méthodes permettent de créer des contraintes en évitant 
	 * de donner accès aux autres classes à l'objet World
	 */
	public WheelConstraintBuilder createWheelConstraintBuilder(){
		return world.createWheelConstraintBuilder();
	}
	public RevoluteConstraintBuilder getRevoluteConstraintBuilder() {
		return world.createRevoluteConstraintBuilder();
	}
	public RopeConstraintBuilder getRopeConstraintBuilder() {
		return world.createRopeConstraintBuilder();
	}
	
	// fournit une référence au personnage central
	public GameEntity getPlayLoad() {
		return playLoad;
	}
	// permet la mise à jour du personnage central
	public void setPlayLoad(GameEntity actor) {
		playLoad = actor;
	}
	
	public Vector getShiftedViewCenter() {
		return shiftedViewCenter;
	}
	
	/**
	 * Initialises game state.
	 * 
	 * @param window
	 *            context to use, not null
	 * @param fileSystem
	 *            file system to use, not null
	 * @return whether the game was successfully started
	 */
	public boolean begin(Window window, FileSystem fileSystem) {
		this.window = window; 
		this.fileSystem = fileSystem;
		
		// create physics engine
		world = new World();
		world.setGravity(new Vector(0.0f, -9.81f));
		
		viewCenter = Vector.ZERO;
		viewTarget = Vector.ZERO;
		shiftedViewCenter  = Vector.ZERO;
		
		actorList = new ArrayList<Actor>();
		actorAsupprimer = new ArrayList<Actor>();
	    actorAajouter = new ArrayList<Actor>();
	    
		return true;
	}
	
	/**pp
	 * Simulates a single time step.
	 * 
	 * @param deltaTime
	 *            elapsed time since last update, in seconds, non-negative
	 */
	public void update(float deltaTime) {
		/*appuyer sur 'P' permet de mettre le jeu en pause s'il celui-ci ne l'était pas
		 * ou de reprendre le jeu si celui-ci était en pause
		 */
		if (window.getKeyboard().get(KeyEvent.VK_P).isPressed()) {
			pause = !pause;
		}
		
		if (!pause) { // on teste si le jeu est en mode non pause 
			world.update(deltaTime);
			
			/* ajoute les acteurs contenus dans le tableau actorAajouter à 
			 * la liste des acteurs du jeu
			 */
			for (int i = 0; i < actorAajouter.size(); ++i)  {
				actorList.add(actorAajouter.get(i));
				actorAajouter.remove(i);
			}
		
			// permet de faire évoluer chaque acteur sur une unité de temps deltaTime selon sa méthode update
			for (int i = 0; i < actorList.size(); ++i) {
				actorList.get(i).update(deltaTime);
			}
			
			/* supprime les acteurs contenus dans le tableau actorAajouter de
			 * la liste des acteurs du jeu.
			 */
			for (int i = 0; i < actorAsupprimer.size(); ++i)  {
			   int j = 0;
			   while (j < actorList.size() &&  actorList.get(j) != actorAsupprimer.get(i)) {
				++j;
			   }
			   if (j <  actorList.size()) {
				 actorList.remove(j);
			   }
			   actorAsupprimer.remove(i);
			}
			
			// Update expected viewport center
			if (viewCandidate != null) {
				viewTarget = viewCandidate.getPosition()
						.add(viewCandidate.getVelocity().mul(VIEW_TARGET_VELOCITY_COMPENSATION));
			}
			// Interpolate with previous location
			float ratio = (float) Math.pow(VIEW_INTERPOLATION_RATIO_PER_SECOND, deltaTime);
			viewCenter = viewCenter.mixed(viewTarget, ratio);
			// Compute new viewport
			Transform viewTransform = Transform.I.scaled(VIEW_SCALE).translated(viewCenter);
			window.setRelativeTransform(viewTransform); 
		   }
	    
	    //permet de dessiner les acteurs, que le jeu soit en pause ou non
		for (int i = 0; i < actorList.size(); ++i) {  
			actorList.get(i).draw(window);  
		}
		
		if (pause){ // on teste si le jeu est en mode pause 
			// deplace la camera vers la droit
			if (getKeyboard().get(KeyEvent.VK_RIGHT).isDown()){
			shiftedViewCenter = shiftedViewCenter.add((new Vector(0.1f, 0.0f))) ;
			} 
			// deplace la camera vers le haut
			if (getKeyboard().get(KeyEvent.VK_UP).isDown()){
				shiftedViewCenter = shiftedViewCenter.add((new Vector(0.0f, 0.1f))) ;
				}
			// deplace la camera vers la gauche
			if (getKeyboard().get(KeyEvent.VK_LEFT).isDown()){
				shiftedViewCenter = shiftedViewCenter.add((new Vector(-0.1f, 0.0f))) ;
				}
			// deplace la camera vers le bas
			if (getKeyboard().get(KeyEvent.VK_DOWN).isDown()){
				shiftedViewCenter = shiftedViewCenter.add((new Vector(0.0f, -0.1f))) ;
				}
			// Compute shifted viewport
			Transform shiftViewTransform =
			Transform.I.scaled(VIEW_SCALE).translated(shiftedViewCenter) ;
			window.setRelativeTransform(shiftViewTransform) ;
			}
	}
	
	// permet de recommencer le jeu
    public void restartGame() {
      	begin(window, fileSystem);
    }
    
	/** Cleans up things, called even if initialisation failed. */
	public void end() {
	}
}



