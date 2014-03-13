/**
 * A player is a "capsule" that exhibits playback capabilities such as 
 * playing, pausing, stopping, skipping to next track, skipping to previous track. 
 *
 */

public interface Player
{
	public Player(InitialPlayqueue);
	/**
	 * Constructor
	 */

	private Playlist playqueue;
	/**
	 * playqueue is the playlist the player is going to operate playback 
	 * operations on.
	 */

	private int trackIndex;
	/**
	 * trackIndex indicates which track is currently being considered for playback,
	 * among the tracks in the playqueue
	 */

	boolean isPlaying;
	/**
	 * This boolean will help determine whether or not playback needs
	 * to start after a next() or previous() operation
	 */


	void play();
	/**
	 * This method will initiate playback of the playqueue starting at the first
	 * song that has not been played.
	 * @ Pre: the playqueue is not empty and is a valid playlist
	 * @ Post: an audio stream is played through the system's default audio out
	 */

	void updateIndexedTrack();
	/**
	 * This method will ensure that we can move from a track to the next
	 * after the current has been played in entirety, by updating the trackIndex.
	 * @ Pre: the playqueue is not empty and is a valid playlist
	 * @ Post: trackIndex > 0 and trackIndex < (length of the playqueue)
	 */

	void updatePlayqueue(Playlist newPlayqueue);
	/**
	 * This method will report changes made to the playqueue externally to the
	 * internal playqueue.
	 * @ Pre: the playqueue provided is not empty and is a valid playlist
	 * @ Post: the playqueue keeps its validity after update
	 */

	void stop();
	/**
	 * This method will pause the playback and go to the beginning
	 * of the current song.
	 * @ Pre: a song is being played by the player
	 * @ Post: audio streaming stops
	 */

	void pause();
	/**
	 * This method will pause the playback of the current song
	 * @ Pre: a song is being played by the player
	 * @ Post: audio streaming is resumed and ready to continue where left off.
	 */

	void next();
	/**
	 * This method will resume the playback of the current song, then move the trackIndex
	 * forward, and then recover the player's play state.
	 * @ Pre: the internal playqueue is not empty and is a valid playlist
	 * @ Post: the playqueue keeps its validity after update
	 * 
	 */

	void previous();
	/**
	 * This method will resume the playback of the current song, then move the trackIndex
	 * backward, and then recover the player's play state.
	 * @ Pre: the internal playqueue is not empty and is a valid playlist
	 * @ Post: the playqueue keeps its validity after update
	 */	
}
