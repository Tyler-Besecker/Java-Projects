//File: Hangman.java
//Author: Tyler Besecker
//Date: April 16th 2016

import java.io.*;
import java.util.*;

public class Hangman {
    
    public static void main(String args[]) throws FileNotFoundException{
	intro(); //Prints intro
	playGame(); // Runs the game
    }    
    public static void intro(){
	System.out.println();
	System.out.println("Program plays a game of hangman.");
	System.out.println("A random word will be chosen and you will guess letters.");
	System.out.println("For each letter you get wrong, another piece of the man");
	System.out.println("will be drawn. When He is completely drawn,");
	System.out.println("you have lost the game. A list of letters you have");
	System.out.println("already tried will be at the bottom of the figure");
	System.out.println("Written by Tyler Besecker");
	System.out.println();

	System.out.println("| | | | __ _ _ __   __ _ _ __ ___   __ _ _ __  "); 
	System.out.println("| |_| |/ _` | '_ \\ / _` | '_ ` _ \\ / _` | '_ \\ ");
        System.out.println("|  _  | (_| | | | | (_| | | | | | | (_| | | | |");
        System.out.println("|_| |_|\\__,_|_| |_|\\__, |_| |_| |_|\\__,_|_| |_|");
        System.out.println("                    |___/ ");
	
    }
    

    public static String getWord(ArrayList<String> wordslist){
	String word = "";
	Random numgen = new Random(); //creates a random num generator 
	int number = numgen.nextInt(wordslist.size()) + 0; // uses the wordlist size to determine numbers

	word = wordslist.get(number);
	
	return word; // returns a random word
    }
    
    public static void playGame() throws FileNotFoundException //handles the bulk of execution 
    {
	ArrayList<String> words = new ArrayList<String>(); // new Arraylist to carry words 
	
	System.out.println("Enter the name of a file of words: ");
	System.out.println();
	
	Scanner sc = new Scanner(System.in); // scanner named sc
	
	String filename = sc.nextLine(); //reads in scanner line
	Scanner infile = new Scanner(new File(filename)); 
	
	while (infile.hasNext()) //writes file of words into arraylist
	    { //SO while
		try
		    {
			words.add(infile.nextLine());
		    }
		catch (NoSuchElementException ex)
		    {
			System.out.println("Failed to write");
		    }
		
	    }//EO while
	infile.close();
	
	String word = getWord(words); //returns a single random word from arraylist
	word = word.toUpperCase(); //makes word uppercase
	
	//Used variables
	int wordLen = word.length(); //length of random word
	int guesses = 0;
	int correctGuesses = 0;
	
	StringBuffer dashes; // number of printed dashes 
	StringBuffer guessedChars; 
	
	Boolean game = true;
	
	String guesslist = "";
	String allLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
	String StringedGuess = "";
	
	dashes = Dashes(word); // prints dashes for current word
	guessedChars = Guessed(allLetters); //prints dashes for current used letters

	System.out.println("The word to guess has " + wordLen + " letters:" + dashes);
	
	while (game == true) //runs the game until win or loss
	    { //Start of game
		
		System.out.print("Enter your letter guess (a-z): ");
		
		StringedGuess = sc.next(); //reads in guess

		StringedGuess = StringedGuess.toUpperCase(); //makes guess uppercase
		char guess = validGuess(StringedGuess, sc, guesslist); //makes sure guess is valid
		String guess2 = Character.toString(guess); 
		
		
		if(word.contains(guess2)) // if the guess is correct
		    {
			dashLetter(word, dashes, guess); //prints out
			
			int counter = 0; // counter to test if char appears more than once
			for( int i=0; i<word.length(); i++ ) {
			    if( word.charAt(i) == guess ) {
				counter++;
			    } 
			}
			if (counter > 1)
			    correctGuesses = correctGuesses * counter; //if letter is in word more than once increase count
			else 
			    correctGuesses++;
		    }
		else //if the guess is wrong
		    {
			guesses++;
		    }
		if(guesses == 6) //if the hangman is fully built end game
		    {
			word = word.toLowerCase(); //turns word into lowercase
			System.out.println("You failed to guess the word " + word);
			game = false;
		    }
		if (correctGuesses == word.length()) //if you guess correctly win the game
		    {
			word = word.toLowerCase(); // turns word into lowercase
			System.out.println("You guessed the word " + word + "!");
			game = false;
		    }
		
		System.out.println(dashes); // prints dashed line of word
		System.out.println(hangmanModel(guesses)); //prints current hangman model
		System.out.println();
		System.out.println(guessedChars); //prints your guessed characters 
		
		guesslist = guesslist + guess; //adds guess to the line
		dashLetter(allLetters, guessedChars, guess); //adds letter to word line
		
	    } //End of game
	
    } //EO playGame
    
