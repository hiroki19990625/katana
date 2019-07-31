package jp.katana.server.utils

import java.util.*

class GitCommitInfo {
    companion object {
        private val bundle = ResourceBundle.getBundle("git")

        val branch: String = bundle.getString("git.branch")
        val buildTime: String = bundle.getString("git.build.time")
        val buildUser: String = bundle.getString("git.build.user.name")
        val buildVersion: String = bundle.getString("git.build.version")
        val commitId: String = bundle.getString("git.commit.id")
        val commitFullMessage: String = bundle.getString("git.commit.message.full")
        val commitShortMessage: String = bundle.getString("git.commit.message.short")
        val commitTime: String = bundle.getString("git.commit.time")
        val commitUser: String = bundle.getString("git.commit.user.name")
    }
}