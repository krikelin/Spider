package com.krikelin.spider;

import java.util.Hashtable;

/**
Event args for RequestOverLayEventArgs
**/
public class OverlayEventArgs
{
    /// <summary>
    /// The view URI
    /// </summary>
    public String URI ;
    /// <summary>
    /// Folders for the views for each engine. You must provide it in the event attached
    /// by the MediaChrome Host (or apporiate implementation)
    /// </summary>
    private Hashtable<String, String> viewFolders ;
    public Hashtable<String, String> getViewFolders () {
    	return this.viewFolders;
    }
    /// <summary>
    /// Gets or sets if the operation should be cancelled or not
    /// </summary>
    public boolean cancel ;
}