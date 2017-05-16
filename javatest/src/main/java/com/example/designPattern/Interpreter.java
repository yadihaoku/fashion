package com.example.designPattern;

import java.util.HashMap;

/**
 * reated by YanYadi on 2017/5/16.
 * <p>
 * 解释器模式
 * 行为模式的一种 ，可以按照某种规则解析其文法。
 * <p>
 * example: 模拟一种 boolean 类型的 逻辑运算 。
 * 计算
 * var a = false;
 * var b = true;
 * var c = false;
 * ((a || b) && (!c)) 的值 ？
 */
public class Interpreter {
    public static void main(String[] args) {
        /**
         * var a = false;
         * var b = true;
         * var c = false;
         * ((a || b) && (!c)) 的值 ？
         */
        Context context = new Context();
        Variable a = new Variable("a");
        Variable b = new Variable("b");
        Variable c = new Variable("c");
        Variable d = new Variable("d");
        context.assign(a, false);
        context.assign(b, true);
        context.assign(c, false);
        context.assign(d, true);


        context.viewVariables();
        //表达式  ((a || b) && (!c))
        Expression expression = new And(new Or(a, b), new Not(c));
        System.out.println(expression.toString() + " = " + expression.interpreter(context));
        //表达式  ((a || b) && (!c)) && d && a
        Expression expression2 = new And(new And(new And(new Or(a, b), new Not(c)), d), a);
        System.out.println(expression2.toString() + " = " + expression2.interpreter(context));

    }

    /**
     * 定义表达式接口
     */
    interface Expression {
        boolean interpreter(Context context);

        String toString();

        boolean equals(Object object);
    }

    /**
     * 逻辑 与
     */
    static class And implements Expression {
        private Expression left, right;

        And(Expression expression1, Expression expression2) {
            left = expression1;
            right = expression2;
        }

        @Override public boolean interpreter(Context context) {
            return left.interpreter(context) && right.interpreter(context);
        }

        @Override public String toString() {
            return "( " + left.toString() + " && " + right.toString() + " )";
        }
    }

    /**
     * 逻辑 或
     */
    static class Or implements Expression {
        private Expression left, right;

        Or(Expression expression1, Expression expression2) {
            left = expression1;
            right = expression2;
        }

        @Override public boolean interpreter(Context context) {
            return left.interpreter(context) || right.interpreter(context);
        }

        @Override public String toString() {
            return "( " + left.toString() + " || " + right.toString() + " )";
        }
    }

    /**
     * 逻辑 或
     */
    static class Not implements Expression {
        private Expression value;

        Not(Expression expression1) {
            value = expression1;
        }

        @Override public boolean interpreter(Context context) {
            return !value.interpreter(context);
        }

        @Override public String toString() {
            return "( ! " + value.toString() + " )";
        }
    }

    /**
     * 单个变量
     */
    static class Variable implements Expression {
        private String name;

        Variable(String vName) {
            name = vName;
        }

        @Override public boolean interpreter(Context context) {
            return context.lookup(this);
        }

        @Override public String toString() {
            return name;
        }
    }

    /**
     * 定义语义上下文
     */
    static class Context {
        private HashMap<Expression, Boolean> variables = new HashMap<>();

        public void assign(Expression expression, boolean val) {
            variables.put(expression, val);
        }

        public boolean lookup(Expression expression) {
            return variables.get(expression);
        }

        public void viewVariables() {
            for (Expression expression : variables.keySet()) {
                System.out.println("var " + expression.toString() + " = " + expression.interpreter(this) + ";");
            }
        }
    }

}
