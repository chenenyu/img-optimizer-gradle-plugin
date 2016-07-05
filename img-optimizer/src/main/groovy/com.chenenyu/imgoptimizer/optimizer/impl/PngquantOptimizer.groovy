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
    void optimize(Project project, Logger log, String suffix, List<File> files) {
        PngquantUtil.copyPngquant2BuildFolder(project)
        if (suffix == null || "".equals(suffix.trim())) {
            suffix = ".png"
        } else if (!suffix.endsWith(".png")) {
            suffix += ".png"
        }

        int succeed = 0
        int skipped = 0
        int failed = 0
        long totalSaved = 0L
        def pngquant = PngquantUtil.getPngquantFilePath(project)
        files.each { file ->
            long originalSize = file.length()

            Process process = new ProcessBuilder(pngquant, "-v", "--force", "--skip-if-larger",
                    "--speed=1", "--ext=${suffix}", file.absolutePath).redirectErrorStream(true).start();
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))
            StringBuilder error = new StringBuilder()
            String line
            while (null != (line = br.readLine())) {
                error.append(line)
            }
            int exitCode = process.waitFor()

            if (exitCode == 0) {
                succeed++
                String output = file.absolutePath.substring(0, file.absolutePath.lastIndexOf(".")).concat(suffix)
                long optimizedSize = new File(output).length()
                float rate = 1.0f * (originalSize - optimizedSize) / originalSize * 100
                totalSaved += (originalSize - optimizedSize)
                log.i("Succeed! ${originalSize}B-->${optimizedSize}B, ${rate}% saved! ${file.absolutePath}")
            } else if (exitCode == 98) {
                skipped++
                log.w("Skipped! ${file.absolutePath}")
            } else {
                failed++
                log.e("Failed! ${file.absolutePath}")
                log.e("Exit: ${exitCode}. " + error.toString())
            }
        }

        log.i("Total: ${files.size()}, Succeed: ${succeed}, " +
                "Skipped: ${skipped}, Failed: ${failed}, Saved: ${totalSaved / 1024}KB")
    }

}