package com.chenenyu.imgoptimizer.optimizer.impl

import com.chenenyu.imgoptimizer.optimizer.Optimizer
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
        files.each { file ->
            def pngquant = PngquantUtil.getPngquantFilePath(project)
            Process process = new ProcessBuilder(pngquant, "--force", "--skip-if-larger",
                    "--speed 1", "--ext ${suffix}", file.absolutePath).start();
            process.waitFor()
        }
    }
}