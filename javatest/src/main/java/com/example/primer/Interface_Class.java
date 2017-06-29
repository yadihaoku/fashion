package com.example.primer;

/**
 * Created by YanYadi on 2017/6/29.
 */
public class Interface_Class {

    public interface Hi{
        void hi();
    }
    static class BaseHi{
        private void hi(){
            System.out.println("base hi");
        }
        public void say(){
            System.out.println("base say");
            this.hi();
        }

    }
    static class HiImpl extends BaseHi {
        protected void hi() {
             System.out.println("HiImpl hi");
        }

        @Override public void say() {
            System.out.println("HiImpl say");
            super.hi();
        }
    }

    public static void main(String[] args) {
           BaseHi hi = new HiImpl();
        hi.say();
    }
}
