
import java.io.*;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * The Huffman Coding Tree
 */
public class HCTree {

    private static final int NUM_CHARS = 256; // alphabet size of extended ASCII
    private static final int BYTE_BITS = 8; // number of bits in a byte

    private HCNode root; // the root of HCTree
    private HCNode [] leaves = new HCNode[NUM_CHARS]; // the leaves of HCTree that contain all the symbols

    /**
     * The Huffman Coding Node
     */
    protected class HCNode implements Comparable<HCNode> {

        byte symbol; // the symbol contained in this HCNode
        int freq; // the frequency of this symbol
        HCNode c0, c1, parent; // c0 is the '0' child, c1 is the '1' child
        
        /**
         * Initialize a HCNode with given parameters
         * @param symbol the symbol contained in this HCNode
         * @param freq the frequency of this symbol
         */
        HCNode(byte symbol, int freq) {
            this.symbol = symbol;
            this.freq = freq;
            //todo
        }

        /**
         * Getter for symbol
         * @return the symbol contained in this HCNode
         */
        byte getSymbol() {
            return this.symbol;
            //todo
        }

        /**
         * Setter for symbol
         * @param symbol the given symbol
         */
        void setSymbol(byte symbol) {
            this.symbol = symbol;
            //todo
        }

        /**
         * Getter for freq
         * @return the frequency of this symbol
         */
        int getFreq() {
            
            //todo
            return this.freq;
        }

        /**
         * Setter for freq
         * @param freq the given frequency
         */
        void setFreq(int freq) {
            this.freq = freq;
            //todo
        }

        /**
         * Getter for '0' child of this HCNode
         * @return '0' child of this HCNode
         */
        HCNode getC0() {
            return c0;
            //todo
        }

        /**
         * Setter for '0' child of this HCNode
         * @param c0 the given '0' child HCNode
         */
        void setC0(HCNode c0) {
            this.c0 = c0;
            //todo
        }

        /**
         * Getter for '1' child of this HCNode
         * @return '1' child of this HCNode
         */
        HCNode getC1() {
            return this.c1;
            //todo
        }

        /**
         * Setter for '1' child of this HCNode
         * @param c1 the given '1' child HCNode
         */
        void setC1(HCNode c1) {
            this.c1 = c1;
            //todo
        }

        /**
         * Getter for parent of this HCNode
         * @return parent of this HCNode
         */
        HCNode getParent() {
            return this.parent;
            //todo
        }

        /**
         * Setter for parent of this HCNode
         * @param parent the given parent HCNode
         */
        void setParent(HCNode parent) {
            this.parent = parent;
            //todo
        }

        /**
         * Check if the HCNode is leaf
         * @return if it's leaf, return true. Otherwise, return false.
         */
        boolean isLeaf() {

            //todo
            return (c0 == null) && (c1 == null);
        }
        /**
         * Defines how object is printed in console
         */
        public String toString() {
            return "Symbol: " + this.symbol + "; Freq: " + this.freq;
        }

        /**
         * compareTo method
         */
        public int compareTo(HCNode o) {
            if (freq - o.freq != 0) {
                return freq-o.freq;
            }
            else {
                return symbol - o.symbol;
            }
        }
    }


    /**
     * todo: add javadoc
    */
    public HCNode getRoot() {
        
        //todo
        return this.root;
    }

    /**
     * todo: add javadoc
    */
    public void setRoot(HCNode root) {
        this.root = root;
        //todo

    }

    /**
     * todo: add javadoc
    */
    public void buildTree(int[] freq) {
        PriorityQueue<HCNode> que = new PriorityQueue<>();
        for(int i = 0;i<NUM_CHARS;i++){
            HCNode local = null;
            //we only update leave if the frequency is not null
            if(freq[i]!=0) {
                local = new HCNode((byte) i, freq[i]);
            }
            leaves[i] = local;
        }
        for(HCNode node : leaves){
            if(node!=null) {
                que.offer(node);
            }
        }
        while (que.size()>1){
            HCNode first = que.remove();
            HCNode second = que.remove();
            //adding frequency
            HCNode newNode = new HCNode(first.getSymbol(),
                    first.getFreq()+second.getFreq());
            //setting children
            newNode.setC0(first);
            newNode.setC1(second);
            first.setParent(newNode);
            second.setParent(newNode);
            //adding the combined node
            que.offer(newNode);
        }
        setRoot(que.peek());
        //todo
    }

    /**
     * todo: add javadoc
    */
    public void encode(byte symbol, BitOutputStream out) throws IOException{

        //todo
        int ascii = symbol & 0xff;
        HCNode target = leaves[ascii];
        ArrayList<Integer> bits = new ArrayList<>();
        //iteratively getting the assigned value(bits) of the byte
        while (target != getRoot()) {
            if (target.getParent().getC0() == target) {
                bits.add(0, 0);
            }
            else if(target.getParent().getC1() == target){
                bits.add(0, 1);
            }
            target = target.getParent();
        }
        //write out the bits
        for(Integer i:bits){
            out.writeBit(i);
        }
    }

    /**
     * todo: add javadoc
    */
    public byte decode(BitInputStream in) throws IOException{

        //todo
        HCNode curr = getRoot();
        //going down to the leaf
        while (! curr.isLeaf()){
            int bit = in.readBit();
            //deciding where to go
            if(bit == 0){
                curr = curr.getC0();
            }
            else if(bit == 1){
                curr = curr.getC1();
            }
        }
        // found the leaf node and return the symbol
        return curr.getSymbol();
    }

}
