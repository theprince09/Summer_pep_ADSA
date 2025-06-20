import java.rmi.MarshalException;

public class avl {
    Node root;
    static class Node{
        int data,height;
        Node left,right;
        Node(int data){
            this.data = data;
            this.left = this.right = null;
            this.height = 1;
        }        
    }
    avl(){this.root = null;}

    private int getHeight(Node root){
        if(root==null) return 0;
        else return root.height;
    }
    private int getbalance(Node root){
        if(root==null) return 0;
        else return getHeight(root.left) - getHeight(root.right);
    }
    public Node leftRotate(Node x){
        Node y = x.right, T2 = y.left;
        y.left  = x;
        x.right = T2;
        x.height = Math.max(getHeight(x.left),getHeight(x.right));
        y.height = Math.max(getHeight(y.left),getHeight(y.right));
        return y;
    }
    public Node RightRotate(Node y){
        Node x = y.left,T2 = x.right;
        x.right = y;
        y.left = T2;
        y.height = 1+ Math.max(getHeight(y.left), getHeight(y.right));
        x.height = 1+ Math.max(getHeight(x.left), getHeight(x.right));
        return x;
    }
    public void insert(int key){
        root = insertData(root,key);
    }
    private Node insertData(Node root,int key){
        if(root == null) return new Node(key);
        else if(key < root.data) root.left = insertData(root.left, key);
        else if(key > root.data) root.right = insertData(root.right, key);
        else return root;
        
        //Backtracking
        root.height = 1+ Math.max(getHeight(root.left), getHeight(root.right));
        int balance = getbalance(root);
        
        if(balance < -1 && key>root.data) return leftRotate(root);
        if(balance > 1 && key<root.data) return RightRotate(root);
        if(balance > 1 && key>root.left.data) {
            root.left = leftRotate(root.left);
            return RightRotate(root);
        }
        if(balance < -1 && key<root.right.data) {
            root.right = RightRotate(root.right);
            return leftRotate(root);
        }
        return root;
    }
    public void inorder(Node node) {
        if (node != null) {
            inorder(node.left);
            System.out.print(node.data + " ");
            inorder(node.right);
        }
    }

    // DELETE IN AVL TREE
    public void Delete(int key){
        root = deleteNode(root,key);
    }
    private Node deleteNode(Node node,int key){
        if(node == null) return root;
        
        if (key < node.data)
            node.left = deleteNode(node.left, key);
        else if (key > node.data)
            node.right = deleteNode(node.right, key);
        else {
            if ((node.left == null) || (node.right == null)) {
                Node temp = (node.left != null) ? node.left : node.right;
                if (temp == null) return null; 
                else node = temp; 
            } else {
                Node temp = minValueNode(node.right);
                node.data = temp.data;
                node.right = deleteNode(node.right, temp.data);
            }
        }

        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        int balance = getbalance(node);

        // Rebalance
        if (balance > 1 && getbalance(node.left) >= 0)
            return RightRotate(node);
        if (balance > 1 && getbalance(node.left) < 0) {
            node.left = leftRotate(node.left);
            return RightRotate(node);
        }
        if (balance < -1 && getbalance(node.right) <= 0)
            return leftRotate(node);
        if (balance < -1 && getbalance(node.right) > 0) {
            node.right = RightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }
    private Node minValueNode(Node node) {
        Node current = node;
        while (current.left != null)
            current = current.left;
        return current;
    }

    
    public static void main(String[] args) {
        avl AVL = new avl();
        AVL.insert(10);
        AVL.insert(20);
        AVL.insert(30);
        AVL.inorder(AVL.root);
        AVL.Delete(10);
        AVL.inorder(AVL.root);
        
    }
}
