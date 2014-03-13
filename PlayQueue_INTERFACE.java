/**
* PlayQueue.java
* The PlayQueue.java handles the playqueue functionality.
*/

public interface PlayQueue {
    /*
	* Main Constructor function handles the opening
	* of the playqueue as the daemon is opened
	* pre-condition: playqueue file must exist
    * post-condition: playqueue path is instantiated
    * Exception: IOError
	*/
	public PlayQueue(String PlayQueueFolderFilePath);

	/*
	* Adds selected tracks to the playqueue
	* pre-conditions: jMPD is listening on the configured port
	* the database is accessible
	* the jMPC is connected to hMPD on the same configured port
	* user is lookking at jMPC_Library_View
	* post-condition: the daemon's play queue now contains the user selected tracks.
	* the client's GUI reflects the changes in the play queue.
	* exceptions: the daemon is not running
	* the music directory cannot be accessed.
	* the database cannot be accessed.
	*/
	public void addToPlayQueue(String[] trackNames);

	/*
	* Removes selected tracks from the playqueue
	* pre-conditions: jMPD is listening on the configured port
	* the database is accessible
	* the jMPC is connected to hMPD on the same configured port
	* user is lookking at jMPC_Play_Queue_View
	* post-condition: the daemon's play queue now contains the user selected tracks.
	* the client's GUI reflects the changes in the play queue.
	* exceptions: the daemon is not running
	* the music directory cannot be accessed.
	* the database cannot be accessed.
	* the client is not connected to the same port as jMPD.
	*/
	public void removeFromPlayQueue(String[] trackNames);

	/*
	* Removes all tracks from the playqueue
	* pre-conditions: jMPD is listening on the configured port
	* the database is accessible
	* the jMPC is connected to hMPD on the same configured port
	* post-condition: the daemon's play queue no longer contains any tracks.
	* the client's GUI reflects the changes in the play queue.
	* exceptions: the daemon is not running
	* the client is not connected to the same port as jMPD.
	*/
	public void clearPlayQueue();

  /*
	* Creates a playlist from the play queue.
	* pre-conditions: jMPD is listening on the configured port
	* the database is accessible
	* the jMPC is connected to hMPD on the same configured port
	* the playlist directory is writable
	* the play queue is not empty
	* post-condition: the client GUI displays the updated playlist information
	* there is a new playlist file containing the play queue tracks.
	* exceptions: the daemon is not running.
	* the playlist directory is not writable.
	* the client is not connected to the same port as jMPD.
	*/
	public void playQueueToPLaylist();
}
