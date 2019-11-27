/**
 * name: Xin Yu
 * PID: A14494949
 */
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MyCompressor {

    private static final int NUM_CHAR = 256; // alphabet size of extended ASCII
    private String corpusLocation;
    private HCTree HuffmanTree;

    /**
     * This method will create the HCTree using the
     * frequencies of the characters in the corpus.
     */
    public static void main(String[] args) throws IOException {

        //todo
        //run compression and decompression here
        MyCompressor temp = new MyCompressor(args[0]);
        if(args[1].equals("compress")){
            //if compress, target the name and compress.
            for(int i = 2; i < args.length;i++){
                File f = new File(args[i]);
                String path1 = f.getPath().substring(0,f.getPath().length()-f.getName().length());
                String name2 = "compressed_"+f.getName();
                temp.compress(args[i],path1+name2);
            }
        }
        //if decompress.
        if(args[1].equals("decompress")){
            //get the target name and ready to decompress.
            for(int i = 2; i < args.length;i++){
                File f = new File(args[i]);
                String path1 = f.getPath().substring(0,f.getPath().length()-f.getName().length());
                String name2 = "decompressed_"+f.getName();
                temp.decompress(args[i],path1+name2);
            }
        }

    }


    /**
     * Constructor of my compressor and decompressor object
     * @param corpusLocation directory to corpus text file
     */
    public MyCompressor(String corpusLocation) throws  IOException {

        this.corpusLocation = corpusLocation;
        this.HuffmanTree = buildHuffmanTree(corpusLocation);
        //todo
    }

    /**
     * Helper Method
     * Finds count of each ascii character and builds Huffman tree
     * @param corpusLocation directory to corpus text file
     * @return reference to the built HCTree
     */
    private HCTree buildHuffmanTree(String corpusLocation) throws IOException{

        //todo
        byte[] in = Files.readAllBytes(Paths.get(corpusLocation));
        int[] freqAry = new int[NUM_CHAR];
        HCTree cp = new HCTree();
        for (int i = 0; i < in.length; i++) {
            freqAry[in[i] & 0XFF] ++;
        }
        cp.buildTree(freqAry);
        return cp;
    }

    /**
     * Compress Method
     * @param inputFile directory of file that is to be compressed.
     * @param compressedFile directory of compressed file.
     */
    public void compress(String inputFile, String compressedFile) throws IOException {
        
        //todo
        FileOutputStream comp = new FileOutputStream(compressedFile);
        DataOutputStream out  = new DataOutputStream(comp);
        BitOutputStream bOut = new BitOutputStream(out);
        byte[] input = Files.readAllBytes( Paths.get(inputFile));
        out.writeInt(input.length);
        for(int i = 0; i < input.length;i++){
            HuffmanTree.encode(input[i],bOut);
        }
        bOut.flush();
        out.close();
        comp.close();
    }

    /**
     * Decompress Method
     * @param compressedFile directory of compressed file.
     * @param outputFile directory of uncompressed file.
     */
    public void decompress(String compressedFile, String outputFile) throws IOException {
        
        FileInputStream inFile = new FileInputStream(compressedFile);
        DataInputStream in = new DataInputStream(inFile);
        BitInputStream bitIn = new BitInputStream(in);
        FileOutputStream outFile = new FileOutputStream(outputFile);
        DataOutputStream out = new DataOutputStream(outFile);

        int len = in.readInt();
        for(int i = 0; i < len;i++){
            out.writeByte(HuffmanTree.decode(bitIn));
        }
        inFile.close();
        in.close();
        out.close();
        outFile.close();
    }
}
