package com.shopping.server.dao

data class FileUpload constructor(var contentType: String, var content: ByteArray) {
}