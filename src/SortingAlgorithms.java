import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SortingAlgorithms extends JFrame {

    JPanel linePanel;
    Graphics g;
    JPanel controls;
    JPanel buttons;
    JPanel sortButtons;
    JButton start;
    JButton shuffle;
    JButton reset;
    JButton [] sorts;
    JComboBox sortList;
    JComboBox modeList;
    String [] sortStrings = {"Bubble Sort", "Cocktail Shaker Sort", "Insertion Sort", "Selection Sort", "Merge Sort", "Quick Sort", "Radix Sort"};
    String [] modeStrings = {"Vertical Lines", "Shell", "Circle", "Numbers", "Dots"};
    startSort startSort;
    startShuffle startShuffle;
    resetList resetList;
    int [] numbers;
    int mode;

    public SortingAlgorithms() {

        controls = new JPanel(new GridLayout(1, 3));
        buttons = new JPanel(new GridLayout(1, 3));
        sortButtons = new JPanel(new GridLayout(sortStrings.length / 4, sortStrings.length / 4));
        start = new JButton("Start");
        shuffle = new JButton("Shuffle");
        reset = new JButton("Reset");
        sorts = new JButton[sortStrings.length];
        sortList = new JComboBox(sortStrings);
        modeList = new JComboBox(modeStrings);
        startSort = new startSort();
        startShuffle = new startShuffle();
        resetList = new resetList();
        numbers = new int[1400];
        mode = 0;

        for(int i = 0; i < sortStrings.length; i++) {
            sorts[i] = new JButton(sortStrings[i]);
            sorts[i].addActionListener(startSort);
            sortButtons.add(sorts[i]);
        }

        sortList.setSelectedIndex(0);
        modeList.setSelectedIndex(0);
        start.addActionListener(startSort);
        shuffle.addActionListener(startShuffle);
        reset.addActionListener(resetList);

        modeList.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                mode = modeList.getSelectedIndex();
                repaint();
            }
        });

        buttons.add(start);
        buttons.add(shuffle);
        buttons.add(reset);

        controls.add(sortList);
        controls.add(modeList);
        controls.add(buttons);

        for(int i = 0; i < numbers.length; i++)
            numbers[i] = i + 1;

        //for(int i = 0; i < numbers.length; i++)
            //System.out.println(numbers[i]);

        this.linePanel = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);

                for(int i = 0; i < numbers.length; i++) {
                    if (mode == 0) {
                        g.setColor(Color.getHSBColor((float) (((float) numbers[i] / numbers.length) - 0.2) % 1, 1f, 0.7f));
                        g.drawLine(i + (linePanel.getWidth() - numbers.length) / 2, 700, i + (linePanel.getWidth() - numbers.length) / 2, 700 - numbers[i] / 2);
                    }
                    else if(mode == 1) {
                        g.setColor(Color.getHSBColor((float)(((float)numbers[i] / numbers.length) - 0.2) % 1, 1f, 0.7f));
                        g.drawLine(700, 350, 700 + (int) (Math.cos(Math.toRadians(i * (float)(360 / (float)numbers.length))) * (float)numbers[i] / 2), 350 + (int) (Math.sin(Math.toRadians(i * (float)(360 / (float)numbers.length))) * (float)numbers[i] / 2));
                    }
                    else if(mode == 2) {
                        g.setColor(Color.getHSBColor((float)(((float)numbers[i] / numbers.length) - 0.2) % 1, 1f, 0.7f));
                        g.drawLine(700, 350, 700 + (int) (Math.cos(Math.toRadians(i * (float)(360 / (float)numbers.length))) * 250), 350 + (int) (Math.sin(Math.toRadians(i * (float)(360 / (float)numbers.length))) * 250));
                    }
                    else if(mode == 3) {
                        //g.drawString("" + numbers[i], 30 + (i / 80) + ((i % 80) * 40), 30 + ((i / 80) * 40));
                        //g.draw3DRect(i + (linePanel.getWidth() - numbers.length) / 2 + 4, 0, 4, 700 - numbers[i] / 2, false);
                    }
                    else if(mode == 4) {
                        g.setColor(Color.getHSBColor((float)(((float)numbers[i] / numbers.length) - 0.2) % 1, 1f, 0.7f));
                        g.drawOval(700 + (int) (Math.cos(Math.toRadians(i * (float)(360 / (float)numbers.length))) * 250), 350 + (int) (Math.sin(Math.toRadians(i * (float)(360 / (float)numbers.length))) * 250), 30, 30);
                        g.drawOval(700 + (int) (Math.cos(Math.toRadians(i * (float)(360 / (float)numbers.length))) * 250) + 5, 350 + (int) (Math.sin(Math.toRadians(i * (float)(360 / (float)numbers.length))) * 250), 30, 30);
                    }
                }
                    //g.drawLine(0, 0, i, 700 - numbers[i] / 2);
            }

            public void repaint(Graphics g){
                paint(g);
            }
        };

        linePanel.setSize(1300, 700);

        add(linePanel);
        add(controls, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1406, 756);
        setResizable(false);
        setVisible(true);

    }

    static void shuffleArray(int[] ar)
    {
      // If running on Java 6 or older, use `new Random()` on RHS here
      Random rnd = ThreadLocalRandom.current();
      for (int i = ar.length - 1; i > 0; i--)
      {
        int index = rnd.nextInt(i + 1);
        // Simple swap
        int a = ar[index];
        ar[index] = ar[i];
        ar[i] = a;
      }
    }

    public void shuffleArraySlow(int[] ar)
    {
      // If running on Java 6 or older, use `new Random()` on RHS here
      Random rnd = ThreadLocalRandom.current();
      for (int i = ar.length - 1; i > 0; i--)
      {
        int index = rnd.nextInt(i + 1);
        // Simple swap
        int a = ar[index];
        ar[index] = ar[i];
        ar[i] = a;

        repaint();

        try {
            TimeUnit.NANOSECONDS.sleep(1);
        } catch (InterruptedException ex) {
            Logger.getLogger(SortingAlgorithms.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }

    private class startSort implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            /*JButton button = (JButton)e.getSource();
            switch (button.getText()) {
                case "Bubble Sort": bubbleSort(numbers);
                    break;
                case "Cocktail Shaker Sort": bubbleSort(numbers);
                    break;
                case "Insertion Sort": bubbleSort(numbers);
                    break;
                case "Selection Sort": bubbleSort(numbers);
                    break;
                case "Merge Sort": bubbleSort(numbers);
                    break;
                case "Quick Sort": bubbleSort(numbers);
                    break;
                case "Radix Sort": bubbleSort(numbers);
                    break;
            }*/



            if(sortStrings[sortList.getSelectedIndex()].equals("Bubble Sort"))
            {
                Thread t = new Thread() {
                public void run() {
                    bubbleSort(numbers);
                }

                };
                t.start();
            }
            else if(sortStrings[sortList.getSelectedIndex()].equals("Cocktail Shaker Sort"))
            {
                Thread t = new Thread() {
                public void run() {
                    csSort(numbers);
                }

                };
                t.start();
            }
            else if(sortStrings[sortList.getSelectedIndex()].equals("Insertion Sort"))
            {
                Thread t = new Thread() {
                public void run() {
                    insertionSort(numbers);
                }

                };
                t.start();
            }
            else if(sortStrings[sortList.getSelectedIndex()].equals("Selection Sort"))
            {
                Thread t = new Thread() {
                public void run() {
                    selectionSort(numbers);
                }

                };
                t.start();
            }
            else if(sortStrings[sortList.getSelectedIndex()].equals("Merge Sort"))
            {
                Thread t = new Thread() {
                    public void run() {
                        mergeSort(numbers);
                    }

                };
                t.start();
            }
            else if(sortStrings[sortList.getSelectedIndex()].equals("Quick Sort"))
            {
                Thread t = new Thread() {
                    public void run() {
                        quickSort(numbers, 0, numbers.length - 1);
                    }

                };
                t.start();
            }
            else if(sortStrings[sortList.getSelectedIndex()].equals("Radix Sort"))
            {
                Thread t = new Thread() {
                    public void run() {
                        radixSort(numbers, numbers.length);
                    }

                };
                t.start();
            }
        }
    }

    private class startShuffle implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            Thread t = new Thread() {
                public void run() {
                    shuffleArraySlow(numbers);
                }
            };
            t.start();

        }
    }

    private class resetList implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            Thread t = new Thread() {
                public void run() {
                    for(int i = 0; i < numbers.length; i++) {
                        if(numbers[i] != i) numbers[i] = i + 1;

                        repaint();

                        try {
                            TimeUnit.NANOSECONDS.sleep(1);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(SortingAlgorithms.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            };
            t.start();

        }
    }

    public void bubbleSort(int [] n)
    {
        int nextNum;
        int i;
        int hold;
        boolean swapped;

        swapped = true;

        for(nextNum = 0; nextNum < n.length && swapped == true; nextNum++)
        {
            swapped = false;
            for(i = 0; i < n.length - 1; i++)
            {
                if(n[i] > n[i + 1])
                {
                    hold = n[i];
                    n[i] = n[i + 1];
                    n[i + 1] = hold;
                    swapped = true;

                    repaint();

                    try {
                        TimeUnit.NANOSECONDS.sleep(1);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SortingAlgorithms.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    public void insertionSort(int [] n)
    {
        int nextNum;
        int i;
        int hold;

        for(nextNum = 1; nextNum < n.length; nextNum++)
        {
            hold = n[nextNum];
            for(i = nextNum; i > 0 && hold < n[i - 1]; i--)
            {
                n[i] = n[i - 1];
                repaint();

                try {
                    TimeUnit.NANOSECONDS.sleep(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SortingAlgorithms.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            n[i] = hold;
        }
    }

    public void csSort(int [] n)
    {
        int nextNum;
        int i;
        int hold;
        boolean swapped;

        swapped = true;

        for(nextNum = 0; nextNum < n.length && swapped == true; nextNum++)
        {
            swapped = false;
            for(i = nextNum; i < n.length - 1; i++)
            {
                if(n[i] > n[i + 1])
                {
                    hold = n[i];
                    n[i] = n[i + 1];
                    n[i + 1] = hold;
                    swapped = true;

                    repaint();

                    try {
                        TimeUnit.NANOSECONDS.sleep(1);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SortingAlgorithms.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            for(;i > nextNum && swapped == true; i--)
            {
                if(n[i] < n[i - 1])
                {
                    hold = n[i];
                    n[i] = n[i - 1];
                    n[i - 1] = hold;
                    swapped = true;

                    repaint();

                    try {
                        TimeUnit.NANOSECONDS.sleep(1);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SortingAlgorithms.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

     public void selectionSort(int[] arr){

        for (int i = 0; i < arr.length - 1; i++)
        {
            int index = i;
            for (int j = i + 1; j < arr.length; j++)
            {
                if (arr[j] < arr[index])
                {
                    index = j;

                    repaint();

                    try {
                        TimeUnit.NANOSECONDS.sleep(1);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SortingAlgorithms.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            int smallerNumber = arr[index];
            arr[index] = arr[i];
            arr[i] = smallerNumber;

            repaint();

            try {
                TimeUnit.NANOSECONDS.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(SortingAlgorithms.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void mergeSort(int[] A) {
      if (A.length > 1) {
          int q = A.length/2;

          int[] leftArray = Arrays.copyOfRange(A, 0, q);
          int[] rightArray = Arrays.copyOfRange(A,q,A.length);

          mergeSort(leftArray);
          mergeSort(rightArray);

          merge(A,leftArray,rightArray);
      }
  }

    private void merge(int[] a, int[] l, int[] r) {
        int totElem = l.length + r.length;
        //int[] a = new int[totElem];
        int i,li,ri;
        i = li = ri = 0;
        while ( i < totElem) {
            if ((li < l.length) && (ri<r.length)) {
                if (l[li] < r[ri]) {
                    a[i] = l[li];
                    i++;
                    li++;
                }
                else {
                    a[i] = r[ri];
                    i++;
                    ri++;
                }
                repaint();

                try {
                    TimeUnit.NANOSECONDS.sleep(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SortingAlgorithms.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else {
                if (li >= l.length) {
                    while (ri < r.length) {
                        a[i] = r[ri];
                        i++;
                        ri++;
                        repaint();

                        try {
                            TimeUnit.NANOSECONDS.sleep(1);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(SortingAlgorithms.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                if (ri >= r.length) {
                    while (li < l.length) {
                        a[i] = l[li];
                        li++;
                        i++;
                        repaint();

                        try {
                            TimeUnit.NANOSECONDS.sleep(1);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(SortingAlgorithms.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
        //return a;

    }

    int partition(int arr[], int low, int high)
    {
        int pivot = arr[high];
        int i = (low-1); // index of smaller element
        for (int j=low; j<high; j++)
        {
            // If current element is smaller than or
            // equal to pivot
            if (arr[j] <= pivot)
            {
                i++;

                // swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;

                repaint();

                try {
                    TimeUnit.NANOSECONDS.sleep(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SortingAlgorithms.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        // swap arr[i+1] and arr[high] (or pivot)
        int temp = arr[i+1];
        arr[i+1] = arr[high];
        arr[high] = temp;

        repaint();

        try {
            TimeUnit.NANOSECONDS.sleep(1);
        } catch (InterruptedException ex) {
            Logger.getLogger(SortingAlgorithms.class.getName()).log(Level.SEVERE, null, ex);
        }

        return i+1;
    }


    /* The main function that implements QuickSort()
      arr[] --> Array to be sorted,
      low  --> Starting index,
      high  --> Ending index */
    private void quickSort(int arr[], int low, int high)
    {
        if (low < high)
        {
            /* pi is partitioning index, arr[pi] is
              now at right place */
            int pi = partition(arr, low, high);

            // Recursively sort elements before
            // partition and after partition
            quickSort(arr, low, pi-1);
            quickSort(arr, pi+1, high);
        }
    }

    static int getMax(int arr[], int n)
    {
        int mx = arr[0];
        for (int i = 1; i < n; i++)
            if (arr[i] > mx)
                mx = arr[i];
        return mx;
    }

    // A function to do counting sort of arr[] according to
    // the digit represented by exp.
    private void countSort(int arr[], int n, int exp)
    {
        int ref[] = new int[n]; // output array
        int i;
        int count[] = new int[10];
        Arrays.fill(count,0);

        for (i = 0; i < n; i++)
            ref[i] = arr[i];

        // Store count of occurrences in count[]
        for (i = 0; i < n; i++)
            count[ (arr[i]/exp)%10 ]++;

        // Change count[i] so that count[i] now contains
        // actual position of this digit in output[]
        for (i = 1; i < 10; i++)
            count[i] += count[i - 1];

        // Build the output array
        for (i = n - 1; i >= 0; i--)
        {
            arr[count[ (ref[i]/exp)%10 ] - 1] = ref[i];
            count[ (ref[i]/exp)%10 ]--;

            repaint();

            try {
                TimeUnit.NANOSECONDS.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(SortingAlgorithms.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // Copy the output array to arr[], so that arr[] now
        // contains sorted numbers according to curent digit
        //for (i = 0; i < n; i++)
        //    arr[i] = output[i];
    }

    // The main function to that sorts arr[] of size n using
    // Radix Sort
    private void radixSort(int arr[], int n)
    {
        // Find the maximum number to know number of digits
        int m = getMax(arr, n);

        // Do counting sort for every digit. Note that instead
        // of passing digit number, exp is passed. exp is 10^i
        // where i is current digit number
        for (int exp = 1; m/exp > 0; exp *= 10)
            countSort(arr, n, exp);
    }

    public static void main(String[]args) {
        new SortingAlgorithms();
    }
}
