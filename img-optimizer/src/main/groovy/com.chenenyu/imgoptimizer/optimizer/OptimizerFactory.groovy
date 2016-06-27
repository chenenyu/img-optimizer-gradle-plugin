package com.chenenyu.imgoptimizer.optimizer

import com.chenenyu.imgoptimizer.optimizer.impl.TinyOptimizer

/**
 * @Author: chenenyu
 * @Created at: 16/6/21 18:05.
 */
class OptimizerFactory {

    static def Optimizer getOptimizer(String type) {
        if (Constants.TINY.equalsIgnoreCase(type)) {
            return new TinyOptimizer()
        }
    }
}
