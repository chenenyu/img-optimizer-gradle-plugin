package com.chenenyu.imgoptimizer.util

import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.Project;

/**
 * @Author: chenenyu
 * @Created: 16/6/27 17:44.
 */
class ZopflipngUtil {

    private static final def name = "zopflipng";

    static def copyZopflipng2BuildFolder(Project project) {
        def pngquantDir = getZopflipngDirectory(project)
        if (!pngquantDir.exists()) {
            pngquantDir.mkdirs()
        }
        def pngFile = new File(getZopflipngFilePath(project))
        if (!pngFile.exists()) {
            new FileOutputStream(pngFile).withStream {
                def inputStream = ZopflipngUtil.class.getResourceAsStream("/$name/${getFilename()}")
                it.write(inputStream.getBytes())
            }
        }
        pngFile.setExecutable(true, false)
    }

    /**
     * .../build/zopflipng
     * @return String
     */
    private static def getZopflipngDirectoryPath(Project project) {
        return project.buildDir.absolutePath + File.separator + "$name"
    }

    /**
     * .../build/zopflipng
     * @return File (Directory)
     */
    private static def getZopflipngDirectory(Project project) {
        return new File(getZopflipngDirectoryPath(project))
    }

    /**
     * .../build/zopflipng/zopflipng(.exe)
     * @return String
     */
    static def getZopflipngFilePath(Project project) {
        return getZopflipngDirectoryPath(project) + File.separator + getFilename()
    }

    static def getFilename() {
        if (Os.isFamily(Os.FAMILY_WINDOWS)) {
            return "${name}.exe"
        } else if (Os.isFamily(Os.FAMILY_MAC)) {
            return "${name}-mac"
        } else {
            return "$name"
        }
    }

}