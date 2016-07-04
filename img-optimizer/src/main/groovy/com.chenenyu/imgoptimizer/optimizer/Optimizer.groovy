package com.chenenyu.imgoptimizer.optimizer

import org.gradle.api.Project

/**
 * @Author: chenenyu
 * @Created at: 16/6/21 14:20.
 */
interface Optimizer {

    /**
     * @param project Project
     * @param suffix String
     * @param files List<File>
     */
    void optimize(Project project, String suffix, List<File> files)

}