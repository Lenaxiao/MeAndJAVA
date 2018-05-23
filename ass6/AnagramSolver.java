// Sijia Xiao
// (AE) Yuma Tou
// HW6
// This program takes a target word and finds all combinations of words in a dictionary.

import java.util.*;

public class AnagramSolver {
   private Map<String, LetterInventory> tree; // word-inventory pair
   
   // Pre: list is not empty, and there are no duplicates 
   // Post: constructs an anagram solver that uses the given list as its dictionary.
   public AnagramSolver(List<String> list) {
      tree = new HashMap<String, LetterInventory>();
      for (String word : list) {
         tree.put(word, new LetterInventory(word));
      } 
   }
   
   // Pre: maximum number of words is no less than 0 (throw IllegalArgumentException otherwise).
   // Post: print all combinations of words from dictionary and the number of the 
   // words is no greater than a given number.
   public void print(String s, int max) {
      if (max < 0) {
         throw new IllegalArgumentException();
      }
      Map<String, LetterInventory> subtree = new HashMap<String, LetterInventory>();
      LetterInventory target = new LetterInventory(s);
      for (String word : tree.keySet()) {
         LetterInventory temp = tree.get(word);
         if (target.subtract(temp) != null) {
            subtree.put(word, temp);
         }
      }
      Stack<String> result = new Stack<String>();
      print(target, max, subtree, max, result);
   }
   
   // Post: recursive backtracking to find combinations of words that have the same letters
   // as the given string.
   private void print(LetterInventory target, int max, Map<String,LetterInventory> subtree,
                      int times, Stack<String> result) {
      if (max == 0) {
         if (target.size() == 0) {
            System.out.println(result);
         } else {
            for (String word : subtree.keySet()) {
               LetterInventory temp = target.subtract(subtree.get(word));
               if (temp != null) { // safe
                  result.push(word); // choose
                  print(temp, max, subtree, times, result); // recursive
                  result.pop(); // unchoose
               }
            }
         }
      } else {
         if (target.size() == 0) {
            System.out.println(result);
         } else {
            for (String word : subtree.keySet()) {
               LetterInventory temp = target.subtract(subtree.get(word));
               if (times != 0 && temp != null) {
                  result.push(word);
                  print(temp, max, subtree, times - 1, result);
                  result.pop();
               }
            }
         }
      }
   }
}