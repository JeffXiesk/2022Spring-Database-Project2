package seok.db_project;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class test {
    public class ttt {
        public int a;
        public String b;

        public ttt(int a, String b) {
            this.a = a;
            this.b = b;
        }
    }

    @Test
    public void aaaa() {
        String a = "acbd";
        String b = "bacd";
        String c = "bbaf";
        List<String> l = new ArrayList<>();
        l.add(c);
        l.add(a);
        l.add(b);

        int d = 20220513;
        int e = 20221011;
        int f = 20201213;
        List<Integer> list = new ArrayList<>();
        list.add(f);
        list.add(e);
        list.add(d);

        List<ttt> list1 = new ArrayList<>();
        list1.add(new ttt(20231022, "aaab"));
        list1.add(new ttt(20211022, "zcdb"));
        list1.add(new ttt(20211022, "bcdg"));
        list1.add(new ttt(20221022, "acdb"));
        list1.add(new ttt(20221222, "caab"));

        list1.sort((aa, bb) -> {
            int aaa = aa.a;
            String aab = aa.b;
            int bba = bb.a;
            String bbb = bb.b;
            if (aaa == bba)
                return aab.compareTo(bbb);
            return aaa > bba ? 1 : -1;
        });

        for (int i = 0; i < list1.size(); i++) {
            System.out.println(list1.get(i).a+" "+list1.get(i).b);
        }
    }

    @Test
    public void bb(){
        Object a = 8318L;
        Object b = 14L;
        String str = String.format("%.1f",Double.parseDouble(a.toString())/(Long)b);
        System.out.println(str);
    }
}
