/*
 *	Author:      Elia Saquand
 *	Date:        10.10.2017
 */

package ch.epfl.cs107.play.game.actor.bike;

import ch.epfl.cs107.play.game.actor.crate.Crate;

import java.awt.Color;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.BasicContactListener;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Window;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.game.actor.general.Terrain;

public class BikeGame extends ActorGame implements GameWithLevels {
	// permet de faire certain affichage au cours du jeu
	private TextGraphics message;
	private TextGraphics message1;
	private Vector startingPoint;
	// liste contenant les différents niveaux du jeu
	private List<Level> levels;
	// niveau courant
	private int currentLevel;
	// permet d'afficher le score (image de la pièce, le nombre et le texte)
	private TextGraphics scoreVisual;
	private TextGraphics scoreText;
	private ImageGraphics scoreGraphics;
	// score du joueur
	private int score;
	// to reset score at the score from the start of the level if restart level
	private int scoreStartOfLevel;
	// indique quand la ligne d'arrivée est franchi par le joueur
	private boolean victory;
	// affiche le countdown
	private TextGraphics countDownGraphics;
	// indique quand le bike subit une situation de chute
	private boolean bikeIsDead;
	// timer pour le countDown
	private float timer;
	// nombre de vie du joueur
	private int lives = 4;
	// permet d'afficher le nombre de vie (les coeurs et le nombre)
	private ImageGraphics livesGraphics;
	private ImageGraphics livesGraphics1;
	private TextGraphics livesVisual;
	// permet de figer le cycliste pendant un certain temps
	private boolean freeze;
	private float freezeTimer;

	public boolean begin(Window window, FileSystem fileSystem) {
		super.begin(window, fileSystem);

		// Generate textGraphics
		createTextGraphics();
		setLivesGraphics();

		// generate list of levels
		levels = createLevelList();

		// start the currentLevel
		levels.get(currentLevel).createAllActors();

		return true;
	}

	public void setStartingPoint(Vector position) {
		startingPoint = position;
	}

	public void setBikeIsDead(boolean bikeIsDead) {
		this.bikeIsDead = bikeIsDead;
	}

	// indique si la ligne d'arrivé est franchi par le joueur
	public void setVictory() {
		victory = true;
	}

	// packages the levels into a levelList
	protected List<Level> createLevelList() {
		return Arrays.asList(new Level1(this), new Level2(this), new Level3(this));
	}

	/*
	 * set the textGraphics for the different texts me may want to display at some
	 * point in the game
	 */
	private void createTextGraphics() {
		message = new TextGraphics("", 0.3f, Color.RED, Color.WHITE, 0.02f, true, false, new Vector(0.5f, 0.5f), 1.0f,
				100.0f);
		message.setParent(getCanvas());
		message.setRelativeTransform(Transform.I.translated(0.0f, -1.0f));

		message1 = new TextGraphics("", 0.1f, Color.RED, Color.WHITE, 0.02f, true, false, new Vector(0.5f, 2.0f), 1.0f,
				100.0f);
		message1.setParent(getCanvas());
		message1.setRelativeTransform(Transform.I.translated(0.0f, -1.0f));

		scoreText = new TextGraphics("Score: ", 0.15f, Color.YELLOW, Color.YELLOW, 0.02f, true, true,
				new Vector(1.4f, 3.5f), 1.0f, 100.0f);
		scoreText.setParent(getCanvas());
		scoreText.setRelativeTransform(Transform.I.translated(0.0f, -1.0f));

		String scoreString = Integer.toString(score);
		scoreVisual = new TextGraphics(scoreString, 0.15f, Color.YELLOW, Color.YELLOW, 0.02f, true, true,
				new Vector(1.0f, 3.5f), 1.0f, 100.0f);
		scoreVisual.setParent(getCanvas());
		scoreVisual.setRelativeTransform(Transform.I.translated(0.0f, -1.0f));

		scoreGraphics = new ImageGraphics("coin.gold.png", 0.1f, 0.1f, new Vector(9.f, -5.2f));
		scoreGraphics.setParent(getCanvas());
		//scoreGraphics.setRelativeTransform(Transform.I.translated(0.0f, -1.0f));

		String scoreString1 = Integer.toString(lives);
		livesVisual = new TextGraphics(scoreString1, 0.15f, Color.RED, Color.RED, 0.02f, true, true,
				new Vector(-9.8f, 3.7f), 1.0f, 100.0f);
		livesVisual.setParent(getCanvas());
		livesVisual.setRelativeTransform(Transform.I.translated(0.0f, -1.0f));
	}

