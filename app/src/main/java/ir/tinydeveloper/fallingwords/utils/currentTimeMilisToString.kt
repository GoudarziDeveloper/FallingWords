package ir.tinydeveloper.fallingwords.utils

fun Long.toShowTime(): String{
    return "${this / 1000}:${(this / 10) % 100}"
}