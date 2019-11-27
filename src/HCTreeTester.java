import org.junit.Test;

import static org.junit.Assert.*;

public class HCTreeTester {

    @Test
    public void buildTree() {
        int[] freq = new int[256];
        freq['a'] = 17;
        freq['b'] = 8;
        freq['c'] = 7;
        freq['d'] = 14;
        freq['e'] = 9;
        freq['f'] = 1;
        freq['\n'] = 1;
        HCTree test = new HCTree();
        test.buildTree(freq);
        test.inorder(test.getRoot());
    }

}