// Sijia Xiao
// (AE) Yuma Tou
// HW3
// This program is to manage information flow of an Assassion game.
// Customers can keep track of people in the current killing pool and
// in the grave yard.

import java.util.*;

public class AssassinManager {
   private AssassinNode alive; // people currently alive
   private AssassinNode dead;  // people currently dead
   
   // Pre:  Assume that the name list is not empty (names.size() > 0, throws
   //       IllegalArgumentException otherwise)
   //       No duplicate names in the list.
   // Post: Construct an assassin manager object in which add names from the
   //       lists in the same order.
   //       current :: tracking node in alive.
   public AssassinManager(List<String> names) {
      if(names.size() == 0) {
         throw new IllegalArgumentException("Empty name list!");
      }
      alive = new AssassinNode(names.get(0));
      AssassinNode current = alive;
      for(int i = 1; i < names.size(); i++) {
         current.next = new AssassinNode(names.get(i));
         current = current.next;
      }
   }
   
   // Post: print the names of the people in the killing ring.
   public void printKillRing() {
      AssassinNode current = alive;
      while(current.next != null) {
         System.out.println("    " + current.name + " is stalking " + current.next.name);
         current = current.next;
      }
      System.out.println("    " + current.name + " is stalking " + alive.name);
   }
   
   // Post: print the names of people in the grave yard.
   // current :: tracking nodes in dead (in this method)
   public void printGraveyard() {
      if(dead != null) {
         AssassinNode current = dead;
         while(current != null) {
            System.out.println("    " + current.name + " was killed by " + current.killer);
            current = current.next;
         } 
      }
   }
   
   // Post: Return true if the person (ignore case) is still in the killing ring
   // or return false otherwise.
   public boolean killRingContains(String name) {
      return checkExist(alive, name);
   }
   
   // Post: Return true if the person (ignore case) is in the grave yard
   // or return false otherwise.
   public boolean graveyardContains(String name) {
      return checkExist(dead, name);
   }
   
   // Post: return true if there is only one person alive and return flase
   // otherwise.
   public boolean gameOver() {
      return (alive.next == null);
   }
   
   // Post: Return the name of the winner of the game and return null otherwise.
   public String winner() {
      if(alive.next == null) {
         return (alive.name);
      } else {
         return null;
      }
   }
   
   // Pre:  The name (ignore case) is in the current killing ring
   //       (throw IllegalArgumentException otherwise).
   //       The game is not over (throw IllegalStateException otherwise).
   // Post: Kill the person with the given name, transfering the person to
   //       the graveyard. 
   public void kill(String name) {
      if(!checkExist(alive, name)) {
         throw new IllegalArgumentException("Name doesn't exist!");
      }
      if(gameOver() == true) {
         throw new IllegalStateException("The game is over!");
      }
      AssassinNode current = alive;
      AssassinNode temp = dead;
      if(alive.name.toLowerCase().equals(name.toLowerCase())) { // kill the first person
         AssassinNode killerCur = alive; // another object to track nodes in alive
         while(killerCur.next != null) { // search for the last person
            killerCur = killerCur.next;
         }
         dead = alive;
         alive = alive.next;
         dead.next = temp;
         dead.killer = killerCur.name;
      } else {  // kill the others
         while(current.next != null) {
            if(current.next.name.toLowerCase().equals(name.toLowerCase())) {
               temp = dead;
               dead = current.next;
               current.next = current.next.next;
               dead.next = temp;
               dead.killer = current.name;
            } else {
               current = current.next;
            }
         }
      }
   }
   
   // Post: Return true if the person's name is in the current object and return
   // false otherwise.
   private boolean checkExist(AssassinNode current, String name) {
      while(current != null) {
         if(name.toLowerCase().equals(current.name.toLowerCase())) {
            return true;
         }
         current = current.next;
      }
      return false;
   }
   
}