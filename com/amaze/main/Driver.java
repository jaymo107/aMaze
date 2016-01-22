package com.amaze.main;
import org.jsfml.system.*;

/**
 * This is a test comment! Please format comments in this style!
<<<<<<< Updated upstream
 * Pasha
=======
 * Matthew Frost
 * KIRAN WA5 HERE
>>>>>>> Stashed changes
 * JAY WAS HERE TOOOOOO, and he made a class.
 */

class Driver{

    public static void main(String[] args){

        Vector3f vec1 = new Vector3f(23f, 324f, 832f);
        Vector3f vec2 = new Vector3f(12f, 22f, 349f);
        Vector3f result = Vector3f.add(vec1, vec2);
    
        System.out.println("This should run without any errors if JSFML is used correctly:\n"+result.toString());
    }

}
