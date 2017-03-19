package sort;

/**
 * 介绍：Sort programs
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2017/3/19.
 */
public class Sort {

    public static void main(String[] args) {
        int[] src = new int[]{10, 5, 6, 9, 1, 3, 6, 6, 8, 10, 32, 10, 0};

        selectionSort(src);
        bubbleSort(src);

        quickSort(src, 0, src.length-1);
        output(src);
    }

    public static void quickSort(int[] src, int left, int right) {
        if (left < right) {
            int i = left;
            int j = right;
            int x = src[i];

            while (i < j) {
                while (i < j && src[j] >= x) {
                    j--;
                }
                if (i < j) {
                    //小的移动到左边
                    src[i++] = src[j];
                }

                while (i < j && src[i] <= x) {
                    i++;
                }
                if (i < j) {
                    //大的移动到右边
                    src[j--] = src[i];
                }
            }
            src[i] = x;

            quickSort(src, left, i - 1);
            quickSort(src, i + 1, right);
        }


    }


    public static void bubbleSort(int[] src) {
        int length = src.length;
        int temp;
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                //前一个比后一个大
                if (src[j - 1] > src[j]) {
                    temp = src[j - 1];
                    src[j - 1] = src[j];
                    src[j] = temp;
                }
            }
        }
        output(src);
    }


    /**
     * 选择排序
     * 算法规则： 将待排序集合(0...n)看成两部分，
     * 在起始状态中，一部分为(k..n)的待排序unsorted集合，
     * 另一部分为(0...k)的已排序sorted集合,
     * 在待排序集合中挑选出最小元素并且记录下标i，
     * 若该下标不等于k，那么 unsorted[i] 与 sorted[k]交换 ，
     * 一直重复这个过程，直到unsorted集合中元素为空为止。
     *
     * @param src
     */
    public static void selectionSort(int[] src) {
        int n = src.length;
        int temp;
        for (int i = 0, k = 0; i < n; i++, k = i) {
            for (int j = k + 1; j < n; j++) {
                if (src[j] < src[k]) {
                    k = j;
                }
            }
            if (k != i) {
                temp = src[k];
                src[k] = src[i];
                src[i] = temp;
            }
        }

        output(src);
    }

    public static void output(int[] src) {
        System.out.println();
        for (int i : src) {
            System.out.print(i + ",");
        }
    }


}
