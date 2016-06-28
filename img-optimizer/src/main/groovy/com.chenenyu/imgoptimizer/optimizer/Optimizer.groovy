package com.chenenyu.imgoptimizer.optimizer

import org.gradle.api.Project

/**
 * @Author: chenenyu
 * @Created at: 16/6/21 14:20.
 */
interface Optimizer {

    void optimize(Project project, String suffix, List<File> files)

}