	// permet de mettre à jour le graphique des vies en fonction du nombre de vie
	private void setLivesGraphics() {
		switch (lives) {
		case 4:
			livesGraphics1 = new ImageGraphics("heart.full.png", 0.2f, 0.2f, new Vector(-1.85f, -7.5f));
			livesGraphics = new ImageGraphics("heart.full.png", 0.2f, 0.2f, new Vector(-3f, -7.5f));
			break;
		case 3:
			livesGraphics1 = new ImageGraphics("heart.half.png", 0.2f, 0.2f, new Vector(-1.85f, -7.5f));
			livesGraphics = new ImageGraphics("heart.full.png", 0.2f, 0.2f, new Vector(-3f, -7.5f));
			break;
		case 2:
			livesGraphics1 = new ImageGraphics("heart.empty.png", 0.2f, 0.2f, new Vector(-1.85f, -7.5f));
			livesGraphics = new ImageGraphics("heart.full.png", 0.2f, 0.2f, new Vector(-3f, -7.5f));
			break;
		case 1:
			livesGraphics1 = new ImageGraphics("heart.empty.png", 0.2f, 0.2f, new Vector(-1.85f, -7.5f));
			livesGraphics = new ImageGraphics("heart.half.png", 0.2f, 0.2f, new Vector(-3f, -7.5f));
			break;
		default:
			livesGraphics1 = new ImageGraphics("heart.empty.png", 0.2f, 0.2f, new Vector(-1.85f, -7.5f));
			livesGraphics = new ImageGraphics("heart.empty.png", 0.2f, 0.2f, new Vector(-3f, -7.5f));
		}
		livesGraphics.setParent(getCanvas());
		livesGraphics.setRelativeTransform(Transform.I.translated(0.0f, -1.0f));
		livesGraphics1.setParent(getCanvas());
		livesGraphics1.setRelativeTransform(Transform.I.translated(0.0f, -1.0f));
	}

	// permet de mettre à jour le nombre de vie
	public void updateLives(boolean gain) {
		if (gain && lives < 4) {
			++lives;
		}
		if (!gain && lives > 0) {
			--lives;
		}
		if (!gain && lives == 0) {
			bikeIsDead = true;
		}
		String scoreString1 = Integer.toString(lives);
		livesVisual.setText(scoreString1);
		setLivesGraphics();
		livesVisual.setText(scoreString1);
	}

	// permet d'immobiliser le cycliste
	public void freeze() {
		freeze = true;
		freezeTimer = 0;
	}

	// permet de mettre à jour le score
	public void updateScore(int points) {
		score += points;
		String scoreString = Integer.toString(score);
		scoreVisual.setText(scoreString);
	}

