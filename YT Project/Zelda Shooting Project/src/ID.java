//allocate ID for each entity
public enum ID {
	
	Player(),
	Block(),
	Crate(), //powerup
	Arrow(), //bullet
	Enemy(),  //random motion. Lizard?
	EnemySpider(), //spider enemy. Left to right motion
	EnemyBoss(), //lion. Following motion
	EnemyMultiplayer(); //multi-player enemy
}
