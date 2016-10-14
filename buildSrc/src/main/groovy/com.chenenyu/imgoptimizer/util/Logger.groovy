package com.chenenyu.imgoptimizer.util

import org.gradle.api.Project

import java.text.SimpleDateFormat

/**
 * @Author: chenenyu
 * @Created: 16/6/30 15:21.
 */
class Logger {

    private static final String LOG_FILE_NAME = "img_optimizer.log";
    private static final String INFO = "info:  ";
    private static final String WARN = "warn:  ";
    private static final String ERROR = "error: ";

    private File file;
    private Writer writer;

    Logger(Project project) {
        file = new File(project.projectDir.absolutePath + File.separator + LOG_FILE_NAME)
        new PrintWriter(file).close()
    }

    private def write(String logLevel, String msg) {
        writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file, true), "UTF-8")), true)
        try {
            writer.write(getDateTime() + "  " + logLevel)
            writer.write(msg + "\r\n")
            writer.write("----------------------------------------\r\n")
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