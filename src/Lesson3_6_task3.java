public class Lesson3_6_task3 {
    public boolean doTask3(int[] arr) {
        boolean res = true;
        int count1 = 0;
        int count4 = 0;
        int i = 0;
        while (i < arr.length && res) {
            if(arr[i] == 1) {
                res = true;
                count1++;
            } else {
                if(arr[i] == 4) {
                    res = true;
                    count4++;
                } else {
                    res = false;
                }
            }
            i++;
        }
        return res && count1 > 0 && count4 > 0;
    }
}
