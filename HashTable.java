import java.util.Random;

public class HashTable {
    
    enum HashingType{
        LinearProbing, DoubleHashing, QuadraticProbing;
    }
    
    HashingType type;
    protected int size = 10;
    protected int numKeys = 0;
    protected int[] keys = new int[size];
    protected int[] elements = new int[size];
    protected Random rand = new Random();

    
    public HashTable(){
        type = HashingType.LinearProbing; // default linearprobing
        for(int i = 0; i < elements.length; i++) {
            elements[i] = rand.nextInt(100)+1;
        }
    }

    
    public String getHashingEquation(){
        if (this.type == HashingType.LinearProbing) {
            return "H(k,i) = (h(k) + i) % m";
        } else if (this.type == HashingType.DoubleHashing) {
            return "H(k,i) = (h(k) + i * g(k)) % m";
        } else {
            return "H(k,i) = (h(k) + i^2) % m";
        }
    }

    
    public void linearProbing() {
        this.type = HashingType.LinearProbing;
    }

    
    public void doubleHashing() {
        this.type = HashingType.DoubleHashing;
    }

    
    public void quadraticProbing() {
        this.type = HashingType.QuadraticProbing;
    }


    public int step(int element) {
        return 1 + (element % (size - 1));
    }

    
    public void insert(int element) {
        int index = element % size;
        int step = 0;
        int collisions = 0;
        if (numKeys == size){
            return;
        }

        if(type == HashingType.DoubleHashing){
            step = step(element);
        }
        
        index = index % size;
        while(keys[index] != 0) {
            collisions++;
            if (type == HashingType.LinearProbing) {
                index++;
                index = index % size;
            } else if (type == HashingType.QuadraticProbing) {
                index = element % size;
                index += (collisions*collisions);
                index = index % size;
            } else { // double hashing.
                index += step;
                index = index % size;
            }
        }
        if (keys[index] == 0) {
            keys[index] = element;
            numKeys++;
        }
    }

    
    public void clear() {
        for(int i = 0; i < size; i++){
            keys[i] = 0;
        }
        numKeys = 0;
    }

    
    public void print() {
        for(int i = 0; i < size; i++) {
            System.out.println(keys[i]);
        }
        System.out.println("-------------------------------");
    } 
}
