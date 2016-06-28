package com.chenenyu.imgoptimizer.task

import com.chenenyu.imgoptimizer.optimizer.impl.PngquantOptimizer
import groovy.io.FileType
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

import java.security.InvalidParameterException

/**
 * @Author: chenenyu
 * @Created at: 2016/6/20.
 */
class ImgOptimizerTask extends DefaultTask {

    @Input
    String suffix

    @Input
    int triggerSize

    /**
     * 图片文件夹(drawable-xxx, mipmap-xxx)
     */
    List<File> imgDirs

    @TaskAction
    void opimize() {
        println("Begin task $name:")
        checkTriggerSize()
        def optimizer = new PngquantOptimizer()
        optimizer.optimize(project, suffix, checkFile())
        println("Task $name executed succesfully.")
    }

    def checkTriggerSize() {
        if (triggerSize < 0) {
            throw new InvalidParameterException("img-optimizer: invalid triggerSize.")
        }
    }

    List<File> checkFile() {
        List<File> files = new ArrayList<>();
        imgDirs.each { dir ->
            dir.eachFile(FileType.FILES) { file ->
                if (file.size() >= (1024 * triggerSize) && !file.name.endsWith('.9.png') &&
                        (file.name.endsWith('.png'))) {
                    files << file
                }
            }
        }
        println("${files.size()} imgs need to be optimized.")
        return files
    }
}
