import java.util.ArrayList;


/**
 * HTable class outlines the data fields and methods of a new hash table object
 * such as the methods for insertion deletion and searching of keys in the
 * hash table. It contains an enumerated type HashingType which specifies
 * which of the two hashing options the table is to use.
 */
public class HTable {


    enum HashingType {
        LinearProbing, DoubleHashing;
    }


    private HashingType t;
    private int size;
    private int numKeys;
    private int[] keys;


    /**
     * The default constructor for HTable, takes a int as input to set the size
     * of the new hash table and sets all keys to their null value 0.
     */
    public HTable(int size) {
        this.t = HashingType.LinearProbing;
        this.size = size;
        this.keys = new int[size];
        this.numKeys = 0;
        for (int i = 0; i < size; i++) {
            keys[i] = 0;
        }
    }


    /**
     * Sets the hashing type to either linear probing or double hahsing.
     * 
     * @param i - 0 for linear probing, 1 for double hashing.
     */
    public void setHashingType(int i) {
        if (i == 0) {
            t = HashingType.LinearProbing;
        } else if (i == 1) {
            t = HashingType.DoubleHashing;
        }
    }


    public Boolean usesLinearProbing() {
        if (t == HashingType.LinearProbing) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Accessor method for returning the size of a hash table.
     * 
     * @return size - the size of the hash table.
     */
    public int getSize() {
        return this.size;
    }


    /**
     * Accessor method returns the value at a given position in the hash table.
     * 
     * @param position - the location in the table to access.
     * @return int - the value at that position.
     */
    public int getKeyValue(int position) {
        if (keys[position] != 0) {
            return keys[position];
        }
        return 0;
    }


    /**
     * Accessor method for the step, or the result of the second hash function.
     * 
     * @param key - the value for whiuch we are calculating the hash step.
     * @return int - the step.
     */
    public int getStep(int key) {
        return (3 * key) + 1;
    }


    /**
     * insert attempts to place new values into the hash table and returns 
     * their position including all intermediary positions.
     * 
     * @param key - the new value to place in the hash table.
     * @return ArrayList - all of the positions the key hashed too including
     *                     its final position.
     */
    public ArrayList<Integer> insert(int key) {
        int index = key % size;
        int collisions = 0;
        int step = getStep(key);
        ArrayList<Integer> intermediateResults = new ArrayList<Integer>();

        while (collisions < size) {
            if (keys[index] == 0) {
                keys[index] = key;
                numKeys++;
                intermediateResults.add(index);
                return intermediateResults;
            } else {
                if (t.equals(HashingType.LinearProbing)) {
                    System.err.println("Linear");
                    intermediateResults.add(index);
                    index = ++index % size;
                    collisions++;
                } else {
                    intermediateResults.add(index);
                    System.err.println("Double");
                    index = (index + step) % size;
                    collisions++;
                }
            }
        }
        System.out.println("Cannot insert, Hash table is full!");
        return intermediateResults;
    }


    /**
     * Search looks for the specified key in the hash table.
     * 
     * @param key - the value to look for.
     * @return int - the position of the element or -1 if it was not found.
     */
    public int search(int key) {
        int index = key % size;
        int collisions = 0;
        int step = getStep(key);

        while (collisions < size) {
            if (keys[index] == key) {
                return index;
            } else {
                if (t.equals(HashingType.LinearProbing)) {
                    index = ++index % size;
                    collisions++;
                } else {
                    index = (index + step) % size;
                    collisions++;
                }
            }
        }
        return -1;
    }


    /**
     * delete finds an element and removes it from the hash table if it exists.
     * 
     * @param key - the value to remove.
     */
    public void delete(int key) {
        int index = key % size;
        int collisions = 0;
        int step = getStep(key);

        while (collisions < size) {
            if (keys[index] == key) {
                keys[index] = 0;
                return;
            } else {
                if (t.equals(HashingType.LinearProbing)) {
                    index = ++index % size;
                    collisions++;
                } else {
                    index = (index + step) % size;
                    collisions++;
                }
            }
        }

        System.out.println("Item not found");
    }



    /**
     * Used to display the contents of the Hash Table on the command line 
     */
    public void printHTable() {
        for (int i = 0; i < size; i++) {
            if (keys[i] != 0) {
                System.out.println("Value at " + i + ": " + keys[i]);
            }
        }
    }
}