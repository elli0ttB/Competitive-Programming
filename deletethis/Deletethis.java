import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.OutputStream;

public class Deletethis {
    Kattio io;
    int width;
    int height;
    Icon[] doDelete;
    Icon[] dontDelete;

    // instantiates all of the variables
    public Deletethis(){
        io = new Kattio(System.in);

        height = io.getInt() + 14;
        width = io.getInt() + 8;

        doDelete = new Icon[io.getInt()];
        dontDelete = new Icon[io.getInt()];

        for (int i = 0; i< doDelete.length; i++)
            doDelete[i] = new Icon(io.getInt() + 7, io.getInt() + 4);
        
        for (int i = 0; i< dontDelete.length; i++)
            dontDelete[i] = new Icon(io.getInt() + 7, io.getInt() + 4);
    }

    class Icon {
       public int row;
       public int col;

       public Icon(int row, int col){
           this.row = row;
           this.col = col;
       }

       public boolean onScreen(){
           return 0 <= row && row <= height &&
                  0 <= col && col <= width
       }

    }
    
    
    public static void main(String[] args){
        new Deletethis();
    }

}

class Kattio extends PrintWriter {
    public Kattio(InputStream i) {
	super(new BufferedOutputStream(System.out));
	r = new BufferedReader(new InputStreamReader(i));
    }
    public Kattio(InputStream i, OutputStream o) {
	super(new BufferedOutputStream(o));
	r = new BufferedReader(new InputStreamReader(i));
    }

    public boolean hasMoreTokens() {
	return peekToken() != null;
    }

    public String peekWord(){
        return peekToken();
    }

    public int getInt() {
	return Integer.parseInt(nextToken());
    }

    public double getDouble() { 
	return Double.parseDouble(nextToken());
    }

    public long getLong() {
	return Long.parseLong(nextToken());
    }

    public String getWord() {
	return nextToken();
    }



    private BufferedReader r;
    private String line;
    private StringTokenizer st;
    private String token;

    private String peekToken() {
	if (token == null) 
	    try {
		while (st == null || !st.hasMoreTokens()) {
		    line = r.readLine();
		    if (line == null) return null;
		    st = new StringTokenizer(line);
		}
		token = st.nextToken();
	    } catch (IOException e) { }
	return token;
    }

    private String nextToken() {
	String ans = peekToken();
	token = null;
	return ans;
    }
}
