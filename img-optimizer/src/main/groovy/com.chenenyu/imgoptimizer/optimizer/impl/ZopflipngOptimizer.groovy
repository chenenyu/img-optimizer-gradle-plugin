package com.chenenyu.imgoptimizer.optimizer.impl

import com.chenenyu.imgoptimizer.optimizer.Optimizer
import com.chenenyu.imgoptimizer.util.Logger
import com.chenenyu.imgoptimizer.util.ZopflipngUtil
import org.gradle.api.Project

/**
 * @Author: chenenyu
 * @Created: 16/7/4 14:43.
 */
class ZopflipngOptimizer implements Optimizer {

    @Override
    void optimize(Project project, String suffix, List<File> files) {
        ZopflipngUtil.copyZopflipng2BuildFolder(project)
        if (suffix == null || "".equals(suffix.trim())) {
            suffix = ".png"
            Logger.getLogger(project).i("if")
        } else if (!suffix.endsWith(".png")) {
            suffix += ".png"
        }

        int succeed = 0
        int skipped = 0
        int failed = 0
        long totalSaved = 0L
        def zopflipng = ZopflipngUtil.getZopflipngFilePath(project)
        files.each { file ->
            long originalSize = file.length()

            String output = file.absolutePath.substring(0, file.absolutePath.lastIndexOf(".")).concat(suffix)
            Process process = new ProcessBuilder(zopflipng, "-y", "-m", file.absolutePath, output).
                    redirectErrorStream(true).start();
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))
            StringBuilder error = new StringBuilder()
            String line
            while (null != (line = br.readLine())) {
                error.append(line)
            }
            int exitCode = process.waitFor()

            if (exitCode == 0) {
                succeed++
                long optimizedSize = new File(file.absolutePath).length()
                float rate = 1.0f * (originalSize - optimizedSize) / originalSize * 100
                totalSaved += (originalSize - optimizedSize)
                Logger.getLogger(project).i("Succeed! ${originalSize}B-->${optimizedSize}B, ${rate}% saved! ${file.absolutePath}")
            } else {
                failed++
                Logger.getLogger(project).e("Failed! ${file.absolutePath}")
                Logger.getLogger(project).e("Exit: ${exitCode}. " + error.toString())
            }
        }

        // Statistics
        Logger.getLogger(project).i("Total: ${files.size()}, Succeed: ${succeed}, " +
                "Skipped: ${skipped}, Failed: ${failed}, Saved: ${totalSaved / 1024}KB")
    }

}