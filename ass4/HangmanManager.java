// Sijia Xiao
// (AE) Yuma Tou
// HW4
// This program allows users to play the game of hangman and keeps track of
// state of this game.

import java.util.*;

public class HangmanManager {
   private Set<String> wordSet; // current set of words
   private int guessLeft; // number of guesses left
   private Set<Character> guessedLetter; // current set of letters that have been guessed
   private String currPattern; // current pattern that has been chosen
   
   // Pre: word length >= 1 and maximum number of wrong guesses >= 0 (throws
   // IllegalArgumentException otherwise).
   // Post: initialize the game. Add words of given length to the current set.
   // Set current pattern contains only "-" and spaces.
   public HangmanManager(Collection<String> dictionary, int length, int max) {
      if(length < 1 || max < 0) {
         throw new IllegalArgumentException("Wrong word length or maximum guesses!");
      }
      wordSet = new TreeSet<String>();
      for(String word : dictionary) {
         if(word.length() == length){
            wordSet.add(word);
         }
      }
      guessLeft = max;
      guessedLetter = new TreeSet<Character>();
      currPattern = "-";
      for(int i = 0; i < length - 1; i++) {
         currPattern += " -";
      }
   }
   
   // Post: return current set of words being considered by the game.
   public Set<String> words() {
      return wordSet;
   }
   
   // Post: return number of guesses the player left.
   public int guessesLeft() {
      return guessLeft;
   }
   
   // Post: return current set of letters that have been guessed by the user.
   public Set<Character> guesses() {
      return guessedLetter;
   }
   
   // Pre: current set of words is not empty (throws IllegalStateException otherwise).
   // Post: return current word pattern being considered.
   public String pattern() {
      if(wordSet.isEmpty()) {
         throw new IllegalStateException("Set of words is empty!");
      }
      return currPattern;
   }
   
   // Pre: number of guesses left is greater than 1 and the set of words is nonempty.
   // characters being guessed was not guessed before. (throw IllegalArgumentException
   // otherwise).
   // Post: return number of occurrences of the guessed letter in the new pattern.
   // Update set of letters being used.
   public int record(char guess) {
      if(guessLeft < 1 || wordSet.isEmpty()) {
         throw new IllegalStateException("Empty set or zero guess left!");
      }
      if(guessedLetter.contains(guess)) {
         throw new IllegalStateException("Letter has already been guessed!");
      }
      guessedLetter.add(guess);
      Map<String, Set<String>> map = new TreeMap<String, Set<String>>();
      buildMap(map, guess);
      whichPattern(map);
      guessLeft(guess);
      return countOccurrence(guess);
   }
   
   // Post: build a map for words based on their patterns.
   private void buildMap(Map<String, Set<String>> map, char guess) {
      for(String word : wordSet) {
         String pattern = currPattern;
         int wordLen = word.length();
         for(int i = 0; i < wordLen - 1; i++) {
            if(word.charAt(i) == guess) {
               pattern = pattern.substring(0, 2 * i) + guess + pattern.substring(2 * i + 1);
            }
         }
         if(word.endsWith("" + guess)) {
            pattern = pattern.substring(0, 2 * (wordLen - 1)) + guess;
         }
         if(!map.containsKey(pattern)){
            map.put(pattern, new TreeSet<String>());
         }
         map.get(pattern).add(word);
      }
   }
   
   // Post: Update set of words being considered and current pattern. 
   private void whichPattern(Map<String, Set<String>> map) {
      int max = 0;
      for(String k : map.keySet()){
         int setSize = map.get(k).size();
         if(setSize > max) {
            max = setSize;
            wordSet = map.get(k);
            currPattern = k;
         }
      }
   }
   
   // Post: Update guesses that the user left.
   private void guessLeft(char guess) {
      if(currPattern.indexOf("" + guess) == -1) {
         guessLeft--;
      }
   }
   
   // Post: return number of occurrences of the guessed letter in the new pattern.
   private int countOccurrence(char guess) {
      int letterOcurr = 0;
      for(int i = 0; i < currPattern.length(); i++) {
         if(currPattern.charAt(i) == guess) {
            letterOcurr++;
         }
      }
      return letterOcurr;
   }
}