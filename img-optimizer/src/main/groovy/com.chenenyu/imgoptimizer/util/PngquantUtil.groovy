package com.chenenyu.imgoptimizer.util

import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.Project;

/**
 * @Author: chenenyu
 * @Created: 16/6/27 17:44.
 */
class PngquantUtil {

    static def copyPngquant2BuildFolder(Project project) {
        def pngquantDir = getPngquantDirectory(project)
        if (!pngquantDir.exists()) {
            pngquantDir.mkdirs()
        }
        def pngFile = new File(getPngquantFilePath(project))
        if (!pngFile.exists()) {
            new FileOutputStream(pngFile).withStream {
                def inputStream = PngquantUtil.class.getResourceAsStream("/pngquant/${getFilename()}")
                it.write(inputStream.getBytes())
            }
        }
        pngFile.setExecutable(true, false)
    }

    /**
     * .../build/pngquant
     * @return String
     */
    private static def getPngquantDirectoryPath(Project project) {
        return project.buildDir.absolutePath + File.separator + "pngquant"
    }

    /**
     * .../build/pngquant
     * @return File (Directory)
     */
    private static def getPngquantDirectory(Project project) {
        return new File(getPngquantDirectoryPath(project))
    }

    /**
     * .../build/pngquant/pngquant(.exe)
     * @return String
     */
    static def getPngquantFilePath(Project project) {
        return getPngquantDirectoryPath(project) + File.separator + getFilename()
    }

    private static def getFilename() {
        return Os.isFamily(Os.FAMILY_WINDOWS) ? "pngquant.exe" : "pngquant"
    }

}