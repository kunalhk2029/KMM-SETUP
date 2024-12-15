package com.package_v1.package_v2

import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

actual object AndroidVersion {
    actual fun isVersionAbove29(): Boolean {
        TODO("Not yet implemented")

    }

    actual fun isVersionAbove33(): Boolean {
        TODO("Not yet implemented")
    }

}


actual fun getCurrentTimeInMillis(): Long {
    return NSDate().timeIntervalSince1970.toLong() * 1000
}