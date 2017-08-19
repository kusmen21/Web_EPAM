package com.epam.hostel.command;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.hostel.command.impl.EmptyCommand;

/**
 * @param request request from client side
 * @return Command defined in request to be executed, otherwise EmptyCommand
 */
public final class CommandFactory {
	private static Logger log = LogManager.getLogger(CommandFactory.class);
	private static final String COMMAND = "command";
	private static final String WARNING_COMMAND_NOT_FOUND = "Command not found ";
	
	public static Command defineCommand(HttpServletRequest request){
		Command current = new EmptyCommand();
		 String action = request.getParameter(COMMAND);
	        if (action == null || action.isEmpty()) {
	            return current;
	        }
	        try{
	        	CommandEnum commandEnum = CommandEnum.valueOf(action.toUpperCase());
	        	current = commandEnum.getCurrentCommand();
	        } catch (IllegalArgumentException e) {
	        	log.warn(WARNING_COMMAND_NOT_FOUND + action);
	        }	        
	    return current;    
	}
}
