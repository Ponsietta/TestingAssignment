package tests.modelling_tests;

/**
 * The KellimniMessagingStates enum contains the states that an account 
 * can be in with regards to being allowed to send messages or not. 
 * This is in respect to the violation which may occur when a user sends 
 * more than 10 messages in a minute. 
 */

public enum KellimniMessagingState {
	ENABLED,
	DISABLED
}
