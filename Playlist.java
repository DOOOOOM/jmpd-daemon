/**
* Playlist.java
* The Playlist.java handles the playlist functionality
*/


class public Playlist{
 /*
	* Main Constructor function handles the opening
	* of the playlist as the daemon is opened
	* pre-condition: playlist file must exist
  * post-condition: playlist path is instantiated
  * Exception: IOError
	*/
	public Playlist(String PlaylistFolderFilePath);

	/*
	* Creates a new playlist
	* pre-conditions: jMPD is listening on the configured port
	* the database is accessible
	* the jMPC is connected to hMPD on the same configured port
	* the playlist directory is writable
	* post-condition: the client GUI displays the information requested by the user.
	* exceptions: the daemon is not running
	* the client is not connected to the same port as jMPD
	* the user string matches a playlist that already exists.
	* displays error message and allows the user to retry.
	*/
	public void newPlaylist(String playlistName);

	/*
	* Deletes a playlist from the playlist folder.
	* pre-condition: jMPD is listening on the configured port
	* the database is accessible
	* the jMPC is connected to hMPD on the same configured port
	* the playlist directory is writable
	* post-condition: the client GUI displays the information requested by the user.
	* exceptions: The daemon is not running.
	* the client is not connected to the same port as jMPD.
	* the playlist directory is not writable.
	*/
	public void deletePlaylist(String playlistName);

	/*
	* Takes an array of songs and adds them to a playlist
	* pre-condition: jMPD is listening on the configured port
	* the database is accessible
	* the jMPC is connected to hMPD on the same configured port
	* the playlist directory is writable
	* post-condition: the client GUI displays the information requested by the user.
	* exceptions: The daemon is not running.
	* the client is not connected to the same port as jMPD.
	* the playlist directory is not writable.
	*/
	public Void addToPlaylist(String[] songs, String playlistName);

	/*
	* Removes the track from the playlist.
	* pre-condition: jMPD is listening on the configured port
	* the database is accessible
	* the jMPC is connected to hMPD on the same configured port
	* the playlist directory is writable
	* post-condition: the client GUI displays the information requested by the user.
	* exceptions: The daemon is not running.
	* the client is not connected to the same port as jMPD.
	* the playlist directory is not writable.
	*/
	public Void removeFromPlaylist(String playListName, int trackID);

}
