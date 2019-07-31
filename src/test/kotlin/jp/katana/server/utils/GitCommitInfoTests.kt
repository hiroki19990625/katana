package jp.katana.server.utils

import org.junit.jupiter.api.Test

class GitCommitInfoTests {
    @Test
    fun tests() {
        println(GitCommitInfo.commitFullMessage)
        println(GitCommitInfo.commitTime)
        println(GitCommitInfo.commitUser)
    }
}