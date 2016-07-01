package com.chenenyu.imgoptimizer.optimizer.impl

import com.chenenyu.imgoptimizer.optimizer.Optimizer
import com.chenenyu.imgoptimizer.util.Logger
import com.chenenyu.imgoptimizer.util.PngquantUtil
import org.gradle.api.Project;

/**
 * @Author: chenenyu
 * @Created: 16/6/27 17:28.
 */
class PngquantOptimizer implements Optimizer {
    @Override
    void optimize(Project project, String suffix, List<File> files) {
        PngquantUtil.copyPngquant2BuildFolder(project)
        if (suffix == null || "".equals(suffix.trim())) {
            suffix = ".png"
        }
        def pngquant = PngquantUtil.getPngquantFilePath(project)
        files.each { file ->
            long originalSize = file.length()
            Process process = new ProcessBuilder(pngquant, "--force", "--skip-if-larger",
                    "--speed 1", "--ext ${suffix}", file.absolutePath).start();
            int ext = process.waitFor()
            if (ext == 0) {
                long optimizedSize = new File(file.absolutePath).length()
                float rate = 1.0f * (originalSize - optimizedSize) / originalSize * 100
                Logger.getLogger(project).i("Succeed! ${originalSize}B-->${optimizedSize}B, ${rate}% saved! ${file.absolutePath}")
            } else {
                Logger.getLogger(project).i("Failed! ${file.absolutePath}")
            }
        }
    }
}