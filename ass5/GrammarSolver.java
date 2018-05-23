// Sijia Xiao
// (AE) Yuma Tou
// HW5
// This program will allow users to generate sentences based on the grammar rules
// in the file.

import java.util.*;

public class GrammarSolver {
   private Map<String, ArrayList<String>> grammarTree; // maps all grammar rules
   
   // Pre: Grammar list is not empty and there is only one entry for the same nonterminal.
   // (throws IllegalArgumentException otherwise)
   // Post: Initialize grammar map by putting all of keys/value pairs in the tree.
   public GrammarSolver(List<String> grammar) {
      if (grammar.isEmpty()) {
         throw new IllegalArgumentException("Empty List!");
      }
      grammarTree = new TreeMap<String, ArrayList<String>>();
      for (int i = 0; i < grammar.size(); i++) {
         String[] components = grammar.get(i).split("[::=|]+");
         if(!grammarTree.containsKey(components[0])) {
            grammarTree.put(components[0], new ArrayList<String>());
         }
         for(int j = 1; j < components.length; j++) {
            grammarTree.get(components[0]).add(components[j]);
         }
      }
      if (grammarTree.size() != grammar.size()) {
         throw new IllegalArgumentException("More than one entrys for the same nonterminal!");
      } 
   }
   
   // Post: return true if the given symbol is a nontermial of the grammar (return
   // false otherwise).
   public boolean grammarContains(String symbol) {
      return (grammarTree.keySet().contains(symbol));
   }
   
   // Pre: grammar should contain the given nonterminal symbol or the number of times
   // is greater than 0. (throw IllegalArgumentException otherwise)
   // Post: return certain number of phrases generated from the given rules.
   public String[] generate(String symbol, int times) {
      if (!grammarTree.keySet().contains(symbol) || times < 0) {
         throw new IllegalArgumentException("Wrong input arguments!");
      }
      String[] phrases = new String[times];
      Random rand = new Random();
      for (int i = 0; i < times; i++) {
         phrases[i] = oneSentence(symbol, rand).substring(1);
      }
      return phrases;
   }
   
   // Post: return a sorted comma-separated nonterminal list enclosed in square brackets.
   public String getSymbols() {
      return grammarTree.keySet().toString();
   }
   
   // Post: return an expression for a certain symbol governed by the grammar rule.
   private String oneSentence(String symbol, Random rand) {
      if (grammarTree.keySet().contains(symbol)) {
         String sentence = "";
         List<String> curRight = new ArrayList<String>();
         curRight = grammarTree.get(symbol);
         String curRules = curRight.get(rand.nextInt(curRight.size()));
         for (String singleRule : curRules.split("[ \t]+")) {
            if(!singleRule.equals("")) {
               sentence += oneSentence(singleRule, rand);
            }
         }
         return sentence;
      } else {
         return " " + symbol;
      }
   }
}