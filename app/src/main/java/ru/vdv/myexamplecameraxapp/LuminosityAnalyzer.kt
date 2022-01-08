package ru.vdv.myexamplecameraxapp

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import java.nio.ByteBuffer
typealias LumaListener = (luma: Double) -> Unit

class LuminosityAnalyzer(private val listener: LumaListener) : ImageAnalysis.Analyzer {
    override fun analyze(image: ImageProxy) {
        val buffer = image.planes[0].buffer
        val data = buffer.toByteArray()
        val pixels = data.map {
            it.toInt() and 0xFF
        }
        val lima = pixels.average()
        listener(lima)
        image.close()
    }

    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()    // устанавливаем буфер на ноль
        val data = ByteArray(remaining())
        get(data)   // Копируем данные в массив байт
        return data // возвращаем массив байт
    }
}