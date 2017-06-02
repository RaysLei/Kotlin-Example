package com.rays.kotlinexample.util

import android.content.Context
import android.os.Environment
import android.text.TextUtils
import android.util.Log

import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

/**
 * Created by _SOLID
 * Date:2016/4/20
 * Time:15:01
 */
object FileUtils {

    private val TAG = "FileUtils"
    private val FILE_WRITING_ENCODING = "UTF-8"
    private val FILE_READING_ENCODING = "UTF-8"

    @Throws(Exception::class)
    fun readFile(_sFileName: String, sEncoding: String?): String {
        var _sEncoding = sEncoding
        var buffContent: StringBuffer? = null

        var fis: FileInputStream? = null
        var buffReader: BufferedReader? = null
        if (_sEncoding == null || "" == _sEncoding) {
            _sEncoding = FILE_READING_ENCODING
        }

        try {
            fis = FileInputStream(_sFileName)
            buffReader = BufferedReader(InputStreamReader(fis,
                    _sEncoding))
            var zFirstLine = "UTF-8".equals(_sEncoding, ignoreCase = true)
            var sLine: String?
            while (true) {
                sLine = buffReader.readLine()
                if (sLine == null) break

                if (buffContent == null) {
                    buffContent = StringBuffer()
                } else {
                    buffContent.append("\n")
                }
                if (zFirstLine) {
                    sLine = removeBomHeaderIfExists(sLine)
                    zFirstLine = false
                }
                buffContent.append(sLine)
            }
            return if (buffContent == null) "" else buffContent.toString()
        } catch (ex: FileNotFoundException) {
            throw Exception("要读取的文件没有找到!", ex)
        } catch (ex: IOException) {
            throw Exception("读取文件时错误!", ex)
        } finally {
            // 增加异常时资源的释放
            try {
                if (buffReader != null)
                    buffReader.close()
                if (fis != null)
                    fis.close()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        }
    }

    @Throws(Exception::class)
    fun writeFile(path: String, content: String, encode: String, isOverride: Boolean): File {
        var encoding = encode
        if (TextUtils.isEmpty(encoding)) {
            encoding = FILE_WRITING_ENCODING
        }
        val bais = ByteArrayInputStream(content.toByteArray(charset(encoding)))
        return writeFile(bais, path, isOverride)
    }

    @Throws(Exception::class)
    fun writeFile(inputStream: InputStream, p: String, isOverride: Boolean): File {
        var path = p
        val sPath = extractFilePath(path)
        if (!pathExists(sPath)) {
            makeDir(sPath, true)
        }

        if (!isOverride && fileExists(path)) {
            if (path.contains(".")) {
                val suffix = path.substring(path.lastIndexOf("."))
                val pre = path.substring(0, path.lastIndexOf("."))
                path = pre + "_" + System.currentTimeMillis() + suffix
            } else {
                path = path + "_" + System.currentTimeMillis()
            }
        }

        var os: FileOutputStream? = null

        try {
            val file = File(path)
            os = FileOutputStream(file)
            val bytes = ByteArray(1024)

            var byteCount: Int
            while (true) {
                byteCount = inputStream.read(bytes)
                if (byteCount == -1) break

                os.write(bytes, 0, byteCount)
            }
            os.flush()

            return file
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("写文件错误", e)
        } finally {
            try {
                if (os != null)
                    os.close()
                inputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    /**
     * 移除字符串中的BOM前缀

     * @param _sLine 需要处理的字符串
     * *
     * @return 移除BOM后的字符串.
     */
    private fun removeBomHeaderIfExists(_sLine: String?): String? {
        if (_sLine == null) {
            return null
        }
        var line: String = _sLine
        if (line.isNotEmpty()) {
            var ch = line[0]
            // 使用while是因为用一些工具看到过某些文件前几个字节都是0xfffe.
            // 0xfeff,0xfffe是字节序的不同处理.JVM中,一般是0xfeff
            while (ch.toInt() == 0xfeff || ch.toInt() == 0xfffe) {
                line = line.substring(1)
                if (line.isEmpty()) {
                    break
                }
                ch = line[0]
            }
        }
        return line
    }

    /**
     * 从文件的完整路径名（路径+文件名）中提取 路径（包括：Drive+Directroy )

     * @param _sFilePathName
     * *
     * @return
     */
    fun extractFilePath(_sFilePathName: String): String {
        var nPos = _sFilePathName.lastIndexOf('/')
        if (nPos < 0) {
            nPos = _sFilePathName.lastIndexOf('\\')
        }

        return if (nPos >= 0) _sFilePathName.substring(0, nPos + 1) else ""
    }

    /**
     * 检查指定文件的路径是否存在

     * @param _sPathFileName 文件名称(含路径）
     * *
     * @return 若存在，则返回true；否则，返回false
     */
    fun pathExists(_sPathFileName: String): Boolean {
        val sPath = extractFilePath(_sPathFileName)
        return fileExists(sPath)
    }

    fun fileExists(_sPathFileName: String): Boolean {
        val file = File(_sPathFileName)
        return file.exists()
    }

    /**
     * 创建目录

     * @param _sDir             目录名称
     * *
     * @param _bCreateParentDir 如果父目录不存在，是否创建父目录
     * *
     * @return
     */
    fun makeDir(_sDir: String, _bCreateParentDir: Boolean): Boolean {
        var zResult: Boolean
        val file = File(_sDir)
        if (_bCreateParentDir)
            zResult = file.mkdirs() // 如果父目录不存在，则创建所有必需的父目录
        else
            zResult = file.mkdir() // 如果父目录不存在，不做处理
        if (!zResult)
            zResult = file.exists()
        return zResult
    }


    fun moveRawToDir(context: Context, rawName: String, dir: String) {
        try {
            writeFile(context.assets.open(rawName), dir, true)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, e.message)
        }

    }

    /**
     * 得到手机的缓存目录

     * @param context
     * *
     * @return
     */
    fun getCacheDir(context: Context): File {
        Log.i("getCacheDir", "cache sdcard state: " + Environment.getExternalStorageState())
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            val cacheDir = context.externalCacheDir
            if (cacheDir != null && (cacheDir.exists() || cacheDir.mkdirs())) {
                Log.i("getCacheDir", "cache dir: " + cacheDir.absolutePath)
                return cacheDir
            }
        }

        val cacheDir = context.cacheDir
        Log.i("getCacheDir", "cache dir: " + cacheDir.absolutePath)

        return cacheDir
    }

    /**
     * 得到皮肤目录

     * @param context
     * *
     * @return
     */
    fun getSkinDir(context: Context): File {
        val skinDir = File(getCacheDir(context), "skin")
        if (skinDir.exists()) {
            skinDir.mkdirs()
        }
        return skinDir
    }

    fun getSkinDirPath(context: Context): String {
        return getSkinDir(context).absolutePath
    }

    fun getSaveImagePath(context: Context): String {

        var path = getCacheDir(context).absolutePath
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            path = Environment.getExternalStorageDirectory().absolutePath + File.separator + Environment.DIRECTORY_DCIM
        } else {
            path = path + File.separator + "Pictures"
        }
        val file = File(path)
        if (!file.exists()) {
            file.mkdir()
        }
        return path
    }

    fun generateFileNameByTime(): String {
        return System.currentTimeMillis().toString() + ""
    }

    fun getFileName(path: String): String {
        val index = path.lastIndexOf('/')
        return path.substring(index + 1)
    }


}
