import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.util.Arrays;

import java.io.IOException;

public class classy {
    final String thirtyEquals = "==============================";
    
    public static void main (String [] args){
        new classy();
    }

    public classy(){
        Kattio io = new Kattio(System.in);
        int tests = io.getInt();
        for (int i=0; i< tests; i++)
            sortPeople(io);
        io.flush();
        io.close();
    }

    public void sortPeople(Kattio io){

        int numNames = io.getInt();
        Person[] people = new Person[numNames];

        for(int i=0; i< numNames; i++){
            String name = io.getWord(); 
            name = name.substring(0, name.length() - 1); // get rid of semicolon
            
            String[] qualifiers = io.getWord().split("-");
            int rank = 0;
            
            for(int j = qualifiers.length - 1; j >= 0; j--){ // backwards for loop
                int increment;
                switch (qualifiers[j].charAt(0)){
                    case 'u' : increment = 0b11; // 3
                               break;
                    case 'm' : increment = 0b10; // 2
                               break;
                    case 'l' : increment = 0b01; // 1
                               break;
                    default: throw new IllegalArgumentException("expected word starting with u, l, or m"); // this never happens
                }
                rank = rank << 2 | increment ;
            }
           
            // at most 10 people, fill up to 10
            for(int j = qualifiers.length; j < 10; j++)
                rank = rank << 2 | 0b10;

            people[i] = new Person(rank, name);
            io.getWord(); // throw away 'class'
        }

        Arrays.sort(people);

        for (int i = numNames -1 ; i >= 0; i--) {
            io.println(people[i].name);
        }
        io.println(thirtyEquals);

    }

    class Person implements Comparable<Person>{
       private int rank;
       private String name;

       public Person(int rep, String nm){
           this.rank = rep;
           this.name = nm;
       }

       @Override
       public int compareTo(Person p){
           int diff = this.rank - p.rank;

           if (diff != 0)
               return diff;
           else
               return -1 * this.name.compareTo(p.name);
       }
    }

    class Student extends Person implements Comparable<Person>{
        public Student(int rep, String nm){
            super(rep, nm);
        }

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
