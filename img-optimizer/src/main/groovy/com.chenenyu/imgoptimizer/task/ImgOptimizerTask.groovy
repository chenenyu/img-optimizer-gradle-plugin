package com.chenenyu.imgoptimizer.task

import com.chenenyu.imgoptimizer.optimizer.Constants
import com.chenenyu.imgoptimizer.optimizer.OptimizerFactory
import com.tinify.Tinify
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
    String optimizerType

    @Input
    @Optional
    String apiKey

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
        if (optimizerType == Constants.TINY) {
            if (apiKey == null || apiKey.trim().empty) {
                throw new IllegalArgumentException('Please config your API key(https://tinypng.com/developers) in build.gradle')
            } else {
                try {
                    Tinify.setKey(apiKey)
                    println('Validating your api key...')
                    Tinify.validate()
                    println("Your usage this month: ${Tinify.compressionCount()} / 500")
                } catch (Exception e) {
                    println('Validation of API key failed.')
                    throw new Exception(e)
                }
            }
        } // todo else condition.
        def optimizer = OptimizerFactory.getOptimizer(optimizerType)
        optimizer.optimize(project, checkFile())
        println("Task $name executed succesfully.")
    }

    def checkTriggerSize() {
        if (triggerSize < 0) {
            throw new InvalidParameterException("img-optimizer: invalid triggerSize.")
        } else if (triggerSize < 10) {
            println("Warn: The triggerSize option is ")
        }
    }

    List<File> checkFile() {
        List<File> files = new ArrayList<>();
        imgDirs.each { dir ->
            dir.eachFile(FileType.FILES) { file ->
                if (file.size() >= (1024 * triggerSize) && !file.name.endsWith('.9.png') &&
                        (file.name.endsWith('.png') || file.name.endsWith('.jpg'))) {
                    files << file
                }
            }
        }
        println("${files.size()} imgs need to be optimized.")
        return files
    }
}
