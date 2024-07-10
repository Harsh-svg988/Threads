//import java.util.*;
//import java.util.concurrent.*;
//class Sorter implements Callable<ArrayList<Integer>> {
//    ArrayList<Integer> listToSort;
//    Sorter(ArrayList<Integer> listToSort) {
//        this.listToSort = listToSort;
//    }
//
//    @Override
//    public ArrayList<Integer> call() throws Exception {
//        if(listToSort.size()<=1) {
//            return listToSort;
//        }
//        int mid = listToSort.size()/2;
//        ArrayList<Integer>leftHalfToSort = getsubArray(listToSort,0,mid);
//        ArrayList<Integer>rightHalfToSort = getsubArray(listToSort,mid,listToSort.size());
//
//        ExecutorService es = Executors.newFixedThreadPool(2);
//        Sorter leftSorter = new Sorter(leftHalfToSort);
//        Sorter rightSorter = new Sorter(rightHalfToSort);
//
//
////      Exxecuting this is multithreaded way
//        Future<ArrayList<Integer>> leftSortedFuture = es.submit(leftSorter);
//        Future<ArrayList<Integer>> rightSortedFuture = es.submit(rightSorter);
//
//        ArrayList<Integer>leftSortedList = leftSortedFuture.get();
//        ArrayList<Integer>rightSortedList = rightSortedFuture.get();
//        es.shutdown();
//        return merge(leftSortedList,rightSortedList);
//
//    }
//    public ArrayList<Integer>merge(ArrayList<Integer>A,ArrayList<Integer>B) {
//        int i = 0;
//        int j = 0;
//        ArrayList<Integer>mergedList = new ArrayList<>();
//        while(i<A.size() && j<B.size()) {
//            if(A.get(i)<B.get(j)) {
//                mergedList.add(A.get(i));
//                i++;
//            }
//            else{
//                mergedList.add(B.get(j));
//                j++;
//            }
//        }
//        while(i<A.size()) {
//            mergedList.add(A.get(i));
//            i++;
//        }
//        while(j<B.size()) {
//            mergedList.add(B.get(j));
//            j++;
//        }
//        return mergedList;
//    }
//    public ArrayList<Integer> getsubArray(ArrayList<Integer> list, int start, int end) {
//        ArrayList<Integer> subList = new ArrayList<>(list);
//        for(int i=start; i<end; i++) {
//            subList.add(list.get(i));
//        }
//        return subList;
//    }
//}
//class ArrayListModifier implements Callable<ArrayList<Integer>> {
//    ArrayList<Integer>listToDouble;
//    public ArrayListModifier(ArrayList<Integer>listToDouble) {
//        this.listToDouble = listToDouble;
//    }
//
//    @Override
//    public ArrayList<Integer> call() throws Exception {
//        ArrayList<Integer>doubledArrayList = new ArrayList<>();
//        for(int i = 0;i<listToDouble.size();i++) {
//            doubledArrayList.add(2*listToDouble.get(i));
//        }
//        return doubledArrayList;
//    }
//}
//
//class HelloWorldPrinter extends Thread {
//    @Override
//    public void run(){
//        System.out.println("Hello, World! " + Thread.currentThread().getName());
//    }
//}
//
//class NumberPrinter extends Thread {
//    int n;
//    public NumberPrinter(int n) {
//        this.n = n;
//    }
//
//    @Override
//    public void run(){
//        System.out.println(n + " " + Thread.currentThread().getName());
//    }
//}
//
//public class ThreadExample {
//    public static void main(String[] args) throws ExecutionException, InterruptedException {
////        System.out.println("Main thread: " + Thread.currentThread().getName());
////
////         HelloWorldPrinter helloWorldPrinter1 = new HelloWorldPrinter();
////         helloWorldPrinter1.start();
////
////         HelloWorldPrinter helloWorldPrinter2 = new HelloWorldPrinter();
////         helloWorldPrinter2.start();
//
//
////         NumberPrinter np1 = new NumberPrinter();
////         np1.start();
////         NumberPrinter np2 = new NumberPrinter();
////         np2.start();
//
//
////        for(int i = 1; i <= 100; i++){
////            NumberPrinter numberPrinter = new NumberPrinter(i);
////            numberPrinter.start();
////        }
//        ArrayList<Integer> list = new ArrayList<>();
//        for(int i = 10;i>=0;i--) {
//            list.add(i);
//        }
////        ArrayListModifier task = new ArrayListModifier(list);
//        Sorter sorter = new Sorter(list);
//        ExecutorService es = Executors.newFixedThreadPool(5);
//        Future<ArrayList<Integer>> sortedArrayList = es.submit(sorter);
//
//        System.out.println(sortedArrayList.get());
//        }
//    }





import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Value{
    private int data;
    public Value(int data){
        this.data = data;
    }
    synchronized public int getData(){
        return data;
    }
    synchronized public void setData(int data){
        this.data = data;
    }
    synchronized public void add(int a){
        this.data += a;
    }
    synchronized public void subtract(int a){
        this.data -= a;
    }
}
class Adder implements Callable<Void>{
    Value val;
    Lock lock;
    public Adder(Value val, Lock lock){
        this.val = val;
        this.lock = lock;
    }
    @Override
    public Void call() throws Exception {
        for(int i = 0;i<=100;i++){
            lock.lock();
            this.val.setData(val.getData()+i);
            lock.unlock();
        }
        return null;
    }
}