	public void update(float deltaTime) {
		super.update(deltaTime);

		freezeTimer += deltaTime;
		if (freeze) { // immobilise le joueur
			getPlayLoad().setVelocity(Vector.ZERO);
		}
		// immobilise le joueur pendant un certain temps
		if (freezeTimer > 1.5f) {
			freeze = false;
		}

		// permet d'ajouter des crates
		if (((Window) getCanvas()).getKeyboard().get(KeyEvent.VK_C).isPressed()) {
			Crate crate4 = new Crate(this, false, getShiftedViewCenter(), "box.4.png", 4, 4);
			ajouterActor(crate4);
		}
		// permet d'ajouter des terrains
		if (((Window) getCanvas()).getKeyboard().get(KeyEvent.VK_T).isPressed()) {
			Polyline polyline1 = new Polyline(getShiftedViewCenter(),
					getShiftedViewCenter().add(new Vector(8.0f, 0.0f)));
			/*
			 * pour le deuxième point de la polyline j'ai ajouter un vecteur à
			 * shiftedViewCenter permettant de modifier la longueur du bout de terrain
			 */
			Terrain terrain1 = new Terrain(this, polyline1, Color.darkGray, Color.GRAY);
		}

		// permet de passer au niveau suivant une fois la ligne d'arrivée franchie
		if (victory && currentLevel < (levels.size() - 1)) {
			nextLevel();
			victory = false;
		}
		// affiche un message de victoire si la ligne d'arrivée est franchie au niveau 3
		if (victory && currentLevel >= (levels.size() - 1)) {
			message.setText("Victory");
			message.draw(getCanvas());
		}

		// Restarts entire game when you click on R
		if (((Window) getCanvas()).getKeyboard().get(KeyEvent.VK_R).isReleased()) {
			removeAllActors();
			destroyAllActors();
			currentLevel = 0;
			score = 0;
			lives = 4;
			// interrupt Coundown and reset timer
			bikeIsDead = false;
			timer = 0;
			restartGame();
		}

		// skips the current level and goes to the nextLevl when you press on S
		if (((Window) getCanvas()).getKeyboard().get(KeyEvent.VK_S).isReleased()) {
			bikeIsDead = false;
			timer = 0;
			nextLevel();
		}

		// restarts only the current level of the game when you press on L
		if (((Window) getCanvas()).getKeyboard().get(KeyEvent.VK_L).isReleased()) {
			// interrupte Coundown and reset timer
			bikeIsDead = false;
			timer = 0;
			//lives = 4; shoudlnt do anything to lives ffs 
			// restarts the level without changing the current level
			resetLevel();
		}

		// quand le bike meurs et qu'il n'a plus de vie
		if (bikeIsDead && lives <= 1) {
			getPlayLoad().destroy();
			message.setText("Game Over");
			message.draw(getCanvas());
			message1.setText("Press 'R' to retry");
			message1.draw(getCanvas());
			lives = 0;
			setLivesGraphics();
			String scoreString1 = Integer.toString(lives);
			livesVisual.setText(scoreString1);
		}

		// dessines les différents affichages
		scoreGraphics.draw(getCanvas());
		livesGraphics1.draw(getCanvas());
		livesGraphics.draw(getCanvas());
		livesVisual.draw(getCanvas());
		scoreText.draw(getCanvas());
		// always display the score
		scoreVisual.draw(getCanvas());

		// if bike dies sets countdown to true and executes timer for automatic reset
		// of the level
		if (bikeIsDead && lives > 1) {
			// create countDownGraphics , a timer, a setCountDown and simply add the
			// setCoundown to bike when he hits
			// begin timer
			timer += deltaTime;

			if (timer > 0 && timer < 1) {
				countDownGraphics = new TextGraphics("3", 0.30f, Color.WHITE, Color.BLACK, 0.01f, true, false,
						new Vector(0.3f, -0.3f), 1.0f, 100.0f);
				countDownGraphics.setParent(getCanvas());
				countDownGraphics.setRelativeTransform(Transform.I.translated(0.0f, -1.0f));
				message.setText("WASTED");
				message.draw(getCanvas());
			}

			if (timer > 1 && timer < 2) {
				countDownGraphics.setText("2");
			}

			if (timer > 2 && timer < 3) {
				countDownGraphics.setText("1");
			}

			if (timer > 3) {
				// reset boolean
				bikeIsDead = false;
				// reset score to the score at the start of the level
				score = scoreStartOfLevel;
				// restarts the level without changing the currentLevel
				restartGame();
				// reset timer
				timer = 0;
				--lives; // le joueur perd une vie
				setLivesGraphics();
				String scoreString1 = Integer.toString(lives);
				livesVisual.setText(scoreString1);
			}
			countDownGraphics.draw(getCanvas());
		}
	}

	// permet de passer au niveau suivant
	@Override
	public void nextLevel() {
		scoreStartOfLevel = score;
		currentLevel++;
		removeAllActors();
		destroyAllActors();
		levels.get(currentLevel).createAllActors();
		ajouterActor(levels.get(currentLevel));
	}

	// permet de recommencer le même niveau
	@Override
	public void resetLevel() {
		score = scoreStartOfLevel;
		restartGame();
	}
}
