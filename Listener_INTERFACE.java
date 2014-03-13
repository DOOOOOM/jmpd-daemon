/**
 * The Listener class will allow the daemon to accept connections
 * and therefore receive (and sometimes send) messages to (and from)
 * the client. 
 */

public interface Listener {
	// constructor
	Listener(String daemonIP, int daemonPort);

	// allows client to connect to the daemon through
	// the listener object
	public int receiveConnection();

	// unpacks the message, routes information to the right places
	public int parseMessage(Message msg);

	// sends an error Message to the client if something unexpected happened
	public int sendError(int error);
}