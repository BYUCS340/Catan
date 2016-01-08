In order to view or play the game, you have to have the server running. 

We may try to standardize the game across multiple browsers, but for now, it works only in Chrome.

Once the server is running, you can use the following URLs (assuming its running on localhost):

***Gameplay***

localhost:8081/login.html
		Username is first name camel case, password is first name lower case.
		Current existing users are Brooke, Sam, Pete, Mark.
		You can also register a new user.
		Once you've been validated, you're redirected to the join game page.

localhost:8081/joinGame.html
		Pre-existing games are displayed in a table.
		You can join a game or create a new one. When you join a game, you choose a color and then are redirected to a 
		waiting page, where you can see how many players have joined, their names, and their colors. This page dynamically updates.
		As soon as the final player has joined, the page directs to the appropriate page based on current game state. 
			(either the setup page or the play page)

localhost:8081/setup.html
		This is where you do the first two rounds of the game (placing a road then a settlement).
		Once you've finished that, you're automatically redirected to the game page.
		
localhost:8081/catan.html
		You play the game from here. See the Functional Spec for details.

***Debugging/testing****

Since it's hard to effectively test things without being able to cheat and play multiple people, we've set up a special config page.

localhost:8081/serverConfig.html

	The top-right corner has player info. Select a game using the drop down box. From there, you can select one of the players in the game
	using the player drop down box. Anytime you select a valid player, you identiy cookie is updated.
			
	You can then navigate to any gameplay page using the buttons to the right.	
	*You don't need to click anything for the game's player state to change. Just selecting the appropriate option works.
	
	
	The rest of the page is to control the game state.
	
	While playing the game, you can retrieve all the commands that have gone to the server by clicking the "fetch" button.
	This loads commands into the "Command History" area and displays the corresponding JSON representation in the "Load Commands from JSON"
	area.
	
	You can alter the state of the game in several ways.
	You can load all the previous commands and alter them individually by editing or removing them, then choosing to "use" or "send" them.
		(use means use from beginning of the game, send means append to the current state)
	You can also just create new commands by clicking the "+" button in the bottom left corner. Then you can use or send them.
	You can restart the current game as new.
	
	If you want to edit the JSON itself, you can do that and then "load" the JSON, which lets you add it to the command history on the left.
	
	Don't worry about the "Command Execution Config" area in the top-left corner for now. That's currently a non-functional system.
	
