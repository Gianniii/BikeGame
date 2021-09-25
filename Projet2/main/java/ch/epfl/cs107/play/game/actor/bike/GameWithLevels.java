package ch.epfl.cs107.play.game.actor.bike;

interface GameWithLevels {
	// gère ce qui se passe lorsque la transition au niveau suivant doit se faire :
	void nextLevel();

	// gère ce qui se passe lorsque l'on veut recommencer le niveau courant :
	void resetLevel();
}
