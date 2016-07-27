[![Download](https://api.bintray.com/packages/chenenyu/maven/img-optimizer/images/download.svg)](https://bintray.com/chenenyu/maven/img-optimizer/_latestVersion) [![License](https://img.shields.io/badge/License-Apache%202.0-orange.svg)](http://www.apache.org/licenses/LICENSE-2.0.html) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-img--optimizer--gradle--plugin-green.svg?style=true)](https://android-arsenal.com/details/1/3863) [![GitHub stars](https://img.shields.io/github/stars/chenenyu/img-optimizer-gradle-plugin.svg)](https://github.com/chenenyu/img-optimizer-gradle-plugin/stargazers)

[中文版](README-zh-rCN.md)

# img-optimizer-gradle-plugin

>A gradle plugin for optimizing PNGs, effctively reducing APK size. Both extreme compression and lossless compression are available.

### Available OS

Tested on `macOS`、`windows10`、`Ubuntu16.04LTS(amd64)`. If you have any questions, plz open issues.

### How to use

Add the following Gradle configuration to your build.gradle: 

```
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        ...
		classpath 'com.chenenyu:img-optimizer:latestVersion'
    }
}
```  

Then in your moudle's build.gradle:  

`apply plugin: 'img-optimizer'`  

Now you can see the tasks in task tree:  
![Task](arts/task.png)  
Double click to execute the task.

### Configuration

You can add the following options to custom the task:   

```
optimizerOptions {
    triggerSize 5
    type "lossy"
    suffix "_opt"
}
```  

1. `triggerSize` Used for filtering pictures. Picture whose size is less than this option will be ignored. Defaults to 0.
2. `type` Now supports`"lossy"` and `"lossless"`。`"lossy"` means extreme compression(recommend, default, fast, effective)，`"lossless"` means loseless compression(slow, inefficient)。
3. `suffix` The suffix of the picture which has been optimized. If `"_opt"`，the optimizer will generate a new picture `orignal_opt.png` for `original.png`. Defaults to null.

### Preview

|Original png|Extreme compression(lossy)|Loseless compression(lossless)|
|:---:|:---:|:---:|
|526K|195K(reduce 63%)|473K(reduce 10%)|
|![原图](arts/lenna.png)|![极限压缩](arts/lenna_lossy.png)|![无损压缩](arts/lenna_lossless.png)|

### Note

If there are multiple modules in your project, please add the optimizer in where you want to execute optimization since each module is independent. The optimizer will generate log file in the root directory of current module.

### Other libraries

[SuperAdapter](https://github.com/byteam/SuperAdapter): Adapter(BaseAdapter, RecyclerView.Adapter) wrapper for Android.

### License

[Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)