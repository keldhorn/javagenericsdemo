package com.generics.exercises;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class App {

    private List<? extends NaturalNumber> ln;
    private ArrayList<? extends NaturalNumber> aln;

    private List<? super EvenNumber> lnSuper;
    private ArrayList<? super EvenNumber> aen;

    public List<? super EvenNumber> getLnSuper() {
        return lnSuper;
    }

    public ArrayList<? super EvenNumber> getAen() {
        return aen;
    }

    public App() {
        EvenNumber en1 = new EvenNumber(2);
        ln = Arrays.asList(en1);
        aln = new ArrayList<>(ln);
        lnSuper = Arrays.asList(en1);
        aen = new ArrayList<>(lnSuper);
    }

    private void addToLn() {
        fooHelper(ln);
    }

    private void addToAln() {
        class Helper{
            static <T extends NaturalNumber> void fooHelperAln(ArrayList<T> l) {
                // l.add(new NaturalNumber(10)); // compile-time error
                try{
                    System.out.println("Adding null to ArrayList<? extends NaturalNumber> in Helper on line: 41");
                    l.add((T) new NaturalNumber(10)); // OK at compile-time unchecked cast warning no exception at runtime
                } catch (UnsupportedOperationException e) {
                    System.out.println("null cannot be added to ArrayList<? extends NaturalNumber> in Helper on line: 41");
                }
                
            }
        }
        Helper.fooHelperAln(aln);
        fooHelperAln(aln);
    }

    private void addToLnSuper(List<? extends EvenNumber> l) {
        try{
            lnSuper.add(l.get(0)); // OK at compile-time throws UnsupportedOperationException at runtime            
        } catch (UnsupportedOperationException e){
            System.out.println("Unsupported Operation Exception caught while adding to lnSuper: " + e);
        }

        try{
            lnSuper.add(new EvenNumber(4)); // OK at compile-time exception at runtime
        } catch (UnsupportedOperationException e){
            System.out.println("Unsupported Operation Exception caught while adding to lnSuper: " + e);
        }
        
        //lnSuper.add(new NaturalNumber(4)); // compile-time error

        try{
            lnSuper.add((EvenNumber)new NaturalNumber(4)); // OK at compile-time, runtime exception
        } catch (ClassCastException e){
            System.out.println("Class Cast Exception caught while adding to lnSuper: " + e);
        }
    }

    private void addToAen(ArrayList<? extends EvenNumber> l) {
        try{
            aen.add(l.get(0)); // OK at compile-time no exception at runtime
        } catch (Exception e){
            System.out.println("Unsupported Operation Exception caught while adding to aen: " + e);
        }

        try{ 
            aen.add(new EvenNumber(4)); // OK at compile-time no exception at runtime
        } catch (Exception e){
            System.out.println("Unsupported Operation Exception caught while adding to aen: " + e);
        }

        //aen.add(new NaturalNumber(4)); // compile-time error

        try{
            aen.add((EvenNumber)new NaturalNumber(4)); // OK at compile-time, runtime exception 
        } catch (ClassCastException e){
            System.out.println("Class Cast Exception caught while adding to aen: " + e);
        }
    }

    // wildcard capture helper method
    private <T> void fooHelperAln(ArrayList<T> l) {
        l.set(l.size() - 1, l.get(0)); // no exception at runtime
        // l.set(0, new NaturalNumber(7)); compile-time error without cast
        l.set(0, (T) new NaturalNumber(7)); // No exception at runtime, unchecked cast warning        
    }

    // wildcard capture helper method
    private <T> void fooHelper(List<T> l) {

        /*
         * java.util.List boolean add(E e)
         * 
         * Appends the specified element to the end of this list (optional operation).
         * 
         * Lists that support this operation may place limitations on what elements may
         * be added to this list. In particular, some lists will refuse to add null
         * elements, and others will impose restrictions on the type of elements that
         * may be added. List classes should clearly specify in their documentation any
         * restrictions on what elements may be added.
         */

        try {
            l.add(null); // OK at compile-time throws UnsupportedOperationException at runtime
        } catch (UnsupportedOperationException e) {
            System.out.println("null cannot be added to List<? extends NaturalNumber>");
        } catch (Exception e) {
            System.out.println("Some other exception caught: " + e);
        }

        // l.add(new NaturalNumber(7)); Compile-time error

        try {
            // unchecked cast warning
            l.add((T) new NaturalNumber(7)); // OK at compile-time throws UnsupportedOperationException at runtime
        } catch (UnsupportedOperationException e) {
            System.out.println("NaturalNumber cannot be added to List<? extends NaturalNumber>");
        } catch (Exception e) {
            System.out.println("Some other exception caught: " + e);
        }

        // l.add(new EvenNumber(6)); Compile-time error

        try {
            // unchecked cast warning
            l.add((T) new EvenNumber(6)); // OK at compile-time throws UnsupportedOperationException at runtime
        } catch (UnsupportedOperationException e) {
            System.out.println("EvenNumber cannot be added to List<? extends NaturalNumber>");
        } catch (Exception e) {
            System.out.println("Some other exception caught: " + e);
        }

        // ArrayList allows adding nulls
        aln.add(null); // OK at compile-time and runtime
        System.out.println(
                "null added to ArrayList<? extends NaturalNumber> aln successfully size of aln: " + aln.size());

        System.out.println("\n The first element in aln is: ");                
        aln.get(0).printNumber();

        try {
            aln.get(1).printNumber(); // runtime NullPointerException
        } catch (NullPointerException e) {
            System.out.println("NullPointerException caught ");
        } catch (Exception e) {
            System.out.println("Some other exception caught: " + e);
        }

        // aln.set(0, new NaturalNumber(7)); compile-time error

    }

    public List<? extends NaturalNumber> getLn() {
        return ln;
    }

    public ArrayList<? extends NaturalNumber> getAln() {
        return aln;
    }

    public static void main(String args[]) {
        
        System.out.println("Generics Exercise - Wildcards with extends\n");
        App app = new App();
        
        app.addToLn();

        System.out.println("\n");

        app.addToAln();
        
        System.out.println("\n");

        List<? extends NaturalNumber> ln = app.getLn();
        
        int index = 1;
        
        System.out.println("\n");

        for (NaturalNumber nn : ln) {
            System.out.println("Element at index " + index++ + ": ");
            if(nn!=null)
                nn.printNumber();
            else
                System.out.println("null element in ln");
        }

        System.out.println("\n");

        ArrayList<? extends NaturalNumber> aln = app.getAln();

        index = 1;

        for (NaturalNumber nn : aln) {
            System.out.println("Element at index " + index++ + ": ");
            if(nn!=null)
                nn.printNumber();
            else
                System.out.println("null element in aln");
        }
        
        System.out.println("\n");
        
        aln.get(0).printNumber();
        //app.addToLnSuper(ln); // Compile-time error
        app.addToLnSuper((List<? extends EvenNumber>)ln); // unchecked cast warning
        //addToAen(ArrayList<? extends EvenNumber> l); // Compile-time error
        app.addToAen((ArrayList<? extends EvenNumber>)aln); // unchecked cast warning
        System.out.println(app.getLnSuper().size());
        System.out.println(app.getAen().size());

        ArrayList<? super EvenNumber> aen = new ArrayList<>();

        //aen.add("Test string"); // compile-time error 

        aen.add(new EvenNumber(8)); // OK
        Object obj = new String("A test string");
        
        try{
            aen.add((EvenNumber)obj); // OK at compile-time, runtime exception
        } 
        catch (ClassCastException e){
            System.out.println("Class Cast Exception caught while adding to aen: " + e);
        }

        //aen.add(new NaturalNumber(10)); // compile-time error 

        class Helper{
            static <T> void addToAen(ArrayList<? super T> aen, T obj) {
                try{
                    System.out.println("Adding to aen in Helper on line: 199 " + obj);
                    aen.add((T) obj); // OK at compile-time, no exception
                } 
                catch (ClassCastException e) {
                    System.out.println("obj could not be added to aen in Helper");
                    System.out.println("Class Cast Exception caught while adding to aen in Helper: " + e);
                }
            }
        }

        Helper.addToAen(aen, new EvenNumber(12)); // OK
        //Helper.addToAen(aen, new NaturalNumber(13)); // compile-time error

        obj = new NaturalNumber(5);

        try{
            aen.add((EvenNumber)obj); // OK at compile-time, runtime exception
        } 
        catch (ClassCastException e){
            System.out.println("Class Cast Exception caught while adding to aen: " + e);
        }

        System.out.println("End of Generics Exercise - Wildcards with extends");

    }
}
