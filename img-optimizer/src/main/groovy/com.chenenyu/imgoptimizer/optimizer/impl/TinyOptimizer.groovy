package com.chenenyu.imgoptimizer.optimizer.impl

import com.chenenyu.imgoptimizer.optimizer.Optimizer
import com.tinify.*
import org.gradle.api.Project

import java.lang.Exception

/**
 * @Author: chenenyu
 * @Created at: 16/6/21 14:28.
 */
class TinyOptimizer implements Optimizer {

    final def TAG = 'TinyOptimizer: '

    void optimize(Project project, List<File> files) {
        if (files != null && files.size() > 0) {
            files.each { img ->
                try {
                    Source source = Tinify.fromFile(img.absolutePath)
                    source.toFile(img.absolutePath)
                    println("$img.absolutePath has been optimized.")
                } catch (AccountException e) {
                    // Verify your API key and account limit.
                    println(TAG + e.getMessage());
                } catch (ClientException e) {
                    // Check your source image and request options.
                    println(TAG + e.getMessage());
                } catch (ServerException e) {
                    // Temporary issue with the Tinify API.
                    println(TAG + e.getMessage());
                } catch (ConnectionException e) {
                    // A network connection error occurred.
                    println(TAG + e.getMessage());
                } catch (Exception e) {
                    // Something else went wrong, unrelated to the Tinify API.
                    println(TAG + e.getMessage());
                }
            }
        }
    }
}
