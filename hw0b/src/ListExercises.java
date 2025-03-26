import java.util.List;
import java.util.ArrayList;

public class ListExercises {

    /** Returns the total sum in a list of integers */
	public static int sum(List<Integer> L) {
        // TODO: Fill in this function.
        int size = L.size();
        int sum = 0;

        for(Integer a : L)
        {
            sum += a;
        }
        return sum;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        // TODO: Fill in this function.
        List<Integer> list = new ArrayList();
        for(Integer a : L)
        {
            if(a % 2 == 0)
                list.add(a);
        }

        return list;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        // TODO: Fill in this function.
        List<Integer> list = new ArrayList();
        for(Integer a : L1)
        {
            if(L2.contains(a))
                list.add(a);
        }

        return list;
    }


    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        // TODO: Fill in this function.
        int count = 0;

        for(String w : words)
        {
            if(w.indexOf(c) != -1)
                count++;
        }
        return count;
    }
}
