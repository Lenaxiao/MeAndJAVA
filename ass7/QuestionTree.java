// Sijia Xiao
// (AE) Yuma Tou
// # HW7
// This program is to play guessing game with users, which will ask players couple of
// questions and give the best answer. It can learn from wrong answers and store it in
// the file to read in the future.

import java.util.*;
import java.io.*;

public class QuestionTree {
   private QuestionNode overallRoot;
   
   // Post: construct a tree with one leaf node representing the "computer".
   public QuestionTree() {
      overallRoot = new QuestionNode("computer");
   }
   
   // Pre: the file is legal and in standard format.
   // Post: read file to the overall tree.
   public void read(Scanner input) {
      overallRoot = readHelper(input);
   }
   
   // Post: read file line by line to a given binary search tree.
   //       return current root.
   private QuestionNode readHelper(Scanner input) {
      char sign = input.nextLine().charAt(0);
      String str = input.nextLine();
      QuestionNode root = new QuestionNode(str);
      if (sign == 'Q') {
         root.left = readHelper(input);
         root.right = readHelper(input);
      } else {
         root.left = null;
         root.right = null;
      }
      return root;
   }
   
   // Post: store the overall tree to the output file.
   public void write(PrintStream output) {
      writeHelper(output, overallRoot);
   }
   
   // Post: store the tree in the output file with given root.
   private void writeHelper(PrintStream output, QuestionNode root) {
      if(root != null) {
         if (root.left == null && root.right == null) {
            output.println("A:");
         } else {
            output.println("Q:");
         }
         output.println(root.data);
         writeHelper(output, root.left);
         writeHelper(output, root.right);
      }
   }
   
   // post: asks the user a question, forcing an answer of "y" or "n";
   //       returns true if the answer was yes, returns false otherwise
   public boolean yesTo(String prompt) {
      System.out.print(prompt + " (y/n)? ");
      Scanner console = new Scanner(System.in);
      String response = console.nextLine().trim().toLowerCase();
      while (!response.equals("y") && !response.equals("n")) {
         System.out.println("Please answer y or n.");
         System.out.print(prompt + " (y/n)? ");
         response = console.nextLine().trim().toLowerCase();
      }
      return response.equals("y");
   }
   
   // Post: store current question tree to the overall tree.
   public void askQuestions() {
      Scanner console = new Scanner(System.in);
      overallRoot = askQuestions(overallRoot, console);
   }
   
   // Post: ask questions and search the tree for the best answer.
   //       if the answer is wrong, expand the tree to include the
   //       object and a new question to distinguish, and return it.
   private QuestionNode askQuestions(QuestionNode root, Scanner console) {
      if (root.left == null && root.right == null) {
         if (yesTo("Would your object happen to be " + root.data + "?")){
            System.out.println("Great, I got it right!");
         } else {
            System.out.print("What is the name of your object? ");
            String rightA = console.nextLine();
            System.out.println("Please give me a yes/no question that");
            System.out.println("distinguishes between your object");
            System.out.print("and mine--> ");
            String addQ = console.nextLine();
            if (yesTo("And what is the answer for your object?")) {
               root = new QuestionNode(addQ, new QuestionNode(rightA), root);
            } else {
               root = new QuestionNode(addQ, root, new QuestionNode(rightA));
            }
         }
      } else {
         if (yesTo(root.data)) {
            root.left = askQuestions(root.left, console);
         } else {
            root.right = askQuestions(root.right, console);
         }
      }
      return root;
   }
}