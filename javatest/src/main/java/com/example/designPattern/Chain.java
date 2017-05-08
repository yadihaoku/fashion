package com.example.designPattern;

/**
 * Created by YanYadi on 2017/5/5.
 * 责任链模式
 */
public class Chain {

    static AbstractLogger getLogChain() {
        DBLog dbLog = new DBLog(AbstractLogger.ERROR, null);
        FileLog fileLog = new FileLog(AbstractLogger.INFO, dbLog);
        ConsoleLog log = new ConsoleLog(AbstractLogger.DEBUG, fileLog);
        return log;
    }

    public static void main(String[] args) {
        AbstractLogger logger = getLogChain();
        logger.log(AbstractLogger.ERROR, " system error");
        logger.log(AbstractLogger.INFO, " app info asfasdfs");
        logger.log(AbstractLogger.DEBUG, " debug debug");
    }

    static abstract class AbstractLogger {
        public static final int ERROR = 3;
        public static final int INFO = 2;
        public static final int DEBUG = 1;
        private int level;
        private AbstractLogger mNextLogger;

        public AbstractLogger(int l, AbstractLogger logger) {
            level = l;
            mNextLogger = logger;
        }

        public void log(int level, String msg) {
            if (level >= this.level) {
                write(msg);
            }
            if (mNextLogger != null)
                mNextLogger.log(level, msg);
        }

        protected abstract void write(String msg);

    }

    static class ConsoleLog extends AbstractLogger {

        public ConsoleLog(int l, AbstractLogger logger) {
            super(l, logger);
        }

        @Override protected void write(String msg) {
            System.out.println("Console log : " + msg);
        }
    }

    static class FileLog extends AbstractLogger {

        public FileLog(int l, AbstractLogger logger) {
            super(l, logger);
        }

        @Override protected void write(String msg) {
            System.out.println("File log : " + msg);
        }
    }

    static class DBLog extends AbstractLogger {

        public DBLog(int l, AbstractLogger logger) {
            super(l, logger);
        }

        @Override protected void write(String msg) {
            System.out.println("Database log : " + msg);
        }
    }
}
