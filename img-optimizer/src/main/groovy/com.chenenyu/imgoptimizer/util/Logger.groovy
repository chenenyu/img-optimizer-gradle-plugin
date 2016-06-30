package com.chenenyu.imgoptimizer.util

import org.gradle.api.Project

import java.text.SimpleDateFormat;

/**
 * @Author: chenenyu
 * @Created: 16/6/30 15:21.
 */
class Logger {

    private static final String LOG_FILE_NAME = "img_optimizer.log";
    private static final String INFO = "info:  ";
    private static final String WARN = "warn:  ";
    private static final String ERROR = "error: ";

    private static Logger instance;
    private static File file;
    private Writer writer;

    private Logger() {}

    static def getLogger(Project project) {
        if (file == null || !file.exists()) {
            file = new File(project.rootDir.absolutePath + File.separator + LOG_FILE_NAME)
        }
        if (instance == null) {
            synchronized (Logger.class) {
                if (instance == null) {
                    instance = new Logger()
                }
            }
        }
        return instance
    }

    private def write(String logLevel, String msg) {
        writer = new PrintWriter(BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file, true)), "UTF-8"), true)
        try {
            writer.write(getDateTime() + "  " + logLevel)
            writer.write(msg + "\r\n")
            writer.write("----------------------------------------")
        } catch (Exception e) {
        } finally {
            writer.close();
        }
    }

    private def getDateTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date())
    }

    def i(String msg) {
        write(INFO, msg)
    }

    def w(String msg) {
        write(WARN, msg)
    }

    def e(String msg) {
        write(ERROR, msg)
    }

}