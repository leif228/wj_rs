package com.wujie.ac.app.framework.rbtree;


import java.util.ArrayList;
import java.util.List;

public class TestRebBlackTree {
    public static void main(String[] args) {

        MyRBTree<Integer, String> tree = new MyRBTree<Integer, String>();

        //顺序插入
        for (int i = 0; i < 12; i++) {

            tree.add(i , "" + i);
            tree.printTreeLevel();
        }

        tree.remove(7);
        tree.printTreeLevel();

        tree.remove(4);
        tree.printTreeLevel();

//        System.out.println(tree.getKey(5));

        System.out.println("--------------随机插入----------------------");
        tree = new MyRBTree<>();

        List<Integer> list = new ArrayList<>();

        //随机插入
        for (int i = 0; i < 20; i++) {

            int ran = (int)(Math.random() * 100);
            System.out.println("新插入结点：" + ran);
            tree.add(ran, "" + i);
            tree.printTreeLevel();

            list.add(ran);

        }

        for (Integer integer : list){
            System.out.print(integer.intValue() + " ");
        }
    }


//    @Test
    public void test1(){

        //55 94 0 8 75 5 82 78 43 47 75 33
        MyRBTree<Integer, String> tree = new MyRBTree<Integer, String>();

        tree.add(55, "#55");
        tree.add(94, "#94");
        tree.add(0, "#0");
        tree.add(8, "#8");
        tree.add(75, "#75");
        tree.add(5, "#5");
        tree.add(82, "#82");
        tree.add(78, "#78");
        tree.add(43, "#43");
        tree.add(47, "#47");
        tree.add(75, "#75");
        tree.add(33, "#33");
//        tree.add(95);

        tree.add(41, "#41");
        tree.add(23, "#23");
        tree.add(24, "#24");
        tree.add(32, "#32");
        tree.add(11, "#11");


        tree.printTreeLevel();

//        tree.remove(82);
        tree.remove(43);

        tree.printTreeLevel();

//        tree.remove(43);
//        tree.printTreeLevel();

        System.out.println(tree.get(55));
    }


//    @Test
    public void test2(){

//       1 //                                        76
//       2 //                   36                                        116
//       4 //         16                   56                  96                    136
//       8 //    6         26         46        66        76        106        126         146
//       16 // 1   11    21  31     41 51     61  71    81  91   101  111   121  131    141  151
        MyRBTree<Integer, String> tree = new MyRBTree<Integer, String>();

        tree.add(76, "#55");
        tree.add(36, "#94");
        tree.add(116, "#0");
        tree.add(16, "#8");
        tree.add(56, "#75");
        tree.add(96, "#5");
        tree.add(1, "#82");
//
        tree.add(11, "#78");
        tree.add(21, "#43");
        tree.add(31, "#47");
        tree.add(41, "#75");
        tree.add(51, "#33");
//
        tree.add(61, "#41");
        tree.add(71, "#23");
        tree.add(81, "#24");
        tree.add(91, "#32");
        tree.add(101, "#11");
        tree.add(111, "#11");
        tree.add(121, "#11");
        tree.add(131, "#11");
        tree.add(141, "#11");
        tree.add(151, "#11");


        tree.printTreeLevel();

//        tree.remove(82);
//        tree.remove(43);

        tree.printTreeLevel();

//        tree.remove(43);
//        tree.printTreeLevel();

//        System.out.println(tree.get(55));
    }


//    @Test
    public void test3(){

        MyRBTree<Integer, String> tree = new MyRBTree<Integer, String>();

        String str = "99 29 3 31 26 19 93 86 35 67 59 10 65 74 74 27 19 89 2 57 13 6 52 20 25 54 41 44 71 27 14 32 83 81 44 14 35 78 67 99 56 6 31 93 50 83 81 93 70 24";
        String[] strs = str.split(" ");
        for (String sss : strs){

            tree.add(Integer.valueOf(sss), sss);
        }

//        tree.printTreeLevel();

        tree.remove(26);

        tree.printTreeLevel2();
    }

//    @Test
    public void test4(){

        MyRBTree tree = new MyRBTree<>();

        List<Integer> list = new ArrayList<>();
        //随机插入
        for (int i = 0; i < 50; i++) {

            int ran = (int)(Math.random() * 100);
            System.out.println("新插入结点：" + ran);
            tree.add(ran, "" + i);
            tree.printTreeLevel();

            list.add(ran);

        }


        for (Integer integer : list){
            System.out.print(integer.intValue() + " ");
        }

        System.out.println();

        tree.printTreeLevel2();

    }


}
