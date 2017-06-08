package com.example.format;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by YanYadi on 2017/6/7.
 */
public class DecimalFormatText {
    public static void main(String[] args) {
        DecimalFormatSymbols customSymbols = DecimalFormatSymbols.getInstance(Locale.CHINA);
        //设置自定义分组样式
        customSymbols.setGroupingSeparator('-');
        //设置货币符号
        customSymbols.setCurrencySymbol("@");
        DecimalFormat decimalFormat = new DecimalFormat("0.00", customSymbols);
        decimalFormat.setRoundingMode(RoundingMode.FLOOR);
        //设置保留的小数位上限
        decimalFormat.setMaximumFractionDigits(1);
        decimalFormat.setGroupingUsed(false);
        decimalFormat.setGroupingSize(3);
        decimalFormat.setDecimalSeparatorAlwaysShown(true);
        //设置正整数的上限
//        decimalFormat.setMaximumIntegerDigits(1);

        System.out.println(decimalFormat.format(new BigDecimal("785524.245")));
        System.out.println(decimalFormat.format(new BigDecimal("2242424.855")));
        System.out.println(decimalFormat.format(new BigDecimal("24.888")));


        System.out.println(NumberFormat.getCurrencyInstance(Locale.CANADA).format(new BigDecimal("234324.234")));
    }
}
