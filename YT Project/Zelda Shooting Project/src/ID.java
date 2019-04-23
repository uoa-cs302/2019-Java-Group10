//allocate ID for each entity
public enum ID {
	
	Player(),
	Block(),
	DirtTile(),
	Crate_healthPlus(), //powerup
	Crate_healthMinus(), //powerdown
	Crate_speedPlus(), //powerup
	Crate_speedMinus(), //powerdown
	Crate_ammoPlus(), //powerup
	Arrow(), //bullet
	Enemy(),  //random motion. Lizard?
	EnemySpider(), //spider enemy. Left to right motion
	EnemyBoss(), //lion. Following motion
	EnemyMultiplayer(),//multi-player enemy
	EnemyHunter(),
	ArrowHunter();
}
