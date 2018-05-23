// Sijia Xiao
// (AE) Yuma Tou
// # HW7
// This class is to construct a single node of a binary tree of Strings.
public class QuestionNode {
   public String data;
   public QuestionNode left;
   public QuestionNode right;
   
   // Post: constructs a leaf node with given data.
   public QuestionNode(String data) {
      this(data, null, null);
   }
   
   // Post: constructs a branch node with given data, left subtree,
//       right subtree
   public QuestionNode(String data, QuestionNode left, QuestionNode right) {
      this.data = data;
      this.left = left;
      this.right = right;
   }
}