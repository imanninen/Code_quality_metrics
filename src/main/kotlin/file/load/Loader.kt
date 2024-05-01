package org.analizer.file.load

import java.io.File

class Loader(
    private val workingDirectory: File,
) {
    init {
        require(workingDirectory.exists()) { "Directory $workingDirectory does not exist." }
        require(workingDirectory.isDirectory) { "Directory $workingDirectory is not a directory." }
    }

    fun loadFile(file: File): List<String> {
        require(file.exists()) { "File $file does not exist." }
        require(file.isFile) { "File $file is not a file." }
        require(file.path.endsWith(".kt")) { "File $file is not a kotlin file." }
        return file.readLines()
    }
}