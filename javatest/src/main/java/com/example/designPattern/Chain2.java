package com.example.designPattern;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YanYadi on 2017/5/8.
 */
public class Chain2 {

    public static void main(String[] args) {
        InterceptorManager interceptorManager = new InterceptorManager(5, "*");
        // 绘制 默认数量的 2 倍个数的星星
        interceptorManager.addInterceptor(new Interceptor() {
            @Override public String intercept(InterceptorChain chain) {
                int count = chain.getSymbolCount() * 2;
                return chain.proceed(count, chain.getSymbol());
            }
        });
        // 把默认绘制的 星星，换为 @ 符号
        interceptorManager.addInterceptor(new Interceptor() {
            @Override public String intercept(InterceptorChain chain) {
                return chain.proceed(chain.getSymbolCount(), "@");
            }
        });


        System.out.println(interceptorManager.makeString());

    }

    interface Interceptor {
        String intercept(InterceptorChain chain);

        interface InterceptorChain {
            String proceed(int c, String s);

            int getSymbolCount();

            String getSymbol();
        }
    }

    static class InterceptorManager {
        List<Interceptor> interceptors;
        private int defaultSymbolCount;
        private String symbol;

        InterceptorManager(int defaultSymbolCount, String symbol) {
            interceptors = new ArrayList<>();
            this.defaultSymbolCount = defaultSymbolCount;
            this.symbol = symbol;
        }

        public InterceptorManager addInterceptor(Interceptor interceptor) {
            this.interceptors.add(interceptor);
            return this;
        }

        public String makeString() {
            return new BlockChain(0, defaultSymbolCount, symbol).proceed(defaultSymbolCount, symbol);
        }

        class BlockChain implements Interceptor.InterceptorChain {
            private int nextInterceptorIndex;
            private int symbolCount;
            private String symbol;

            BlockChain(int index, int symbolCount, String symbol) {
                this.nextInterceptorIndex = index;
                this.symbolCount = symbolCount;
                this.symbol = symbol;
            }

            @Override public String proceed(int blankCount, String symbol) {
                if (nextInterceptorIndex < interceptors.size()) {
                    Interceptor interceptor = interceptors.get(nextInterceptorIndex);
                    Interceptor.InterceptorChain chain = new BlockChain(nextInterceptorIndex + 1, blankCount, symbol);
                    return interceptor.intercept(chain);
                }

                return fillBlank(blankCount,symbol);
            }

            @Override public int getSymbolCount() {
                return symbolCount;
            }

            @Override public String getSymbol() {
                return symbol;
            }

            private String fillBlank(int blankCount, String symbol) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < blankCount; i++)
                    stringBuilder.append(symbol);
                return stringBuilder.toString();
            }
        }
    }
}
