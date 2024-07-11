package lacar.junilu

import java.security.MessageDigest

class Day04(private val secretKey: String) : Solution<Int>() {
    override fun part1(): Int {
        return (1..Int.MAX_VALUE).first { secretKey.md5(it).startsWith("00000") }
    }

    override fun part2(): Int {
        return (1..Int.MAX_VALUE).first { secretKey.md5(it).startsWith("000000") }
    }
}

@OptIn(ExperimentalStdlibApi::class)
fun String.md5(salt: Int): String {
    val md = MessageDigest.getInstance("MD5")
    val digest = md.digest((this + salt).toByteArray())
    return digest.toHexString()
}