class Subtractor implements Callable<Void>{
    Value val;
    Lock lock;
    public Subtractor(Value val,Lock lock){
        this.val = val;
        this.lock = lock;
    }
    @Override
    public Void call() throws Exception {
        for(int i = 0;i<=100;i++){
            lock.lock();
            this.val.setData(val.getData()-i);
            lock.unlock();

        }
        return null;
    }

}
class AdderWithSyncronization implements Callable<Void>{
    Value val;
    public AdderWithSyncronization(Value val){
        this.val = val;

    }
    @Override
    public Void call() throws Exception {
        for(int i = 0;i<=10000;i++){
//            synchronized(val){
//                this.val.setData(val.getData()+i);
                  this.val.add(i);
//            }
        }
        return null;
    }
}
class SubtractorWithSyncronization implements Callable<Void>{
    Value val;
    public SubtractorWithSyncronization(Value val){
        this.val = val;

    }
    @Override
    public Void call() throws Exception {
        for(int i = 0;i<=10000;i++){
//            synchronized(val){
//                this.val.data = val.data - i;
//                this.val.setData(val.getData()-i);
                  this.val.subtract(i);

//            }
        }
        return null;
    }

}
class Sorter implements Callable<ArrayList<Integer>> {
    ArrayList<Integer> listToSort;

    Sorter(ArrayList<Integer> listToSort) {
        this.listToSort = listToSort;
    }

    @Override
    public ArrayList<Integer> call() throws Exception {
        if (listToSort.size() <= 1) {
            return listToSort;
        }

        int mid = listToSort.size() / 2;
        ArrayList<Integer> leftHalfToSort = new ArrayList<>(listToSort.subList(0, mid));
        ArrayList<Integer> rightHalfToSort = new ArrayList<>(listToSort.subList(mid, listToSort.size()));

        ExecutorService es = Executors.newCachedThreadPool(); // Use 2 threads for left and right sorting
        Sorter leftSorter = new Sorter(leftHalfToSort);
        Sorter rightSorter = new Sorter(rightHalfToSort);

        // Executing this in a multithreaded way
        Future<ArrayList<Integer>> leftSortedFuture = es.submit(leftSorter);
        Future<ArrayList<Integer>> rightSortedFuture = es.submit(rightSorter);

        ArrayList<Integer> leftSortedList = leftSortedFuture.get();
        ArrayList<Integer> rightSortedList = rightSortedFuture.get();

        es.shutdown(); // Shutdown the executor service

        return merge(leftSortedList, rightSortedList);
    }

    public ArrayList<Integer> merge(ArrayList<Integer> A, ArrayList<Integer> B) {
        int i = 0, j = 0;
        ArrayList<Integer> mergedList = new ArrayList<>();
        while (i < A.size() && j < B.size()) {
            if (A.get(i) < B.get(j)) {
                mergedList.add(A.get(i));
                i++;
            } else {
                mergedList.add(B.get(j));
                j++;
            }
        }
        while (i < A.size()) {
            mergedList.add(A.get(i));
            i++;
        }
        while (j < B.size()) {
            mergedList.add(B.get(j));
            j++;
        }
        return mergedList;
    }
}

class ArrayListModifier implements Callable<ArrayList<Integer>> {
    ArrayList<Integer> listToDouble;

    public ArrayListModifier(ArrayList<Integer> listToDouble) {
        this.listToDouble = listToDouble;
    }

    @Override
    public ArrayList<Integer> call() throws Exception {
        ArrayList<Integer> doubledArrayList = new ArrayList<>();
        for (int i : listToDouble) {
            doubledArrayList.add(2 * i);
        }
        return doubledArrayList;
    }
}

class HelloWorldPrinter extends Thread {
    @Override
    public void run() {
        System.out.println("Hello, World! " + Thread.currentThread().getName());
    }
}

class NumberPrinter extends Thread {
    int n;

    public NumberPrinter(int n) {
        this.n = n;
    }

    @Override
    public void run() {
        System.out.println(n + " " + Thread.currentThread().getName());
    }
}

public class ThreadExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        ArrayList<Integer> list = new ArrayList<>();
//        for (int i = 10; i >= 0; i--) {
//            list.add(i);
//        }
//
//        Sorter sorter = new Sorter(list);
//        ExecutorService es = Executors.newFixedThreadPool(10); // Single thread pool for main sorter task
//        Future<ArrayList<Integer>> sortedArrayList = es.submit(sorter);
//
//        System.out.println(sortedArrayList.get());
//
//        es.shutdown();
        Lock lock = new ReentrantLock();
        ExecutorService es = Executors.newCachedThreadPool();
        Value VAL = new Value(0);
//        System.out.println(VAL.data);
//        Adder AD = new Adder(VAL,lock);
//        Subtractor SUB = new Subtractor(VAL,lock);
//
//        Future<Void> add = es.submit(AD);
//        Future<Void> sub = es.submit(SUB);
//        add.get();
////        System.out.println(VAL.data);
//        sub.get();
//        System.out.println(VAL.data);
//        es.shutdown();
        AdderWithSyncronization ad = new AdderWithSyncronization(VAL);
        SubtractorWithSyncronization su = new SubtractorWithSyncronization(VAL);
        Future<Void>add = es.submit(ad);
        Future<Void>sub = es.submit(su);
        add.get();
        sub.get();
        System.out.println(VAL.getData());
        es.shutdown();



    }
}
