package com.lucazamador.drools.monitor.eclipse;

/**
 * Interface defining the application's command IDs. Key bindings can be defined
 * for specific commands. To associate an action with a command, use
 * IAction.setActionDefinitionId(commandId).
 * 
 * @see org.eclipse.jface.action.IAction#setActionDefinitionId(String)
 */
public interface ICommandIds {

    public static final String ADD_GRAPHIC = "addGraphic";
    public static final String ADD_MONITORING_AGENT = "addMonitoringAgent";
    public static final String REMOVE_GRAPHIC = "removeGraphic";
    public static final String REMOVE_MONITORING_AGENT = "removeMonitoringAgent";
    public static final String REMOVE_MONITORING_CONSOLE = "removeMonitoringConsole";
    public static final String RECONNECT_MONITORING_AGENT = "reconnectMonitoringAgent";

}