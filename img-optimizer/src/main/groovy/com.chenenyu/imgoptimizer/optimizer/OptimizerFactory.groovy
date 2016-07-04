package com.chenenyu.imgoptimizer.optimizer

import com.chenenyu.imgoptimizer.optimizer.impl.PngquantOptimizer
import com.chenenyu.imgoptimizer.optimizer.impl.ZopflipngOptimizer;

/**
 * @Author: chenenyu
 * @Created: 16/7/4 15:45.
 */
class OptimizerFactory {

    private OptimizerFactory() {}

    static Optimizer getOptimizer(String type) {
        if (Constants.LOSSY == type) {
            return new PngquantOptimizer()
        } else if (Constants.LOSSLESS == type) {
            return new ZopflipngOptimizer()
        } else {
            throw new IllegalArgumentException("Unacceptable optimizer type. Please use lossy or lossless.")
        }
    }
}