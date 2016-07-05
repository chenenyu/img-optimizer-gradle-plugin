package com.chenenyu.imgoptimizer.task

import com.chenenyu.imgoptimizer.optimizer.OptimizerFactory
import com.chenenyu.imgoptimizer.util.Logger
import groovy.io.FileType
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction

import java.security.InvalidParameterException

/**
 * @Author: chenenyu
 * @Created at: 2016/6/20.
 */
class ImgOptimizerTask extends DefaultTask {

    @Input
    @Optional
    String suffix

    @Input
    @Optional
    int triggerSize

    @Input
    @Optional
    String type

    /**
     * 图片文件夹(drawable-xxx, mipmap-xxx)
     */
    List<File> imgDirs

    Logger log

    @TaskAction
    void opimize() {
        log = new Logger(project)
        log.i("Task $name begin:")
        checkTriggerSize()
        def optimizer = OptimizerFactory.getOptimizer(type)
        optimizer.optimize(project, log, suffix, checkFile())
        log.i("Task $name executed successfully.")
    }

    def checkTriggerSize() {
        if (triggerSize < 0) {
            throw new InvalidParameterException("img-optimizer: invalid triggerSize.")
        }
    }

    def checkFile() {
        List<File> files = new ArrayList<>();
        imgDirs.each { dir ->
            dir.eachFile(FileType.FILES) { file ->
                if (file.size() >= (1024 * triggerSize) && !file.name.endsWith('.9.png') &&
                        (file.name.endsWith('.png'))) {
                    files << file
                }
            }
        }
        log.i("${files.size()} images need to be optimized.")
        return files
    }
}
