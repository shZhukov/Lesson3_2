public class Lesson3_6_Task2 {
    public int[] doTask2(int[] arr) throws RuntimeException {
        int[] res = null;
        int i = arr.length - 1;
        int j = 0;
        boolean found = false;
        while (i >= 0 && !found) {
            found = arr[i] == 4;
            j = i;
            i--;
        }
        if(found) {
            res = new int[arr.length - j - 1];
            for (i = j + 1; i < arr.length; i++) {
                res[i - j - 1] = arr[i];
            }
        } else {
            throw new RuntimeException("Array have no 4");
        }
        return  res;
    }
}
