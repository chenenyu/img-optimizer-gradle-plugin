package com.chenenyu.imgoptimizer.extension

import com.chenenyu.imgoptimizer.optimizer.Constants

/**
 * @Author: chenenyu
 * @Created at: 16/6/20.
 */
class ImgOptimizerExtension {

    /**
     * 优化类型
     */
    String optimizer = Constants.TINY
    /**
     * https://tinypng.com/developers
     */
    String apiKey = 'W6lcOtSLZT7ZdL4CzaQASKhHF0dkL68k'

    /**
     * 触发优化的起始大小(kb)
     */
    int triggerSize = 50
}
