## Clippin

[![Build Status](https://travis-ci.org/droibit/clippin.svg?branch=develop)](https://travis-ci.org/droibit/clippin) [![Software License](https://img.shields.io/badge/license-Apache%202.0-brightgreen.svg)](https://github.com/droibit/clippin/blob/master/LICENSE) ![Jitpack.io](https://img.shields.io/github/release/droibit/clippin.svg?label=JitPack)

Easily and flexibly applies the Reveal Effect.
Reveal Effect is added animation from Android 5.0. So effect does not apply only in 5.0 and more device. However, you do not need to use the `Build.VERSION.SDK_INT`. Library switch the process depending on the API level at the time of execution.

![Capture](http://cl.ly/image/0q0N223f0k1v/capture.gif)

### Usage

It is possible to apply the Reveal Effect like ViewPropertyAnimaor.

```java
View overlayView = ...;
View centerView = ...;
Clippin.animate().target(overlayView)
                 .center(centerView) // or #center(int)
                 .duration(400) // default is 500 milliseconds
                 .delay(10) // option
                 .interpolator(new AccelerateDecelerateInterpolator())  // option
                 .show(null);   // sets a call back if necessary

                 // or

                 .hide(null);
```

It is also possible to set the following constants in the `#center(int)` method.

* `CENTER_ORIGIN`
* `CENTER_LEFT_TOP`
* `CENTER_RIGHT_TOP`
* `CENTER_LEFT_BOTTOM`
* `CENTER_RIGHT_BOTTOM`
* `CENTER_ORIGIN_BOTTOM`

## Download

Add the following code to module build.gradle.

```
repositories {
    maven { url "https://jitpack.io" }
}

dependencies {
    compile 'com.github.droibit:clippin:1.0.1'
}
```

### Dependencies

* Support Annotations v22.2.0

## License

[Apache Version 2.0](https://github.com/droibit/clippin/blob/master/LICENSE)
