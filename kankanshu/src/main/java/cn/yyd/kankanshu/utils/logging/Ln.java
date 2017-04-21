package cn.yyd.kankanshu.utils.logging;

/**
 * Author by Yadi Yan
 */
public final class Ln {


    protected static LnInterface lnImpl = new LnImpl();

    private Ln() {}

    public static int v(Throwable t) {
        return lnImpl.v(t);
    }

    public static int v(Object s1, Object... args) {
        return lnImpl.v(s1, args);
    }

    public static int v(Throwable throwable, Object s1, Object... args ) {
        return lnImpl.v(throwable,s1,args);
    }

    public static int d(Throwable t) {
        return lnImpl.d(t);
    }

    public static int d(Object s1, Object... args) {
        return lnImpl.d(s1,args);
    }

    public static int d(Throwable throwable, Object s1, Object... args) {
        return lnImpl.d(throwable, s1, args);
    }

    public static int i(Throwable t) {
        return lnImpl.i(t);
    }

    public static int i( Object s1, Object... args) {
        return lnImpl.i(s1, args);
    }

    public static int i(Throwable throwable, Object s1, Object... args) {
        return lnImpl.i(throwable, s1, args);
    }

    public static int w(Throwable t) {
        return lnImpl.w(t);
    }

    public static int w( Object s1, Object... args) {
        return lnImpl.w(s1,args);
    }

    public static int w( Throwable throwable, Object s1, Object... args) {
        return lnImpl.w(throwable,s1,args);
    }

    public static int e(Throwable t) {
        return lnImpl.e(t);
    }

    public static int e( Object s1, Object... args) {
        return lnImpl.e(s1,args);
    }

    public static int e( Throwable throwable, Object s1, Object... args) {
        return lnImpl.e(throwable,s1,args);
    }

    public static boolean isDebugEnabled() {
        return lnImpl.isDebugEnabled();
    }

    public static boolean isVerboseEnabled() {
        return lnImpl.isVerboseEnabled();
    }

    public static int getLoggingLevel() {
        return lnImpl.getLoggingLevel();
    }

    public static void setLoggingLevel(int level) {
        lnImpl.setLoggingLevel(level);
    }

    public static String logLevelToString( int loglevel ) {
        return lnImpl.logLevelToString(loglevel);
    }

}