    public static String hangmanModel(int guesses)
    {
	String Hangman = ""; //creates hangman model
	char rightLeg = '\\'; 
	
	switch(guesses) //prints correct amount of pieces per incorrect guesses
	    {
	    case 1: Hangman = Hangman + "  O  ";
		return Hangman;
	    case 2: Hangman = Hangman + "  O  " + "\n" + "  |  ";
		return Hangman;
	    case 3: Hangman = Hangman + "  O  " + "\n" + "  |  " + "\n" + " /";
		return Hangman;
	    case 4: Hangman = Hangman + "  O  " + "\n" + "  |  " + "\n" + " / " + rightLeg;
		return Hangman;
	    case 5: Hangman = Hangman + "  O  " + "\n" + " /|  " + "\n" + " / " + rightLeg;
		return Hangman;
	    case 6: Hangman = Hangman + "  O  " + "\n" + " /|" + rightLeg + "\n" + " / " + rightLeg;
		return Hangman;
		
	    }
	return "";
    }
    
    public static StringBuffer Guessed(String alph)
    {
	StringBuffer guessLetters = new StringBuffer(alph.length());
	for (int i = 0; i < alph.length(); i++)
	    guessLetters.append('-'); //creates a StringBuffer of our guessed letters
	return guessLetters;
    }
    
    public static void dashLetter(String word, StringBuffer dashes, char guess)
    {
	for (int i = 0; i < word.length(); i++)
	    {
		if (word.charAt(i) == guess)
		    dashes.setCharAt(i, guess); //creates a StringBuffer of our current word
		
	    }
    }
    
    public static StringBuffer Dashes(String word)
    {
	StringBuffer dashes = new StringBuffer(word.length()); //creates new StringBuffer of word len
	for (int i = 0; i < word.length(); i++)
	    dashes.append('-'); // creates a StringBuffer with correct amount of dashes
	return dashes;
    }
    
    public static char validGuess(String StringedGuess, Scanner sc, String guesslist)
    {
	Boolean guesstest = true;
	char guess = 0;
	
	if( StringedGuess.length() > 1) //checks if the guess is more than one in length
	    {
		System.out.println(StringedGuess + " is not a valid input. Try Again");
		guesstest = false;
	    }
	
	guess = StringedGuess.charAt(0); // converts guess to a character
	

	if (!Character.isLetter(guess)) //tests if character is a letter
	    {
		System.out.println(guess + " is not a letter. Try again.");
		guesstest = false;
	    }
	
	
	if (guesslist.contains(StringedGuess)) //checks if letter was already used
	    {
		System.out.println("You already guessed " + guess + " try again");
		guesstest = false;
	    }
	
	while (guesstest == false) //if the guess is greater than the length of one or not a letter Prompts to re-enter
	    {
		StringedGuess = sc.next(); //re-enter letter
		guess = StringedGuess.charAt(0);
		if (Character.isLetter(guess))
		    guesstest = true;
	    }
	
	return guess;
    }
    
} //EO File
