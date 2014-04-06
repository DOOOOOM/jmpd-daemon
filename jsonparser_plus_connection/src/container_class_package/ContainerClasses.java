package container_class_package;

public interface ContainerClasses {
	
	/**
	 * getObjectName: would return a string
	 * which would be the name of the Object
	 * NOTE: Names should be in camel notation.
	 * */
	public String getObjectName();
	
	/**
	 * isActive: would be implemented to for 
	 * container classes with dual states
	 * e.g the toggle container would need to have
	 * 2 state play and pause. 
	 * Another example would be when the next button
	 * is press the isActive should indicated this. 
	 * the default state is true
	 * @return true or false
	 */
    public boolean isActive();
    
    /**
     * dataList: would be used to house containers
     * the have list of options eg playlist.
     * */
    public java.util.List<String> dataList();
    
    /**
     * commandRespondedTo: would hold the command
     * to which the server is responding to 
     * eg. if getMemberName is the issuing command
     * to the server to return list of member
     * the commandRespondedTo would be getMemberName
     * */
    public String commandRespondedTo();
    
    /**
     * getResponseID: would be an ID associated with
     * the request for this data
     * @return response ID
     * */
    public int getResponseID();

}
